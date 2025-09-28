package de.lxca.slimeRanks.commands;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.guis.RankOverviewGui;
import de.lxca.slimeRanks.objects.Message;
//import de.lxca.slimeRanks.objects.Rank;
//import de.lxca.slimeRanks.objects.RankManager;
//import net.luckperms.api.LuckPerms;
//import net.luckperms.api.node.Node;
//import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
//import java.util.concurrent.atomic.AtomicBoolean;

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
//        } else if (strings.length == 2) {
//            if (strings[0].equals("import")) {
//                if (strings[1].equals("luckperms")) {
//                    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
//
//                    if (provider == null) {
//                        new Message(commandSender, true, "Chat.Command.Import.LuckPermsNotFound");
//                        return false;
//                    }
//
//                    LuckPerms api = provider.getProvider();
//                    AtomicBoolean couldImportAllRanks = new AtomicBoolean(true);
//
//                    api.getGroupManager().getLoadedGroups().forEach(group -> {
//                        String rankIdentifier = group.getIdentifier().getName();
//                        Rank rank = new Rank(rankIdentifier);
//
//                        if (!rank.exists()) {
//                            rank = Rank.createRank(rankIdentifier);
//                        }
//
//                        if (rank == null || !rank.exists()) {
//                            Main.getLogger("SlimeRanks").warn("Failed to create rank for LuckPerms group: {}! Make sure the group-identifier only contains letters.", rankIdentifier);
//                            couldImportAllRanks.set(false);
//                            return;
//                        }
//
//                        if (rank.exists()) {
//                            String permission = rank.getPermission();
//
//                            if (group.data().toCollection().stream().noneMatch(node -> node.getKey().equals(permission))) {
//                                group.data().add(Node.builder(permission).build());
//                                api.getGroupManager().saveGroup(group);
//                            }
//                        }
//                    });
//
//                    if (couldImportAllRanks.get()) {
//                        new Message(commandSender, true, "Chat.Command.Import.LuckPermsSuccess");
//                    } else {
//                        new Message(commandSender, true, "Chat.Command.Import.LuckPermsPartiallySuccess");
//                    }
//                    RankManager.getInstance().reloadDisplays();
//                    if (commandSender instanceof Player player) {
//                        player.openInventory(new RankOverviewGui().getInventory());
//                    }
//                    return true;
//                } else {
//                    HashMap<String, String> replacements = new HashMap<>();
//                    replacements.put("import_service", strings[1]);
//
//                    new Message(commandSender, true, "Chat.Command.Import.UnknownService", replacements);
//                    return false;
//                }
//            } else {
//                sendUnknownMessage(commandSender, s, strings);
//                return false;
//            }
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
        if (!sender.hasPermission("slimeranks.admin")) {
            return Collections.emptyList();
        }

        ArrayList<String> completerList = new ArrayList<>();

        if (args.length == 1) {
            completerList.add("about");
            completerList.add("gui");
            completerList.add("help");
//            completerList.add("import");
            completerList.add("reload");
        }
//        else if (args.length == 2) {
//            if (args[0].equals("import")) {
//                completerList.add("luckperms");
//            }
//        }

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
