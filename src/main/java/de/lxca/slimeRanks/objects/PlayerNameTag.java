package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import org.bukkit.*;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;

public class PlayerNameTag {

    private static final HashMap<Player, PlayerNameTag> playerNameTags = new HashMap<>();
    private static final NamespacedKey nameTagKey = new NamespacedKey(Main.getInstance(), "slimeranks_rank");

    private final Player player;
    private final TextDisplay nameTag;

    private PlayerNameTag(Player player) {
        this.player = player;
        this.nameTag = spawnNameTag();

        playerNameTags.put(player, this);
    }

    private @NotNull TextDisplay spawnNameTag() {
        TextDisplay nameTag = player.getWorld().spawn(getNameTagLocation(player), TextDisplay.class);
        nameTag.setTransformation(new Transformation(
                new Vector3f(0, 0.25F, 0),
                new Quaternionf(),
                new Vector3f(1, 1, 1),
                new Quaternionf())
        );
        nameTag.setBillboard(Display.Billboard.CENTER);
        nameTag.setAlignment(TextDisplay.TextAlignment.CENTER);
        nameTag.getPersistentDataContainer().set(nameTagKey, PersistentDataType.BOOLEAN, true);
        setText(nameTag);
        setSeeThrough(nameTag, !player.isSneaking());

        setVisibility(nameTag, player, false);
        mount(nameTag);

        return nameTag;
    }

    private void setText(@NotNull TextDisplay nameTag) {
        nameTag.text(RankManager.getInstance().getPlayerRank(player).getNameTagFormat(player));
    }

    public void setSeeThrough(boolean seeThrough) {
        setSeeThrough(nameTag, seeThrough);
    }

    private void setSeeThrough(@NotNull TextDisplay nameTag, boolean seeThrough) {
        nameTag.setSeeThrough(seeThrough);
    }

    public void setVisibility(Player viewer, boolean visible) {
        setVisibility(nameTag, viewer, visible);
    }

    private void setVisibility(TextDisplay nameTag, Player viewer, boolean visible) {
        if (visible) {
            viewer.showEntity(Main.getInstance(), nameTag);
        } else {
            viewer.hideEntity(Main.getInstance(), nameTag);
        }
    }

    public void mount() {
        mount(nameTag);
    }

    public void mount(@NotNull TextDisplay nameTag) {
        if (player.getWorld() != nameTag.getWorld()) {
            nameTag.teleportAsync(getNameTagLocation(player));
        }
        player.addPassenger(nameTag);
    }

    public void hideForAll() {
        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            setVisibility(loopPlayer, false);
        }
    }

    public void showForAllPermittedPlayers() {
        if (!shouldDisplayPlayerNameTag(player, true)) {
            return;
        }

        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            if (player == loopPlayer || !loopPlayer.canSee(player)) {
                continue;
            }
            setVisibility(loopPlayer, true);
        }
    }

    public void remove() {
        removeTextDisplay(nameTag);
        playerNameTags.remove(player);
    }

    public static boolean hasNameTag(@NotNull Player player) {
        return playerNameTags.containsKey(player);
    }

    public synchronized static @Nullable PlayerNameTag getPlayerNameTag(@NotNull Player player) {
        if (playerNameTags.containsKey(player)) {
            return playerNameTags.get(player);
        } else if (RankManager.getInstance().getPlayerRank(player) != null) {
            return new PlayerNameTag(player);
        } else {
            return null;
        }
    }

    public static void clearPlayerNameTags() {
        HashMap<Player, PlayerNameTag> playerNameTags = new HashMap<>(PlayerNameTag.playerNameTags);

        for (PlayerNameTag playerNameTag : playerNameTags.values()) {
            playerNameTag.remove();
        }
    }

    public static void clearBuggyNameTags(@NotNull World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof TextDisplay nameTag && nameTag.getPersistentDataContainer().has(PlayerNameTag.nameTagKey, PersistentDataType.BOOLEAN)) {
                nameTag.remove();
            }
        }
    }

    public static boolean shouldDisplayPlayerNameTag(@NotNull Player player, boolean invisibleCheck) {
        Rank rank = RankManager.getInstance().getPlayerRank(player);

        return rank != null && rank.nameTagIsActive() && player.getGameMode() != GameMode.SPECTATOR && (!invisibleCheck || !player.hasPotionEffect(PotionEffectType.INVISIBILITY));
    }

    private static Location getNameTagLocation(@Nullable Player player) {
        if (player == null) {
            return null;
        }

        return player.getLocation().add(0, 1.80, 0);
    }

    private static void removeTextDisplay(@NotNull TextDisplay nameTag) {
        if (Main.isFolia()) {
            nameTag.getScheduler().run(
                    Main.getInstance(),
                    task -> nameTag.remove(),
                    null
            );
        } else {
            nameTag.remove();
        }
    }
}
