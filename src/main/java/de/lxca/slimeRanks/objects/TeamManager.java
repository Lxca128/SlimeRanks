package de.lxca.slimeRanks.objects;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class TeamManager {

    private static TeamManager instance;
    private static Team invisibleNameTagTeam;

    private TeamManager() {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getMainScoreboard();

        String invisibleNameTagTeamName;
        do {
            invisibleNameTagTeamName = UUID.randomUUID().toString();
        } while (scoreboard.getTeam(invisibleNameTagTeamName) != null);

        invisibleNameTagTeam = scoreboard.registerNewTeam(invisibleNameTagTeamName);
        invisibleNameTagTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
    }

    public static synchronized TeamManager getInstance() {
        if (instance == null) {
            instance = new TeamManager();
        }

        return instance;
    }

    public void hidePlayerNameTag(@NotNull Player player) {
        if (invisibleNameTagTeam.hasEntity(player)) {
            return;
        }

        invisibleNameTagTeam.addEntity(player);
    }

    public void showPlayerNameTag(@NotNull Player player) {
        if (!invisibleNameTagTeam.hasEntity(player)) {
            return;
        }

        invisibleNameTagTeam.removeEntity(player);
    }

    public void removeInvisibleNameTagTeam() {
        invisibleNameTagTeam.unregister();
    }
}
