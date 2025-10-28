package de.lxca.slimeRanks.guis;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.items.GlobalItems;
import de.lxca.slimeRanks.items.OverviewItems;
import de.lxca.slimeRanks.objects.Message;
import de.lxca.slimeRanks.objects.Rank;
import de.lxca.slimeRanks.objects.RankManager;
import de.lxca.slimeRanks.objects.dialogs.RankIdentifierDialog;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RankOverviewGui implements InventoryHolder {

    private static final int guiSize = 4 * 9;
    private static final List<Integer> rankSlots = List.of(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25);

    private final Inventory inventory;
    private ArrayList<Rank> ranks;
    private int page;

    public RankOverviewGui() {
        this.inventory = Main.getInstance().getServer().createInventory(
                this,
                guiSize,
                new Message("Gui.Overview.Title").getMessage()
        );
        setRanks();
        this.page = 1;

        setItems();
    }

    private void setRanks() {
        this.ranks = RankManager.getInstance().getRanks();
    }

    private void setItems() {
        for (int i = 0; i < guiSize; i++) {
           inventory.setItem(i, GlobalItems.getBackgroundItem());
        }

        inventory.setItem(4, OverviewItems.getTitleItem());
        for (Integer rankSlot : rankSlots) {
            if (ranks.size() > ((page - 1) * rankSlots.size()) + rankSlots.indexOf(rankSlot)) {
                Rank rank = ranks.get(((page - 1) * rankSlots.size()) + rankSlots.indexOf(rankSlot));
                inventory.setItem(rankSlot, GlobalItems.getRankItem(rank));
            } else {
                inventory.clear(rankSlot);
            }
        }
        inventory.setItem(30, GlobalItems.getPreviousPageItem(page > 1));
        inventory.setItem(32, GlobalItems.getNextPageItem(ranks.size() > (page * rankSlots.size())));
        inventory.setItem(34, OverviewItems.getReloadItem());
        inventory.setItem(35, OverviewItems.getCreateRankItem());
    }

    @Override
    public @NotNull Inventory getInventory() {
        return inventory;
    }

    public void clickHandler(@NotNull InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        int slot = event.getRawSlot();

        if (rankSlots.contains(slot)) {
            if (ranks.size() > ((page - 1) * rankSlots.size()) + rankSlots.indexOf(slot)) {
                Rank rank = ranks.get(((page - 1) * rankSlots.size()) + rankSlots.indexOf(slot));
                player.openInventory(new RankEditGui(rank).getInventory());
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
            }
        } else if (slot == 30) {
            if (page > 1) {
                page--;
                setItems();
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
            }
        } else if (slot == 32) {
            if (ranks.size() > page * rankSlots.size()) {
                page++;
                setItems();
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1.0F, 1.0F);
            }
        } else if (slot == 34) {
            Main.reload();
            setRanks();
            setItems();
            player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 2.0F);
        } else if (slot == 35) {
            new RankIdentifierDialog(player).open();
        }
    }
}
