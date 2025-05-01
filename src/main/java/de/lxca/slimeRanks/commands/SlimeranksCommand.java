package de.lxca.slimeRanks.commands;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.guis.RankOverviewGui;
import de.lxca.slimeRanks.objects.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlimeranksCommand extends Command {

    public SlimeranksCommand(@NotNull String name) {
        super(name);
        setAliases(List.of("sr", "rank", "ranks"));
        setDescription("Command to manage the SlimeRanks plugin.");
        setPermission("slimeranks.admin");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (strings.length == 0) {
            sendAboutMessage(commandSender);
            return true;
        } else if (strings.length == 1) {
            switch (strings[0]) {
                case "about":
                    sendAboutMessage(commandSender);
                    return true;
                case "gui":
                    if (commandSender instanceof Player player) {
                        player.openInventory(new RankOverviewGui().getInventory());
                        return true;
                    } else {
                        new Message(commandSender, true, "Chat.Command.OnlyPlayers");
                        return false;
                    }
                case "help":
                    new Message(commandSender, false, "Chat.Command.Help");
                    return true;
                case "reload":
                    Main.reload();
                    new Message(commandSender, true, "Chat.Command.Reload");
                    return true;
                default:
                    sendUnknownMessage(commandSender, s, strings);
                    return false;
            }
        } else {
            sendUnknownMessage(commandSender, s, strings);
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

    private void sendUnknownMessage(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String @NotNull [] strings) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("command", "/" + s + " " + String.join(" ", strings));

        new Message(commandSender, true, "Chat.Command.Unknown", replacements);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String @NotNull [] args) {
        ArrayList<String> completerList = new ArrayList<>();
        if (args.length == 1 && sender.hasPermission("slimeranks.admin")) {
            completerList.add("about");
            completerList.add("gui");
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
