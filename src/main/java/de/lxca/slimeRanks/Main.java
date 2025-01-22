package de.lxca.slimeRanks;

import de.lxca.slimeRanks.listeners.AsyncChatListener;
import de.lxca.slimeRanks.listeners.PlayerJoinListener;
import de.lxca.slimeRanks.listeners.PlayerQuitListener;
import de.lxca.slimeRanks.listeners.WorldLoadListener;
import de.lxca.slimeRanks.objects.RankManager;
import de.lxca.slimeRanks.objects.configurations.MessagesYml;
import de.lxca.slimeRanks.objects.configurations.RanksYml;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main extends JavaPlugin {

    private static MessagesYml messagesYml;
    private static RanksYml ranksYml;
    private static RankManager rankManager;

    @Override
    public void onEnable() {
        messagesYml = new MessagesYml();
        ranksYml = new RanksYml();
        rankManager = RankManager.getInstance();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new AsyncChatListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new WorldLoadListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static @NotNull Main getInstance() {
        return Main.getPlugin(Main.class);
    }

    public static Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    public static MessagesYml getMessagesYml() {
        return messagesYml;
    }

    public static RanksYml getRanksYml() {
        return ranksYml;
    }

    public static RankManager getRankManager() {
        return rankManager;
    }
}
