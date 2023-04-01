package com.itkeller.smpsystems.Utils.Inventory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.itkeller.Main;
import com.itkeller.smpsystems.Utils.Helper.Chat;
import com.itkeller.smpsystems.Utils.Helper.YML;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;



public class InventoryManager {

    public final HashMap<InventoryKey,CustomInventory> map;
    private String ymlname;

    public InventoryManager(String ymlname){
        map = new HashMap<InventoryKey,CustomInventory>();
        this.ymlname=ymlname;
    }
    //returns an Inventory out of the map. If not found in map or yml it retunrs null
    public Inventory get(UUID uuid,String invname){
        if(map.get(new InventoryKey(uuid,invname)) == null){
            loadifExsists(uuid,invname);
        }
        return map.get(new InventoryKey(uuid,invname)).getInv();
    }
    //returns an Inventory out of the map. If not found in map or yml it creates one with size
    public Inventory get(UUID uuid,String invname,int size){
        if(map.get(new InventoryKey(uuid,invname)) == null){
            loadifExsists(uuid,invname);
        }
        Inventory inv;
        if(map.get(new InventoryKey(uuid,invname))==null){
            inv = create(uuid, size, invname);
        }else{
            inv = map.get(new InventoryKey(uuid,invname)).getInv();
        }
        return inv;
    }
    //loads the Inventory out of the yml into the map
    private void loadifExsists(UUID uuid,String invname){
        YML yml=new YML(uuid.toString(),ymlname);
        //check if exsists
        if(yml.get(invname)!=null){
            int size = (int) yml.get(invname+".size");
            Inventory inv=Bukkit.createInventory(null,size,"§l"+invname);
            try {
                inv.setContents(toItemStack(yml.get(invname+".value").toString()));
                map.put(new InventoryKey(uuid, invname), new CustomInventory(inv, size));
            } catch (IllegalArgumentException | IOException e) {
                e.printStackTrace();
            }
        }
    }
    //creates Inventory and stores it in map
    public Inventory create(UUID uuid,int size,String invname){
        Inventory inv= Bukkit.createInventory(null,size,"§l"+invname);
        map.put(new InventoryKey(uuid,invname), new CustomInventory(inv, size));
        return inv;
    }
    //saves all current map items to their ymls
    public void saveall(){
        for(Map.Entry<InventoryKey, CustomInventory> entry : map.entrySet()) {
            InventoryKey key = entry.getKey();
            CustomInventory inv = entry.getValue();
            YML yml=new YML(key.getUuid().toString(),ymlname);
            Main.LOGGER.info(String.valueOf(yml.exsists()));
            yml.set(key.getName()+".value", toBase64(inv.getInv()));
            yml.set(key.getName()+".size", inv.getSize());
        }
        Main.LOGGER.info("saved all inventorys into: '"+ymlname+".yml'");
    }


    /*Utils*/
    public String toBase64(Inventory inv){
        return Base64.itemStackArrayToBase64(inv.getContents());
    }
    public ItemStack[] toItemStack(String inv) throws IOException{
        return Base64.itemStackArrayFromBase64(inv);
    }



    public void printall(Player player){
        for(HashMap.Entry<InventoryKey,CustomInventory> entry:map.entrySet()){
            String msg= "* "+entry.getKey().getName()+" -> "+entry.getKey().getUuid().toString();
            if(player!=null){
                TextComponent copytoChat = new TextComponent(msg+" §2[OPEN]");
                copytoChat.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/perm inv "+entry.getKey().getUuid()+" "+entry.getKey().getName()));
                copytoChat.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§e§oClick to copy to Chat")));
                player.spigot().sendMessage(copytoChat);
            }else{
                Main.LOGGER.info(msg);
            }
            
            
        }
    }
    
}
