package de.lxca.slimeRanks.listeners;

import de.lxca.slimeRanks.guis.RankEditGui;
import de.lxca.slimeRanks.guis.RankOverviewGui;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        InventoryHolder inventoryHolder = event.getInventory().getHolder();

        if (inventoryHolder instanceof RankEditGui gui) {
            gui.clickHandler(event);
        } if (inventoryHolder instanceof RankOverviewGui gui) {
            gui.clickHandler(event);
        }
    }
}
