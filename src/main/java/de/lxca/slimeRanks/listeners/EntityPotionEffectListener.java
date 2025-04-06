package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.PlayerNameTag;
import de.lxca.slimeRanks.objects.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

public class EntityPotionEffectListener implements Listener {

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        if (event.getModifiedType() != PotionEffectType.INVISIBILITY || !(event.getEntity() instanceof Player player)) {
            return;
        }

        if (PlayerNameTag.shouldDisplayPlayerNameTag(player, false) && event.getNewEffect() == null) {
            PlayerNameTag.getPlayerNameTag(player);
            TeamManager.getInstance().showPlayerNameTag(player);
        } else {
            TeamManager.getInstance().hidePlayerNameTag(player);
            PlayerNameTag.getPlayerNameTag(player).remove();
        }
    }
}
