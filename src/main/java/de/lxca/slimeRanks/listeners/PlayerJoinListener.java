package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.objects.PlayerNameTag;
import de.lxca.slimeRanks.objects.Rank;
import de.lxca.slimeRanks.objects.RankManager;
import de.lxca.slimeRanks.objects.UpdateChecker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        RankManager rankManager = RankManager.getInstance();
        Rank rank = rankManager.getPlayerRank(player);

        if (rank != null) {
            if (rank.tabIsActive()) {
                player.playerListName(rank.getTabFormat(player));
                player.setPlayerListOrder(rank.getTabPriority());
            }

            if (!Main.isFolia() && PlayerNameTag.shouldDisplayPlayerNameTag(player, true)) {
                PlayerNameTag.getPlayerNameTag(player);
            }
        }

        if (player.hasPermission("slimeranks.admin")) {
            UpdateChecker updateChecker = new UpdateChecker();

            updateChecker.notifyUpdateAvailable(player);
        }
    }
}
