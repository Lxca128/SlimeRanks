package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Message(@NotNull String messageKey) {
        this.commandSender = null;
        this.withPrefix = false;
        this.messageKey = messageKey;
        this.replacements = new HashMap<>();
    }

    public Message(@NotNull String messageKey, @NotNull HashMap<String, String> replacements) {
        this.commandSender = null;
        this.withPrefix = false;
        this.messageKey = messageKey;
        this.replacements = replacements;
    }

    public String getRawMessage() {
        String messageString = Main.getMessagesYml().getYmlConfig().getString(messageKey, null);

        if (messageString == null) {
            Main.getLogger(this.getClass()).warn("Message with key {} not found in messages.yml!", messageKey);
            return messageKey;
        }

        if (withPrefix) {
            messageString = getPrefix() + messageString;
        }

        HashMap<String, String> replacements = getReplacedReplacements();
        for (String key : replacements.keySet()) {
            String regex = "\\{" + key + "}";
            messageString = messageString.replaceAll(regex, replacements.get(key));
        }

        return messageString;
    }

    public Component getMessage() {
        return MiniMessage.miniMessage().deserialize(getRawMessage());
    }

    public ArrayList<Component> getLore() {
        List<?> loreLines = Main.getMessagesYml().getYmlConfig().getList(messageKey, null);

        if (loreLines == null || loreLines.isEmpty()) {
            Main.getLogger(this.getClass()).warn("Message with key {} not found or is empty in messages.yml!", messageKey);
            return null;
        }

        HashMap<String, String> replacements = getReplacedReplacements();
        ArrayList<Component> loreLineComponents = new ArrayList<>();

        for (Object loreLine : loreLines) {
            if (!(loreLine instanceof String loreLineString)) {
                Main.getLogger(this.getClass()).warn("Message with key {} contains non-string lore line at index {} in messages.yml!", messageKey, loreLines.indexOf(loreLine));
                continue;
            }

            for (String key : replacements.keySet()) {
                String regex = "\\{" + key + "}";
                loreLineString = loreLineString.replaceAll(regex, replacements.get(key));
            }

            loreLineComponents.add(MiniMessage.miniMessage().deserialize(loreLineString));
        }

        return loreLineComponents;
    }

    private @NotNull HashMap<String, String> getReplacedReplacements() {
        HashMap<String, String> replacements = new HashMap<>();

        for (Map.Entry<String, String> entry : this.replacements.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value != null && value.startsWith("%")) {
                String valueMessage = new Message(value.substring(1)).getRawMessage();
                replacements.put(key, valueMessage);
            } else {
                replacements.put(key, value);
            }
        }

        return replacements;
    }

    public void sendMessage() {
        if (commandSender == null) {
            return;
        }

        commandSender.sendMessage(getMessage());
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
