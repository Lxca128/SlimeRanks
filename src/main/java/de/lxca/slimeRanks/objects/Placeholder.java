package de.lxca.slimeRanks.objects;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholder extends PlaceholderExpansion {

    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public @NotNull String getIdentifier() {
        return "slimeranks";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Lxca128";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return null;
        }

        Rank rank = RankManager.getInstance().getPlayerRank(player);
        if (rank == null) {
            return "";
        }

        if (identifier.equalsIgnoreCase("rank_identifier")) {
            return rank.getIdentifier();
        } else if (identifier.equalsIgnoreCase("rank_tab_raw_format")) {
            return rank.getRawTabFormat();
        } else if (identifier.equalsIgnoreCase("rank_tab_format")) {
            return miniMessage.serialize(rank.getTabFormat(player));
        } else if (identifier.equalsIgnoreCase("rank_chat_raw_format")) {
            return rank.getRawChatFormat();
        } else if (identifier.equalsIgnoreCase("rank_chat_format")) {
            return miniMessage.serialize(rank.getChatFormat(player, ""));
        } else if (identifier.equalsIgnoreCase("rank_name_tag_raw_format")) {
            return rank.getRawNameTagFormat();
        } else if (identifier.equalsIgnoreCase("rank_name_tag_format")) {
            return miniMessage.serialize(rank.getNameTagFormat(player));
        }

        return null;
    }
}
