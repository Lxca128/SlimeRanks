package de.lxca.slimeRanks.items;

import de.lxca.slimeRanks.objects.ItemBuilder;
import de.lxca.slimeRanks.objects.Rank;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class EditItems {

    public static ItemStack getTablistItem(@NotNull Rank rank) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("format", rank.getRawTabFormat());

        ItemBuilder itemBuilder = new ItemBuilder(Material.JUNGLE_SIGN);

        itemBuilder.setItemName("Gui.Edit.ItemName.Tablist");
        itemBuilder.setLore("Gui.Edit.ItemLore.Tablist", replacements);

        return itemBuilder.getItemStack();
    }

    public static ItemStack getChatItem(@NotNull Rank rank) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("format", rank.getRawChatFormat());

        ItemBuilder itemBuilder = new ItemBuilder(Material.WRITABLE_BOOK);

        itemBuilder.setItemName("Gui.Edit.ItemName.Chat");
        itemBuilder.setLore("Gui.Edit.ItemLore.Chat", replacements);

        return itemBuilder.getItemStack();
    }

    public static ItemStack getNameTagItem(@NotNull Rank rank) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("format", rank.getRawNameTagFormat());

        ItemBuilder itemBuilder = new ItemBuilder(Material.NAME_TAG);

        itemBuilder.setItemName("Gui.Edit.ItemName.NameTag");
        itemBuilder.setLore("Gui.Edit.ItemLore.NameTag", replacements);

        return itemBuilder.getItemStack();
    }

    public static ItemStack getTabPriorityItem(@NotNull Rank rank) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("priority", String.valueOf(rank.getTabPriority()));

        ItemBuilder itemBuilder = new ItemBuilder(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);

        itemBuilder.setItemName("Gui.Edit.ItemName.TabPriority");
        itemBuilder.setLore("Gui.Edit.ItemLore.TabPriority", replacements);

        itemBuilder.addItemFlag(ItemFlag.HIDE_ADDITIONAL_TOOLTIP);
        itemBuilder.addItemFlag(ItemFlag.HIDE_ATTRIBUTES);

        return itemBuilder.getItemStack();
    }

    public static ItemStack getRankPriorityItem(@NotNull Rank rank) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("priority", String.valueOf(rank.getRankPriority()));

        ItemBuilder itemBuilder = new ItemBuilder(Material.ANVIL);

        itemBuilder.setItemName("Gui.Edit.ItemName.RankPriority");
        itemBuilder.setLore("Gui.Edit.ItemLore.RankPriority", replacements);

        return itemBuilder.getItemStack();
    }

    public static ItemStack getPermissionItem(@NotNull Rank rank) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("permission", rank.getPermission() == null ? "%Placeholder.None" : rank.getPermission());

        ItemBuilder itemBuilder = new ItemBuilder(Material.FLOWER_BANNER_PATTERN);

        itemBuilder.setItemName("Gui.Edit.ItemName.Permission");
        itemBuilder.setLore("Gui.Edit.ItemLore.Permission", replacements);

        return itemBuilder.getItemStack();
    }

    public static ItemStack getHideOnSneakItem(@NotNull Rank rank) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("status", rank.hideNameTagOnSneak() ? "%Placeholder.Yes" : "%Placeholder.No");

        ItemBuilder itemBuilder = new ItemBuilder(Material.ENDER_EYE);

        itemBuilder.setItemName("Gui.Edit.ItemName.HideOnSneak");
        itemBuilder.setLore("Gui.Edit.ItemLore.HideOnSneak", replacements);

        return itemBuilder.getItemStack();
    }

    public static ItemStack getColoredMessagesItem(@NotNull Rank rank) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("status", rank.getColoredMessages() ? "%Placeholder.Yes" : "%Placeholder.No");

        ItemBuilder itemBuilder = new ItemBuilder(Material.GLOW_INK_SAC);

        itemBuilder.setItemName("Gui.Edit.ItemName.ColoredMessages");
        itemBuilder.setLore("Gui.Edit.ItemLore.ColoredMessages", replacements);

        return itemBuilder.getItemStack();
    }

    public static ItemStack getStatusItem(boolean active) {
        ItemBuilder itemBuilder = new ItemBuilder(active ? Material.LIME_DYE : Material.GRAY_DYE);

        if (active) {
            itemBuilder.setItemName("Gui.Edit.ItemName.Status.Activated");
            itemBuilder.setLore("Gui.Edit.ItemLore.Status.Activated");
        } else {
            itemBuilder.setItemName("Gui.Edit.ItemName.Status.Deactivated");
            itemBuilder.setLore("Gui.Edit.ItemLore.Status.Deactivated");
        }

        return itemBuilder.getItemStack();
    }

    public static ItemStack getDeleteItem() {
        ItemBuilder itemBuilder = new ItemBuilder(Material.LAVA_BUCKET);

        itemBuilder.setItemName("Gui.Edit.ItemName.Delete");
        itemBuilder.setLore("Gui.Edit.ItemLore.Delete");

        return itemBuilder.getItemStack();
    }
}
