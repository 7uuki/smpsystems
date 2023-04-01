package com.itkeller.smpsystems.Commands;

import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.Permissions.PermissionManager;
import com.itkeller.smpsystems.Utils.Position.PositionManager;


public class PermissionCMD implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //PLAYER UTILITY
        if (args[0].equalsIgnoreCase("player")){
            if(args[1].equalsIgnoreCase("print")){
                Main.playerUtility.printAll();
            }else if(args[1].equalsIgnoreCase("save")){
                Main.playerUtility.saveAll();
            }else if(args[1].equalsIgnoreCase("load")){
                Player player=Objects.requireNonNull(Bukkit.getPlayer(args[2]));
                if(player!=null){
                    Main.playerUtility.load(player);
                    Main.LOGGER.info("PlayerStats: ");
                    Main.playerUtility.printAll();
                }              
            }
        }
        //INVNETORY MANAGER
        if (args[0].equalsIgnoreCase("inv")){
            if(args[1].equalsIgnoreCase("print")){
                if(sender instanceof Player){
                    Player player=(Player) sender;
                    player.sendMessage("Inventorys: ");
                    Main.inventoryManager.printall(player);
                }else{
                    Main.LOGGER.info("Inventorys: ");
                    Main.inventoryManager.printall(null);
                }
            }else{
                UUID uuid=UUID.fromString(args[1]);
                String name=args[2];
                if(sender instanceof Player){
                    Player player= (Player)sender;
                    Inventory inv=Main.inventoryManager.get(uuid, name);
                    if(inv!=null){
                        player.openInventory(inv);
                    }
                }

            }
            
        }
        if (args[0].equalsIgnoreCase("test")){
            
            Player player=(Player) sender;
            PositionManager positionManager=new PositionManager("positions", player.getUniqueId());
            System.out.println(positionManager.exsists("test"));
            System.out.println(positionManager.exsists("wow"));

        }
        
        
        return false;
    }
    
}
