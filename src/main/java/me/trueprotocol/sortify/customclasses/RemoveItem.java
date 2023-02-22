package me.trueprotocol.sortify.customclasses;

import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class RemoveItem {

    public static class removeFromDispenser extends BukkitRunnable {
        private final Dispenser dispenserBlock;
        private final ItemStack itemToRemove;

        public removeFromDispenser(Dispenser dispenserBlock, ItemStack itemToRemove) {
            this.dispenserBlock = dispenserBlock;
            this.itemToRemove = itemToRemove;
        }

        public void run() { dispenserBlock.getInventory().removeItem(itemToRemove); }
    }

    public static class removeFromDropper extends BukkitRunnable {
        private final Dropper dropperBlock;
        private final ItemStack itemToRemove;

        public removeFromDropper(Dropper dropperBlock, ItemStack itemToRemove) {
            this.dropperBlock = dropperBlock;
            this.itemToRemove = itemToRemove;
        }

        public void run() { dropperBlock.getInventory().removeItem(itemToRemove); }
    }

    public static class removeFromHopper extends BukkitRunnable {
        private final Inventory hopperInv;
        private final ItemStack itemToRemove;

        public removeFromHopper(Inventory hopperInv, ItemStack itemToRemove) {
            this.hopperInv = hopperInv;
            this.itemToRemove = itemToRemove;
        }

        public void run() { hopperInv.removeItem(itemToRemove); }
    }
}