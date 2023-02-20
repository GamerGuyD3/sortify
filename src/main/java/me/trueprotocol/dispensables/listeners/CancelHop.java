package me.trueprotocol.dispensables.listeners;

import me.trueprotocol.dispensables.Dispensables;
import me.trueprotocol.dispensables.runnables.CompareItem;
import me.trueprotocol.dispensables.runnables.RemoveFromHopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CancelHop implements Listener {

    Dispensables plugin;

    public CancelHop(Dispensables plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent e) {

        if (!(e.getSource().getType().equals(InventoryType.HOPPER) || e.getDestination().getType().equals(InventoryType.HOPPER))) return;
        // Check if enabled in config
        if (!plugin.getConfig().getBoolean("hoppers.enabled")) return;

        String blockTypeString = "hoppers";
        ItemStack item = e.getItem();
        // Filters item based on 'items' in config.yml
        CompareItem.ItemFilter result = CompareItem.ItemFilter.compareItem(plugin, item, blockTypeString);
        // Get results from filter
        boolean cancelEvent = result.isEventCancelled();
        ItemStack itemFiltered = result.getFilteredItemStack();
        // Set values from filter
        e.setCancelled(cancelEvent);
        e.setItem(itemFiltered);

        // Set Amount to Remove from Hopper
        ItemStack itemToRemove = e.getItem();
        itemToRemove.setAmount(item.getAmount() - 1);

        // Remove item from Hopper
        Inventory hopperInv = e.getSource();
        // Runs when items in hopper is less than amount trying to be hopped
        if (!hopperInv.containsAtLeast(item, item.getAmount() - 1)) {
            int amountLeft = 1;
            for (ItemStack slot : hopperInv.getContents()) {
                if (slot != null && slot.isSimilar(item)) amountLeft += slot.getAmount();
            }
            item.setAmount(amountLeft);
            if (amountLeft > 1) new RemoveFromHopper(hopperInv, itemToRemove).runTaskLater(plugin, 1);
            // Normal item removal
        } else new RemoveFromHopper(hopperInv, itemToRemove).runTaskLater(plugin, 1);
        e.setItem(item);
    }
}