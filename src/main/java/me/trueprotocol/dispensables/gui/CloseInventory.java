package me.trueprotocol.dispensables.gui;

import me.trueprotocol.dispensables.Dispensables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;


import java.util.ArrayList;
import java.util.List;

public class CloseInventory implements Listener {

    Dispensables plugin;
    public CloseInventory(Dispensables plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e) {
        // Dispenser Material Save
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "    [" + ChatColor.BLUE + "Dispensers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Material List")) {
            List<String> dispensemat = new ArrayList<String>();
            for (ItemStack mat : e.getInventory().getContents()) {
                dispensemat.add(mat.getType().toString());
                plugin.getConfig().set("dispensers.dispense-items", dispensemat); plugin.saveConfig();
            }
        }
        // Dispenser Name Save
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "      [" + ChatColor.BLUE + "Dispensers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Name List")) {
            List<String> dispensename = new ArrayList<String>();
            for (ItemStack name : e.getInventory().getContents()) {
                dispensename.add(name.getItemMeta().getDisplayName());
                plugin.getConfig().set("dispensers.name.dispense-items", dispensename); plugin.saveConfig();
            }
        }
        // Dropper Material Save
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "     [" + ChatColor.BLUE + "Droppers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Material List")) {
            List<String> dropmat = new ArrayList<String>();
            for (ItemStack mat : e.getInventory().getContents()) {
                dropmat.add(mat.getType().toString());
                plugin.getConfig().set("droppers.dispense-items", dropmat); plugin.saveConfig();
            }
        }
        // Dropper Name Save
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "       [" + ChatColor.BLUE + "Droppers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Name List")) {
            List<String> dropname = new ArrayList<String>();
            for (ItemStack name : e.getInventory().getContents()) {
                dropname.add(name.getItemMeta().getDisplayName());
                plugin.getConfig().set("droppers.name.dispense-items", dropname); plugin.saveConfig();
            }
        }
        // Hopper Material Save
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "      [" + ChatColor.BLUE + "Hoppers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Material List")) {
            List<String> hopmat = new ArrayList<String>();
            for (ItemStack mat : e.getInventory().getContents()) {
                hopmat.add(mat.getType().toString());
                plugin.getConfig().set("hoppers.transfer-items", hopmat); plugin.saveConfig();
            }
        }
        // Hopper Name Save
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "        [" + ChatColor.BLUE + "Hoppers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Name List")) {
            List<String> hopname = new ArrayList<String>();
            for (ItemStack name : e.getInventory().getContents()) {
                hopname.add(name.getItemMeta().getDisplayName());
                plugin.getConfig().set("hoppers.name.transfer-items", hopname); plugin.saveConfig();
            }
        }
    }
}
