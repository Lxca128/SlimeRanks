package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.RankManager;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class WorldLoadListener implements Listener {

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();

        RankManager.getInstance().clearPlayerNameTags(world);
    }
}
