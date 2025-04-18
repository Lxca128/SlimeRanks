package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.PlayerNameTag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();

        if (PlayerNameTag.hasNameTag(player)) {
            PlayerNameTag playerNameTag = PlayerNameTag.getPlayerNameTag(player);

            if (playerNameTag != null) {
                playerNameTag.remove();
            }
        }
    }
}
