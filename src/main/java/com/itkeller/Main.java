package com.itkeller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.itkeller.smpsystems.Commands.PermissionCMD;
import com.itkeller.smpsystems.Commands.SimpleCMDs.DeathCounterCMD;
import com.itkeller.smpsystems.Commands.SimpleCMDs.MsgCMD;
import com.itkeller.smpsystems.Commands.Systems.BackPackCMD;
import com.itkeller.smpsystems.Commands.Systems.ColorCMD;
import com.itkeller.smpsystems.Commands.Systems.InvCMD;
import com.itkeller.smpsystems.Commands.Systems.SettingsCMD;
import com.itkeller.smpsystems.Listener.ChatListener;
import com.itkeller.smpsystems.Listener.JoinListener;
import com.itkeller.smpsystems.Listener.PlayerDeathListener;
import com.itkeller.smpsystems.Listener.QuitListener;
import com.itkeller.smpsystems.Utils.Config;
import com.itkeller.smpsystems.Utils.Scoreboard;
import com.itkeller.smpsystems.Utils.Inventory.InventoryManager;
import com.itkeller.smpsystems.Utils.MenuMaker.MenuListener;
import com.itkeller.smpsystems.Utils.Permissions.PermissionManager;
import com.itkeller.smpsystems.Utils.PlayerUtility.PlayerUtility;


/*
 * smpsystems java plugin
 */

public class Main extends JavaPlugin
{
  public static Main plugin;  
  public static PlayerUtility playerUtility;
  public static InventoryManager inventoryManager;
  public static PermissionManager permissionManager;
  public static String folder = "plugin/smpsystems";
  public static final Logger LOGGER=Logger.getLogger("SMPSystems");
  public static String prefix = "[smpsys] ";

  public void onEnable()
  {
    plugin = this;
    initiateStructure();
    
    inventoryManager=new InventoryManager("invs");
    playerUtility= new PlayerUtility("general");
    permissionManager = new PermissionManager("permissions");

    
    listenerRegistration();
    commandRegistration();
    updateplayers();
    LOGGER.info(Main.prefix+"Enabled");
    
  }

  public void onDisable()
  {
    inventoryManager.saveall();
    playerUtility.saveAll();
    LOGGER.info(Main.prefix+"Disabled");
  }

  public void listenerRegistration(){
    PluginManager pluginManager= Bukkit.getPluginManager();
    pluginManager.registerEvents(new JoinListener(), this);
    pluginManager.registerEvents(new QuitListener(), this);
    pluginManager.registerEvents(new ChatListener(), this);
    pluginManager.registerEvents(new MenuListener(), this);
    pluginManager.registerEvents(new PlayerDeathListener(), this);
  }
  private void commandRegistration() {
    getCommand("color").setExecutor(new ColorCMD());
    getCommand("deathcount").setExecutor(new DeathCounterCMD());
    getCommand("backpack").setExecutor(new BackPackCMD());
    getCommand("settings").setExecutor(new SettingsCMD());
    getCommand("message").setExecutor(new MsgCMD());
    getCommand("perm").setExecutor(new PermissionCMD());
    getCommand("inventory").setExecutor(new InvCMD());
  }

  private void initiateStructure(){
    File folder=Main.plugin.getDataFolder();
    if (!folder.exists()) {
      folder.mkdir();
      Config.createDefaultYML("permissions");
      Config.createDefaultYML("config");
      Main.LOGGER.info(Main.prefix+"structure initialied");
    }
  }
  private void updateplayers(){
    for(Player player:Bukkit.getOnlinePlayers()){
      playerUtility.get(player);
      Scoreboard.updateplayer(player);
    }
  }
}

  


//TODO: HELP
//Bed Queue
//Error System / Help System
//Settings
// * doDayNightCycle
//Permissions
//Posititions