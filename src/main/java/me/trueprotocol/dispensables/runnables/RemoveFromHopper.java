package me.trueprotocol.dispensables.runnables;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveFromHopper extends BukkitRunnable {
    private Inventory hopperInv;
    private ItemStack itemToRemove;

    public RemoveFromHopper(Inventory hopperInv, ItemStack itemToRemove) {
        this.hopperInv = hopperInv;
        this.itemToRemove = itemToRemove;
    }

    public void run() {
        hopperInv.removeItem(itemToRemove);
    }
}