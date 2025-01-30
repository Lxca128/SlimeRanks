package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Message {

    private static String prefix;
    private final CommandSender commandSender;
    private final boolean withPrefix;
    private final String messageKey;
    private final HashMap<String, String> replacements;

    public Message(@NotNull CommandSender commandSender, boolean withPrefix, @NotNull String messageKey) {
        this.commandSender = commandSender;
        this.withPrefix = withPrefix;
        this.messageKey = messageKey;
        this.replacements = new HashMap<>();

        sendMessage();
    }

    public Message(@NotNull CommandSender commandSender, boolean withPrefix, @NotNull String messageKey, @NotNull HashMap<String, String> replacements) {
        this.commandSender = commandSender;
        this.withPrefix = withPrefix;
        this.messageKey = messageKey;
        this.replacements = replacements;

        sendMessage();
    }

    private void sendMessage() {
        String messageString = Main.getMessagesYml().getYmlConfig().getString(messageKey, null);

        if (messageString == null) {
            commandSender.sendMessage(messageKey);
            Main.getLogger(this.getClass()).warn("Message with key {} not found in messages.yml!", messageKey);
            return;
        }

        if (withPrefix) {
            messageString = getPrefix() + messageString;
        }

        for (String key : replacements.keySet()) {
            String regex = "\\{" + key + "}";
            messageString = messageString.replaceAll(regex, replacements.get(key));
        }

        commandSender.sendMessage(MiniMessage.miniMessage().deserialize(messageString));
    }

    private String getPrefix() {
        if (prefix != null) {
            return prefix;
        }

        prefix = Main.getMessagesYml().getYmlConfig().getString("Chat.Brand", "Chat.Brand");

        return prefix;
    }

    public static void resetPrefix() {
        prefix = null;
    }
}
