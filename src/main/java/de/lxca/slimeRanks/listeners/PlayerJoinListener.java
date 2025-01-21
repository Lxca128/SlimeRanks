package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.objects.Rank;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Rank rank = Main.getRankManager().getPlayerRank(player);

        if (rank == null || !rank.tabIsActive()) {
            return;
        }

        player.playerListName(rank.getTabFormat(player));
        player.setPlayerListOrder(rank.getPriority());
    }
}
