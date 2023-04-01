package com.itkeller.smpsystems.Utils.PlayerUtility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.Helper.Chat;

import net.md_5.bungee.api.ChatColor;

public class PlayerStats {

    private Player lastmsgplayer;
    private Map<String,Object> stats=new HashMap<>();

    public PlayerStats(){
        InputStream configStream = Main.plugin.getResource("player_general.yml");

        if (configStream != null) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(configStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    //System.out.println(line);//TODO: not by hand
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }else{
          System.out.println("null");
        }

        stats.put("color", "#FFFFFF");
        stats.put("customname", null);
        stats.put("permission_group", "player");
        stats.put("deaths", 0);
    }

    public void setColor(String color) {stats.put("color", color);}
    public String getColor() {return stats.get("color").toString();}

    public void setCustomname(String customname) {stats.put("customname", customname);}
    public String getCustomname() {return stats.get("customname").toString();}

    public void setPermission_group(String permission_group) {stats.put("permission_group", permission_group);}
    public String getPermission_group() {return stats.get("permission_group").toString();}

    public ChatColor getChatColor() {return Chat.byHex(stats.get("color").toString());}

    public Map<String,Object> getStats(){return stats;}

    public Player getLastmsgplayer() {return lastmsgplayer;}
    public void setLastmsgplayer(Player lastmsgplayer) {this.lastmsgplayer = lastmsgplayer;}
}
