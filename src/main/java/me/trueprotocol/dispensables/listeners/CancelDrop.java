package me.trueprotocol.dispensables.listeners;

import me.trueprotocol.dispensables.Dispensables;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;


public class CancelDrop implements Listener {

    Dispensables plugin;
    public CancelDrop(Dispensables plugin) { this.plugin = plugin; }

    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
        if (e.getBlock().getType() == Material.DROPPER && plugin.getConfig().getBoolean("droppers.enabled", true)) {
            boolean whitelist = plugin.getConfig().getBoolean("droppers.whitelist-mode");

            //Custom Name Items
            if (e.getItem().getItemMeta().hasDisplayName()) {
                boolean namewl = plugin.getConfig().getBoolean("droppers.name.whitelist-mode");
                for (String name : plugin.getConfig().getStringList("droppers.name.dispense-items")) {
                    if (!namewl && e.getItem().getItemMeta().getDisplayName().equals(name)) { e.setCancelled(true); break; }
                    else if (namewl)
                    {
                        if (e.getItem().getItemMeta().getDisplayName().equals(name)) { e.setCancelled(false); break; }
                        else e.setCancelled(true);
                    }
                }
            }
            //Regular Items
            for (String mat : plugin.getConfig().getStringList("droppers.dispense-items")) {
                if (!whitelist && e.getItem().getType().equals(Material.matchMaterial(mat))) { e.setCancelled(true); break; }
                else if (whitelist)
                {
                    if (e.getItem().getType().equals(Material.matchMaterial(mat))) { e.setCancelled(false); break; }
                    else e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent e) {
        if (e.getSource().getType().equals(InventoryType.DROPPER)) {
            boolean whitelist = plugin.getConfig().getBoolean("droppers.whitelist-mode");

            //Custom Name Items
            if (e.getItem().getItemMeta().hasDisplayName()) {
                boolean namewl = plugin.getConfig().getBoolean("droppers.name.whitelist-mode");
                for (String name : plugin.getConfig().getStringList("droppers.name.dispense-items")) {
                    if (!namewl && e.getItem().getItemMeta().getDisplayName().equals(name)) { e.setCancelled(true); break; }
                    else if (namewl)
                    {
                        if (e.getItem().getItemMeta().getDisplayName().equals(name)) { e.setCancelled(false); break; }
                        else e.setCancelled(true);
                    }
                }
            }
            //Regular Items
            for (String mat : plugin.getConfig().getStringList("droppers.dispense-items")) {
                if (!whitelist && e.getItem().getType().equals(Material.matchMaterial(mat))) { e.setCancelled(true); break; }
                else if (whitelist)
                {
                    if (e.getItem().getType().equals(Material.matchMaterial(mat))) { e.setCancelled(false); break; }
                    else e.setCancelled(true);
                }
            }
        }
    }
}

