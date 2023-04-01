package com.itkeller.smpsystems.Commands.Systems;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.itkeller.smpsystems.Utils.Error;
import com.itkeller.smpsystems.Utils.Permissions.PermissionManager;

import net.md_5.bungee.api.ChatColor;

public class InvCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(PermissionManager.hasPermission(sender, "inv")){
                if(args.length==1){
                    Player reciever=Bukkit.getPlayer(args[0]);
                    if(reciever!=null){
                        player.openInventory(reciever.getInventory());                

                    }else{player.sendMessage(ChatColor.WHITE+args[0]+ChatColor.RED+" was not found");}
                }else{Error.wrongCMD(player);}//TODO:
            }else{Error.noPermission(player);}
        }else{Error.noPlayer(sender);}
        return false;
    }
        
    
}


    