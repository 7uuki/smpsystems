package com.itkeller.smpsystems.Utils.Inventory;

import org.bukkit.inventory.Inventory;

public class CustomInventory {

    private Inventory inv;
    private int size;

    public CustomInventory(Inventory inv, int size) {
        this.inv = inv;
        this.size = size;
    }
    
    public Inventory getInv() {
        return inv;
    }
    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    
}
