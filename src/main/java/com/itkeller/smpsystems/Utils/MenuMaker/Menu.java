package com.itkeller.smpsystems.Utils.MenuMaker;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu implements InventoryHolder {

    private Inventory inventory;
    private Map<Integer, Icon> icons;
    private Menu parent;

    /**
     * Creates a menu
     *
     * @param title the title of the menu
     * @param rows  the amount of rows for the GUI to have
     */
    public Menu(String title, int rows) {
        this.icons = new HashMap<>();
        this.inventory = Bukkit.createInventory(this, rows * 9, title);
    }

    /**
     * Adds an icon to the first available slot in the inventory
     *
     * @param icon the icon to add
     * @return will return false if the inventory has no available slots
     */
    public boolean addIcon(Icon icon) {
        int first = getInventory().firstEmpty();

        if (first == -1) {
            return false;
        }

        addIcon(icon, first);
        return true;
    }

    /**
     * Adds an {@link Icon} to a specific slot in the inventory, overriding and
     * removing any previous {@link Icon}
     *
     * @param icon the icon to add
     * @param slot the slot in the inventory to add
     */
    public void addIcon(Icon icon, int slot) {
        if (icons.containsKey(slot)) {
            removeIcon(slot);
        }

        getInventory().setItem(slot, icon.getItemStack());
        icons.put(slot, icon);
    }

    /**
     * Returns an {@link Icon} at a specific slot
     *
     * @param slot the slot
     * @return the icon at the slot, null if there is no icon there
     */
    public Icon getIcon(int slot) {
        return icons.get(slot);
    }

    /**
     * Removes an icon at a specific slot
     *
     * @param slot the slot
     */
    public void removeIcon(int slot) {
        if (!icons.containsKey(slot)) {
            return;
        }

        icons.remove(slot);
        getInventory().setItem(slot, null);
    }

    /**
     * Clicks an icon at a slot for a player
     *
     * @param slot   the slot number
     * @param player the player that clicked inside the menu
     */
    public void executeclick(int slot, Player player) {
        if (!icons.containsKey(slot)) {
            return;
        }

        Icon icon = icons.get(slot);
        icon.click(player, this);
    }
    public void executerightclick(int slot, Player player) {
        if (!icons.containsKey(slot)) {
            return;
        }

        Icon icon = icons.get(slot);
        icon.rightclick(player, this);
    }

    /**
     * Sets the parent menu
     *
     * @param parent the new parent menu
     */
    public void setParent(Menu parent) {
        this.parent = parent;
    }

    /**
     * Returns the parent menu
     *
     * @return the parent menu
     */
    public Menu getParent() {
        return parent;
    }

    /**
     * Updates the inventory of all viewers inside the inventory
     */
    public void update() {
        // Update icons
        icons.forEach((slot, icon) -> getInventory().setItem(slot, icon.getItemStack()));
        getInventory().getViewers().stream().filter(he -> he instanceof Player).forEach(he -> ((Player) he).updateInventory());
    }

    /**
     * Closes the inventory on all viewers
     
    public void close() {
        new ArrayList()<>(getInventory().getViewers()).forEach(HumanEntity::closeInventory);
    }
    */

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public static ItemStack createIcon(final Material material, final String name, final String... lore) {
        //Helper

        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        // Set the name of the item
        meta.setDisplayName(name);

        // Set the lore of the item
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }
}