package com.itkeller.smpsystems.Commands.Systems;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.Helper.Chat;
import com.itkeller.smpsystems.Utils.Helper.Error;
import com.itkeller.smpsystems.Utils.Helper.Scoreboard;
import com.itkeller.smpsystems.Utils.MenuMaker.Icon;
import com.itkeller.smpsystems.Utils.MenuMaker.Menu;
import com.itkeller.smpsystems.Utils.Permissions.PermissionManager;
import com.itkeller.smpsystems.Utils.PlayerUtility.PlayerUtility;

import net.md_5.bungee.api.ChatColor;


public class ColorCMD implements CommandExecutor{

    public static String changeString="§e§oClick to change";
    public static String colorString=" Preset";

    public static Map<String, String> colormap;
    static {
        colormap = new HashMap<>();
        colormap.put("Red", "#AA0000");
        colormap.put("Orange", "#FFAA00");
        colormap.put("Yellow", "#FFFF55");
        colormap.put("Pink", "#FF5555");
        colormap.put("Megenta", "#FF55FF");
        colormap.put("Purple", "#AA00AA");
        colormap.put("Blue", "#0000AA");
        colormap.put("Cyan", "#00AAAA");
        colormap.put("Light Blue", "#55FFFF");
        colormap.put("Green", "#00AA00");
        colormap.put("Lime", "#55FF55");
        colormap.put("White", "#FFFFFF");
        colormap.put("Light Gray", "#AAAAAA");
        colormap.put("Gray", "#555555");
        colormap.put("Black", "#000000");
    }


    private Menu menu;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(args.length==0 || args.length>1){
                if (PermissionManager.hasPermission(sender, "color")){
                    menu= new Menu("§4§lC§6§lh§e§la§c§lt §d§lC§5§lo§1§ll§3§lo§b§lr §2§lP§a§li§f§lc§7§lk§8§le§0§lr", 2);
                    menu.addIcon(createColor(Material.RED_DYE,"Red"),0);
                    menu.addIcon(createColor(Material.ORANGE_DYE,"Orange"),1);
                    menu.addIcon(createColor(Material.YELLOW_DYE,"Yellow"),2);
                    menu.addIcon(createColor(Material.PINK_DYE,"Pink"),3);
                    menu.addIcon(createColor(Material.MAGENTA_DYE,"Megenta"),4);
                    menu.addIcon(createColor(Material.PURPLE_DYE,"Purple"),5);
                    menu.addIcon(createColor(Material.BLUE_DYE,"Blue"),6);
                    menu.addIcon(createColor(Material.CYAN_DYE,"Cyan"),7);
                    menu.addIcon(createColor(Material.LIGHT_BLUE_DYE,"Light Blue"),8);
                    menu.addIcon(createColor(Material.GREEN_DYE, "Green"),10);
                    menu.addIcon(createColor(Material.LIME_DYE, "Lime"),11);
                    menu.addIcon(createColor(Material.BONE_MEAL, "White"),12);
                    menu.addIcon(createColor(Material.LIGHT_GRAY_DYE, "Light Gray"),14);
                    menu.addIcon(createColor(Material.GRAY_DYE, "Gray"),15);
                    menu.addIcon(createColor(Material.BLACK_DYE, "Black"),16);

                    Icon custom = new Icon(Menu.createIcon(Material.LOOM,"§f§l"+"Custom Color","§7Your current color is ","§e§oLeftlick to change","§6§oRightclick to copy your color")){
                        @Override
                        public void click(Player player, Menu menu){
                            //Do nothing
                            player.closeInventory();
                            Chat.sendCopyToChatMessage(player,"/color #","Execute /color followed by your desired color in Hex §7(f.e. /color #DF4141)"+" §e§o[Copy to Chat]");
                            
                        }@Override public void rightclick(Player player, Menu menu) {
                            player.closeInventory();
                            Chat.sendCopyToChatMessage(player,Main.playerUtility.get(player).getColor(),"§e§o[Copy to Chat]");
                        }};
                    menu.addIcon(custom,13);
                    refreshLoom(player);
                    player.openInventory(menu.getInventory());
                }else{Error.noPermission(player);}
            }else if(args.length==1){
                String color=args[0];
                if(Chat.isValidHexCode(color)){
                    changeplayerdisplaycolor(player, color);
                }else{player.sendMessage("§cError: "+color+" is not a valid hexcode");}
            }
            

            

        }
        return false;
    }

    
    private void refreshLoom(Player player){
        String currentcolor= Main.playerUtility.get(player).getColor();
        String currentcolorString= currentcolor;
        if(colormap.containsValue(currentcolor)){
            for (Entry<String, String> entry : colormap.entrySet()) {
                if (entry.getValue().equals(currentcolor)) {
                    currentcolorString=entry.getKey()+" Preset";
                    menu.getIcon(13).setItemStack(Menu.createIcon(Material.LOOM,"§f§l"+"Custom Color","§7Your current color is "+byHex(currentcolor)+"§l"+currentcolorString,"§e§oClick to change to a custom color"));
                }
            }
        }else{menu.getIcon(13).setItemStack(Menu.createIcon(Material.LOOM,"§f§l"+"Custom Color","§7Your current color is "+byHex(currentcolor)+"§l"+currentcolorString,"§e§oLeftclick to change color","§6§oRightclick to copy your colorcode"));}
        menu.update();   
    }

    private Icon createColor(Material material,String title){
        String color= colormap.get(title);
        Icon icon = new Icon(Menu.createIcon(material,byHex(color)+"§l"+title+colorString,changeString)){
            @Override
            public void click(Player player, Menu menu){
                changeplayerdisplaycolor(player, color);
                refreshLoom(player);
            }@Override public void rightclick(Player player, Menu menu) {click(player, menu);}};
        return icon;

    }
    public static void changeplayerdisplaycolor(Player player,String color){   
        PlayerUtility playerUtility=Main.playerUtility;
        playerUtility.get(player).setColor(color);
        player.sendMessage("Your Name will now appear as "+byHex(playerUtility.get(player).getColor())+player.getName());
        Scoreboard.updateplayer(player);
    }
    public static String getplayerdisplaycolor(Player player){   
       
        PlayerUtility playerUtility=Main.playerUtility;
        return playerUtility.get(player).getColor();
    }
    public static ChatColor byHex(String hex){
        String color=hex;
        if(!color.contains("#")){
            color="#"+color;
        }
        return net.md_5.bungee.api.ChatColor.of(color);
    }
    
}
