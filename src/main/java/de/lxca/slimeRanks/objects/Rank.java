package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import io.github.miniplaceholders.api.MiniPlaceholders;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Rank {

    private final String identifier;
    private boolean tabActive;
    private String tabFormat;
    private int tabPriority;
    private boolean chatActive;
    private String chatFormat;
    private boolean coloredMessages;
    private boolean nameTagActive;
    private String nameTagFormat;
    private boolean hideNameTagOnSneak;
    private String permission;
    private int rankPriority;

    public Rank(String identifier) {
        YamlConfiguration ranksYml = Main.getRanksYml().getYmlConfig();

        this.identifier = identifier;
        this.tabActive = ranksYml.getBoolean("Ranks." + identifier + ".Tab.Active", false);
        this.tabFormat = ranksYml.getString("Ranks." + identifier + ".Tab.Format", null);
        this.tabPriority = ranksYml.getInt("Ranks." + identifier + ".Tab.Priority", 0);
        this.chatActive = ranksYml.getBoolean("Ranks." + identifier + ".Chat.Active", false);
        this.chatFormat = ranksYml.getString("Ranks." + identifier + ".Chat.Format", null);
        this.coloredMessages = ranksYml.getBoolean("Ranks." + identifier + ".Chat.ColoredMessages", false);
        this.nameTagActive = ranksYml.getBoolean("Ranks." + identifier + ".NameTag.Active", false);
        this.nameTagFormat = ranksYml.getString("Ranks." + identifier + ".NameTag.Format", null);
        this.hideNameTagOnSneak = ranksYml.getBoolean("Ranks." + identifier + ".NameTag.HideOnSneak", true);
        this.permission = ranksYml.getString("Ranks." + identifier + ".Permission", null);
        this.rankPriority = ranksYml.getInt("Ranks." + identifier + ".RankPriority", 0);
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

        return parseComponent(tabFormat, player, null);
    }

    public String getRawTabFormat() {
        return tabFormat;
    }

    public void setTabFormat(@NotNull String tabFormat) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Tab.Format", tabFormat);
        Main.getRanksYml().saveYmlConfig();
        this.tabFormat = tabFormat;
    }

    public int getTabPriority() {
        return tabPriority;
    }

    public void setTabPriority(int tabPriority) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Tab.Priority", tabPriority);
        Main.getRanksYml().saveYmlConfig();
        this.tabPriority = tabPriority;
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

        return parseComponent(chatFormat, player, message);
    }

    public String getRawChatFormat() {
        return chatFormat;
    }

    public void setChatFormat(@NotNull String chatFormat) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Chat.Format", chatFormat);
        Main.getRanksYml().saveYmlConfig();
        this.chatFormat = chatFormat;
    }

    public boolean getColoredMessages() {
        return coloredMessages;
    }

    public void setColoredMessages(boolean coloredMessages) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Chat.ColoredMessages", coloredMessages);
        Main.getRanksYml().saveYmlConfig();
        this.coloredMessages = coloredMessages;
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

        return parseComponent(nameTagFormat, player, null);
    }

    public String getRawNameTagFormat() {
        return nameTagFormat;
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

    public int getRankPriority() {
        return rankPriority;
    }

    public void setRankPriority(int rankPriority) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".RankPriority", rankPriority);
        Main.getRanksYml().saveYmlConfig();
        this.rankPriority = rankPriority;
    }

    public void delete() {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier, null);
        Main.getRanksYml().saveYmlConfig();
        RankManager.getInstance().reloadRanks();
    }

    private @NotNull Component parseComponent(@NotNull String format, @NotNull Player player, @Nullable String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component deserializedFormat = null;

        if (Main.isPluginEnabled("PlaceholderAPI")) {
            format = PlaceholderAPI.setPlaceholders(player, format);
        }

        if (Main.isPluginEnabled("MiniPlaceholders")) {
            TagResolver tagResolver = MiniPlaceholders.getAudienceGlobalPlaceholders(player);
            deserializedFormat = miniMessage.deserialize(format, tagResolver);
        }

        if (deserializedFormat == null) {
            format = format.replace("{player}", player.getName());

            if (message != null) {
                format = format.replace("{message}", message);
            }
        } else {
            TextReplacementConfig playerReplacementConfig = TextReplacementConfig.builder()
                    .matchLiteral("{player}")
                    .replacement(player.getName())
                    .build();
            deserializedFormat = deserializedFormat.replaceText(playerReplacementConfig);

            if (message != null) {
                TextReplacementConfig messageReplacementConfig = TextReplacementConfig.builder()
                        .matchLiteral("{message}")
                        .replacement(message)
                        .build();
                deserializedFormat = deserializedFormat.replaceText(messageReplacementConfig);
            }
        }

        return deserializedFormat != null ? deserializedFormat : miniMessage.deserialize(format);
    }
}
