package com.itkeller.smpsystems.Utils.MenuMaker;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Icon {

    private final ItemStack itemStack;
    /**
     * Creates an Icon for a {@link Menu}
     *
     * @param itemStack the item represented by this icon
     */
    public Icon(final ItemStack itemStack) {
        if (itemStack == null) {
            throw new IllegalArgumentException("item cannot be null");
        }
        this.itemStack = itemStack;
    }

    /**
     * Represents an unclickable icon
     */
    public static class UnclickableIcon extends Icon {

        /**
         * Creates an Icon for a {@link Menu}
         *
         * @param itemStack the item represented by this icon
         */
        public UnclickableIcon(ItemStack itemStack) {
            super(itemStack);
        }

        @Override
        public void click(Player player, Menu menu) {
            // Do nothing
        }
        @Override
        public void rightclick(Player player, Menu menu) {
            click(player, menu);
        }
    }
    public static class ExitIcon extends Icon {

        /**
         * Creates an Icon for a {@link Menu}
         *
         * @param itemStack the item represented by this icon
         */
        public ExitIcon() {
            super(Menu.createIcon(Material.BARRIER, "§4§lEXIT", "§c§oClick to close the menu"));
        }

        @Override
        public void click(Player player, Menu menu) {
            player.closeInventory();
        }
        @Override
        public void rightclick(Player player, Menu menu) {
            click(player, menu);
        }
    }
    public static class EmptyIcon extends UnclickableIcon {

        public EmptyIcon() {
            super(Menu.createIcon(Material.BLACK_STAINED_GLASS_PANE, " "));
        }

    }

    /**
     * Gets the item
     *
     * @return the item that this icon represents
     */
    public ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Sets the item represented by this icon
     *
     * @param itemStack the new item
     */
    public void setItemStack(ItemStack itemStack) {
        if (itemStack == null) {
            System.out.println("empty stack");
            return;
        }

        // Change all data
        this.itemStack.setType(itemStack.getType());
        this.itemStack.setAmount(itemStack.getAmount());
        //this.itemStack.setDurability(itemStack.getDurability());

        // Set item meta
        if (itemStack.hasItemMeta()) {
            this.itemStack.setItemMeta(itemStack.getItemMeta().clone());
        } else {
            this.itemStack.setItemMeta(null);
        }
    }

    /**
     * Handles a player clicking the inventory
     *
     * @param player the player that clicked this icon
     * @param menu   the menu this icon was clicked inside
     */
    public abstract void click(Player player, Menu menu);
    public abstract void rightclick(Player player, Menu menu);
}