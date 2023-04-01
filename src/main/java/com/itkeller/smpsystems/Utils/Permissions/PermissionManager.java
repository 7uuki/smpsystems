package com.itkeller.smpsystems.Utils.Permissions;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.YML;
import com.itkeller.smpsystems.Utils.PlayerUtility.PlayerStats;

public class PermissionManager {
    //every group with all it inheritances f.e. {mod=[mod, player, miniplayer], admin=[admin, mod, player, miniplayer], miniplayer=[miniplayer, player], player=[player, miniplayer]}
    public static Map<String,ArrayList<Object>> groupinheritance;
    //every group with all it inheritances f.e. {mod=[mod, player, miniplayer], admin=[admin, mod, player, miniplayer], miniplayer=[miniplayer, player], player=[player, miniplayer]}
    public static Map<String,ArrayList<Object>> grouppermissions;
    public static String prefix = "smpsystems.";

    private final String ymlname;

    public PermissionManager(String ymlname) {
        this.ymlname=ymlname;
        YML yml=new YML("", ymlname);
        load();
    }
    public void setPermissions(Player player){
        PlayerStats playerStats=Main.playerUtility.get(player);
        PermissionAttachment attachment= player.addAttachment(Main.plugin);
        PermissionManager permissionManager=Main.permissionManager;
        System.out.println("group: "+playerStats.getPermission_group());
        for(Object permission:permissionManager.getPermissions(playerStats.getPermission_group())){
            System.out.println("set permission: "+prefix+permission.toString());
            attachment.setPermission(prefix+permission.toString(), true);
        }
    }
    public ArrayList<Object> getPermissions(String groupname){
        ArrayList<Object> retList=new ArrayList<>();
        if(groupinheritance.containsKey(groupname)){
            for(Object inheritance:groupinheritance.get(groupname)){
                if(grouppermissions.containsKey(inheritance)){
                    for (Object permission:grouppermissions.get(inheritance)){
                        retList.add(permission);
                    }
                }
                
            }
        }
        System.out.println("grouppermissions: "+retList.toString());
        return retList;

    }
    public void load(){
        try{
            YML yml=new YML("", ymlname);
            groupinheritance=new HashMap<>();
            grouppermissions=new HashMap<>();
            //puls every group and ther inherintance out of the yml and puts it into groupinheritance
            for(String group:yml.getfirstConfigurationSection("groups")){
                ArrayList<Object> group_inh=new ArrayList<Object>();
                getAllInheritances(group_inh, group);
                groupinheritance.put(group_inh.get(0).toString(),group_inh);     
                grouppermissions.put(group, getAllPermissions(group));                        
            }  
            Main.LOGGER.info("Permission.yml loaded");  
            
        }catch (Exception e) {
            Main.LOGGER.info("Permission.yml file is broken");
            return;
        }       
        
    }
    private ArrayList<Object> getAllPermissions(String group){
        return YML.getAllList("groups."+group+".permissions");
    }
    private void getAllInheritances(ArrayList<Object> arrayList,String root){
        if(!arrayList.contains(root)){
            arrayList.add(root);
            for(Object o:YML.getAllList("groups."+root+".inheritance")){
                getAllInheritances(arrayList,o.toString());
            }
            
        }
    }
    
    public void removePermissions(Player player){
        PlayerStats playerStats=Main.playerUtility.get(player);
        for(PermissionAttachmentInfo permissionAttachment:player.getEffectivePermissions()){
            if(permissionAttachment.getPermission().split(".")[0].equals(prefix))
            player.sendMessage(permissionAttachment.getPermission());

        }
        //TODO: FEHLER
    }

    public static boolean hasPermission(CommandSender sender,String permission){
        for(String perm:splitStringRecursive(permission)){
            System.out.println(perm);
            if(sender.hasPermission(PermissionManager.prefix+perm)){return true;}
        }return false;

    }
    public static String[] splitStringRecursive(String input) {
        ArrayList<String> list = new ArrayList<String>();
        if (input.indexOf('.') != -1) {
            list.add(input);
            String parent = input.substring(0, input.lastIndexOf('.'));
            String[] parentArray = splitStringRecursive(parent);
            for (int i = 0; i < parentArray.length; i++) {
                list.add(parentArray[i]);
            }
        } else {
            list.add(input);
        }
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }
}
