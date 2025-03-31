package de.lxca.slimeRanks.objects;

import com.google.gson.*;
import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.enums.UpdateChannel;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;

public class UpdateChecker {

    private UpdateChannel updateChannel;

    public UpdateChecker() {
        setUpdateChannel();
    }

    public boolean newUpdateAvailable() {
        JsonObject latestVersion = getLatestVersion();

        if (latestVersion == null) {
            return false;
        }

        String latestVersionNumber = latestVersion.get("version_number").getAsString();

        return !latestVersionNumber.equals(Main.getInstance().getPluginMeta().getVersion());
    }

    public void notifyUpdateAvailable(CommandSender commandSender) {
        if (newUpdateAvailable()) {
            JsonObject latestVersion = getLatestVersion();

            if (latestVersion == null) {
                return;
            }

            String serveVersion = Main.getInstance().getServer().getMinecraftVersion();
            boolean newestVersionSupportsServerVersion = false;

            for (JsonElement element : latestVersion.getAsJsonArray("game_versions")) {
                if (element.getAsString().equals(serveVersion)) {
                    newestVersionSupportsServerVersion = true;
                    break;
                }
            }

            boolean shouldNotify = newestVersionSupportsServerVersion ?
                    Main.getConfigYml().getYmlConfig().getBoolean("NotifyForPluginUpdates") :
                    Main.getConfigYml().getYmlConfig().getBoolean("NotifyForVersionUpdates");

            if (!shouldNotify) {
                return;
            }

            String currentPluginversion = Main.getInstance().getPluginMeta().getVersion();
            String latestPluginVersion = latestVersion.get("version_number").getAsString();

            if (commandSender instanceof Player) {
                HashMap<String, String> replacements = new HashMap<>();
                replacements.put("current_version", currentPluginversion);
                replacements.put("newest_version", latestPluginVersion);

                String messageKey = newestVersionSupportsServerVersion ? "Chat.Action.UpdateAvailable" : "Chat.Action.VersionUpdateAvailable";

                new Message(
                        commandSender,
                        true,
                        messageKey,
                        replacements
                );
            } else {
                Main.getLogger("SlimeRanks").info("A new Version of SlimeRanks is available!");
                Main.getLogger("SlimeRanks").info("SlimeRanks {} -> {} released on Modrinth: https://modrinth.com/plugin/slimeranks", currentPluginversion, latestPluginVersion);
                if (!newestVersionSupportsServerVersion) {
                    Main.getLogger("SlimeRanks").warn("The latest version of SlimeRanks no longer supports this Minecraft version, so support is only limitedly available.");
                }
            }
        }
    }

    public @Nullable JsonObject getLatestVersion() {
        try {
            URI uri = new URI("https://api.modrinth.com/v2/project/GIyZWUYg/version");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject version = element.getAsJsonObject();
                String versionType = version.get("version_type").getAsString();

                boolean isValidVersion = switch (updateChannel) {
                    case RELEASE -> versionType.equalsIgnoreCase("release");
                    case BETA -> versionType.equalsIgnoreCase("release") || versionType.equalsIgnoreCase("beta");
                };

                if (isValidVersion) {
                    return version;
                }
            }
        } catch (Exception exception) {
            Main.getLogger(this.getClass()).warn("Couldn't check for the latest version of SlimeRanks on Modrinth. Please ensure your server is connected to the internet.");
        }

        return null;
    }

    private void setUpdateChannel() {
        YamlConfiguration config = Main.getConfigYml().getYmlConfig();

        if (!config.contains("UpdateChannel")) {
            Main.getLogger(UpdateChannel.class).warn("UpdateChannel not found in config.yml. Using the RELEASE update channel.");
        }

        UpdateChannel updateChannel;

        try {
            updateChannel = UpdateChannel.valueOf(config.getString("UpdateChannel"));
        } catch (IllegalArgumentException e) {
            Main.getLogger(UpdateChannel.class).warn("Invalid UpdateChannel found in config.yml. Using the RELEASE update channel.");
            updateChannel = UpdateChannel.RELEASE;
        }

        this.updateChannel = updateChannel;
    }
}
