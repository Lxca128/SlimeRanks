package de.lxca.slimeRanks.objects;

import de.lxca.slimeRanks.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

public class RankManager {

    private static RankManager instance;
    private static final ArrayList<Rank> ranks = new ArrayList<>();

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

    public void reloadDisplays() {
        PlayerNameTag.clearPlayerNameTags();
        for (World world : Bukkit.getWorlds()) {
            PlayerNameTag.clearBuggyNameTags(world);
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            Rank rank = RankManager.instance.getPlayerRank(player);

            if (rank != null && rank.tabIsActive()) {
                player.playerListName(rank.getTabFormat(player));
                player.setPlayerListOrder(rank.getTabPriority());
                PlayerNameTag.getPlayerNameTag(player);
            } else {
                player.playerListName(player.name());
                player.setPlayerListOrder(0);
            }
        }
    }
}
