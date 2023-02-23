package me.trueprotocol.sortify.listeners;

import me.trueprotocol.sortify.Sortify;
import me.trueprotocol.sortify.customclasses.CompareItem;
import me.trueprotocol.sortify.customclasses.RemoveItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CancelHop implements Listener {

    Sortify plugin;

    public CancelHop(Sortify plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHopperMoveItem(InventoryMoveItemEvent e) {

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
        if (hopperInv.getType().equals(InventoryType.HOPPER) && !hopperInv.containsAtLeast(item, item.getAmount() - 1)) {
            int amountLeft = 1;
            for (ItemStack slot : hopperInv.getContents()) {
                if (slot != null && slot.isSimilar(item)) amountLeft += slot.getAmount();
            }
            item.setAmount(amountLeft);
            if (amountLeft > 1) new RemoveItem.removeFromHopper(hopperInv, itemToRemove).runTaskLater(plugin, 1);
            // Normal item removal
        } else new RemoveItem.removeFromHopper(hopperInv, itemToRemove).runTaskLater(plugin, 1);
        e.setItem(item);
    }

    @EventHandler
    public void onHopperPickupItem(InventoryPickupItemEvent e) {
        if (!e.getInventory().getType().equals(InventoryType.HOPPER)) return;
        // Check if enabled in config
        if (!plugin.getConfig().getBoolean("hoppers.enabled")) return;

        String blockTypeString = "hoppers";
        ItemStack item = e.getItem().getItemStack();
        int amount = item.getAmount();
        // Filters item based on 'items' in config.yml
        CompareItem.ItemFilter result = CompareItem.ItemFilter.compareItem(plugin, item, blockTypeString);
        // Get results from filter
        boolean cancelEvent = result.isEventCancelled();
        // Set values from filter
        e.getItem().getItemStack().setAmount(amount);
        e.setCancelled(cancelEvent);
    }
}