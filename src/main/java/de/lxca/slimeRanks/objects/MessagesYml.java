package de.lxca.slimeRanks.objects;

public class MessagesYml extends Yml{

    private static final String filePath = "plugins/SlimeRanks/";
    private static final String fileName = "messages.yml";

    public MessagesYml() {
        super(filePath, fileName);
        setDefaultYmlKeys();
    }

    @Override
    protected void setDefaultYmlKeys() {
        createConfigKey("Chat.Brand", "<b><color:#39ff14>SlimeRanks</color></b> <gray>»</gray> ");
        saveYmlConfig();
    }
}
