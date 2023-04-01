package com.itkeller.smpsystems.Listener;

import java.io.IOException;
import java.io.InputStream;

import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;

import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.Scoreboard;
import com.itkeller.smpsystems.Utils.YML;
import com.itkeller.smpsystems.Utils.Permissions.PermissionManager;
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
