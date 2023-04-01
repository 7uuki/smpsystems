package com.itkeller.smpsystems.Commands.SimpleCMDs;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.itkeller.smpsystems.Utils.YML;

public class ManualDataCorrectionCMD implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(!player.hasPermission("")){//TODO
                return false;
            }
        }

        if(args.length==3){
            if(args[0].equalsIgnoreCase("load")){
                if(Bukkit.getPlayer(args[2]) != null){
                    Player player = Bukkit.getPlayer(args[2]);
                    if(args[1].equalsIgnoreCase("playerStats")){
                        YML yml=new YML(player.getUniqueId().toString(),"general");
                        //Main.playerUtility.setPlayerStats(player, yml.get);
                    

                    }else if(args[1].equalsIgnoreCase("inventory")){
        
                    }
                    
                }
            }
        }
        return false;
    }

    
    
}
