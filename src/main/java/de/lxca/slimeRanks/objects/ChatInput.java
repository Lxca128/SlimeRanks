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
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.HashMap;

public class ChatInput {

    private static final HashMap<Player, ChatInput> chatInputSessions = new HashMap<>();

    private final Player player;
    private final ChatInputType chatInputType;
    private final int timerSeconds;
    private final Object linkedObject;

    public ChatInput(Player player, ChatInputType chatInputType, int timerSeconds, Component infoMessage) {
        killChatInputSession(player);

        this.player = player;
        this.chatInputType = chatInputType;
        this.timerSeconds = timerSeconds;
        this.linkedObject = null;

        chatInputSessions.put(player, this);

        startTimer(infoMessage);
    }

    public ChatInput(Player player, ChatInputType chatInputType, int timerSeconds, Object linkedObject, Component infoMessage) {
        killChatInputSession(player);

        this.player = player;
        this.chatInputType = chatInputType;
        this.timerSeconds = timerSeconds;
        this.linkedObject = linkedObject;

        chatInputSessions.put(player, this);

        startTimer(infoMessage);
    }

    private void startTimer(Component infoMessage) {
        new Message(player, true, "Chat.Input.Started");

        if (infoMessage != null) {
            player.sendMessage(infoMessage);
        }

        player.playSound(player, Sound.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, 1.0F, 1.0F);

        runTimer(player, timerSeconds);
    }

    private void runTimer(Player player, int secondsLeft) {
        if (!playerHasActivateChatInputSession(player)) {
            return;
        }

        if (player.isOnline() && secondsLeft > 0) {
            HashMap<String, String> replacements = new HashMap<>();
            replacements.put("seconds", String.valueOf(secondsLeft));

            Title.Times times = Title.Times.times(Duration.ZERO, Duration.ofSeconds(1), Duration.ofSeconds(1));
            Title title = Title.title(
                    new Message("Title.ChatInput.Main").getMessage(),
                    new Message("Title.ChatInput.Sub", replacements).getMessage(),
                    times
            );

            player.showTitle(title);

            Bukkit.getServer().getRegionScheduler().runDelayed(
                    Main.getInstance(),
                    player.getLocation(),
                    task -> runTimer(player, secondsLeft - 1),
                    20L
            );
        } else {
            endSession(true, null, false);
        }
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

            Rank createdRank = Rank.createRank(messageString);

            if (createdRank == null) {
                Component errorMessage = new Message("Chat.Input.RankIdentifier.CreationFailed", true).getMessage();
                endSession(true, errorMessage, false);
                return;
            }

            endSession(true, null, true);
            player.openInventory(new RankEditGui(createdRank).getInventory());
        } else if (chatInputType == ChatInputType.RANK_TAB_FORMAT) {
            Rank rank = (Rank) linkedObject;
            rank.setTabFormat(messageString);
            endSession(true, null, true);
            player.openInventory(new RankEditGui(rank).getInventory());
        } else if (chatInputType == ChatInputType.RANK_CHAT_FORMAT) {
            Rank rank = (Rank) linkedObject;
            rank.setChatFormat(messageString);
            endSession(true, null, true);
            player.openInventory(new RankEditGui(rank).getInventory());
        } else if (chatInputType == ChatInputType.RANK_NAME_TAG_FORMAT) {
            Rank rank = (Rank) linkedObject;
            rank.setNameTagFormat(messageString);
            endSession(true, null, true);
            player.openInventory(new RankEditGui(rank).getInventory());
        } else if (chatInputType == ChatInputType.RANK_TAB_PRIORITY || chatInputType == ChatInputType.RANK_PRIORITY) {
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
            if (chatInputType == ChatInputType.RANK_PRIORITY) {
                rank.setRankPriority(priority);
            } else {
                rank.setTabPriority(priority);
            }
            endSession(true, null, true);
            player.openInventory(new RankEditGui(rank).getInventory());
        } else if (chatInputType == ChatInputType.RANK_PERMISSION) {
            Rank rank = (Rank) linkedObject;
            rank.setPermission(messageString);
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
            player.clearTitle();
            chatInputSessions.remove(player);
        }
    }
}
