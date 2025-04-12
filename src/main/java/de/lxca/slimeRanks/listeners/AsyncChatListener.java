package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.objects.ChatInput;
import de.lxca.slimeRanks.objects.Rank;
import de.lxca.slimeRanks.objects.RankManager;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AsyncChatListener implements Listener {

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        if (ChatInput.playerHasActivateChatInputSession(player)) {
            event.setCancelled(true);
            Main.getInstance().getServer().getScheduler().runTask(
                    Main.getInstance(), () -> ChatInput.getChatInputSession(player).executeLogic(event));
            return;
        }

        Rank rank = RankManager.getInstance().getPlayerRank(player);

        if (rank == null || !rank.chatIsActive()) {
            return;
        }
        event.setCancelled(true);

        Component playerMessage = event.message();
        String playerMessageString;
        if (rank.getColoredMessages() || player.hasPermission("slimeranks.chat.color")) {
            playerMessageString = PlainTextComponentSerializer.plainText().serialize(playerMessage);
        } else {
            playerMessageString = MiniMessage.miniMessage().serialize(playerMessage);
        }
        Component formattedPlayerMessage = rank.getChatFormat(player, playerMessageString);

        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            loopPlayer.sendMessage(formattedPlayerMessage);
        }
        if (Main.getConfigYml().getYmlConfig().getBoolean("ShowChatMessageInConsole")) {
            Main.getLogger("SlimeRanks - Chat").info(
                    PlainTextComponentSerializer.plainText().serialize(formattedPlayerMessage)
            );
        }
    }
}
