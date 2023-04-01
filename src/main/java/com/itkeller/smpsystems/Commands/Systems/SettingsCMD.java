package com.itkeller.smpsystems.Commands.Systems;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.itkeller.smpsystems.Utils.Helper.Error;
import com.itkeller.smpsystems.Utils.Helper.YML;
import com.itkeller.smpsystems.Utils.MenuMaker.Icon;
import com.itkeller.smpsystems.Utils.MenuMaker.Menu;
import com.itkeller.smpsystems.Utils.MenuMaker.Icon.EmptyIcon;
import com.itkeller.smpsystems.Utils.MenuMaker.Icon.ExitIcon;
import com.itkeller.smpsystems.Utils.MenuMaker.Icon.UnclickableIcon;
import com.itkeller.smpsystems.Utils.Permissions.PermissionManager;


public class SettingsCMD implements CommandExecutor{
    private static boolean doDayNightCycleb=true;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if(PermissionManager.hasPermission(sender, "settings.open")){
                    Menu menu= new Menu("Settings", 4); 
                              
                    ItemStack active=Menu.createIcon(Material.GREEN_CONCRETE,"§a§lactive");
                    ItemStack inactive=Menu.createIcon(Material.RED_CONCRETE,"§4§linactive");

                    ItemStack doDayNightCycle_ON=Menu.createIcon(Material.CLOCK, "§f§ldoDayNighCycle("+"§a§ltrue"+"§f§l)", "§e§oClick to deactivate");
                    ItemStack doDayNightCycle_OFF=Menu.createIcon(Material.CLOCK,"§f§ldoDayNighCycle("+"§4§lfalse"+"§f§l)", "§e§oClick to activate");
                    UnclickableIcon doDayNightCycle_Indikator= new UnclickableIcon(active.clone());
                    Icon doDayNightCycle = new Icon(doDayNightCycle_ON.clone()){
                        @Override
                        public void click(Player player, Menu menu){
                            if (PermissionManager.hasPermission(sender, "settings.change")){
                                if(doDayNightCycleb){
                                    setItemStack(doDayNightCycle_OFF);
                                    doDayNightCycle_Indikator.setItemStack(inactive);
                                    doDayNightCycleb=false;
                                    Bukkit.broadcastMessage("Backpacks are now activated");
                                }else{
                                    setItemStack(doDayNightCycle_ON);
                                    doDayNightCycle_Indikator.setItemStack(active);
                                    doDayNightCycleb=true;
                                    Bukkit.broadcastMessage("Backpacks are now deactivated");
                                }
                            }else{Error.noPermission(player);}
                                                    
                        }@Override public void rightclick(Player player, Menu menu) {click(player, menu);}};

                    YML configYml=new YML("config");
                    ItemStack allow_backpack_ON=Menu.createIcon(Material.CHEST, "§f§lallow Backpacks("+"§a§ltrue"+"§f§l)", "§e§oClick to deactivate");
                    ItemStack allow_backpack_OFF=Menu.createIcon(Material.CHEST,"§f§lallow Backpacks("+"§4§lfalse"+"§f§l)", "§e§oClick to activate");
                    UnclickableIcon allow_backpack_Indikator;
                    ItemStack allow_backpack_startIcon;
                    if((boolean)configYml.get("settings.backpack.allow")){
                        allow_backpack_startIcon=allow_backpack_ON.clone();
                        allow_backpack_Indikator= new UnclickableIcon(active.clone());
                    }else{
                        allow_backpack_startIcon=allow_backpack_OFF.clone();
                        allow_backpack_Indikator= new UnclickableIcon(inactive.clone());
                    }
                    Icon allow_backpack = new Icon(allow_backpack_startIcon){
                        @Override
                        public void click(Player player, Menu menu){
                            if (PermissionManager.hasPermission(sender, "settings.change")){
                                boolean allow=(boolean)configYml.get("settings.backpack.allow");
                                if(allow){
                                    setItemStack(allow_backpack_OFF);
                                    allow_backpack_Indikator.setItemStack(inactive);
                                    configYml.set("settings.backpack.allow", false);
                                }else{
                                    setItemStack(allow_backpack_ON);
                                    allow_backpack_Indikator.setItemStack(active);
                                    configYml.set("settings.backpack.allow", true);
                                } 
                            }else{Error.noPermission(player);}                        
                        }@Override public void rightclick(Player player, Menu menu) {click(player, menu);}};
                    

                        

        
                    menu.addIcon(doDayNightCycle,10);
                    menu.addIcon(doDayNightCycle_Indikator,19);
                    menu.addIcon(allow_backpack,11);
                    menu.addIcon(allow_backpack_Indikator,20);
                    menu.addIcon(new ExitIcon(),31);
                    menu.addIcon(new EmptyIcon(),32);



                    player.openInventory(menu.getInventory());

                }else{Error.noPermission(player);}
            }else{Error.wrongCMD(player);}
        }else{Error.noPlayer(sender);}
        return false;
    }
    /* 
    private void togglePVP(Player player){
        World world=player.getWorld();
        if(world.getPVP()){
            world.setPVP(false);
            Bukkit.broadcastMessage("PvP has been disabled!");
        }else{
            world.setPVP(true);
            Bukkit.broadcastMessage("PvP has been enabled!");
        }

    }*/
    



    //TODO: Permissions



}
