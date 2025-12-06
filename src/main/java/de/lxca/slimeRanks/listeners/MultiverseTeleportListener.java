package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.PlayerNameTag;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.mvplugins.multiverse.core.event.MVTeleportDestinationEvent;

public class MultiverseTeleportListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMultiverseTeleport(MVTeleportDestinationEvent event) {
        Entity teleportee = event.getTeleportee();

        if (!(teleportee instanceof Player player)) {
            return;
        }

        if (!PlayerNameTag.hasNameTag(player)) {
            return;
        }

        player.eject();
    }
}
