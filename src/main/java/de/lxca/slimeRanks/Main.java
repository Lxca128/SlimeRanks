package de.lxca.slimeRanks;

import de.lxca.slimeRanks.commands.SlimeranksCommand;
import de.lxca.slimeRanks.listeners.*;
import de.lxca.slimeRanks.objects.Metrics;
import de.lxca.slimeRanks.objects.RankManager;
import de.lxca.slimeRanks.objects.TeamManager;
import de.lxca.slimeRanks.objects.configurations.MessagesYml;
import de.lxca.slimeRanks.objects.configurations.RanksYml;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Main extends JavaPlugin {

    private static MessagesYml messagesYml;
    private static RanksYml ranksYml;
    private static Metrics metrics;

    @Override
    public void onEnable() {
        initializeVariables();

        CommandMap commandMap = Bukkit.getCommandMap();
        commandMap.register("slimeventure", new SlimeranksCommand("slimeranks"));

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new AsyncChatListener(), this);
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new PlayerChangedWorldListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerGameModeChangeListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerToggleSneakListener(), this);
        pluginManager.registerEvents(new PlayerPostRespawnListener(), this);
        pluginManager.registerEvents(new WorldLoadListener(), this);

        for (World world : Bukkit.getWorlds()) {
            RankManager.getInstance().clearPlayerNameTags(world);
        }

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            RankManager.getInstance().reload();
        }

        initializeMetrics();
    }

    @Override
    public void onDisable() {
        for (World world : Bukkit.getWorlds()) {
            RankManager.getInstance().clearPlayerNameTags(world);
        }
        TeamManager.getInstance().removeInvisibleNameTagTeam();
    }

    public static @NotNull Main getInstance() {
        return Main.getPlugin(Main.class);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    public static void initializeVariables() {
        messagesYml = new MessagesYml();
        ranksYml = new RanksYml();
        metrics = new Metrics(getInstance(), 24715);
    }

    private static void initializeMetrics() {
        metrics.addCustomChart(new Metrics.SimplePie("rank_count", () -> String.valueOf(RankManager.getInstance().getRankCount())));
    }

    public static MessagesYml getMessagesYml() {
        return messagesYml;
    }

    public static RanksYml getRanksYml() {
        return ranksYml;
    }
}
