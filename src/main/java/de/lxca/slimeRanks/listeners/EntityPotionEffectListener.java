package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.objects.RankManager;
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

        RankManager rankManager = RankManager.getInstance();

        if (rankManager.shouldDisplayPlayerNameTag(player, false) && event.getNewEffect() == null) {
            rankManager.showPlayerNameTag(player, !player.isSneaking(), false);
        } else {
            rankManager.hidePlayerNameTag(player, true);
        }
    }
}
