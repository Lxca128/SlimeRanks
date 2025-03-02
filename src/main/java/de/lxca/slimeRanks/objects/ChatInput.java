package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.enums.ChatInputType;
import de.lxca.slimeRanks.guis.RankEditGui;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.HashMap;

public class ChatInput {

    private static final HashMap<Player, ChatInput> chatInputSessions = new HashMap<>();

    private final Player player;
    private final ChatInputType chatInputType;
    private final int timerSeconds;
    private final Object linkedObject;
    private BukkitTask titleTask;

    public ChatInput(Player player, ChatInputType chatInputType, int timerSeconds, Component infoMessage) {
        killChatInputSession(player);

        this.player = player;
        this.chatInputType = chatInputType;
        this.timerSeconds = timerSeconds;
        this.linkedObject = null;

        startTimer(infoMessage);

        chatInputSessions.put(player, this);
    }

    public ChatInput(Player player, ChatInputType chatInputType, int timerSeconds, Object linkedObject, Component infoMessage) {
        killChatInputSession(player);

        this.player = player;
        this.chatInputType = chatInputType;
        this.timerSeconds = timerSeconds;
        this.linkedObject = linkedObject;

        startTimer(infoMessage);

        chatInputSessions.put(player, this);
    }

    private void startTimer(Component infoMessage) {
        new Message(player, true, "Chat.Input.Started");
        if (infoMessage != null) {
            player.sendMessage(infoMessage);
        }
        player.playSound(player, Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1.0F, 1.0F);

        final int[] seconds = {timerSeconds};
        Runnable titleTaskRunnable = () -> {
            if (!playerHasActivateChatInputSession(player)) {
                return;
            }
            if (player.isOnline() && seconds[0] > 0) {
                HashMap<String, String> replacements = new HashMap<>();
                replacements.put("seconds", String.valueOf(seconds[0]));

                Title.Times times = Title.Times.times(Duration.ZERO, Duration.ofSeconds(1), Duration.ofSeconds(1));
                Title title = Title.title(
                        new Message("Title.ChatInput.Main").getMessage(),
                        new Message("Title.ChatInput.Sub", replacements).getMessage(),
                        times
                );
                player.showTitle(title);
                seconds[0]--;
                return;
            }

            endSession(true, null, false);
        };
        titleTask = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), titleTaskRunnable, 0, 20);
    }

    public void endSession(boolean notifyPlayer, Component additionalInfoMessage, boolean actionSuccessful) {
        killChatInputSession(player);
        if (notifyPlayer) {
            if (actionSuccessful) {
                player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 2.0F);
            } else {
                player.playSound(player, Sound.ENTITY_WARDEN_SONIC_BOOM, 1.0F, 1.0F);
            }
            new Message(player, true, "Chat.Input.Ended");
            if (additionalInfoMessage != null) {
                player.sendMessage(additionalInfoMessage);
            }
        }
    }

    public void executeLogic(@NotNull AsyncChatEvent event) {
        String messageString = PlainTextComponentSerializer.plainText().serialize(event.message());

        if (messageString.equalsIgnoreCase("cancel")) {
            endSession(true, null, false);
            return;
        }

        if (chatInputType == ChatInputType.RANK_IDENTIFIER) {
            if (!messageString.chars().allMatch(Character::isAlphabetic)) {
                Component errorMessage = new Message("Chat.Input.RankIdentifier.OnlyLetters", true).getMessage();
                endSession(true, errorMessage, false);
                return;
            }
            YamlConfiguration ranksYml = Main.getRanksYml().getYmlConfig();
            ConfigurationSection ranksSection = ranksYml.getConfigurationSection("Ranks");

            if (ranksSection != null && ranksSection.getKeys(false).contains(messageString)) {
                Component errorMessage = new Message("Chat.Input.RankIdentifier.AlreadyUsed", true).getMessage();
                endSession(true, errorMessage, false);
                return;
            }

            ranksYml.set("Ranks." + messageString + ".Tab.Active", true);
            ranksYml.set("Ranks." + messageString + ".Tab.Format", "<gray>" + messageString + "</gray> <dark_gray>-</dark_gray> <gray>{player}</gray>");
            ranksYml.set("Ranks." + messageString + ".Tab.Priority", 0);
            ranksYml.set("Ranks." + messageString + ".Chat.Active", true);
            ranksYml.set("Ranks." + messageString + ".Chat.Format", "<gray>" + messageString + "</gray> <dark_gray>-</dark_gray> <gray>{player}</gray><dark_gray>:</dark_gray> <white>{message}</white>");
            ranksYml.set("Ranks." + messageString + ".NameTag.Active", true);
            ranksYml.set("Ranks." + messageString + ".NameTag.Format", "<gray>" + messageString + "</gray> <dark_gray>-</dark_gray> <gray>{player}</gray>");
            ranksYml.set("Ranks." + messageString + ".NameTag.HideOnSneak", true);
            ranksYml.set("Ranks." + messageString + ".Permission", "slimeranks.rank." + messageString);
            Main.getRanksYml().saveYmlConfig();
            RankManager.getInstance().reloadRanks();
            endSession(true, null, true);
            player.openInventory(new RankEditGui(new Rank(messageString)).getInventory());
        } else if (chatInputType == ChatInputType.RANK_TAB_FORMAT) {
            Rank rank = (Rank) linkedObject;
            rank.setTabFormat(messageString);
            RankManager.getInstance().reloadDisplays();
            endSession(true, null, true);
            player.openInventory(new RankEditGui(rank).getInventory());
        } else if (chatInputType == ChatInputType.RANK_CHAT_FORMAT) {
            Rank rank = (Rank) linkedObject;
            rank.setChatFormat(messageString);
            RankManager.getInstance().reloadDisplays();
            endSession(true, null, true);
            player.openInventory(new RankEditGui(rank).getInventory());
        } else if (chatInputType == ChatInputType.RANK_NAME_TAG_FORMAT) {
            Rank rank = (Rank) linkedObject;
            rank.setNameTagFormat(messageString);
            RankManager.getInstance().reloadDisplays();
            endSession(true, null, true);
            player.openInventory(new RankEditGui(rank).getInventory());
        } else if (chatInputType == ChatInputType.RANK_TAB_PRIORITY) {
            if (!messageString.chars().allMatch(Character::isDigit)) {
                Component additionalInfoMessage = new Message("Chat.Input.OnlyNumbers", true).getMessage();
                endSession(true, additionalInfoMessage, false);
                return;
            }

            int priority = Integer.parseInt(messageString);

            if (priority < 0) {
                Component additionalInfoMessage = new Message("Chat.Input.HigherThanZero", true).getMessage();
                endSession(true, additionalInfoMessage, false);
                return;
            }

            Rank rank = (Rank) linkedObject;
            rank.setPriority(priority);
            RankManager.getInstance().reloadDisplays();
            endSession(true, null, true);
            player.openInventory(new RankEditGui(rank).getInventory());
        } else if (chatInputType == ChatInputType.RANK_PERMISSION) {
            Rank rank = (Rank) linkedObject;
            rank.setPermission(messageString);
            RankManager.getInstance().reloadDisplays();
            endSession(true, null, true);
            player.openInventory(new RankEditGui(rank).getInventory());
        } else {
            endSession(true, null, false);
        }
    }

    public static boolean playerHasActivateChatInputSession(Player player) {
        return chatInputSessions.containsKey(player);
    }

    public static ChatInput getChatInputSession(Player player) {
        return chatInputSessions.get(player);
    }

    private static void killChatInputSession(Player player) {
        ChatInput chatInput = chatInputSessions.getOrDefault(player, null);

        if (chatInput != null) {
            chatInput.titleTask.cancel();
            player.clearTitle();
            chatInputSessions.remove(player);
        }
    }
}
