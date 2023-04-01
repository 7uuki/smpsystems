package com.itkeller.smpsystems.Utils.Helper;



import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class Chat {

    public static void sendCopyToChatMessage(Player player,String value,String msg) {
        TextComponent copytoChat = new TextComponent(msg);
        copytoChat.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, value));
        copytoChat.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§e§oClick to copy to Chat")));
        player.spigot().sendMessage(copytoChat);
    }
    public static boolean isValidHexCode(String input) {
        String hexCode = input.replaceAll("#", ""); // remove any '#' character
        return hexCode.matches("[0-9A-Fa-f]{6}"); // check if the remaining characters are valid hex digits
    }
    public static ChatColor byHex(String hex){
        String color=hex;
        if(!color.contains("#")){
            color="#"+color;
        }
        return net.md_5.bungee.api.ChatColor.of(color);
    }
    


}
