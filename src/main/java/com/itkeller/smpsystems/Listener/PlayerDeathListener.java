package com.itkeller.smpsystems.Listener;



import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.Helper.Scoreboard;

import net.md_5.bungee.api.ChatColor;

public class PlayerDeathListener implements org.bukkit.event.Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event){
        Player player=event.getEntity();
        Map<String,Object> stats=Main.playerUtility.get(player).getStats();
        int deathcount=(int)stats.get("deaths")+1;
        stats.put("deaths", deathcount);
        event.setDeathMessage(
            ChatColor.RED+"☠["+ChatColor.WHITE
            +String.valueOf(deathcount)
            +ChatColor.RED+"] "
            +ChatColor.WHITE + event.getDeathMessage().replace(
               player.getName(),Main.playerUtility.get(player).getChatColor() + player.getName()+ ChatColor.WHITE));
        Scoreboard.updateplayer(player);
    }

}
