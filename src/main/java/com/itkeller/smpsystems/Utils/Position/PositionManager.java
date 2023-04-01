package com.itkeller.smpsystems.Utils.Position;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import com.itkeller.smpsystems.Utils.Helper.YML;

public class PositionManager {

    public final HashMap<PermissionKey,Location> map;
    private String ymlname;
    private UUID uuid;

    public PositionManager(String ymlname,UUID uuid) {
        map = new HashMap<>();
        this.ymlname=ymlname;
        this.uuid=uuid;
    }
    
    public HashMap<String,Location> getAll(){
        HashMap<String,Location> locations=new HashMap<>();
        YML yml=new YML(uuid.toString(),ymlname);
        for(String locname:yml.getfirstConfigurationSection("")){
            String world=(String) yml.get(locname+".world");
            double x=Double.parseDouble(yml.get(locname+".x").toString());
            double y=Double.parseDouble(yml.get(locname+".y").toString());
            double z=Double.parseDouble(yml.get(locname+".z").toString());
            Location location=new Location(Bukkit.getWorld(world), x, y, z);
            locations.put(locname, location);      
        }
        return locations;
    }

    public Location get(String name){
        HashMap<String,Location> locations=getAll();
        return locations.get(name);
    }
    public void delete(String name){
        YML yml=new YML(uuid.toString(),ymlname);
        yml.set(name, null);
    }

    public void set(String name,Location location){
        YML yml=new YML(uuid.toString(),ymlname);
        yml.set(name+".x",location.getX());
        yml.set(name+".y",location.getY());
        yml.set(name+".z",location.getZ());
        yml.set(name+".world",location.getWorld().getName());        
    }



    public boolean exsists(String name){
        return getAll().containsKey(name);
    }

    
    
}
