package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public class Yml {

    private final String filePath;
    private final String fileName;
    private File configFile;
    private YamlConfiguration ymlConfig;

    public Yml(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
        configFile = createConfigFileIfNotExists();
        ymlConfig = loadYmlConfig();
    }

    private @Nullable File createConfigFileIfNotExists() {
        if (!pathExists() && !new File(filePath).mkdirs()) {
            Main.getLogger(this.getClass()).warn("Failed to create directory {}.", filePath);
            return null;
        }
        if (!fileExists()) {
            try {
                if (!new File(filePath + fileName).createNewFile()) {
                    Main.getLogger(this.getClass()).warn("Failed to create file {}{}.", filePath, fileName);
                    return null;
                }
            } catch (IOException e) {
                Main.getLogger(this.getClass()).error("Failed to create file {}{}!", filePath, fileName);
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
            Main.getLogger(this.getClass()).error("Failed to load file {}{}!", filePath, fileName);
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

    protected void setDefaultYmlKeys() {
        saveYmlConfig();
    }

    protected void createConfigKey(String key, Object defaultValue) {
        if (!ymlConfig.getKeys(true).contains(key)) {
            ymlConfig.set(key, defaultValue);
        }
    }

    private boolean pathExists() {
        return new File(filePath).exists();
    }

    private boolean fileExists() {
        return new File(filePath + fileName).exists();
    }
}
