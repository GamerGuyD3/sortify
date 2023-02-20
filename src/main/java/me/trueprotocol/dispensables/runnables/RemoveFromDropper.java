package me.trueprotocol.dispensables.runnables;

import org.bukkit.block.Dropper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveFromDropper extends BukkitRunnable {
    private final Dropper dropperBlock;
    private final ItemStack itemToRemove;

    public RemoveFromDropper(Dropper dropperBlock, ItemStack itemToRemove) {
        this.dropperBlock = dropperBlock;
        this.itemToRemove = itemToRemove;
    }

    public void run() {
        dropperBlock.getInventory().removeItem(itemToRemove);
    }
}