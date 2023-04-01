package com.itkeller.smpsystems.Utils.MenuMaker;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class MenuListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onItemClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        // Only listening for menus
        if (!(inventory.getHolder() instanceof Menu)) {
            return;
        }
        event.setCancelled(true);
        Menu menu = (Menu) inventory.getHolder();

        // Only when they click inside a menu
        if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
            return;
        }

        int index = event.getRawSlot();
        if (index > inventory.getSize()) {
            return;
        }
        ClickType clickType = event.getClick();
        if (clickType == ClickType.LEFT) {
            menu.executeclick(index, (Player) event.getWhoClicked());
        } else if (clickType == ClickType.RIGHT) {
            menu.executerightclick(index, (Player) event.getWhoClicked());
        }
        menu.update();
    }
}