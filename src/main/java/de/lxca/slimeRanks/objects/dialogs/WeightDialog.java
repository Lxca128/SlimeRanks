package de.lxca.slimeRanks.objects.dialogs;

import de.lxca.slimeRanks.enums.WeightType;
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
import io.papermc.paper.registry.data.dialog.type.DialogType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickCallback;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class WeightDialog extends BaseDialog {

    private static final String WEIGHT_KEY = "weight";
    private final WeightType weightType;

    public WeightDialog(@NotNull Player player, Rank rank, WeightType weightType) {
        super(player, rank, false);
        this.weightType = weightType;
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
                                DialogInput.numberRange(WEIGHT_KEY, Component.empty(), 0, Math.max(getCurrentWeight(), 100))
                                        .step(1f)
                                        .initial((float) getCurrentWeight())
                                        .labelFormat("%s %s")
                                        .width(300)
                                        .build()
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

    private int getCurrentWeight() {
        if (weightType == WeightType.RANK) {
            return rank.getRankPriority();
        } else {
            return rank.getTabPriority();
        }
    }

    private Component getDialogTitle() {
        if (weightType == WeightType.RANK) {
            return new Message("dialog.edit_rank.rank_weight.title").getMessage();
        } else {
            return new Message("dialog.edit_rank.tab_weight.title").getMessage();
        }
    }

    private Component getBodyDescription() {
        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("rank", rank.getIdentifier());

        if (weightType == WeightType.RANK) {
            return new Message(
                    "dialog.edit_rank.rank_weight.description",
                    replacements
            ).getMessage();
        } else {
            return new Message(
                    "dialog.edit_rank.tab_weight.description",
                    replacements
            ).getMessage();
        }
    }

    private void setNewWeight(int newWeight) {
        if (weightType == WeightType.RANK) {
            rank.setRankPriority(newWeight);
        } else {
            rank.setTabPriority(newWeight);
        }

        RankManager.getInstance().reloadDisplays();
    }

    @Contract(" -> new")
    private @NotNull DialogAction getSaveDialogAction() {
        return DialogAction.customClick(
                (view, audience) -> {
                    Float weightFloat = view.getFloat(WEIGHT_KEY);
                    int newWeight = weightFloat != null ? weightFloat.intValue() : getCurrentWeight();

                    setNewWeight(newWeight);
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
