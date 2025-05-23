package de.lxca.slimeRanks;

import de.lxca.slimeRanks.commands.SlimeranksCommand;
import de.lxca.slimeRanks.listeners.*;
import de.lxca.slimeRanks.objects.*;
import de.lxca.slimeRanks.objects.configurations.ConfigYml;
import de.lxca.slimeRanks.objects.configurations.MessagesYml;
import de.lxca.slimeRanks.objects.configurations.RanksYml;
import de.lxca.slimeRanks.schedulers.NameUpdateScheduler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public final class Main extends JavaPlugin {

    private static ConfigYml configYml;
    private static MessagesYml messagesYml;
    private static RanksYml ranksYml;
    private static Metrics metrics;
    private static BukkitTask nameUpdateTask;

    @Override
    public void onEnable() {
        initializeVariables();

        CommandMap commandMap = Bukkit.getCommandMap();
        commandMap.register("slimeventure", new SlimeranksCommand("slimeranks"));

        PluginManager pluginManager = Bukkit.getPluginManager();
        Listener[] listeners = {
                new AsyncChatListener(),
                new ChunkLoadListener(),
                new EntityAddToWorldListener(),
                new EntityPotionEffectListener(),
                new InventoryClickListener(),
                new PlayerChangedWorldListener(),
                new PlayerDeathListener(),
                new PlayerGameModeChangeListener(),
                new PlayerHideEntityListener(),
                new PlayerJoinListener(),
                new PlayerPostRespawnListener(),
                new PlayerQuitListener(),
                new PlayerShowEntityListener(),
                new PlayerToggleSneakListener()
        };
        for (Listener listener : listeners) {
            pluginManager.registerEvents(listener, this);
        }

        runNameUpdateTask();

        for (World world : Bukkit.getWorlds()) {
            PlayerNameTag.clearBuggyNameTags(world);
        }

        if (!Bukkit.getOnlinePlayers().isEmpty()) {
            RankManager.getInstance().reloadDisplays();
        }

        initializeMetrics();

        new UpdateChecker().notifyUpdateAvailable(Bukkit.getConsoleSender());
    }

    @Override
    public void onDisable() {
        if (isFolia()) {
            return;
        }

        for (World world : Bukkit.getWorlds()) {
            PlayerNameTag.clearBuggyNameTags(world);
        }

        TeamManager.getInstance().removeInvisibleNameTagTeam();
    }

    public static @NotNull Main getInstance() {
        return Main.getPlugin(Main.class);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }

    public static Logger getLogger(String prefix) {
        return LogManager.getLogger(prefix);
    }

    public static void initializeVariables() {
        configYml = new ConfigYml();
        messagesYml = new MessagesYml();
        ranksYml = new RanksYml();
        metrics = new Metrics(getInstance(), 24715);
    }

    private static void initializeMetrics() {
        metrics.addCustomChart(new Metrics.SingleLineChart("created_ranks", () -> RankManager.getInstance().getRankCount()));
        metrics.addCustomChart(new Metrics.SimplePie("rank_count", () -> String.valueOf(RankManager.getInstance().getRankCount())));
    }

    public static ConfigYml getConfigYml() {
        return configYml;
    }

    public static MessagesYml getMessagesYml() {
        return messagesYml;
    }

    public static RanksYml getRanksYml() {
        return ranksYml;
    }

    public static void reload() {
        Message.resetPrefix();
        Main.initializeVariables();
        RankManager.getInstance().reloadRanks();
        RankManager.getInstance().reloadDisplays();
        runNameUpdateTask();
    }

    public static boolean isPluginEnabled(@NotNull String pluginName) {
        return Bukkit.getPluginManager().isPluginEnabled(pluginName);
    }

    public static void runNameUpdateTask() {
        if (nameUpdateTask != null) {
            nameUpdateTask.cancel();
        }

        int nameUpdateInterval = configYml.getYmlConfig().getInt("NameUpdateInterval");
        if (nameUpdateInterval > 0) {
            nameUpdateTask =  Bukkit.getScheduler().runTaskTimer(getInstance(), new NameUpdateScheduler(), (nameUpdateInterval * 20L), nameUpdateInterval * 20L);
        }
    }

    public static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
