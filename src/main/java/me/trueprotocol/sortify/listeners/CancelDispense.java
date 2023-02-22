package me.trueprotocol.sortify.listeners;

import me.trueprotocol.sortify.Sortify;
import me.trueprotocol.sortify.customclasses.CompareItem;
import me.trueprotocol.sortify.customclasses.RemoveItem;
import org.bukkit.Material;

import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CancelDispense implements Listener {

    Sortify plugin;
    public CancelDispense(Sortify plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent e) {

        // Check if block is Dispenser or Dropper
        String blockTypeString;
        if (e.getBlock().getType().equals(Material.DISPENSER)) blockTypeString = "dispensers";
        else if (e.getBlock().getType().equals(Material.DROPPER)) blockTypeString = "droppers";
        else return;

        // Check if enabled in config
        if (!plugin.getConfig().getBoolean(blockTypeString + ".enabled")) return;

        ItemStack item = e.getItem();
        // Filters item based on 'items' in config.yml
        CompareItem.ItemFilter result = CompareItem.ItemFilter.compareItem(plugin, item, blockTypeString);
        // Get results from filter
        boolean cancelEvent = result.isEventCancelled();
        ItemStack itemFiltered = result.getFilteredItemStack();
        // Set values from filter
        e.setCancelled(cancelEvent);
        e.setItem(itemFiltered);

        // Set Amount to Remove from Dispenser
        ItemStack itemToRemove = e.getItem();
        itemToRemove.setAmount(itemFiltered.getAmount() - 1);

        // Remove item from Dispenser
        if (blockTypeString.equals("dispensers")) {
            Dispenser dispenser = (Dispenser) e.getBlock().getState();
            Inventory dispenserInv = dispenser.getInventory();
            // Runs when items in dispenser is less than amount trying to be dispensed
            if (!dispenserInv.containsAtLeast(itemFiltered, itemFiltered.getAmount() - 1)) {
                int amountLeft = 1;
                for (ItemStack slot : dispenserInv.getContents()) {
                    if (slot != null && slot.isSimilar(itemFiltered)) amountLeft += slot.getAmount();
                }
                itemFiltered.setAmount(amountLeft);
                System.out.println("Set AmountLeft: " + amountLeft);
                if (amountLeft > 1) new RemoveItem.removeFromDispenser(dispenser, itemToRemove).runTaskLater(plugin, 1);
            // Normal item removal
            } else new RemoveItem.removeFromDispenser(dispenser, itemToRemove).runTaskLater(plugin, 1);

        // Remove item from Dropper
        } else {
            Dropper dropper = (Dropper) e.getBlock().getState();
            Inventory dropperInv = dropper.getInventory();
            // Runs when items in dropper is less than amount trying to be dispensed
            if (!dropperInv.containsAtLeast(itemFiltered, itemFiltered.getAmount() - 1)) {
                int amountLeft = 1;
                for (ItemStack slot : dropperInv.getContents()) {
                    if (slot != null && slot.isSimilar(itemFiltered)) amountLeft += slot.getAmount();
                }
                itemFiltered.setAmount(amountLeft);
                if (amountLeft > 1) new RemoveItem.removeFromDropper(dropper, itemToRemove).runTaskLater(plugin, 1);
            // Normal item removal
            } else new RemoveItem.removeFromDropper(dropper, itemToRemove).runTaskLater(plugin, 1);
        }
    }
}