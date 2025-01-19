package de.lxca.slimeRanks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class SlimeranksCommand extends Command {

    public SlimeranksCommand(@NotNull String name) {
        super(name);
        setAliases(Collections.singletonList("sr"));
        setPermission("slimeranks.command.slimeranks");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String @NotNull [] strings) {





        return false;
    }
}
