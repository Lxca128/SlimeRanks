package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class RankManager {

    private static RankManager instance;
    private static final ArrayList<Rank> ranks = new ArrayList<>();
    private static final HashMap<Player, TextDisplay> playerNameTags = new HashMap<>();
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
        TextDisplay textDisplay = player.getWorld().spawn(player.getLocation(), TextDisplay.class);
        textDisplay.text(rank.getNameTagFormat(player));
        textDisplay.setBillboard(Display.Billboard.CENTER);
        textDisplay.getPersistentDataContainer().set(rankKey, PersistentDataType.BOOLEAN, true);

        player.hideEntity(Main.getInstance(), textDisplay);

        player.addPassenger(textDisplay);
        playerNameTags.put(player, textDisplay);
    }

    public void removePlayerNameTag(Player player) {
        TextDisplay textDisplay = playerNameTags.get(player);

        if (textDisplay == null) {
            return;
        }

        player.removePassenger(textDisplay);
        playerNameTags.remove(player);
        textDisplay.remove();
    }

    public void clearPlayerNameTags(@NotNull World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof TextDisplay textDisplay && textDisplay.getPersistentDataContainer().has(rankKey, PersistentDataType.BOOLEAN)) {
                playerNameTags.entrySet().removeIf(entry -> entry.getValue().equals(textDisplay));
                textDisplay.remove();
            }
        }
    }
}
