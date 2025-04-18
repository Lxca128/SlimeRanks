package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.objects.PlayerNameTag;
import de.lxca.slimeRanks.objects.RankManager;
import de.lxca.slimeRanks.objects.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.potion.PotionEffectType;

public class EntityPotionEffectListener implements Listener {

    @EventHandler
    public void onEntityPotionEffect(EntityPotionEffectEvent event) {
        if (event.getModifiedType() != PotionEffectType.INVISIBILITY
                || !(event.getEntity() instanceof Player player)
                || RankManager.getInstance().getPlayerRank(player) == null
        ) {
            return;
        }

        PlayerNameTag playerNameTag = PlayerNameTag.getPlayerNameTag(player);

        if (PlayerNameTag.shouldDisplayPlayerNameTag(player, true, false) && event.getNewEffect() == null) {
            if (!Main.isFolia()) {
                TeamManager.getInstance().showPlayerNameTag(player);
            }
        } else {
            if (!Main.isFolia()) {
                TeamManager.getInstance().hidePlayerNameTag(player);
            }
            if (playerNameTag != null) {
                playerNameTag.remove();
            }
        }
    }
}
