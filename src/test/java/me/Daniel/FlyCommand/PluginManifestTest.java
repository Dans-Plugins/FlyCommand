package me.Daniel.FlyCommand;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Checks the plugin.yml that ends up in the jar: a wrong {@code main} or an
 * unfiltered version would stop the plugin loading, and nothing else in this
 * repository would notice before a server did.
 */
class PluginManifestTest {

    private YamlConfiguration manifest() throws Exception {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("plugin.yml")) {
            assertNotNull(in, "plugin.yml is not on the classpath");
            return YamlConfiguration.loadConfiguration(new InputStreamReader(in, StandardCharsets.UTF_8));
        }
    }

    @Test
    void mainNamesThePluginClass() throws Exception {
        Class<?> main = Class.forName(manifest().getString("main"));
        assertTrue(JavaPlugin.class.isAssignableFrom(main));
    }

    @Test
    void versionIsFilledInByTheBuild() throws Exception {
        String version = manifest().getString("version");
        assertNotNull(version);
        assertFalse(version.contains("${"), "plugin.yml version was not filtered: " + version);
    }

    @Test
    void declaresTheFlyCommandAndItsPermission() throws Exception {
        YamlConfiguration yml = manifest();
        assertTrue(yml.isConfigurationSection("commands.Fly"));
        // Bukkit splits the node name on its dot when loading, which is also how it reads it back.
        assertEquals("op", yml.getString("permissions.FlyCommand.fly.default"));
    }
}
