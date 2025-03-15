package de.lxca.slimeRanks.objects.configurations;

public class ConfigYml extends Yml {

    private static final String filePath = "plugins/SlimeRanks/";
    private static final String fileName = "config.yml";

    public ConfigYml() {
        super(filePath, fileName);
        if (getYmlConfig().getKeys(true).isEmpty()) {
            setDefaultYmlKeys();
        }
    }

    @Override
    protected void setDefaultYmlKeys() {
        createConfigKey("ConfigVersion", 1);
        createConfigKey("UpdateChannel", "RELEASE");
        saveYmlConfig();
    }
}
