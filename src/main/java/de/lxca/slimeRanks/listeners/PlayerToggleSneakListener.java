package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.PlayerNameTag;
import de.lxca.slimeRanks.objects.Rank;
import de.lxca.slimeRanks.objects.RankManager;
import de.lxca.slimeRanks.objects.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerToggleSneakListener implements Listener {

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();
        Rank rank = RankManager.getInstance().getPlayerRank(player);

        if (event.isSneaking()) {
            if (rank.hideNameTagOnSneak()) {
                if (PlayerNameTag.hasNameTag(player)) {
                    PlayerNameTag.getPlayerNameTag(player).hideForAll();
                }
                TeamManager.getInstance().hidePlayerNameTag(player);
            } else {
                if (PlayerNameTag.hasNameTag(player)) {
                    PlayerNameTag.getPlayerNameTag(player).setSeeThrough(false);
                }
            }
        } else {
            if (PlayerNameTag.shouldDisplayPlayerNameTag(player, true)) {
                if (rank.hideNameTagOnSneak()) {
                    PlayerNameTag.getPlayerNameTag(player).showForAllPermittedPlayers();
                    TeamManager.getInstance().showPlayerNameTag(player);
                } else {
                    PlayerNameTag.getPlayerNameTag(player).setSeeThrough(true);
                }
            }
        }
    }
}
