package com.itkeller.smpsystems.Commands.SimpleCMDs;

import org.bukkit.Bukkit;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.itkeller.Main;
import com.itkeller.smpsystems.Listener.ChatListener;

public class MsgCMD implements CommandExecutor{

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
            
            if(args.length==0){
                //TODO: ERRORMSG
            }else {
                boolean found = false;
                StringBuilder msg= new StringBuilder();
                for (String x : args)
                            if (!x.equals(args[0])) {
                                msg.append((x + " "));
                            }
                if(args[0].equalsIgnoreCase("CONSOLE")){
                    sender.sendMessage(ChatColor.LIGHT_PURPLE+ "to "+ChatColor.GRAY+"CONSOLE: §f"+String.valueOf(msg));
                    Bukkit.getConsoleSender().sendMessage(getPrefixFrom(sender)+String.valueOf(msg));
                }else if(sender.getName().equals("CONSOLE")){
                    Player receiver=Bukkit.getPlayer(args[0]);
                    receiver.sendMessage(getPrefixFrom(sender)+String.valueOf(msg));
                    Bukkit.getConsoleSender().sendMessage(getPrefixTo(receiver)+String.valueOf(msg));
                }else{
                    for(Player receiver : Bukkit.getServer().getOnlinePlayers()) {
                        Player player = (Player) sender;
                        if (ChatColor.stripColor(receiver.getName()).equalsIgnoreCase(args[0])) {
                            found=true;
                            TextComponent message_To=new TextComponent("§dto ");
                            TextComponent player_To=new TextComponent(receiver.getName());
                            player_To.setColor(Main.playerUtility.get(receiver).getChatColor());
                            player_To.addExtra("§f: ");
                            message_To.addExtra(player_To);

                            TextComponent message_From=new TextComponent("§dfrom ");
                            TextComponent player_From=new TextComponent(player.getName());
                            player_From.setColor(Main.playerUtility.get(player).getChatColor());
                            player_From.addExtra("§f: ");
                            message_From.addExtra(player_From);

                            TextComponent messageContent=ChatListener.format(msg.toString());
                            message_To.addExtra(messageContent);
                            message_From.addExtra(messageContent);

                            sender.spigot().sendMessage(message_To);
                            receiver.spigot().sendMessage(message_From);
                        }
                    }
                    if(!found){sender.sendMessage("player "+args[0]+" was not found");}
                }
                

            }

            return false;
        }

    public static String getPrefixTo(CommandSender receiver){
        ChatColor recievercolor=ChatColor.GRAY;
        if (receiver instanceof Player) {
            Player player=(Player) receiver;
            recievercolor = Main.playerUtility.get(player).getChatColor();
        }
        return ChatColor.LIGHT_PURPLE+ "to "+recievercolor+receiver.getName()+ChatColor.WHITE+": ";
    }
        
    public static String getPrefixFrom(CommandSender sender){
        ChatColor sendercolor=ChatColor.GRAY;
        if (sender instanceof Player) {
            Player player=(Player) sender;
            sendercolor = Main.playerUtility.get(player).getChatColor();
        }
        return ChatColor.LIGHT_PURPLE+ "from "+sendercolor+sender.getName()+ChatColor.WHITE+": ";
    }

}