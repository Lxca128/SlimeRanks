package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class RankManager {

    private static RankManager instance;
    private static final ArrayList<Rank> ranks = new ArrayList<>();
    private static final HashMap<Player, ArmorStand> playerNameTags = new HashMap<>();
    private static final NamespacedKey rankKey = new NamespacedKey(Main.getInstance(), "slimeranks_rank");

    private RankManager() {
        YamlConfiguration ranksYml = Main.getRanksYml().getYmlConfig();
        ConfigurationSection ranksSection = ranksYml.getConfigurationSection("Ranks");

        if (ranksSection == null) {
            return;
        }

        for (String identifier : ranksSection.getKeys(false)) {
            ranks.add(new Rank(identifier));
        }
    }

    public static synchronized RankManager getInstance() {
        if (instance == null) {
            instance = new RankManager();
        }

        return instance;
    }

    public Rank getPlayerRank(@NotNull Player player) {
        for (Rank rank : ranks) {
            if (rank.getPermission() == null || player.hasPermission(rank.getPermission())) {
                return rank;
            }
        }

        return null;
    }

    public void addPlayerNameTag(Player player) {
        Rank rank = getPlayerRank(player);
        ArmorStand nameTag = player.getWorld().spawn(player.getLocation().add(0, 1.25, 0), ArmorStand.class);
        nameTag.customName(rank.getNameTagFormat(player));
        nameTag.setCustomNameVisible(true);
        nameTag.setVisible(false);
        nameTag.setSmall(true);
        nameTag.setMarker(true);
        nameTag.setCanTick(false);
        nameTag.setInvulnerable(true);
        nameTag.getPersistentDataContainer().set(rankKey, PersistentDataType.BOOLEAN, true);

        player.hideEntity(Main.getInstance(), nameTag);

        player.addPassenger(nameTag);
        playerNameTags.put(player, nameTag);
    }

    public void removePlayerNameTag(Player player) {
        ArmorStand nameTag = playerNameTags.get(player);

        if (nameTag == null) {
            return;
        }

        player.removePassenger(nameTag);
        playerNameTags.remove(player);
        nameTag.remove();
    }

    public void mountPlayerNameTag(@NotNull Player player) {
        ArmorStand nameTag = playerNameTags.get(player);

        if (nameTag == null) {
            return;
        }

        nameTag.teleport(player.getLocation());
        player.addPassenger(nameTag);
    }

    public void clearPlayerNameTags(@NotNull World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof ArmorStand nameTag && nameTag.getPersistentDataContainer().has(rankKey, PersistentDataType.BOOLEAN)) {
                playerNameTags.entrySet().removeIf(entry -> entry.getValue().equals(nameTag));
                nameTag.remove();
            }
        }
    }

    public void reload() {
        for (World world : Bukkit.getWorlds()) {
            clearPlayerNameTags(world);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            Rank rank = RankManager.instance.getPlayerRank(player);

            if (rank.tabIsActive()) {
                player.playerListName(rank.getTabFormat(player));
                player.setPlayerListOrder(rank.getPriority());
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
