package me.trueprotocol.dispensables.runnables;

import org.bukkit.block.Dispenser;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveFromDispenser extends BukkitRunnable {
    private final Dispenser dispenserBlock;
    private final ItemStack itemToRemove;

    public RemoveFromDispenser(Dispenser dispenserBlock, ItemStack itemToRemove) {
        this.dispenserBlock = dispenserBlock;
        this.itemToRemove = itemToRemove;
    }

    public void run() {
        dispenserBlock.getInventory().removeItem(itemToRemove);
    }
}