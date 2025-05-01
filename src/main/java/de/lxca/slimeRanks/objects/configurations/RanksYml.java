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
        createConfigKey("Ranks.admin.Tab.Active", true);
        createConfigKey("Ranks.admin.Tab.Format", "<color:#e63946>Admin</color> <dark_gray>|</dark_gray> <gray>{player}</gray>");
        createConfigKey("Ranks.admin.Tab.Priority", 2);
        createConfigKey("Ranks.admin.Chat.Active", true);
        createConfigKey("Ranks.admin.Chat.Format", "<color:#e63946>Admin</color> <dark_gray>|</dark_gray> <gray>{player}</gray> <dark_gray>»</dark_gray> <color:#ededed>{message}</color>");
        createConfigKey("Ranks.admin.Chat.ColoredMessages", true);
        createConfigKey("Ranks.admin.NameTag.Active", true);
        createConfigKey("Ranks.admin.NameTag.Format", "<color:#e63946>Admin</color> <dark_gray>|</dark_gray> <gray>{player}</gray>");
        createConfigKey("Ranks.admin.NameTag.HideOnSneak", false);
        createConfigKey("Ranks.admin.RankPriority", 1);
        createConfigKey("Ranks.admin.Permission", "slimeranks.rank.admin");
        createConfigKey("Ranks.default.Tab.Active", true);
        createConfigKey("Ranks.default.Tab.Format", "<color:#b0b0b0>Player</color> <dark_gray>|</dark_gray> <gray>{player}</gray>");
        createConfigKey("Ranks.default.Tab.Priority", 1);
        createConfigKey("Ranks.default.Chat.Active", true);
        createConfigKey("Ranks.default.Chat.Format", "<color:#b0b0b0>Player</color> <dark_gray>|</dark_gray> <gray>{player}</gray> <dark_gray>»</dark_gray> <color:#ededed>{message}</color>");
        createConfigKey("Ranks.default.Chat.ColoredMessages", false);
        createConfigKey("Ranks.default.NameTag.Active", true);
        createConfigKey("Ranks.default.NameTag.Format", "<color:#b0b0b0>Player</color> <dark_gray>|</dark_gray> <gray>{player}</gray>");
        createConfigKey("Ranks.default.NameTag.HideOnSneak", false);
        createConfigKey("Ranks.default.RankPriority", 0);
        saveYmlConfig();
    }
}
