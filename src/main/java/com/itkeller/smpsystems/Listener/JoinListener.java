package com.itkeller.smpsystems.Listener;

import java.io.IOException;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.Helper.Scoreboard;
import com.itkeller.smpsystems.Utils.PlayerUtility.PlayerStats;

import net.md_5.bungee.api.ChatColor;

public class JoinListener implements Listener{

    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) throws IOException{
        Player player=event.getPlayer();        
        
        PlayerStats playerStats=Main.playerUtility.get(player);
        Scoreboard.updateplayer(player);
        Main.permissionManager.setPermissions(player);

        event.setJoinMessage(ChatColor.GREEN+"§l>> "+ChatColor.DARK_GREEN+ playerStats.getChatColor()+player.getName());

    }
    
}
