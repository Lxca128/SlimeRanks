package de.lxca.slimeRanks.guis;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.enums.ChatInputType;
import de.lxca.slimeRanks.enums.FormatType;
import de.lxca.slimeRanks.items.GlobalItems;
import de.lxca.slimeRanks.items.EditItems;
import de.lxca.slimeRanks.objects.ChatInput;
import de.lxca.slimeRanks.objects.Message;
import de.lxca.slimeRanks.objects.Rank;
import de.lxca.slimeRanks.objects.dialogs.FormatDialog;
import net.kyori.adventure.text.Component;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class RankEditGui implements InventoryHolder {

    private static final int guiSize = 6 * 9;

    private final Inventory inventory;
    private final Rank rank;

    public RankEditGui(@NotNull Rank rank) {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("identifier", rank.getIdentifier());

        this.inventory = Main.getInstance().getServer().createInventory(
                this,
                guiSize,
                new Message("Gui.Edit.Title", replacements).getMessage()
        );
        this.rank = rank;

        setItems();
    }

    private void setItems() {
        for (int i = 0; i < guiSize; i++) {
            inventory.setItem(i, GlobalItems.getBackgroundItem());
        }

        // ROW-1
        inventory.setItem(4, GlobalItems.getRankItem(rank));

        // ROW-2
        inventory.setItem(10, EditItems.getHideOnSneakItem(rank));
        inventory.setItem(12, EditItems.getTablistItem(rank));
        inventory.setItem(13, EditItems.getChatItem(rank));
        inventory.setItem(14, EditItems.getNameTagItem(rank));
        inventory.setItem(16, EditItems.getColoredMessagesItem(rank));

        // ROW-3
        inventory.setItem(19, EditItems.getStatusItem(rank.hideNameTagOnSneak()));
        inventory.setItem(21, EditItems.getStatusItem(rank.tabIsActive()));
        inventory.setItem(22, EditItems.getStatusItem(rank.chatIsActive()));
        inventory.setItem(23, EditItems.getStatusItem(rank.nameTagIsActive()));
        inventory.setItem(25, EditItems.getStatusItem(rank.getColoredMessages()));

        // ROW-5
        inventory.setItem(38, EditItems.getRankPriorityItem(rank));
        inventory.setItem(40, EditItems.getPermissionItem(rank));
        inventory.setItem(42, EditItems.getTabPriorityItem(rank));

        // ROW-6
        inventory.setItem(45, GlobalItems.getBackItem());
        inventory.setItem(53, EditItems.getDeleteItem());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void clickHandler(@NotNull InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if (slot == 12) {
            new FormatDialog(player, rank, FormatType.TAB).open();
        } else if (slot == 13) {
            new FormatDialog(player, rank, FormatType.CHAT).open();
        } else if (slot == 14) {
            new FormatDialog(player, rank, FormatType.NAME_TAG).open();
        } else if (slot == 19) {
            rank.setHideNameTagOnSneak(!rank.hideNameTagOnSneak());
            inventory.setItem(4, GlobalItems.getRankItem(rank));
            inventory.setItem(10, EditItems.getHideOnSneakItem(rank));
            inventory.setItem(slot, EditItems.getStatusItem(rank.hideNameTagOnSneak()));
            player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1.0F, 1.0F);
        } else if (slot == 21) {
            rank.setTabActive(!rank.tabIsActive());
            inventory.setItem(slot, EditItems.getStatusItem(rank.tabIsActive()));
            player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1.0F, 1.0F);
        } else if (slot == 22) {
            rank.setChatActive(!rank.chatIsActive());
            inventory.setItem(slot, EditItems.getStatusItem(rank.chatIsActive()));
            player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1.0F, 1.0F);
        } else if (slot == 23) {
            rank.setNameTagActive(!rank.nameTagIsActive());
            inventory.setItem(slot, EditItems.getStatusItem(rank.nameTagIsActive()));
            player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1.0F, 1.0F);
        } else if (slot == 25) {
            rank.setColoredMessages(!rank.getColoredMessages());
            inventory.setItem(4, GlobalItems.getRankItem(rank));
            inventory.setItem(16, EditItems.getColoredMessagesItem(rank));
            inventory.setItem(slot, EditItems.getStatusItem(rank.getColoredMessages()));
            player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1.0F, 1.0F);
        } else if (slot == 38) {
            Component additionalInfoMessage = new Message("Chat.Input.RankPriority", true).getMessage();
            new ChatInput(player, ChatInputType.RANK_PRIORITY, 30, rank, additionalInfoMessage);
            player.closeInventory();
        } else if (slot == 40) {
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                rank.setPermission(null);
                inventory.setItem(slot, EditItems.getPermissionItem(rank));
                player.playSound(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
            } else {
                Component additionalInfoMessage = new Message("Chat.Input.Permission", true).getMessage();
                new ChatInput(player, ChatInputType.RANK_PERMISSION, 30, rank, additionalInfoMessage);
                player.closeInventory();
            }
        } else if (slot == 42) {
            Component additionalInfoMessage = new Message("Chat.Input.TabPriority", true).getMessage();
            new ChatInput(player, ChatInputType.RANK_TAB_PRIORITY, 30, rank, additionalInfoMessage);
            player.closeInventory();
        } else if (slot == 45) {
            player.openInventory(new RankOverviewGui().getInventory());
            player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
        } else if (slot == 53 && (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
            rank.delete();
            player.openInventory(new RankOverviewGui().getInventory());
            player.playSound(player, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
        }
    }
}
