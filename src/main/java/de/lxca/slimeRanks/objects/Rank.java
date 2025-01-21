package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Rank {

    private final String identifier;
    private final boolean tabActive;
    private final String tabFormat;
    private final int priority;
    private final boolean chatActive;
    private final String chatFormat;
    private final boolean nameTagActive;
    private final String nameTagFormat;
    private final boolean hideNameTagOnSneak;
    private final String permission;

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

    public Component getTabFormat(@NotNull Player player) {
        if (tabFormat == null) {
            return null;
        }

        return MiniMessage.miniMessage().deserialize(tabFormat.replace("{player}", player.getName()));
    }

    public int getPriority() {
        return priority;
    }

    public boolean chatIsActive() {
        return chatActive;
    }

    public Component getChatFormat(@NotNull Player player, @NotNull String message) {
        if (chatFormat == null) {
            return null;
        }

        return MiniMessage.miniMessage().deserialize(chatFormat.replace("{player}", player.getName()).replace("{message}", message));
    }

    public boolean nameTagIsActive() {
        return nameTagActive;
    }

    public Component getNameTagFormat(@NotNull Player player) {
        if (nameTagFormat == null) {
            return null;
        }

        return MiniMessage.miniMessage().deserialize(nameTagFormat.replace("{player}", player.getName()));
    }

    public String getPermission() {
        return permission;
    }
}
