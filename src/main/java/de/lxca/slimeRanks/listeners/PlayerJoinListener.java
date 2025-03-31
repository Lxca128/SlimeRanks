package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.Rank;
import de.lxca.slimeRanks.objects.RankManager;
import de.lxca.slimeRanks.objects.UpdateChecker;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Rank rank = RankManager.getInstance().getPlayerRank(player);

        if (rank == null) {
            return;
        }

        if (rank.tabIsActive()) {
            player.playerListName(rank.getTabFormat(player));
            player.setPlayerListOrder(rank.getTabPriority());
        }

        if (rank.nameTagIsActive() && player.getGameMode() != GameMode.SPECTATOR) {
            RankManager.getInstance().addPlayerNameTag(player);
        }

        if (player.hasPermission("slimeranks.admin")) {
            UpdateChecker updateChecker = new UpdateChecker();

            updateChecker.notifyUpdateAvailable(player);
        }
    }
}
