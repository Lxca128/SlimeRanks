package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.Main;
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

        if (rank == null) {
            return;
        }

        if (event.isSneaking()) {
            if (rank.hideNameTagOnSneak()) {
                if (PlayerNameTag.hasNameTag(player)) {
                    PlayerNameTag playerNameTag = PlayerNameTag.getPlayerNameTag(player);

                    if (playerNameTag != null) {
                        playerNameTag.hideForAll();
                    }
                }
                if (!Main.isFolia()) {
                    TeamManager.getInstance().hidePlayerNameTag(player);
                }
            } else {
                if (PlayerNameTag.hasNameTag(player)) {
                    PlayerNameTag playerNameTag = PlayerNameTag.getPlayerNameTag(player);

                    if (playerNameTag != null) {
                        playerNameTag.setSeeThrough(false);
                    }
                }
            }
        } else {
            if (PlayerNameTag.shouldDisplayPlayerNameTag(player, true, true)) {
                PlayerNameTag playerNameTag = PlayerNameTag.getPlayerNameTag(player);

                if (playerNameTag == null) {
                    return;
                }

                if (rank.hideNameTagOnSneak()) {
                    playerNameTag.showForAllPermittedPlayers();
                    if (!Main.isFolia()) {
                        TeamManager.getInstance().showPlayerNameTag(player);
                    }
                } else {
                    playerNameTag.setSeeThrough(true);
                }
            }
        }
    }
}
