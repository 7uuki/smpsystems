package com.itkeller.smpsystems.Commands.Systems;


import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.swing.Action;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.itkeller.smpsystems.Utils.Helper.Error;
import com.itkeller.smpsystems.Utils.Permissions.PermissionManager;
import com.itkeller.smpsystems.Utils.Position.PositionManager;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;

public class PositionCMD implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println(command.getName());
        if(sender instanceof Player){
            Player player = (Player) sender;
            PositionManager positionManager=new PositionManager("positions", player.getUniqueId());
            if(PermissionManager.hasPermission(sender, "position")){
                if(command.getName().equals("position")){
                    if(args.length==1){
                        //add new
                        String locname=args[0];
                        if(locname.contains(".")){
                            player.sendMessage("§c You cannt have '.' in the name");
                            return false;
                        }
                        if(positionManager.exsists(locname)){
                            sendPosition(player, locname);
                        }else{
                            positionManager.set(locname, player.getLocation());
                            TextComponent msg = new TextComponent("§7[§8POS§7] §2created position "+ChatColor.DARK_GREEN+ChatColor.BOLD+locname);
                            TextComponent open = new TextComponent(" §e[§lOPEN§r§e]");
                            open.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pos "+locname)); 
                            open.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§fClick to "+ChatColor.YELLOW+ChatColor.BOLD+"OPEN §rthe position")));
                            msg.addExtra(open);
                            TextComponent send = new TextComponent(" §b[§lCOPY§r§b]");
                            send.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, getChatPos(positionManager.get(locname)))); 
                            send.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§fClick to "+ChatColor.AQUA+ChatColor.BOLD+"COPY §rthe position")));
                            msg.addExtra(send);
                            
                            player.spigot().sendMessage(msg);
                        }
                    }else{Bukkit.dispatchCommand(player, "poslist");}        
                }else if(command.getName().equals("positionlist")){
                    Map<String, Location> unsortedMap = positionManager.getAll();
                    List<String> sortedKeys = new ArrayList<>(unsortedMap.keySet());
                    Collections.sort(sortedKeys);
                    player.sendMessage("----------------------------------");
                    if(sortedKeys.isEmpty()){
                        player.sendMessage("§cYou have no positions stored");

                    }else{
                        for (String key : sortedKeys) {
                            sendPosition(player, key);
                        }
                    }
                    
                    player.sendMessage("----------------------------------");

                }else if(command.getName().equals("positiondelete")){
                    if(args.length==1){
                        String position=args[0];
                        if(positionManager.exsists(position)){
                            positionManager.delete(position);
                            player.sendMessage("§a Deleted position "+position);
                        }else{
                            player.sendMessage("§cYou dont have a position with the name "+position);

                        }
                    }else{Error.wrongCMD(player);}

                }else if(command.getName().equals("positionset")){
                    if(args.length>=1){
                        String position=args[0];
                        if(positionManager.exsists(position)){
                            int x;int y; int z;World world=positionManager.get(position).getWorld();
                            if(args.length==4){
                                x=(int)Double.parseDouble(args[1]);
                                y=(int)Double.parseDouble(args[2]);
                                z=(int)Double.parseDouble(args[3]);
                            }else if(args.length==1){
                                x=(int)player.getLocation().getX();
                                y=(int)player.getLocation().getY();
                                z=(int)player.getLocation().getZ();
                            }else if(args.length==5){
                                x=(int)Double.parseDouble(args[1]);
                                y=(int)Double.parseDouble(args[2]);
                                z=(int)Double.parseDouble(args[3]);
                                if(args[4].equalsIgnoreCase("e")||args[4].equalsIgnoreCase("end")||args[4].equalsIgnoreCase("the_end")){
                                    world=Bukkit.getWorld("world_the_end");
                                }else if(args[4].equalsIgnoreCase("n")||args[4].equalsIgnoreCase("nether")){
                                    world=Bukkit.getWorld("world_nether");
                                }else{
                                    world=Bukkit.getWorld("world");
                                }
                            }else{Error.wrongCMD(player);return false;}
                            Location newlocation=new Location(world, x, y, z);
                            positionManager.set(position, newlocation);
                            TextComponent copy = new TextComponent("§a changed §2§l"+position+" §e[§lOPEN§r§e]");
                            copy.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/pos "+position)); 
                            copy.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§fClick to "+ChatColor.YELLOW+ChatColor.BOLD+"OPEN §rthe position")));
                            player.spigot().sendMessage(copy);//
                        }else{
                            player.sendMessage("§cYou dont have a position with the name "+position);
                        }
                    }else{Error.wrongCMD(player);}
                    
                }else{Error.wrongCMD(player);}//TODO:
            }else{Error.noPermission(player);}
        }else{Error.noPlayer(sender);}
        return false;

    }

    private void sendPosition(Player player,String locname){
        PositionManager positionManager=new PositionManager("positions", player.getUniqueId());
        TextComponent msg = new TextComponent();
        
        msg.setText("§7[§8POS§7] §f§l"+locname+": ");

        TextComponent copy=new TextComponent();
        Location location=positionManager.get(locname);
        copy.setText(getPosString(location));
        copy.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, getChatPos(location))); 
        copy.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§fClick to "+ChatColor.AQUA+ChatColor.BOLD+"COPY §rto Clipboard")));
        msg.addExtra(copy);
        TextComponent change=new TextComponent();
        change.setText(" §e[§lCHANGE§r§e]");
        String world=player.getLocation().getWorld().getName().replace("world_", "");
        if(world.isBlank()){
            world="overworld";
        }
        change.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/positionset "+locname+" "+(int)player.getLocation().getX()+" "+(int)player.getLocation().getY()+" "+(int)player.getLocation().getZ()+" "+world)); 
        change.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§fClick to "+ChatColor.YELLOW+ChatColor.BOLD+"CHANGE §rthe position §o(the given values are your current coordinates)")));
        msg.addExtra(change);
        TextComponent delete=new TextComponent();
        delete.setText(" §c[§lDELETE§r§c]");
        delete.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/positiondelete "+locname)); 
        delete.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new Text("§fClick to "+ChatColor.RED+ChatColor.BOLD+"DELETE §rthe position")));
        msg.addExtra(delete);

        player.spigot().sendMessage(msg);


    }
    public static String getChatPos(Location location){
        String outpString="[";
        switch (location.getWorld().getName()) {
            case "world_the_end":outpString+="e";break;
            case "world_nether": outpString+="n";break;
        }
        outpString+=" x:"+String.valueOf(location.getX()).split("\\.")[0];
        outpString+=" y:"+String.valueOf(location.getY()).split("\\.")[0];
        outpString+=" z:"+String.valueOf(location.getZ()).split("\\.")[0];
        outpString+="]";
        return outpString;
    }
    public static String getPosString(Location loc){
        String pos=
            ChatColor.RED + "§lX" + "§r:" + ChatColor.WHITE + (int) loc.getX() 
            + ChatColor.GREEN + "§l Y" + "§r:" + ChatColor.WHITE + (int) loc.getY() 
            + ChatColor.BLUE + "§l Z" + "§r:" + ChatColor.WHITE + (int) loc.getZ();
        String world="";
        switch (loc.getWorld().getName()) {
            case "world_the_end": return "§5["+pos+ "§5]";
            case "world_nether": return "§4["+pos+ "§4]";
            default: return "§7[" +pos+ "§7]";
        }
    }
    
    
}
