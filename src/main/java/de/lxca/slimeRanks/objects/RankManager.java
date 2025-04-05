package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Transformation;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class RankManager {

    private static RankManager instance;
    private static final ArrayList<Rank> ranks = new ArrayList<>();
    private static final HashMap<Player, TextDisplay> playerNameTags = new HashMap<>();
    private static final NamespacedKey rankKey = new NamespacedKey(Main.getInstance(), "slimeranks_rank");

    private RankManager() {
        reloadRanks();
    }

    public static synchronized RankManager getInstance() {
        if (instance == null) {
            instance = new RankManager();
        }

        return instance;
    }

    public void reloadRanks() {
        ranks.clear();
        YamlConfiguration ranksYml = Main.getRanksYml().getYmlConfig();
        ConfigurationSection ranksSection = ranksYml.getConfigurationSection("Ranks");

        if (ranksSection == null) {
            Main.getLogger(this.getClass()).warn("Could not find ranks section in ranks.yml. Please check the file and recreate if necessary.");
            return;
        }

        for (String identifier : ranksSection.getKeys(false)) {
            ranks.add(new Rank(identifier));
        }

        ranks.sort(Comparator.comparingInt(Rank::getRankPriority).reversed());
    }

    public ArrayList<Rank> getRanks() {
        return new ArrayList<>(ranks);
    }

    public int getRankCount() {
        return ranks.size();
    }

    public Rank getPlayerRank(@NotNull Player player) {
        for (Rank rank : ranks) {
            if (rank.getPermission() == null || player.hasPermission(rank.getPermission())) {
                return rank;
            }
        }

        return null;
    }

    public boolean shouldDisplayPlayerNameTag(@NotNull Player player, boolean invisibleCheck) {
        Rank rank = getPlayerRank(player);

        return rank != null && rank.nameTagIsActive() && player.getGameMode() != GameMode.SPECTATOR && (!invisibleCheck || !player.hasPotionEffect(PotionEffectType.INVISIBILITY));
    }

    public void addPlayerNameTag(Player player) {
        Rank rank = getPlayerRank(player);
        TextDisplay nameTag = player.getWorld().spawn(player.getLocation().add(0, 1.80, 0), TextDisplay.class);
        nameTag.setTransformation(new Transformation(new Vector3f(0, 0.25F, 0), new Quaternionf(), new Vector3f(1, 1, 1), new Quaternionf()));
        nameTag.text(rank.getNameTagFormat(player));
        nameTag.setBillboard(Display.Billboard.CENTER);
        nameTag.setAlignment(TextDisplay.TextAlignment.CENTER);
        nameTag.setSeeThrough(!player.isSneaking());
        nameTag.getPersistentDataContainer().set(rankKey, PersistentDataType.BOOLEAN, true);

        player.hideEntity(Main.getInstance(), nameTag);

        player.addPassenger(nameTag);
        playerNameTags.put(player, nameTag);
    }

    public void removePlayerNameTag(Player player) {
        TextDisplay nameTag = playerNameTags.get(player);

        if (nameTag == null) {
            return;
        }

        player.removePassenger(nameTag);
        playerNameTags.remove(player);
        nameTag.remove();
    }

    public void mountPlayerNameTag(@NotNull Player player) {
        TextDisplay nameTag = playerNameTags.get(player);

        if (nameTag == null) {
            return;
        }

        nameTag.teleport(player.getLocation());
        player.addPassenger(nameTag);
    }

    public void hidePlayerNameTag(@NotNull Player player, boolean completely) {
        TextDisplay nameTag = playerNameTags.get(player);

        if (nameTag == null) {
            return;
        }

        if (completely) {
            for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
                loopPlayer.hideEntity(Main.getInstance(), nameTag);
            }
        } else {
            nameTag.setSeeThrough(false);
        }
    }

    public void showPlayerNameTag(@NotNull Player player, boolean completely, boolean invisibleCheck) {
        if (!playerNameTags.containsKey(player) && shouldDisplayPlayerNameTag(player, invisibleCheck)) {
            addPlayerNameTag(player);
        }

        TextDisplay nameTag = playerNameTags.get(player);

        if (nameTag == null) {
            return;
        }

        for (Player loopPlayer : Bukkit.getOnlinePlayers()) {
            if (loopPlayer.equals(player)) {
                continue;
            }

            if (shouldDisplayPlayerNameTag(player, true) && loopPlayer.canSee(player)) {
                loopPlayer.showEntity(Main.getInstance(), nameTag);
            }
        }

        nameTag.setSeeThrough(completely);
    }

    public void setNameTagVisibility(@NotNull Player player, @NotNull Player viewer, boolean visible) {
        if (visible && !playerNameTags.containsKey(player) && shouldDisplayPlayerNameTag(player, false)) {
            addPlayerNameTag(player);
        }

        TextDisplay nameTag = playerNameTags.get(player);

        if (nameTag == null) {
            return;
        }

        if (visible) {
            viewer.showEntity(Main.getInstance(), nameTag);
        } else {
            viewer.hideEntity(Main.getInstance(), nameTag);
        }
    }

    public void clearPlayerNameTags(@NotNull World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof TextDisplay nameTag && nameTag.getPersistentDataContainer().has(rankKey, PersistentDataType.BOOLEAN)) {
                playerNameTags.entrySet().removeIf(entry -> entry.getValue().equals(nameTag));
                nameTag.remove();
            }
        }
    }

    public void reloadDisplays() {
        for (World world : Bukkit.getWorlds()) {
            clearPlayerNameTags(world);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            Rank rank = RankManager.instance.getPlayerRank(player);

            if (rank.tabIsActive()) {
                player.playerListName(rank.getTabFormat(player));
                player.setPlayerListOrder(rank.getTabPriority());
            } else {
                player.playerListName(player.name());
                player.setPlayerListOrder(0);
            }

            if (rank.nameTagIsActive()) {
                addPlayerNameTag(player);
            }
        }
    }
}
