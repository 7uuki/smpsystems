package com.itkeller.smpsystems.Listener;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.itkeller.Main;
import com.itkeller.smpsystems.Commands.Systems.ColorCMD;

import net.md_5.bungee.api.ChatColor;

public class ChatListener implements Listener{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) throws IOException{
        Player player=event.getPlayer();
        String msg =Main.playerUtility.get(player).getChatColor()+player.getName()+"§f: "+event.getMessage();
        
        Bukkit.broadcastMessage(format(msg));
        event.setCancelled(true);
        
    }


    public String format(String msg){
        //HEX into ChatColor 
        Pattern HEX_PATTERN=Pattern.compile("#[a-fA-F0-9]{6}");
        Matcher HEX_MATCH = HEX_PATTERN.matcher(msg);
        while (HEX_MATCH.find()){
            String color= msg.substring(HEX_MATCH.start(), HEX_MATCH.end());
            msg=msg.replace(color, ColorCMD.byHex(color)+"");
        }
        Pattern COLORCODE_PATTERN=Pattern.compile("$*[a-fA-F0-9l-oL-O]");
        Matcher COLORCODE_MATCH = COLORCODE_PATTERN.matcher(msg);
        while (COLORCODE_MATCH.find()){
            msg=msg.replace("$*", "§");
        }
        return msg;
    }

    public static ChatColor hexToChatColor(String hexColor) {
        hexColor = hexColor.replace("#", "");
        int colorCode = Integer.parseInt(hexColor, 16);
        ChatColor chatColor = ChatColor.getByChar(Integer.toHexString(colorCode).charAt(0));
        return chatColor;
    }

    
    
}
