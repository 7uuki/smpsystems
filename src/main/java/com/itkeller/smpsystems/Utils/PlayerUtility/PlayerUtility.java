package com.itkeller.smpsystems.Utils.PlayerUtility;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.Helper.YML;

public class PlayerUtility {

    private static HashMap<UUID,PlayerStats> map;
    private String ymlname;
    private String prefix ="Stats";

    public PlayerUtility(String ymlname){
        map = new HashMap<>();
        this.ymlname=ymlname;
    }
    //returns an Inventory out of the map. If not found in map or yml it creates yml
    public PlayerStats get(Player player){
        if(map.get(player.getUniqueId()) == null){
            load(player);
        }
        return map.get(player.getUniqueId());
    }

    /**
     * @param player
     * creates a Objekt PlayerStats out of the config and puts it into the list
     */
    public void load(Player player){
        PlayerStats playerStats=new PlayerStats();
        YML yml=new YML(player.getUniqueId().toString(),ymlname);
        if(!yml.isEmptyYml()){
            for (String stat: yml.getfirstConfigurationSection(prefix)) {
                String key=stat;
                Object value=yml.get(prefix+"."+stat);
                if(value!=null){
                    playerStats.getStats().put(key, value);
                }
                
            }  
        }      
        map.put(player.getUniqueId(),playerStats);
        
    }
    
    public void setPlayerStats(Player player,PlayerStats stats){
        map.put(player.getUniqueId(),stats);
    }

    public void saveAll(){
        for(Map.Entry<UUID, PlayerStats> entry : map.entrySet()) {
            UUID uuid = entry.getKey();
            PlayerStats playerStats = entry.getValue();
            YML yml=new YML(uuid.toString(),ymlname);
            for (Map.Entry<String,Object> stat: playerStats.getStats().entrySet()) {
                String key=stat.getKey();
                Object value=stat.getValue();
                yml.set(prefix+"."+key, value);
            }
            //yml.set("name", Bukkit.getPlayer(uuid).getName());
        }
        Main.LOGGER.info("saved all PlayerData into: '"+ymlname+".yml'");
    }

    public void printAll(){
        Main.LOGGER.info("PlayerUtility: ");
        for(HashMap.Entry<UUID,PlayerStats> playerStats:map.entrySet()){
            Main.LOGGER.info("* "+playerStats.getKey().toString());
            for(Map.Entry<String,Object> playerStats_entry:playerStats.getValue().getStats().entrySet()){
                Main.LOGGER.info("  - "+playerStats_entry.getKey()+": "+playerStats_entry.getValue());
            }


        }
        
    }

}
