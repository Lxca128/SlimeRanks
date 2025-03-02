package de.lxca.slimeRanks.items;

import de.lxca.slimeRanks.objects.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class OverviewItems {

    public static ItemStack getTitleItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.SLIME_BLOCK);

        itemBuilder.setItemName("Gui.Overview.ItemName.Title");
        itemBuilder.setLore("Gui.Overview.ItemLore.Title");

        return itemBuilder.getItemStack();
    }

    public static ItemStack getReloadItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.HONEYCOMB);

        itemBuilder.setItemName("Gui.Overview.ItemName.Reload");
        itemBuilder.setLore("Gui.Overview.ItemLore.Reload");

        return itemBuilder.getItemStack();
    }

    public static ItemStack getCreateRankItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.CRAFTING_TABLE);

        itemBuilder.setItemName("Gui.Overview.ItemName.CreateRank");
        itemBuilder.setLore("Gui.Overview.ItemLore.CreateRank");

        return itemBuilder.getItemStack();
    }
}
