package com.itkeller.smpsystems.Commands.SimpleCMDs;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.Helper.Error;
import com.itkeller.smpsystems.Utils.Permissions.PermissionManager;

import net.md_5.bungee.api.ChatColor;

public class DeathCounterCMD implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if(PermissionManager.hasPermission(sender, "deathcounter")){
                    player.sendMessage(ChatColor.RED+"Your deaths ["+ChatColor.WHITE+String.valueOf(Main.playerUtility.get(player).getStats().get("deaths"))+ChatColor.RED+"]");
                }else{Error.noPermission(player);}
                
            }  else {
                //TODO HELP
            }
        }//TODO ERROR
        return false;
    }
    
}
//TODO: Permissions
