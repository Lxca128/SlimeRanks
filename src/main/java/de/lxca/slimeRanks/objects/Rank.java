package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import io.github.miniplaceholders.api.MiniPlaceholders;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.configuration.ConfigurationSection;
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

        if (ranksYml == null) {
            this.identifier = null;
            return;
        }

        ConfigurationSection ranksSection = ranksYml.getConfigurationSection("Ranks");

        if (ranksSection == null) {
            this.identifier = null;
            return;
        }

        if (!ranksSection.getKeys(false).contains(identifier)) {
            this.identifier = null;
            return;
        }

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

    public boolean exists() {
        return identifier != null;
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
        RankManager.getInstance().reloadDisplays();
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
        RankManager.getInstance().reloadDisplays();
    }

    public int getTabPriority() {
        return tabPriority;
    }

    public void setTabPriority(int tabPriority) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Tab.Priority", tabPriority);
        Main.getRanksYml().saveYmlConfig();
        this.tabPriority = tabPriority;
        RankManager.getInstance().reloadDisplays();
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
        RankManager.getInstance().reloadDisplays();
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
        RankManager.getInstance().reloadDisplays();
    }

    public boolean hideNameTagOnSneak() {
        return hideNameTagOnSneak;
    }

    public void setHideNameTagOnSneak(boolean hideNameTagOnSneak) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".NameTag.HideOnSneak", hideNameTagOnSneak);
        Main.getRanksYml().saveYmlConfig();
        this.hideNameTagOnSneak = hideNameTagOnSneak;
        RankManager.getInstance().reloadDisplays();
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(@Nullable String permission) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".Permission", permission);
        Main.getRanksYml().saveYmlConfig();
        this.permission = permission;
        RankManager.getInstance().reloadDisplays();
    }

    public int getRankPriority() {
        return rankPriority;
    }

    public void setRankPriority(int rankPriority) {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier + ".RankPriority", rankPriority);
        Main.getRanksYml().saveYmlConfig();
        this.rankPriority = rankPriority;
        RankManager.getInstance().reloadDisplays();
    }

    public void delete() {
        Main.getRanksYml().getYmlConfig().set("Ranks." + identifier, null);
        Main.getRanksYml().saveYmlConfig();
        RankManager.getInstance().reloadRanks();
        RankManager.getInstance().reloadDisplays();
    }

    private @NotNull Component parseComponent(@NotNull String format, @NotNull Player player, @Nullable String message) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component deserializedFormat = null;

        if (Main.isPluginEnabled("PlaceholderAPI")) {
            format = PlaceholderAPI.setPlaceholders(player, format);
        }

        if (Main.isPluginEnabled("MiniPlaceholders")) {
            TagResolver tagResolver = MiniPlaceholders.audiencePlaceholders();
            deserializedFormat = miniMessage.deserialize(format, player, tagResolver);
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

    public static @Nullable Rank createRank(@NotNull String identifier) {
        if (!identifier.chars().allMatch(Character::isAlphabetic)) {
            return null;
        }

        YamlConfiguration ranksYml = Main.getRanksYml().getYmlConfig();
        ConfigurationSection ranksSection = ranksYml.getConfigurationSection("Ranks");

        if (ranksSection == null || ranksSection.getKeys(false).contains(identifier)) {
            return null;
        }

        String formattedIdentifier = identifier.substring(0, 1).toUpperCase() + identifier.substring(1).toLowerCase();

        ranksYml.set("Ranks." + identifier + ".Tab.Active", true);
        ranksYml.set("Ranks." + identifier + ".Tab.Format", "<color:#b0b0b0>" + formattedIdentifier + "</color> <dark_gray>|</dark_gray> <gray>{player}</gray>");
        ranksYml.set("Ranks." + identifier + ".Tab.Priority", 0);
        ranksYml.set("Ranks." + identifier + ".Chat.Active", true);
        ranksYml.set("Ranks." + identifier + ".Chat.Format", "<color:#b0b0b0>" + formattedIdentifier + "</color> <dark_gray>|</dark_gray> <gray>{player}</gray> <dark_gray>»</dark_gray> <color:#ededed>{message}</color>");
        ranksYml.set("Ranks." + identifier + ".Chat.ColoredMessages", false);
        ranksYml.set("Ranks." + identifier + ".NameTag.Active", true);
        ranksYml.set("Ranks." + identifier + ".NameTag.Format", "<color:#b0b0b0>" + formattedIdentifier + "</color> <dark_gray>|</dark_gray> <gray>{player}</gray>");
        ranksYml.set("Ranks." + identifier + ".NameTag.HideOnSneak", false);
        ranksYml.set("Ranks." + identifier + ".Permission", "slimeranks.rank." + identifier);
        ranksYml.set("Ranks." + identifier + ".RankPriority", 1);
        Main.getRanksYml().saveYmlConfig();
        RankManager.getInstance().reloadRanks();
        RankManager.getInstance().reloadDisplays();

        return new Rank(identifier);
    }
}
