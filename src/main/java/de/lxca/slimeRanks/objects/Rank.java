package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Rank {

    private final String identifier;
    private boolean tabActive;
    private String tabFormat;
    private int priority;
    private boolean chatActive;
    private String chatFormat;
    private boolean nameTagActive;
    private String nameTagFormat;
    private boolean hideNameTagOnSneak;
    private String permission;

    public Rank(String identifier) {
        YamlConfiguration ranksYml = Main.getRanksYml().getYmlConfig();

        this.identifier = identifier;
        this.tabActive = ranksYml.getBoolean("Ranks." + identifier + ".Tab.Active", false);
        this.tabFormat = ranksYml.getString("Ranks." + identifier + ".Tab.Format", null);
        this.priority = ranksYml.getInt("Ranks." + identifier + ".Tab.Priority", 0);
        this.chatActive = ranksYml.getBoolean("Ranks." + identifier + ".Chat.Active", false);
        this.chatFormat = ranksYml.getString("Ranks." + identifier + ".Chat.Format", null);
        this.nameTagActive = ranksYml.getBoolean("Ranks." + identifier + ".NameTag.Active", false);
        this.nameTagFormat = ranksYml.getString("Ranks." + identifier + ".NameTag.Format", null);
        this.hideNameTagOnSneak = ranksYml.getBoolean("Ranks." + identifier + ".NameTag.HideOnSneak", true);
        this.permission = ranksYml.getString("Ranks." + identifier + ".Permission", null);
    }

    public String getIdentifier() {
        return identifier;
    }

    public boolean tabIsActive() {
        return tabActive;
    }

    public void setTabActive(boolean tabActive) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Tab.Active", tabActive);
        Main.getRanksYml().saveYmlConfig();
        this.tabActive = tabActive;
    }

    public Component getTabFormat(@NotNull Player player) {
        if (tabFormat == null) {
            return null;
        }

        return MiniMessage.miniMessage().deserialize(tabFormat.replace("{player}", player.getName()));
    }

    public void setTabFormat(@NotNull String tabFormat) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Tab.Format", tabFormat);
        Main.getRanksYml().saveYmlConfig();
        this.tabFormat = tabFormat;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Tab.Priority", priority);
        Main.getRanksYml().saveYmlConfig();
        this.priority = priority;
    }

    public boolean chatIsActive() {
        return chatActive;
    }

    public void setChatActive(boolean chatActive) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Chat.Active", chatActive);
        Main.getRanksYml().saveYmlConfig();
        this.chatActive = chatActive;
    }

    public Component getChatFormat(@NotNull Player player, @NotNull String message) {
        if (chatFormat == null) {
            return null;
        }

        return MiniMessage.miniMessage().deserialize(chatFormat.replace("{player}", player.getName()).replace("{message}", message));
    }

    public void setChatFormat(@NotNull String chatFormat) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Chat.Format", chatFormat);
        Main.getRanksYml().saveYmlConfig();
        this.chatFormat = chatFormat;
    }

    public boolean nameTagIsActive() {
        return nameTagActive;
    }

    public void setNameTagActive(boolean nameTagActive) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".NameTag.Active", nameTagActive);
        Main.getRanksYml().saveYmlConfig();
        this.nameTagActive = nameTagActive;
    }

    public Component getNameTagFormat(@NotNull Player player) {
        if (nameTagFormat == null) {
            return null;
        }

        return MiniMessage.miniMessage().deserialize(nameTagFormat.replace("{player}", player.getName()));
    }

    public void setNameTagFormat(@NotNull String nameTagFormat) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".NameTag.Format", nameTagFormat);
        Main.getRanksYml().saveYmlConfig();
        this.nameTagFormat = nameTagFormat;
    }

    public boolean hideNameTagOnSneak() {
        return hideNameTagOnSneak;
    }

    public void setHideNameTagOnSneak(boolean hideNameTagOnSneak) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".NameTag.HideOnSneak", hideNameTagOnSneak);
        Main.getRanksYml().saveYmlConfig();
        this.hideNameTagOnSneak = hideNameTagOnSneak;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(@Nullable String permission) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Permission", permission);
        Main.getRanksYml().saveYmlConfig();
        this.permission = permission;
    }
}
