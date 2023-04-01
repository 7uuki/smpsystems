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
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class ChatListener implements Listener{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) throws IOException{
        Player player=event.getPlayer();
        //String msg =Main.playerUtility.get(player).getChatColor()+player.getName()+"§f: "+event.getMessage();
        TextComponent msg=new TextComponent(player.getName());
        msg.setColor(Main.playerUtility.get(player).getChatColor());
        msg.addExtra("§f: ");
        TextComponent messageContent=format(event.getMessage());
        msg.addExtra(messageContent);
        

        Bukkit.spigot().broadcast(msg);
        event.setCancelled(true);
        
    }


    public static TextComponent format(String msg){
        TextComponent textComponent=new TextComponent();
        textComponent.setText(msg);
        textComponent.setColor(ChatColor.WHITE);
        //Pattern -> Loc
        Pattern Position_PATTERN = Pattern.compile("\\[\\s*(?:(n|e|o)\\s+)?[xX]:\\s*(-?\\d+)\\s*[yY]:\\s*(-?\\d+)\\s*(?:[zZ]:\\s*(-?\\d+))?\\s*\\]");
        Matcher Position_MATCH = Position_PATTERN.matcher(msg);
        if (Position_MATCH.find()){
            String start=msg.substring(0, Position_MATCH.start());
            String copy=msg.substring(Position_MATCH.start(), Position_MATCH.end());
            String end=msg.substring(Position_MATCH.end(),msg.length());

            String optionalArg = Position_MATCH.group(1);
            int x = Integer.parseInt(Position_MATCH.group(2));
            int y = Integer.parseInt(Position_MATCH.group(3));

            String pos=
            ChatColor.RED + "§lX" + "§r:" + ChatColor.WHITE + x
            + ChatColor.GREEN + "§l Y" + "§r:" + ChatColor.WHITE + y;

            if (Position_MATCH.group(4) != null) {
                int z = Integer.parseInt(Position_MATCH.group(4));
                pos+=ChatColor.BLUE + "§l Z" + "§r:" + ChatColor.WHITE + z;
            }
            textComponent.setText(start);
           
            TextComponent mid=new TextComponent();
            if (optionalArg != null) {
                switch(optionalArg) {
                    case "e": mid.setText("§5["+pos+ "§5]");break;
                    case "n": mid.setText("§4["+pos+ "§4]");break;
                    default: mid.setText("§7[" +pos+ "§7]");break;
                }
            } else {
                mid.setText("§7[" +pos+ "§7]");
            }
            mid.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§fClick to "+ChatColor.AQUA+ChatColor.BOLD+"COPY §rto Clipboard")));
            mid.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, copy)); 

            textComponent.addExtra(mid);
            TextComponent finish=new TextComponent(end);
            textComponent.addExtra(finish);
        }
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
        
        return textComponent;
    }



    
    
}
