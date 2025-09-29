package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.TooltipDisplay;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }

    public void setItemName(String messageKey) {
        setItemName(messageKey, null);
    }

    public void setItemName(String messageKey, HashMap<String, String> replacements) {
        if (replacements == null) {
            itemMeta.itemName(new Message(messageKey).getMessage());
        } else {
            itemMeta.itemName(new Message(messageKey, replacements).getMessage());
        }
    }

    public void setLore(String messageKey) {
        setLore(messageKey, null);
    }

    public void setLore(String messageKey, HashMap<String, String> replacements) {
        ArrayList<Component> lore;

        if (replacements == null) {
            lore = new Message(messageKey).getLore();
        } else {
            lore = new Message(messageKey, replacements).getLore();
        }

        if (lore == null) {
            return;
        }

        itemMeta.lore(lore);
    }

    public void addItemFlag(ItemFlag itemFlag) {
        itemMeta.addItemFlags(itemFlag);
        itemMeta.addAttributeModifier(
                Attribute.ARMOR,
                new AttributeModifier(
                        new NamespacedKey(Main.getInstance(), UUID.randomUUID().toString()),
                        0,
                        AttributeModifier.Operation.ADD_NUMBER
                )
        );
    }

    public void setHideTooltip(boolean value) {
        itemMeta.setHideTooltip(value);
    }

    @SuppressWarnings("UnstableApiUsage")
    public ItemBuilder setTooltipDisplay(@NotNull TooltipDisplay.Builder tooltipDisplayBuilder) {
        itemStack.setData(DataComponentTypes.TOOLTIP_DISPLAY, tooltipDisplayBuilder.build());
        return this;
    }

    public ItemStack getItemStack() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
