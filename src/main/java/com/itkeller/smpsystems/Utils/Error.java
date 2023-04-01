package com.itkeller.smpsystems.Utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Error {

    private static String Errorprefix=ChatColor.DARK_RED+"["+ChatColor.WHITE+"Error"+ChatColor.RED+"] "+ChatColor.RED;

    public static void noPermission(Player player){
        player.sendMessage(Errorprefix+"You dont't have the Permission do this");
    }
    public static void noPlayer(CommandSender sender){
        sender.sendMessage(Errorprefix+"You need to be a player to execute this command");
    }
    public static void wrongCMD(Player player){
        player.sendMessage(Errorprefix+"wrong CMD execution");//TODO
    }


    
}
