package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.PlayerNameTag;
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
            if (PlayerNameTag.hasNameTag(player)) {
                PlayerNameTag playerNameTag = PlayerNameTag.getPlayerNameTag(player);

                if (playerNameTag != null) {
                    playerNameTag.remove();
                }
            }
        } else if (player.getGameMode() == GameMode.SPECTATOR && PlayerNameTag.shouldDisplayPlayerNameTag(player, false, true)) {
            PlayerNameTag.getPlayerNameTag(player);
        }
    }
}
