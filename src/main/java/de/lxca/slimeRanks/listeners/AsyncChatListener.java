package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.objects.Rank;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener {

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        Rank rank = Main.getRankManager().getPlayerRank(player);

        if (rank == null || !rank.chatIsActive()) {
            return;
        }
        event.setCancelled(true);

        Component playerMessage = event.message();
        String playerMessageString = MiniMessage.miniMessage().serialize(playerMessage);
        Component serverMessage = rank.getChatFormat(player, playerMessageString);

        Bukkit.broadcast(serverMessage);
    }
}
