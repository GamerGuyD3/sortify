package me.trueprotocol.dispensables.listeners;

import me.trueprotocol.dispensables.Dispensables;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;


public class CancelDispense implements Listener {

    Dispensables plugin;
    public CancelDispense(Dispensables plugin) { this.plugin = plugin; }

    @EventHandler
    public void onDispense(BlockDispenseEvent e) {
        if (e.getBlock().getType().equals(Material.DISPENSER) && plugin.getConfig().getBoolean("dispensers.enabled", true)) {
            boolean whitelist = plugin.getConfig().getBoolean("dispensers.whitelist-mode");
            //Custom Item Names
            if (e.getItem().getItemMeta().hasDisplayName()) {
                boolean namewl = plugin.getConfig().getBoolean("dispensers.name.whitelist-mode");
                for (String name : plugin.getConfig().getStringList("dispensers.name.dispense-items")) {
                    if (!namewl && e.getItem().getItemMeta().getDisplayName().equals(name)) { e.setCancelled(true); break; }
                    else if (namewl)
                    {
                        if (e.getItem().getItemMeta().getDisplayName().equals(name)) { e.setCancelled(false); break; }
                        else e.setCancelled(true);
                    }
                }
            }
            //Regular Items
            for (String mat : plugin.getConfig().getStringList("dispensers.dispense-items")) {
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

