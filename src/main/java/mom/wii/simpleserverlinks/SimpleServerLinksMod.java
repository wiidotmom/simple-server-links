package mom.wii.simpleserverlinks;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerConfigurationConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.packet.s2c.common.ServerLinksS2CPacket;
import net.minecraft.server.ServerLinks;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

public class SimpleServerLinksMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("simple-server-links");

    @Override
    public void onInitialize() {
        Path configPath = FabricLoader.getInstance().getConfigDir().resolve("server-links.properties");
        LinkedHashMap<String, String> links = loadConfig(configPath.toFile());

        ServerLinks serverLinks = new ServerLinks(
                links.entrySet().stream()
                        .map(entry -> ServerLinks.Entry.create(Text.translatable(entry.getKey()), URI.create(entry.getValue())))
                        .toList()
        );

        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) ->
                handler.sendPacket(new ServerLinksS2CPacket(serverLinks.getLinks()))
        );

        LOGGER.info("Loaded {} server links", links.size());
    }

    private LinkedHashMap<String, String> loadConfig(File file) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("# SimpleServerLinks configuration\n");
                writer.write("# Lines beginning with # are ignored.\n");
                writer.write("# Uncomment and edit the desired entries.\n");
				writer.write("# You can add arbitrary text as label to the buttons.\n\n");

                writer.write("#known_server_link.announcements=https://example.com/announcements\n");
                writer.write("#known_server_link.community=https://example.com/community\n");
                writer.write("#known_server_link.community_guidelines=https://example.com/guidelines\n");
                writer.write("#known_server_link.feedback=https://example.com/feedback\n");
                writer.write("#known_server_link.forums=https://example.com/forums\n");
                writer.write("#known_server_link.news=https://example.com/news\n");
                writer.write("#known_server_link.report_bug=https://example.com/bugreport\n");
                writer.write("#known_server_link.status=https://example.com/status\n");
                writer.write("#known_server_link.support=https://example.com/support\n");

                writer.write("Wiki=https://wiki.mc.jahus.net/\n");
                writer.write("known_server_link.website=https://mc.jahus.net/\n");

                writer.flush();
                LOGGER.info("Created default server-links.properties");
            } catch (IOException e) {
                LOGGER.error("Failed to create default config", e);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.lines()
                    .filter(line -> !line.isBlank() && !line.trim().startsWith("#"))
                    .forEach(line -> {
                        int eq = line.indexOf('=');
                        if (eq > 0) {
                            String key = line.substring(0, eq).trim();
                            String value = line.substring(eq + 1).trim();
                            map.put(key, value);
                        }
                    });
        } catch (IOException e) {
            LOGGER.error("Failed to load config", e);
        }

        return map;
    }
}
