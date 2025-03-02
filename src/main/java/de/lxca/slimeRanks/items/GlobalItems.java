package de.lxca.slimeRanks.items;

import de.lxca.slimeRanks.objects.ItemBuilder;
import de.lxca.slimeRanks.objects.Rank;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class GlobalItems {

    public static ItemStack getBackgroundItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE);

        itemBuilder.setHideTooltip(true);

        return itemBuilder.getItemStack();
    }

    public static ItemStack getCloseItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.BARRIER);

        itemBuilder.setItemName("Gui.Global.ItemName.Close");

        return itemBuilder.getItemStack();
    }

    public static ItemStack getBackItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.ARROW);

        itemBuilder.setItemName("Gui.Global.ItemName.Back");

        return itemBuilder.getItemStack();
    }

    public static ItemStack getPreviousPageItem(boolean pageAvailable) {
        ItemBuilder itemBuilder = new ItemBuilder(pageAvailable ? Material.LIME_DYE : Material.GRAY_DYE);

        itemBuilder.setItemName("Gui.Global.ItemName.PreviousPage");

        return itemBuilder.getItemStack();
    }

    public static ItemStack getNextPageItem(boolean pageAvailable) {
        ItemBuilder itemBuilder = new ItemBuilder(pageAvailable ? Material.LIME_DYE : Material.GRAY_DYE);

        itemBuilder.setItemName("Gui.Global.ItemName.NextPage");

        return itemBuilder.getItemStack();
    }

    public static ItemStack getRankItem(@NotNull Rank rank) {
        ItemBuilder itemBuilder = new ItemBuilder(Material.FILLED_MAP);
        HashMap<String, String> nameReplacements = new HashMap<>();
        HashMap<String, String> loreReplacements = new HashMap<>();

        nameReplacements.put("identifier", rank.getIdentifier());
        loreReplacements.put("identifier", rank.getIdentifier());
        loreReplacements.put("tablist_format", rank.getRawTabFormat());
        loreReplacements.put("chat_format", rank.getRawChatFormat());
        loreReplacements.put("name_tag_format", rank.getRawNameTagFormat());
        loreReplacements.put("tab_priority", String.valueOf(rank.getPriority()));
        loreReplacements.put("permission", rank.getPermission() == null ? "%Placeholder.None" : rank.getPermission());
        loreReplacements.put("hide_name_tag_on_sneak", rank.hideNameTagOnSneak() ? "%Placeholder.Yes" : "%Placeholder.No");

        itemBuilder.setItemName("Gui.Global.ItemName.Rank", nameReplacements);
        itemBuilder.setLore("Gui.Global.ItemLore.Rank", loreReplacements);

        itemBuilder.addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        itemBuilder.addItemFlag(ItemFlag.HIDE_ATTRIBUTES);

        return itemBuilder.getItemStack();
    }
}
