package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.objects.Placeholder;
import de.lxca.slimeRanks.objects.RankManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

public class PluginEnableListener implements Listener {
    @EventHandler
    public void onPluginEnable(PluginEnableEvent event) {
        String pluginName = event.getPlugin().getName();

        switch (pluginName) {
            case "Multiverse-Core":
                Bukkit.getPluginManager().registerEvents(new MultiverseTeleportListener(), Main.getInstance());
                break;
            case "PlaceholderAPI":
                new Placeholder().register();
                RankManager.getInstance().reloadDisplays();
                break;
            case "MiniPlaceholders":
                RankManager.getInstance().reloadDisplays();
                break;
        }
    }
}
