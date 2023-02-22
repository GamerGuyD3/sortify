package me.trueprotocol.sortify.gui;

import me.trueprotocol.sortify.Sortify;
import me.trueprotocol.sortify.customclasses.MenuCreator;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class CloseInventory implements Listener {

    Sortify plugin;
    public CloseInventory(Sortify plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e) {
        // Dispenser Menu Save
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "             [" + ChatColor.GREEN + "Dispenser" + ChatColor.DARK_GRAY + "]")) {
            String blockTypeString = "dispenser";
            Inventory inventory = e.getInventory();
            new MenuCreator.mainMenu(plugin).saveItemConfigEditor(blockTypeString, inventory);
            return;
        }
        // Dropper Menu Save
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "              [" + ChatColor.GREEN + "Dropper" + ChatColor.DARK_GRAY + "]")) {
            String blockTypeString = "dropper";
            Inventory inventory = e.getInventory();
            new MenuCreator.mainMenu(plugin).saveItemConfigEditor(blockTypeString, inventory);
            return;
        }
        // Hopper Menu Save
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "               [" + ChatColor.GREEN + "Hopper" + ChatColor.DARK_GRAY + "]")) {
            String blockTypeString = "hopper";
            Inventory inventory = e.getInventory();
            new MenuCreator.mainMenu(plugin).saveItemConfigEditor(blockTypeString, inventory);
        }
    }
}
