package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.PlayerNameTag;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoadListener implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (event.isNewChunk()) {
            return;
        }

        PlayerNameTag.clearBuggyNameTags(event.getChunk());
    }
}
