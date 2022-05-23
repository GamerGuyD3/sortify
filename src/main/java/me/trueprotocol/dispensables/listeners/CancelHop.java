package me.trueprotocol.dispensables.listeners;

import me.trueprotocol.dispensables.Dispensables;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;

public class CancelHop implements Listener {

    Dispensables plugin;

    public CancelHop(Dispensables plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onInventoryMoveItem(InventoryMoveItemEvent e) {
        if (e.getDestination().getType().equals(InventoryType.HOPPER) || e.getSource().getType().equals(InventoryType.HOPPER)) {
            boolean whitelist = plugin.getConfig().getBoolean("hoppers.whitelist-mode");

            //Custom Name Items
            if (e.getItem().getItemMeta().hasDisplayName()) {
                boolean namewl = plugin.getConfig().getBoolean("hoppers.name.whitelist-mode");
                for (String name : plugin.getConfig().getStringList("hoppers.name.transfer-items")) {
                    if (!namewl && e.getItem().getItemMeta().getDisplayName().equals(name)) { e.setCancelled(true); break; }
                    else if (namewl)
                    {
                        if (e.getItem().getItemMeta().getDisplayName().equals(name)) { e.setCancelled(false); break; }
                        else e.setCancelled(true);
                    }
                }
            }
            //Regular Items
            for (String mat : plugin.getConfig().getStringList("hoppers.transfer-items")) {
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
    public void onInventoryPickupItem(InventoryPickupItemEvent e) {
        if (e.getInventory().getType().equals(InventoryType.HOPPER)) {
            boolean whitelist = plugin.getConfig().getBoolean("hoppers.whitelist-mode");

            //Custom Name Items
           if (e.getItem().getItemStack().getItemMeta().hasDisplayName()) {
               boolean namewl = plugin.getConfig().getBoolean("hoppers.name.whitelist-mode");
               for (String name : plugin.getConfig().getStringList("hoppers.name.transfer-items")) {
                   if (!namewl && e.getItem().getItemStack().getItemMeta().getDisplayName().equals(name)) { e.setCancelled(true); break; }
                   else if (namewl)
                   {
                       if (e.getItem().getItemStack().getItemMeta().getDisplayName().equals(name)) { e.setCancelled(false); break; }
                       else e.setCancelled(true);
                   }
               }
           }
           //Regular Items
           for (String mat : plugin.getConfig().getStringList("hoppers.transfer-items")) {
               if (!whitelist && e.getItem().getItemStack().getType().equals(Material.matchMaterial(mat))) { e.setCancelled(true); break; }
               else if (whitelist)
               {
                   if (e.getItem().getItemStack().getType().equals(Material.matchMaterial(mat))) { e.setCancelled(false); break; }
                   else e.setCancelled(true);
               }
           }
        }
    }
}
