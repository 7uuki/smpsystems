package com.itkeller.smpsystems.Commands.Systems;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.Helper.Error;
import com.itkeller.smpsystems.Utils.Helper.YML;
import com.itkeller.smpsystems.Utils.Permissions.PermissionManager;

import net.md_5.bungee.api.ChatColor;


public class BackPackCMD implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,String[] args){
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (PermissionManager.hasPermission(sender, "backpack.open")){
                YML yml=new YML("config");
                if((Boolean) yml.get("settings.backpack.allow")){
                    //Syst(Integer.parseInt(yml.get("settings.backpack.size").toString())));
                    player.openInventory(Main.inventoryManager.get(player.getUniqueId(),"Backpack",36));    
                }else{player.sendMessage(ChatColor.RED+"Backpacks are disabled");}
                            
            }else{Error.noPermission(player);}
        }//TODO ERROR
        return true;
    }

}