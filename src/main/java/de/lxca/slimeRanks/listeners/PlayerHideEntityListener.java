package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.PlayerNameTag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerHideEntityEvent;

public class PlayerHideEntityListener implements Listener {

    @EventHandler
    public void onPlayerHideEntity(PlayerHideEntityEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (PlayerNameTag.hasNameTag(player)) {
            PlayerNameTag.getPlayerNameTag(player).setVisibility(event.getPlayer(), false);
        }
    }
}
