package de.lxca.slimeRanks.objects.configurations;

public class ConfigYml extends Yml {

    private static final String filePath = "plugins/SlimeRanks/";
    private static final String fileName = "config.yml";

    public ConfigYml() {
        super(filePath, fileName);
        setDefaultYmlKeys();
    }

    @Override
    protected void setDefaultYmlKeys() {
        createConfigKey("ConfigVersion", 1);
        createConfigKey("UpdateChannel", "RELEASE");
        createConfigKey("NotifyForPluginUpdates", true);
        createConfigKey("NotifyForVersionUpdates", true);
        createConfigKey("NameUpdateInterval", 0);
        createConfigKey("ShowChatMessageInConsole", true);
        saveYmlConfig();
    }
}
