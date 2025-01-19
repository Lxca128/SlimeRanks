package de.lxca.slimeRanks;

import de.lxca.slimeRanks.objects.MessagesYml;
import de.lxca.slimeRanks.objects.RanksYml;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Main extends JavaPlugin {

    private static MessagesYml messagesYml;
    private static RanksYml ranksYml;

    @Override
    public void onEnable() {
        messagesYml = new MessagesYml();
        ranksYml = new RanksYml();
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
}
