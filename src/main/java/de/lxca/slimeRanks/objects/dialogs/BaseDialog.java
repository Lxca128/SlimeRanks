package de.lxca.slimeRanks.objects.dialogs;

import de.lxca.slimeRanks.objects.Rank;
import io.papermc.paper.dialog.Dialog;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BaseDialog {

    protected final Player player;
    protected final Rank rank;
    protected Dialog dialog;

    public BaseDialog(@NotNull Player player, @Nullable Rank rank, boolean buildDialog) {
        this.player = player;
        this.rank = rank;
        if (buildDialog) {
            this.dialog = buildDialog();
        }
    }

    protected abstract @NotNull Dialog buildDialog();

    public void open() {
        player.showDialog(dialog);
        player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1, 1);
    }
}
