package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Message {

    private static String prefix;
    private final CommandSender commandSender;
    private final boolean withPrefix;
    private final String messageKey;

    public Message(@NotNull CommandSender commandSender, boolean withPrefix, @NotNull String messageKey) {
        this.commandSender = commandSender;
        this.withPrefix = withPrefix;
        this.messageKey = messageKey;

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
