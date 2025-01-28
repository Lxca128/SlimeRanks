package de.lxca.slimeRanks.objects.configurations;

public class MessagesYml extends Yml {

    private static final String filePath = "plugins/SlimeRanks/";
    private static final String fileName = "messages.yml";

    public MessagesYml() {
        super(filePath, fileName);
        setDefaultYmlKeys();
    }

    @Override
    protected void setDefaultYmlKeys() {
        createConfigKey("Chat.Brand", "<color:#39ff14><b>SlimeRanks</b></color> <dark_gray>»</dark_gray> ");
        createConfigKey("Chat.Command.Reload", "<gray>The configurations have been reloaded</gray><dark_gray>.</dark_gray>");
        createConfigKey("Chat.Command.About", "<newline><dark_gray>-==========- <color:#39ff14><b>Slimeranks</b></color> -==========-</dark_gray><newline><newline><dark_gray>|</dark_gray> <gray>Description</gray><dark_gray>:</dark_gray> <color:#39ff14>{description}</color><newline><dark_gray>|</dark_gray> <gray>Author</gray><dark_gray>:</dark_gray> <color:#39ff14>{author}</color><newline><dark_gray>|</dark_gray> <gray>Version</gray><dark_gray>:</dark_gray> <color:#39ff14>{version}</color><newline><newline><dark_gray>-==========- <color:#39ff14><b>Slimeranks</b></color> -==========-</dark_gray><newline>");
        createConfigKey("Chat.Command.Help", "<newline><dark_gray>-==========- <color:#39ff14><b>Slimeranks</b></color> -==========-</dark_gray><newline><newline><dark_gray>|</dark_gray> <color:#39ff14>/slimeranks about</color> <dark_gray>|</dark_gray> <gray>Displays information about SlimeRanks</gray><dark_gray>.</dark_gray><newline><dark_gray>|</dark_gray> <color:#39ff14>/slimeranks help</color> <dark_gray>|</dark_gray> <gray>Shows this help message</gray><dark_gray>.</dark_gray><newline><dark_gray>|</dark_gray> <color:#39ff14>/slimeranks reload</color> <dark_gray>|</dark_gray> <gray>Reloads the configurations</gray><dark_gray>.</dark_gray><newline><newline><dark_gray>-==========- <color:#39ff14><b>Slimeranks</b></color> -==========-</dark_gray><newline>");
        createConfigKey("Chat.Command.Unknown", "<gray>The command <color:#39ff14>{command}</color> is unknown<dark_gray>.</dark_gray> Use <color:#39ff14>/slimeranks help</color> to see all available commands</gray><dark_gray>.</dark_gray>");
        saveYmlConfig();
    }
}
