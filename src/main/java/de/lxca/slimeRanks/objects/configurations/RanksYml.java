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
}
