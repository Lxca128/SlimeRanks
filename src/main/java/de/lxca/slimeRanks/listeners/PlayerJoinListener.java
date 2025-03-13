package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.objects.Message;
import de.lxca.slimeRanks.objects.Rank;
import de.lxca.slimeRanks.objects.RankManager;
import de.lxca.slimeRanks.objects.UpdateChecker;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

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
            player.setPlayerListOrder(rank.getPriority());
        }

        if (rank.nameTagIsActive() && player.getGameMode() != GameMode.SPECTATOR) {
            RankManager.getInstance().addPlayerNameTag(player);
        }

        if (player.hasPermission("slimeranks.admin")) {
            UpdateChecker updateChecker = new UpdateChecker();

            if (updateChecker.newUpdateAvailable()) {
                HashMap<String, String> replacements = new HashMap<>();
                replacements.put("current_version", Main.getInstance().getPluginMeta().getVersion());
                replacements.put("newest_version", updateChecker.getLatestVersion());

                new Message(
                        player,
                        true,
                        "Chat.Action.UpdateAvailable",
                        replacements
                );
            }
        }
    }
}
