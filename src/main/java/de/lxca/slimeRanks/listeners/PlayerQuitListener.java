package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.RankManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        RankManager.getInstance().removePlayerNameTag(player);
    }
}
