package de.lxca.slimeRanks.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import de.lxca.slimeRanks.objects.Rank;
import de.lxca.slimeRanks.objects.RankManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPostRespawnListener implements Listener {

    @EventHandler
    public void onPlayerPostRespawn(PlayerPostRespawnEvent event) {
        Player player = event.getPlayer();
        Rank rank = RankManager.getInstance().getPlayerRank(player);

        if (rank == null) {
            return;
        }

        if (rank.nameTagIsActive() && player.getGameMode() != GameMode.SPECTATOR) {
            RankManager.getInstance().addPlayerNameTag(player);
        }
    }
}
