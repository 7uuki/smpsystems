package com.itkeller.smpsystems.Utils.Helper;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import com.itkeller.Main;

import net.md_5.bungee.api.ChatColor;

public class Scoreboard {
    public static org.bukkit.scoreboard.Scoreboard createScoreboard(){
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        org.bukkit.scoreboard.Scoreboard scoreboard = scoreboardManager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("customList", "dummy", "Custom List");
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        Score score = objective.getScore("first line");
        score.setScore(1);
        return scoreboard;
    }

    public static void updateplayer(Player player){
        player.setPlayerListName(
            Main.playerUtility.get(player).getChatColor()
            +player.getName()+" "
            +ChatColor.RED+"☠["
            +ChatColor.WHITE+String.valueOf(Main.playerUtility.get(player).getStats().get("deaths"))
            +ChatColor.RED+"] "
            + ChatColor.AQUA
                //+"⛏["+ChatColor.WHITE+String.valueOf(walked)+"m"+ChatColor.AQUA+"]"
        );

    }

}
