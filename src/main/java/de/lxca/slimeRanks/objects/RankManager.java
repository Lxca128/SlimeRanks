package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
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
        ArmorStand nameTag = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
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

    public void clearPlayerNameTags(@NotNull World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof ArmorStand nameTag && nameTag.getPersistentDataContainer().has(rankKey, PersistentDataType.BOOLEAN)) {
                playerNameTags.entrySet().removeIf(entry -> entry.getValue().equals(nameTag));
                nameTag.remove();
            }
        }
    }
}
