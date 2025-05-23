package de.lxca.slimeRanks.listeners;

import com.destroystokyo.paper.event.entity.EntityAddToWorldEvent;
import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.objects.PlayerNameTag;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityAddToWorldListener implements Listener {

    @EventHandler
    public void onEntityAddToWorld(EntityAddToWorldEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (PlayerNameTag.shouldDisplayPlayerNameTag(player, true, true)) {
            player.getScheduler().run(
                    Main.getInstance(),
                    scheduledTask -> {
                        PlayerNameTag playerNameTag = PlayerNameTag.getPlayerNameTag(player);
                        if (playerNameTag != null) {
                            playerNameTag.setVisibility(player, false);
                        }
                    },
                    null
            );
        }
    }
}
