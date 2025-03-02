package de.lxca.slimeRanks.commands;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.objects.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SlimeranksCommand extends Command {

    public SlimeranksCommand(@NotNull String name) {
        super(name);
        setAliases(Collections.singletonList("sr"));
        setDescription("Command to manage the SlimeRanks plugin.");
        setPermission("slimeranks.admin");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length == 0) {
            sendAboutMessage(commandSender);
            return true;
        } else if (strings.length == 1) {
            if (strings[0].equalsIgnoreCase("about")) {
                sendAboutMessage(commandSender);
                return true;
            } else if (strings[0].equalsIgnoreCase("help")) {
                new Message(commandSender, false, "Chat.Command.Help");
                return true;
            } else if (strings[0].equalsIgnoreCase("reload")) {
                Main.reload();
                new Message(commandSender, true, "Chat.Command.Reload");
                return true;
            } else {
                sendUnknownMessage(commandSender, s);
                return false;
            }
        } else {
            sendUnknownMessage(commandSender, s);
            return false;
        }
    }

    private void sendAboutMessage(@NotNull CommandSender commandSender) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("author", Main.getInstance().getPluginMeta().getAuthors().getFirst());
        replacements.put("description", Main.getInstance().getPluginMeta().getDescription());
        replacements.put("version", Main.getInstance().getPluginMeta().getVersion());

        new Message(commandSender, false, "Chat.Command.About", replacements);
    }

    private void sendUnknownMessage(@NotNull CommandSender commandSender, @NotNull String command) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("command", command);

        new Message(commandSender, false, "Chat.Command.Unknown", replacements);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String @NotNull [] args) {
        ArrayList<String> completerList = new ArrayList<>();
        if (args.length == 1 && sender.hasPermission("slimeranks.admin")) {
            completerList.add("about");
            completerList.add("help");
            completerList.add("reload");
        }

        ArrayList<String> completerListFiltered = new ArrayList<>();
        for (String loopString : completerList) {
            String loopStringLowerCase = loopString.toLowerCase();
            if (loopStringLowerCase.startsWith(args[args.length-1].toLowerCase())) {
                completerListFiltered.add(loopString);
            }
        }
        return completerListFiltered;
    }
}
