package com.itkeller.smpsystems.Utils.Helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.itkeller.Main;

public class YML {
    private static String folder = Main.folder;
    private File file;
    private static FileConfiguration config;
    private static boolean exsists;

    public boolean exsists() {
        return exsists;
    }
    public boolean exsists(String key) {
        return exsists;
    }
    public YML(String ymlPath){
        file = new File("plugins/smpsystems/"+ymlPath+".yml");
        exsists = true;
        config= YamlConfiguration.loadConfiguration(file);
        try {
            config.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Main.LOGGER.info("ERROR: couldn't save"+File.listRoots().toString());
            e.printStackTrace();
        }       
        //Main.LOGGER.info(Main.prefix+"created new YML at: "+folder+ymlPath+".yml");
    }
    public YML(String path,String ymlname){
        this(path+"/"+ymlname);
    }

    public Object get(String key){
        //Main.LOGGER.info("get "+key);
        if(exsists){return config.get(key);}
        return null;
    }

    public void set(String key,Object value){
        if(exsists){
            config.set(key,value);
            try {
                config.save(file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Main.LOGGER.warning(Main.prefix+"ERROR: couldn't save "+File.listRoots().toString()+e.getMessage());
                e.printStackTrace();
            }
        }
        
    }
    
    public static boolean checkYamlExists(File yml) {
        return yml.exists() && !yml.isDirectory();
    }
    public boolean isEmptyYml() {
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    scanner.close();
                    return false;
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static ArrayList<Object> getAllList(String path){
        Optional<List<?>> listitems = Optional.ofNullable(config.getList(path));
        ArrayList<Object> items=new ArrayList<>();
        listitems.ifPresent(l -> {
            for (Object element : l) {
                items.add(element);
            }
        });
        return items;
    }

    public Set<String> getfirstConfigurationSection(String path){
        return config.getConfigurationSection(path).getKeys(false);
    }

    
    

}
