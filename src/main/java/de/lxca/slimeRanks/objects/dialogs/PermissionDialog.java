package de.lxca.slimeRanks.objects.dialogs;

import de.lxca.slimeRanks.guis.RankEditGui;
import de.lxca.slimeRanks.objects.Message;
import de.lxca.slimeRanks.objects.Rank;
import de.lxca.slimeRanks.objects.RankManager;
import io.papermc.paper.dialog.Dialog;
import io.papermc.paper.registry.data.dialog.ActionButton;
import io.papermc.paper.registry.data.dialog.DialogBase;
import io.papermc.paper.registry.data.dialog.action.DialogAction;
import io.papermc.paper.registry.data.dialog.body.DialogBody;
import io.papermc.paper.registry.data.dialog.input.DialogInput;
import io.papermc.paper.registry.data.dialog.input.TextDialogInput;
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class PermissionDialog extends BaseDialog {
    public PermissionDialog(@NotNull Player player, Rank rank) {
        super(player, rank, false);
        this.dialog = buildDialog();
    }

    @Override
    protected @NotNull Dialog buildDialog() {
        return Dialog.create(builder -> builder.empty()
                .base(DialogBase.builder(getDialogTitle())
                        .body(List.of(
                                DialogBody.plainMessage(
                                        getBodyDescription(),
                                        300
                                )
                        ))
                        .inputs(List.of(
                                DialogInput.text(
                                        "permission",
                                        300,
                                        Component.empty(),
                                        false,
                                        rank.getPermission() != null ? rank.getPermission() : "",
                                        128,
                                        TextDialogInput.MultilineOptions.create(1, 17))
                        ))
                        .build()
                )
                .type(DialogType.confirmation(
                        ActionButton.create(
                                new Message("dialog.global.back").getMessage(),
                                null,
                                100,
                                null
                        ),
                        ActionButton.create(
                                new Message("dialog.global.save").getMessage(),
                                null,
                                100,
                                getSaveDialogAction()
                        )
                ))
        );
    }

    private Component getDialogTitle() {
        return new Message(
                "dialog.edit_rank.permission.title"
        ).getMessage();
    }

    private Component getBodyDescription() {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("rank", rank.getIdentifier());

        return new Message(
                "dialog.edit_rank.permission.description",
                replacements
        ).getMessage();
    }

    private void setNewPermission(@Nullable String newPermission) {
        rank.setPermission(newPermission);

        RankManager.getInstance().reloadDisplays();
    }

    @Contract(" -> new")
    private @NotNull DialogAction getSaveDialogAction() {
        return DialogAction.customClick(
                (view, audience) -> {
                    String newPermission = view.getText("permission");
                    if (newPermission == null || newPermission.isEmpty()) {
                        setNewPermission(null);
                    } else {
                        setNewPermission(newPermission);
                    }

                    if (audience instanceof Player player) {
                        player.playSound(player, Sound.ENTITY_VILLAGER_WORK_CARTOGRAPHER, 1, 1);
                        player.openInventory(new RankEditGui(rank).getInventory());
                    }
                },
                ClickCallback.Options.builder()
                        .uses(1)
                        .lifetime(ClickCallback.DEFAULT_LIFETIME)
                        .build()
        );
    }
}
