package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public class RanksYml {

    private static final String filePath = "plugins/SlimeRanks/";
    private static final String fileName = "ranks.yml";
    private static File configFile;
    private static YamlConfiguration ymlConfig;

    public RanksYml() {
        configFile = createConfigFileIfNotExists();
        ymlConfig = loadYmlConfig();

        if (ymlConfig.getKeys(true).isEmpty()) {
            setDefaultYmlKeys();
        }
    }

    private @Nullable File createConfigFileIfNotExists() {
        if (!pathExists() && !new File(filePath).mkdirs()) {
            Main.getLogger(this.getClass()).warn("Failed to create directory " + filePath + ".");
            return null;
        }
        if (!fileExists()) {
            try {
                if (!new File(filePath + fileName).createNewFile()) {
                    Main.getLogger(this.getClass()).warn("Failed to create file " + filePath + fileName + ".");
                    return null;
                }
            } catch (IOException e) {
                Main.getLogger(this.getClass()).error("Failed to create file " + filePath + fileName + "!");
                throw new RuntimeException(e);
            }
        }

        return new File(filePath + fileName);
    }

    public YamlConfiguration loadYmlConfig() {
        if (!fileExists()) {
            configFile = createConfigFileIfNotExists();
        }

        if (configFile == null) {
            Main.getLogger(this.getClass()).error("Failed to load file " + filePath + fileName + "!");
            return null;
        }

        ymlConfig = YamlConfiguration.loadConfiguration(configFile);
        return ymlConfig;
    }

    public YamlConfiguration getYmlConfig() {
        return ymlConfig;
    }

    public void saveYmlConfig() {
        try {
            ymlConfig.save(configFile);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void setDefaultYmlKeys() {
        createConfigKey("ConfigVersion", 1);
        createConfigKey("Ranks.Admin.Tab.Active", true);
        createConfigKey("Ranks.Admin.Tab.Format", "<red>Admin</red> <dark_gray>-</dark_gray> <red>{player}</red>");
        createConfigKey("Ranks.Admin.Tab.Priority", 0);
        createConfigKey("Ranks.Admin.Chat.Active", true);
        createConfigKey("Ranks.Admin.Chat.Format", "<red>Admin</red> <dark_gray>-</dark_gray> <red>{player}</red><dark_gray>:</dark_gray> <gray>{message}</gray>");
        createConfigKey("Ranks.Admin.NameTag.Active", true);
        createConfigKey("Ranks.Admin.NameTag.Format", "<red>Admin</red> <dark_gray>-</dark_gray> <red>{player}</red>");
        createConfigKey("Ranks.Admin.NameTag.HideOnSneak", true);
        createConfigKey("Ranks.Admin.Permission", "slimeranks.rank.admin");
        createConfigKey("Ranks.Player.Tab.Active", true);
        createConfigKey("Ranks.Player.Tab.Format", "<gray>Player</gray> <dark_gray>-</dark_gray> <gray>{player}</gray>");
        createConfigKey("Ranks.Player.Tab.Priority", 0);
        createConfigKey("Ranks.Player.Chat.Active", true);
        createConfigKey("Ranks.Player.Chat.Format", "<gray>Player</gray> <dark_gray>-</dark_gray> <gray>{player}</gray><dark_gray>:</dark_gray> <white>{message}</white>");
        createConfigKey("Ranks.Player.NameTag.Active", true);
        createConfigKey("Ranks.Player.NameTag.Format", "<gray>Player</gray> <dark_gray>-</dark_gray> <gray>{player}</gray>");
        createConfigKey("Ranks.Player.NameTag.HideOnSneak", true);
        saveYmlConfig();
    }

    private void createConfigKey(String key, Object defaultValue) {
        if (!ymlConfig.getKeys(true).contains(key)) {
            ymlConfig.set(key, defaultValue);
        }
    }

    private static boolean pathExists() {
        return new File(filePath).exists();
    }

    private static boolean fileExists() {
        return new File(filePath + fileName).exists();
    }
}
