package me.Daniel.FlyCommand;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Checks the plugin.yml that ends up in the jar by reading it with
 * {@link PluginDescriptionFile}, the parser a server runs before it loads a
 * plugin: a manifest it rejects, a wrong {@code main} or an unfiltered version
 * would stop the plugin loading, and nothing else in this repository would
 * notice before a server did.
 */
class PluginManifestTest {

    private InputStream manifestFile() {
        InputStream in = getClass().getClassLoader().getResourceAsStream("plugin.yml");
        assertNotNull(in, "plugin.yml is not on the classpath");
        return in;
    }

    private PluginDescriptionFile manifest() throws Exception {
        try (InputStream in = manifestFile()) {
            return new PluginDescriptionFile(in);
        }
    }

    @Test
    void mainNamesThePluginClass() throws Exception {
        Class<?> main = Class.forName(manifest().getMain());
        assertTrue(JavaPlugin.class.isAssignableFrom(main));
    }

    @Test
    void versionIsFilledInByTheBuild() throws Exception {
        String version = manifest().getVersion();
        assertFalse(version.contains("${"), "plugin.yml version was not filtered: " + version);
    }

    /** The name is also the prefix of /flycommand:fly, which the docs promise always reaches this plugin. */
    @Test
    void nameIsTheOneTheDocsUse() throws Exception {
        assertEquals("FlyCommand", manifest().getName());
    }

    @Test
    void apiVersionIsTheOneTheDocsState() throws Exception {
        assertEquals("1.13", manifest().getAPIVersion());
    }

    @Test
    void declaresTheFlyCommand() throws Exception {
        assertTrue(manifest().getCommands().containsKey("Fly"));
    }

    /**
     * PluginDescriptionFile cannot be asked for this: building a Permission
     * needs a running server, so the block is read back as plain YAML.
     */
    @Test
    void declaresTheFlyPermission() throws Exception {
        try (InputStream in = manifestFile()) {
            YamlConfiguration yml = YamlConfiguration.loadConfiguration(new InputStreamReader(in, StandardCharsets.UTF_8));
            // Bukkit splits the node name on its dot when loading, which is also how it reads it back.
            assertEquals("op", yml.getString("permissions.FlyCommand.fly.default"));
        }
    }
}
