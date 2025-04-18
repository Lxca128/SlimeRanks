package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.PlayerNameTag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShowEntityEvent;

public class PlayerShowEntityListener implements Listener {

    @EventHandler
    public void onPlayerShowEntity(PlayerShowEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (PlayerNameTag.hasNameTag(player)) {
            PlayerNameTag playerNameTag = PlayerNameTag.getPlayerNameTag(player);

            if (playerNameTag != null) {
                playerNameTag.setVisibility(event.getPlayer(), true);
            }
        }
    }
}
