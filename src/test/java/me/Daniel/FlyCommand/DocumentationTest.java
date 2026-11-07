package me.Daniel.FlyCommand;

import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Reads the Markdown docs next to the files they copy from, so that a version,
 * message or permission changed in one place fails the build until the docs
 * agree. The strings are taken from the sources, not repeated here, so these
 * tests do not need editing when a message changes.
 */
class DocumentationTest {

    private static final String SOURCE = "src/main/java/me/Daniel/FlyCommand/Main.java";

    private static String read(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    }

    /** The first group of every match, failing if there are none so a stale pattern cannot pass by finding nothing. */
    private static List<String> matches(String pattern, String text) {
        List<String> found = new ArrayList<>();
        Matcher matcher = Pattern.compile(pattern).matcher(text);
        while (matcher.find()) {
            found.add(matcher.group(1));
        }
        assertFalse(found.isEmpty(), "nothing matched " + pattern);
        return found;
    }

    @Test
    void versionListsMatchMinecraftVersionsJson() throws Exception {
        // JSON is valid YAML, so Bukkit's YAML loader reads this file too.
        List<String> supported = YamlConfiguration.loadConfiguration(new File("minecraft-versions.json"))
                .getStringList("supported");
        assertFalse(supported.isEmpty(), "minecraft-versions.json lists no versions");

        for (String doc : new String[] { "README.md", "USER_GUIDE.md" }) {
            List<String> lists = matches("currently ((?:\\*\\*[^*]+\\*\\*(?:, | and )?)+)", read(doc));
            assertEquals(1, lists.size(), doc);
            assertEquals(supported, matches("\\*\\*([^*]+)\\*\\*", lists.get(0)), doc);
        }
    }

    @Test
    void docsQuoteEveryMessageTheCommandSends() throws Exception {
        List<String> messages = matches("sendMessage\\(\"([^\"]+)\"\\)", read(SOURCE));

        for (String doc : new String[] { "COMMANDS.md", "USER_GUIDE.md", "README.md" }) {
            String text = read(doc);
            for (String message : messages) {
                assertTrue(text.contains("`" + message + "`"), doc + " does not quote: " + message);
            }
        }
    }

    @Test
    void docsNameEveryPermissionTheCommandChecks() throws Exception {
        YamlConfiguration manifest = YamlConfiguration.loadConfiguration(new File("src/main/resources/plugin.yml"));

        for (String permission : matches("hasPermission\\(\"([^\"]+)\"\\)", read(SOURCE))) {
            String row = "| `" + permission + "` | `" + manifest.getString("permissions." + permission + ".default") + "` |";
            assertTrue(read("USER_GUIDE.md").contains(row), "USER_GUIDE.md has no row: " + row);
            assertTrue(read("COMMANDS.md").contains("| `/fly` | `" + permission + "` |"), "COMMANDS.md: " + permission);
            assertTrue(read("CONFIG.md").contains("`" + permission + "`"), "CONFIG.md: " + permission);
        }
    }
}
