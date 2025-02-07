package de.lxca.slimeRanks;

import de.lxca.slimeRanks.commands.SlimeranksCommand;
import de.lxca.slimeRanks.listeners.*;
import de.lxca.slimeRanks.objects.RankManager;
import de.lxca.slimeRanks.objects.TeamManager;
import de.lxca.slimeRanks.objects.configurations.MessagesYml;
import de.lxca.slimeRanks.objects.configurations.RanksYml;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main extends JavaPlugin {

    private static MessagesYml messagesYml;
    private static RanksYml ranksYml;

    @Override
    public void onEnable() {
        initializeVariables();

        CommandMap commandMap = Bukkit.getCommandMap();
        commandMap.register("slimeventure", new SlimeranksCommand("slimeranks"));

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new AsyncChatListener(), this);
        pluginManager.registerEvents(new PlayerChangedWorldListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerGameModeChangeListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerToggleSneakListener(), this);
        pluginManager.registerEvents(new PlayerPostRespawnListener(), this);
        pluginManager.registerEvents(new WorldLoadListener(), this);

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            RankManager.getInstance().reload();
        }
    }

    @Override
    public void onDisable() {
        TeamManager.getInstance().removeInvisibleNameTagTeam();
    }

    public static @NotNull Main getInstance() {
        return Main.getPlugin(Main.class);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static void initializeVariables() {
        messagesYml = new MessagesYml();
        ranksYml = new RanksYml();
    }

    public static MessagesYml getMessagesYml() {
        return messagesYml;
    }

    public static RanksYml getRanksYml() {
        return ranksYml;
    }
}
