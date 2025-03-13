package de.lxca.slimeRanks.objects;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.enums.UpdateChannel;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class UpdateChecker {

    private UpdateChannel updateChannel;

    public UpdateChecker() {
        setUpdateChannel();
    }

    public boolean newUpdateAvailable() {
        String latestVersion = getLatestVersion();

        if (latestVersion == null) {
            return false;
        }

        return !latestVersion.equals(Main.getInstance().getPluginMeta().getVersion());
    }

    public @Nullable String getLatestVersion() {
        try {
            URI uri = new URI("https://api.modrinth.com/v2/project/GIyZWUYg/version");
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");

            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject version = element.getAsJsonObject();
                String latestVersionNumber = version.get("version_number").getAsString();
                String versionType = version.get("version_type").getAsString();

                boolean isValidVersion = switch (updateChannel) {
                    case RELEASE -> versionType.equalsIgnoreCase("release");
                    case BETA -> versionType.equalsIgnoreCase("release") || versionType.equalsIgnoreCase("beta");
                };

                if (isValidVersion) {
                    return latestVersionNumber;
                }
            }
        } catch (Exception exception) {
            throw new RuntimeException(exception);
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
