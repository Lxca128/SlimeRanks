package de.lxca.slimeRanks.guis;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.enums.ChatInputType;
import de.lxca.slimeRanks.items.GlobalItems;
import de.lxca.slimeRanks.items.EditItems;
import de.lxca.slimeRanks.objects.ChatInput;
import de.lxca.slimeRanks.objects.Message;
import de.lxca.slimeRanks.objects.Rank;
import de.lxca.slimeRanks.objects.RankManager;
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

    private static final int guiSize = 4 * 9;

    private final Inventory inventory;
    private final Rank rank;

    public RankEditGui(Rank rank) {
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

        inventory.setItem(4, GlobalItems.getRankItem(rank));
        inventory.setItem(10, EditItems.getTablistItem(rank));
        inventory.setItem(11, EditItems.getChatItem(rank));
        inventory.setItem(12, EditItems.getNameTagItem(rank));
        inventory.setItem(14, EditItems.getTabPriorityItem(rank));
        inventory.setItem(15, EditItems.getPermissionItem(rank));
        inventory.setItem(16, EditItems.getHideOnSneakItem(rank));
        inventory.setItem(19, EditItems.getStatusItem(rank.tabIsActive()));
        inventory.setItem(20, EditItems.getStatusItem(rank.chatIsActive()));
        inventory.setItem(21, EditItems.getStatusItem(rank.nameTagIsActive()));
        inventory.setItem(23, EditItems.getPriorityStatusItem(rank.getPriority()));
        inventory.setItem(24, EditItems.getPermissionStatusItem(rank.getPermission()));
        inventory.setItem(25, EditItems.getStatusItem(rank.hideNameTagOnSneak()));
        inventory.setItem(27, GlobalItems.getBackItem());
        inventory.setItem(35, EditItems.getDeleteItem());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void clickHandler(@NotNull InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if (slot == 10) {
            Component additionalInfoMessage = new Message("Chat.Input.TabFormat", true).getMessage();
            new ChatInput(player, ChatInputType.RANK_TAB_FORMAT, 120, rank, additionalInfoMessage);
            player.closeInventory();
        } else if (slot == 11) {
            Component additionalInfoMessage = new Message("Chat.Input.ChatFormat", true).getMessage();
            new ChatInput(player, ChatInputType.RANK_CHAT_FORMAT, 120, rank, additionalInfoMessage);
            player.closeInventory();
        } else if (slot == 12) {
            Component additionalInfoMessage = new Message("Chat.Input.NameTagFormat", true).getMessage();
            new ChatInput(player, ChatInputType.RANK_NAME_TAG_FORMAT, 120, rank, additionalInfoMessage);
            player.closeInventory();
        } else if (slot == 19) {
            rank.setTabActive(!rank.tabIsActive());
            inventory.setItem(slot, EditItems.getStatusItem(rank.tabIsActive()));
            RankManager.getInstance().reloadDisplays();
            player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1.0F, 1.0F);
        } else if (slot == 20) {
            rank.setChatActive(!rank.chatIsActive());
            inventory.setItem(slot, EditItems.getStatusItem(rank.chatIsActive()));
            RankManager.getInstance().reloadDisplays();
            player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1.0F, 1.0F);
        } else if (slot == 21) {
            rank.setNameTagActive(!rank.nameTagIsActive());
            inventory.setItem(slot, EditItems.getStatusItem(rank.nameTagIsActive()));
            RankManager.getInstance().reloadDisplays();
            player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1.0F, 1.0F);
        } else if (slot == 23) {
            Component additionalInfoMessage = new Message("Chat.Input.TabPriority", true).getMessage();
            new ChatInput(player, ChatInputType.RANK_TAB_PRIORITY, 30, rank, additionalInfoMessage);
            player.closeInventory();
        } else if (slot == 24) {
            if (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
                rank.setPermission(null);
                inventory.setItem(slot, EditItems.getPermissionStatusItem(rank.getPermission()));
                RankManager.getInstance().reloadDisplays();
                player.playSound(player, Sound.BLOCK_LAVA_POP, 1.0F, 1.0F);
            } else {
                Component additionalInfoMessage = new Message("Chat.Input.Permission", true).getMessage();
                new ChatInput(player, ChatInputType.RANK_PERMISSION, 30, rank, additionalInfoMessage);
                player.closeInventory();
            }
        } else if (slot == 25) {
            rank.setHideNameTagOnSneak(!rank.hideNameTagOnSneak());
            inventory.setItem(slot, EditItems.getStatusItem(rank.hideNameTagOnSneak()));
            RankManager.getInstance().reloadDisplays();
            player.playSound(player, Sound.BLOCK_LEVER_CLICK, 1.0F, 1.0F);
        } else if (slot == 27) {
            player.openInventory(new RankOverviewGui().getInventory());
            player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
        } else if (slot == 35 && (event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT)) {
            rank.delete();
            player.openInventory(new RankOverviewGui().getInventory());
            player.playSound(player, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);
        }
    }
}
