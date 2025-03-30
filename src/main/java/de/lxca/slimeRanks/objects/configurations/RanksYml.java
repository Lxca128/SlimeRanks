package de.lxca.slimeRanks.objects.configurations;

public class RanksYml extends Yml {

    private static final String filePath = "plugins/SlimeRanks/";
    private static final String fileName = "ranks.yml";

    public RanksYml() {
        super(filePath, fileName);
        if (getYmlConfig().getKeys(true).isEmpty()) {
            setDefaultYmlKeys();
        }
    }

    @Override
    protected void setDefaultYmlKeys() {
        createConfigKey("ConfigVersion", 1);
        createConfigKey("Ranks.Admin.Tab.Active", true);
        createConfigKey("Ranks.Admin.Tab.Format", "<color:#e63946>Admin</color> <dark_gray>|</dark_gray> <gray>{player}</gray>");
        createConfigKey("Ranks.Admin.Tab.Priority", 2);
        createConfigKey("Ranks.Admin.Chat.Active", true);
        createConfigKey("Ranks.Admin.Chat.Format", "<color:#e63946>Admin</color> <dark_gray>|</dark_gray> <gray>{player}</gray> <dark_gray>»</dark_gray> <color:#ededed>{message}</color>");
        createConfigKey("Ranks.Admin.Chat.ColoredMessages", true);
        createConfigKey("Ranks.Admin.NameTag.Active", true);
        createConfigKey("Ranks.Admin.NameTag.Format", "<color:#e63946>Admin</color> <dark_gray>|</dark_gray> <gray>{player}</gray>");
        createConfigKey("Ranks.Admin.NameTag.HideOnSneak", false);
        createConfigKey("Ranks.Admin.RankPriority", 1);
        createConfigKey("Ranks.Admin.Permission", "slimeranks.rank.admin");
        createConfigKey("Ranks.Player.Tab.Active", true);
        createConfigKey("Ranks.Player.Tab.Format", "<color:#b0b0b0>Player</color> <dark_gray>|</dark_gray> <gray>{player}</gray>");
        createConfigKey("Ranks.Player.Tab.Priority", 1);
        createConfigKey("Ranks.Player.Chat.Active", true);
        createConfigKey("Ranks.Player.Chat.Format", "<color:#b0b0b0>Player</color> <dark_gray>|</dark_gray> <gray>{player}</gray> <dark_gray>»</dark_gray> <color:#ededed>{message}</color>");
        createConfigKey("Ranks.Player.Chat.ColoredMessages", false);
        createConfigKey("Ranks.Player.NameTag.Active", true);
        createConfigKey("Ranks.Player.NameTag.Format", "<color:#b0b0b0>Player</color> <dark_gray>|</dark_gray> <gray>{player}</gray>");
        createConfigKey("Ranks.Player.NameTag.HideOnSneak", false);
        createConfigKey("Ranks.Player.RankPriority", 0);
        saveYmlConfig();
    }
}
