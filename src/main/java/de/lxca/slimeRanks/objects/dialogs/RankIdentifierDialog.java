package de.lxca.slimeRanks.objects.dialogs;

import de.lxca.slimeRanks.Main;
import de.lxca.slimeRanks.guis.RankEditGui;
import de.lxca.slimeRanks.objects.Message;
import de.lxca.slimeRanks.objects.Rank;
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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class RankIdentifierDialog extends BaseDialog {

    private static final String RANK_IDENTIFIER_KEY = "rank_identifier";

    public RankIdentifierDialog(@NotNull Player player) {
        super(player, null, false);
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
                                        RANK_IDENTIFIER_KEY,
                                        300,
                                        Component.empty(),
                                        false,
                                        "",
                                        32,
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
                                new Message("dialog.rank_identifier.create").getMessage(),
                                null,
                                100,
                                getCreateRankDialogAction()
                        )
                ))
        );
    }

    private Component getDialogTitle() {
        return new Message(
                "dialog.create_rank.rank_identifier.title"
        ).getMessage();
    }

    private Component getBodyDescription() {
        return new Message(
                "dialog.create_rank.rank_identifier.description"
        ).getMessage();
    }

    private boolean validateAlphabetic(@NotNull String string) {
        return string.chars().allMatch(Character::isAlphabetic);
    }

    private boolean validateNotExisting(@NotNull String string) {
        YamlConfiguration ranksYml = Main.getRanksYml().getYmlConfig();
        ConfigurationSection ranksSection = ranksYml.getConfigurationSection("Ranks");

        return ranksSection != null && !ranksSection.getKeys(false).contains(string);
    }

    @Contract(" -> new")
    private @NotNull DialogAction getCreateRankDialogAction() {
        return DialogAction.customClick(
                (view, audience) -> {
                    if (!(audience instanceof Player player)) {
                        return;
                    }

                    String input = view.getText(RANK_IDENTIFIER_KEY);
                    if (input == null || input.isEmpty() || !validateAlphabetic(input)) {
                        new Message(player,true, "dialog.create_rank.rank_identifier.error.only_letters");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        player.closeInventory();
                        return;
                    }

                    if (!validateNotExisting(input)) {
                        new Message(player, true, "dialog.create_rank.rank_identifier.error.already_used");
                        player.playSound(player, Sound.BLOCK_NOTE_BLOCK_BASS, 1, 1);
                        player.closeInventory();
                        return;
                    }

                    Rank createdRank = Rank.createRank(input);
                    if (createdRank == null) {
                        new Message(player, true, "dialog.create_rank.rank_identifier.error.creation_failed");
                        player.playSound(player, Sound.BLOCK_ANVIL_DESTROY, 1, 1);
                        player.closeInventory();
                        return;
                    }

                    player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                    player.openInventory(new RankEditGui(createdRank).getInventory());
                },
                ClickCallback.Options.builder()
                        .uses(1)
                        .lifetime(ClickCallback.DEFAULT_LIFETIME)
                        .build()
        );
    }
}
