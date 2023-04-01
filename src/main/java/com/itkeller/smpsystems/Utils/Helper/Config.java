package com.itkeller.smpsystems.Utils.Helper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;


import org.bukkit.configuration.file.YamlConfiguration;

import com.itkeller.Main;

public class Config {

    
    public static void createDefaultYML(String ymlname){createDefaultYML(ymlname,ymlname,"");}

    public static void createDefaultYML(String resources_ymlname,String ymlname,String path){
        // Get the configuration file from the plugin jar as a resource
        resources_ymlname+=".yml";ymlname+=".yml";
        InputStream configStream = Main.plugin.getResource(resources_ymlname);
    
        // Create the plugin folder if it doesn't exist
        File folder=Main.plugin.getDataFolder();
    
        // Create the configuration file in the plugin folder if it doesn't exist
        File configFile = new File(folder.toPath()+path,ymlname);
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                Main.LOGGER.warning("§7[smpsystems] "+ymlname+".yml could not be created: " + e.getMessage());
            }
        }
    
        // Copy the default configuration file from the jar to the plugin folder
        if (configStream != null) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
            config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(configStream, StandardCharsets.UTF_8)));
            config.options().copyDefaults(true);
            try {
                config.save(configFile);
                Main.LOGGER.info(Main.prefix+ymlname+".yml initialied");
            } catch (IOException e) {
                Main.LOGGER.warning(Main.prefix+ymlname+".yml could not be initialied: " + e.getMessage());
            }
        }
    }


    
    


    
}
