package de.lxca.slimeRanks.listeners;

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

        if (rank.hideNameTagOnSneak() && event.isSneaking()) {
            TeamManager.getInstance().hidePlayerNameTag(player);
            RankManager.getInstance().hidePlayerNameTag(player);
        } else {
            RankManager.getInstance().showPlayerNameTag(player);
            TeamManager.getInstance().showPlayerNameTag(player);
        }
    }
}
