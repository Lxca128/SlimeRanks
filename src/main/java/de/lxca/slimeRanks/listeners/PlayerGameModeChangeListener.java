package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.RankManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class PlayerGameModeChangeListener implements Listener {

    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();

        if (event.getNewGameMode() == GameMode.SPECTATOR) {
            RankManager.getInstance().removePlayerNameTag(player);
        } else if (player.getGameMode() == GameMode.SPECTATOR) {
            RankManager.getInstance().addPlayerNameTag(player);
        }
    }
}
