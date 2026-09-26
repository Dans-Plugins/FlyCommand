package me.Daniel.FlyCommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Runs {@code onCommand} against stand-in senders, so the /fly behaviour the
 * docs describe is checked without a server.
 */
class FlyCommandTest {

    private final Command fly = new Command("Fly") {
        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            return false;
        }
    };

    /** A player whose flight, permission and received messages the test can see. */
    private static class FakePlayer {
        boolean allowFlight;
        final boolean hasPermission;
        final List<String> messages = new ArrayList<>();
        final Player player;

        FakePlayer(boolean hasPermission) {
            this.hasPermission = hasPermission;
            this.player = (Player) Proxy.newProxyInstance(Player.class.getClassLoader(), new Class<?>[] { Player.class },
                    (proxy, method, args) -> {
                        switch (method.getName()) {
                            case "hasPermission":
                                return "FlyCommand.fly".equals(args[0]) && this.hasPermission;
                            case "getAllowFlight":
                                return allowFlight;
                            case "setAllowFlight":
                                allowFlight = (Boolean) args[0];
                                return null;
                            case "sendMessage":
                                messages.add((String) args[0]);
                                return null;
                            default:
                                throw new UnsupportedOperationException(method.getName());
                        }
                    });
        }
    }

    /**
     * JavaPlugin's constructor refuses to run outside a server's plugin class
     * loader, and onCommand reads none of the state it would set up, so the
     * plugin is allocated without running it.
     */
    private static Main plugin() throws Exception {
        Field field = Class.forName("sun.misc.Unsafe").getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Object unsafe = field.get(null);
        return (Main) unsafe.getClass().getMethod("allocateInstance", Class.class).invoke(unsafe, Main.class);
    }

    @Test
    void flyTogglesFlightOnAndOff() throws Exception {
        Main main = plugin();
        FakePlayer p = new FakePlayer(true);

        assertTrue(main.onCommand(p.player, fly, "fly", new String[0]));
        assertTrue(p.allowFlight);
        assertTrue(main.onCommand(p.player, fly, "fly", new String[0]));
        assertFalse(p.allowFlight);
        assertEquals(Arrays.asList("Flight enabled.", "Flight disabled."), p.messages);
    }

    @Test
    void pluginPrefixedLabelAndAliasesAlsoToggleFlight() throws Exception {
        Main main = plugin();
        for (String label : new String[] { "flycommand:fly", "flight" }) {
            FakePlayer p = new FakePlayer(true);
            assertTrue(main.onCommand(p.player, fly, label, new String[0]), label);
            assertTrue(p.allowFlight, label);
            assertEquals(Arrays.asList("Flight enabled."), p.messages, label);
        }
    }

    @Test
    void playerWithoutPermissionIsRefused() throws Exception {
        FakePlayer p = new FakePlayer(false);

        assertTrue(plugin().onCommand(p.player, fly, "fly", new String[0]));
        assertFalse(p.allowFlight);
        assertEquals(Arrays.asList("Alert: Permission 'FlyCommand.fly' required."), p.messages);
    }

    @Test
    void consoleIsRefused() throws Exception {
        List<String> messages = new ArrayList<>();
        CommandSender console = (CommandSender) Proxy.newProxyInstance(CommandSender.class.getClassLoader(),
                new Class<?>[] { CommandSender.class }, (proxy, method, args) -> {
                    if (method.getName().equals("sendMessage")) {
                        messages.add((String) args[0]);
                        return null;
                    }
                    throw new UnsupportedOperationException(method.getName());
                });

        assertTrue(plugin().onCommand(console, fly, "fly", new String[0]));
        assertEquals(Arrays.asList("Alert: Can't be used by console."), messages);
    }
}
