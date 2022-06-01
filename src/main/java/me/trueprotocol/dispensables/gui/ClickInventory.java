package me.trueprotocol.dispensables.gui;

import me.trueprotocol.dispensables.Dispensables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ClickInventory implements Listener {

    Dispensables plugin;
    public ClickInventory(Dispensables plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {

        Player p = (Player) e.getWhoClicked();
        // On Click Main Menu
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "           [" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "]")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            // Dispensers Menu
            if (e.getCurrentItem().getType().equals(Material.DISPENSER) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Dispensers")) {
                Inventory dispensemenu = Bukkit.createInventory(p, 18, ChatColor.DARK_GRAY + "            [" + ChatColor.BLUE + "Dispensers" + ChatColor.DARK_GRAY + "]");
                // Materials
                ItemStack mat = new ItemStack(Material.COBBLESTONE);
                ItemMeta matMeta = mat.getItemMeta();
                ArrayList<String> matLore = new ArrayList<String>();
                matMeta.setDisplayName(ChatColor.YELLOW + "Material List");
                matLore.add(ChatColor.GRAY + "Add or remove items from menu"); matLore.add(ChatColor.GRAY + "to set them in config.yml");
                matMeta.setLore(matLore); mat.setItemMeta(matMeta);
                dispensemenu.setItem(12, mat);
                // Material Whitelist
                if (plugin.getConfig().getBoolean("dispensers.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.GREEN + "ON");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    dispensemenu.setItem(3, whitelist);
                }
                else if (!plugin.getConfig().getBoolean("dispensers.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.RED + "OFF");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    dispensemenu.setItem(3, whitelist);
                }
                // Names
                ItemStack name = new ItemStack(Material.NAME_TAG);
                ItemMeta nameMeta = name.getItemMeta();
                ArrayList<String> nameLore = new ArrayList<String>();
                nameMeta.setDisplayName(ChatColor.YELLOW + "Name List");
                nameLore.add(ChatColor.GRAY + "Add or remove named items to"); nameLore.add(ChatColor.GRAY + "set the name in config.yml");
                nameMeta.setLore(nameLore); name.setItemMeta(nameMeta);
                dispensemenu.setItem(14, name);
                // Name Whitelist
                if (plugin.getConfig().getBoolean("dispensers.name.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.GREEN + "ON");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    dispensemenu.setItem(5, whitelist);
                }
                else if (!plugin.getConfig().getBoolean("dispensers.name.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.RED + "OFF");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    dispensemenu.setItem(5, whitelist);
                }
                p.openInventory(dispensemenu);
            }
            // Dispensers Enabled
            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Dispensers: " + ChatColor.GREEN + "Enabled")) {
                plugin.getConfig().set("dispensers.enabled", false); plugin.saveConfig();
                ItemStack dispenseenabled = new ItemStack(Material.RED_WOOL);
                ItemMeta dispenseenabledMeta = dispenseenabled.getItemMeta();
                dispenseenabledMeta.setDisplayName(ChatColor.YELLOW + "Dispensers: " + ChatColor.RED + "Disabled");
                dispenseenabled.setItemMeta(dispenseenabledMeta);
                e.getClickedInventory().setItem(2, dispenseenabled);
            }
            // Dispensers Disabled
            else if (e.getCurrentItem().getType().equals(Material.RED_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Dispensers: " + ChatColor.RED + "Disabled")) {
                plugin.getConfig().set("dispensers.enabled", true); plugin.saveConfig();
                ItemStack dispenabled = new ItemStack(Material.GREEN_WOOL);
                ItemMeta dispenabledMeta = dispenabled.getItemMeta();
                dispenabledMeta.setDisplayName(ChatColor.YELLOW + "Dispensers: " + ChatColor.GREEN + "Enabled");
                dispenabled.setItemMeta(dispenabledMeta);
                e.getClickedInventory().setItem(2, dispenabled);
            }
            // Droppers Menu
            if (e.getCurrentItem().getType().equals(Material.DROPPER) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Droppers")) {
                Inventory dropmenu = Bukkit.createInventory(p, 18, ChatColor.DARK_GRAY + "             [" + ChatColor.BLUE + "Droppers" + ChatColor.DARK_GRAY + "]");
                // Materials
                ItemStack mat = new ItemStack(Material.COBBLESTONE);
                ItemMeta matMeta = mat.getItemMeta();
                ArrayList<String> matLore = new ArrayList<String>();
                matMeta.setDisplayName(ChatColor.YELLOW + "Material List");
                matLore.add(ChatColor.GRAY + "Add or remove items from menu"); matLore.add(ChatColor.GRAY + "to set them in config.yml");
                matMeta.setLore(matLore); mat.setItemMeta(matMeta);
                dropmenu.setItem(12, mat);
                // Material Whitelist
                if (plugin.getConfig().getBoolean("droppers.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.GREEN + "ON");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    dropmenu.setItem(3, whitelist);
                }
                else if (!plugin.getConfig().getBoolean("droppers.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.RED + "OFF");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    dropmenu.setItem(3, whitelist);
                }
                // Names
                ItemStack name = new ItemStack(Material.NAME_TAG);
                ItemMeta nameMeta = name.getItemMeta();
                ArrayList<String> nameLore = new ArrayList<String>();
                nameMeta.setDisplayName(ChatColor.YELLOW + "Name List");
                nameLore.add(ChatColor.GRAY + "Add or remove named items to"); nameLore.add(ChatColor.GRAY + "set the name in config.yml");
                nameMeta.setLore(nameLore); name.setItemMeta(nameMeta);
                dropmenu.setItem(14, name);
                // Name Whitelist
                if (plugin.getConfig().getBoolean("droppers.name.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.GREEN + "ON");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    dropmenu.setItem(5, whitelist);
                }
                else if (!plugin.getConfig().getBoolean("droppers.name.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.RED + "OFF");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    dropmenu.setItem(5, whitelist);
                }
                p.openInventory(dropmenu);
            }
            // Droppers Enabled
            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Droppers: " + ChatColor.GREEN + "Enabled")) {
                plugin.getConfig().set("droppers.enabled", false); plugin.saveConfig();
                ItemStack dropenabled = new ItemStack(Material.RED_WOOL);
                ItemMeta dropenabledMeta = dropenabled.getItemMeta();
                dropenabledMeta.setDisplayName(ChatColor.YELLOW + "Droppers: " + ChatColor.RED + "Disabled");
                dropenabled.setItemMeta(dropenabledMeta);
                e.getClickedInventory().setItem(4, dropenabled);
            }
            // Droppers Disabled
            else if (e.getCurrentItem().getType().equals(Material.RED_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Droppers: " + ChatColor.RED + "Disabled")) {
                plugin.getConfig().set("droppers.enabled", true); plugin.saveConfig();
                ItemStack dropenabled = new ItemStack(Material.GREEN_WOOL);
                ItemMeta dropenabledMeta = dropenabled.getItemMeta();
                dropenabledMeta.setDisplayName(ChatColor.YELLOW + "Droppers: " + ChatColor.GREEN + "Enabled");
                dropenabled.setItemMeta(dropenabledMeta);
                e.getClickedInventory().setItem(4, dropenabled);
            }
            // Hoppers Menu
            if (e.getCurrentItem().getType().equals(Material.HOPPER) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Hoppers")) {
                Inventory hopmenu = Bukkit.createInventory(p, 18, ChatColor.DARK_GRAY + "              [" + ChatColor.BLUE + "Hoppers" + ChatColor.DARK_GRAY + "]");
                // Materials
                ItemStack mat = new ItemStack(Material.COBBLESTONE);
                ItemMeta matMeta = mat.getItemMeta();
                ArrayList<String> matLore = new ArrayList<String>();
                matMeta.setDisplayName(ChatColor.YELLOW + "Material List");
                matLore.add(ChatColor.GRAY + "Add or remove items from menu"); matLore.add(ChatColor.GRAY + "to set them in config.yml");
                matMeta.setLore(matLore); mat.setItemMeta(matMeta);
                hopmenu.setItem(12, mat);
                // Material Whitelist
                if (plugin.getConfig().getBoolean("hoppers.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.GREEN + "ON");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    hopmenu.setItem(3, whitelist);
                }
                else if (!plugin.getConfig().getBoolean("hoppers.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.RED + "OFF");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    hopmenu.setItem(3, whitelist);
                }
                // Names
                ItemStack name = new ItemStack(Material.NAME_TAG);
                ItemMeta nameMeta = name.getItemMeta();
                ArrayList<String> nameLore = new ArrayList<String>();
                nameMeta.setDisplayName(ChatColor.YELLOW + "Name List");
                nameLore.add(ChatColor.GRAY + "Add or remove named items to"); nameLore.add(ChatColor.GRAY + "set the name in config.yml");
                nameMeta.setLore(nameLore); name.setItemMeta(nameMeta);
                hopmenu.setItem(14, name);
                // Name Whitelist
                if (plugin.getConfig().getBoolean("hoppers.name.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.GREEN + "ON");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    hopmenu.setItem(5, whitelist);
                }
                else if (!plugin.getConfig().getBoolean("hoppers.name.whitelist-mode")) {
                    ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                    ItemMeta whitelistMeta = mat.getItemMeta();
                    ArrayList<String> whitelistLore = new ArrayList<String>();
                    whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.RED + "OFF");
                    whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                    whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                    hopmenu.setItem(5, whitelist);
                }
                p.openInventory(hopmenu);
            }
            // Hoppers Enabled
            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Hoppers: " + ChatColor.GREEN + "Enabled")) {
                plugin.getConfig().set("hoppers.enabled", false); plugin.saveConfig();
                ItemStack hopenabled = new ItemStack(Material.RED_WOOL);
                ItemMeta hopenabledMeta = hopenabled.getItemMeta();
                hopenabledMeta.setDisplayName(ChatColor.YELLOW + "Hoppers: " + ChatColor.RED + "Disabled");
                hopenabled.setItemMeta(hopenabledMeta);
                e.getClickedInventory().setItem(6, hopenabled);
            }
            // Hoppers Disabled
            else if (e.getCurrentItem().getType().equals(Material.RED_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Hoppers: " + ChatColor.RED + "Disabled")) {
                plugin.getConfig().set("hoppers.enabled", true); plugin.saveConfig();
                ItemStack hopenabled = new ItemStack(Material.GREEN_WOOL);
                ItemMeta hopenabledMeta = hopenabled.getItemMeta();
                hopenabledMeta.setDisplayName(ChatColor.YELLOW + "Hoppers: " + ChatColor.GREEN + "Enabled");
                hopenabled.setItemMeta(hopenabledMeta);
                e.getClickedInventory().setItem(6, hopenabled);
            }
        }
        // On Click Dispensers Menu
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "            [" + ChatColor.BLUE + "Dispensers" + ChatColor.DARK_GRAY + "]")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            // Dispenser Material List
            if (e.getCurrentItem().getType().equals(Material.COBBLESTONE) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Material List")) {
                Inventory dispensematmenu = Bukkit.createInventory(p, 54, ChatColor.DARK_GRAY + "    [" + ChatColor.BLUE + "Dispensers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Material List");
                // Copy config to gui
                List<String> matlist = plugin.getConfig().getStringList("dispensers.dispense-items");
                for (int i = 0; i < matlist.size(); i++) {
                    ItemStack mat = new ItemStack(Material.matchMaterial(matlist.get(i)));
                    dispensematmenu.addItem(mat);
                }
                p.openInventory(dispensematmenu);
            }
            // Material Whitelist Enabled
            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.GREEN + "ON")) {
                plugin.getConfig().set("dispensers.whitelist-mode", false); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.RED + "OFF");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(3, whitelist);
            }
            // Material Whitelist Disabled
            else if (e.getCurrentItem().getType().equals(Material.RED_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.RED + "OFF")) {
                plugin.getConfig().set("dispensers.whitelist-mode", true); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.GREEN + "ON");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(3, whitelist);
            }
            // Dispenser Name List
            if (e.getCurrentItem().getType().equals(Material.NAME_TAG) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Name List")) {
                Inventory dispensenamemenu = Bukkit.createInventory(p, 54, ChatColor.DARK_GRAY + "      [" + ChatColor.BLUE + "Dispensers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Name List");
                // Copy config to gui
                List<String> namelist = plugin.getConfig().getStringList("dispensers.name.dispense-items");
                for (int i = 0; i < namelist.size(); i++) {
                    ItemStack name = new ItemStack(Material.NAME_TAG);
                    ItemMeta nameMeta = name.getItemMeta();
                    nameMeta.setDisplayName(namelist.get(i));
                    name.setItemMeta(nameMeta);
                    dispensenamemenu.addItem(name);
                }
                p.openInventory(dispensenamemenu);
            }
            // Name Whitelist Enabled
            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.GREEN + "ON")) {
                plugin.getConfig().set("dispensers.name.whitelist-mode", false); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.RED + "OFF");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(5, whitelist);
            }
            // Name Whitelist Disabled
            else if (e.getCurrentItem().getType().equals(Material.RED_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.RED + "OFF")) {
                plugin.getConfig().set("dispensers.name.whitelist-mode", true); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.GREEN + "ON");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(5, whitelist);
            }
        }
        // On Click Droppers Menu
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "             [" + ChatColor.BLUE + "Droppers" + ChatColor.DARK_GRAY + "]")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            // Dropper Material List
            if (e.getCurrentItem().getType().equals(Material.COBBLESTONE) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Material List")) {
                Inventory dropmatmenu = Bukkit.createInventory(p, 54, ChatColor.DARK_GRAY + "     [" + ChatColor.BLUE + "Droppers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Material List");
                // Copy config to gui
                List<String> matlist = plugin.getConfig().getStringList("droppers.dispense-items");
                for (int i = 0; i < matlist.size(); i++) {
                    ItemStack mat = new ItemStack(Material.matchMaterial(matlist.get(i)));
                    dropmatmenu.addItem(mat);
                }
                p.openInventory(dropmatmenu);
            }
            // Material Whitelist Enabled
            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.GREEN + "ON")) {
                plugin.getConfig().set("droppers.whitelist-mode", false); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.RED + "OFF");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(3, whitelist);
            }
            // Material Whitelist Disabled
            else if (e.getCurrentItem().getType().equals(Material.RED_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.RED + "OFF")) {
                plugin.getConfig().set("droppers.whitelist-mode", true); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.GREEN + "ON");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(3, whitelist);
            }
            // Dropper Name List
            if (e.getCurrentItem().getType().equals(Material.NAME_TAG) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Name List")) {
                Inventory dropnamemenu = Bukkit.createInventory(p, 54, ChatColor.DARK_GRAY + "       [" + ChatColor.BLUE + "Droppers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Name List");
                // Copy config to gui
                List<String> namelist = plugin.getConfig().getStringList("droppers.name.dispense-items");
                for (int i = 0; i < namelist.size(); i++) {
                    ItemStack name = new ItemStack(Material.NAME_TAG);
                    ItemMeta nameMeta = name.getItemMeta();
                    nameMeta.setDisplayName(namelist.get(i));
                    name.setItemMeta(nameMeta);
                    dropnamemenu.addItem(name);
                }
                p.openInventory(dropnamemenu);
            }
            // Name Whitelist Enabled
            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.GREEN + "ON")) {
                plugin.getConfig().set("droppers.name.whitelist-mode", false); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.RED + "OFF");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(5, whitelist);
            }
            // Name Whitelist Disabled
            else if (e.getCurrentItem().getType().equals(Material.RED_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.RED + "OFF")) {
                plugin.getConfig().set("droppers.name.whitelist-mode", true); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.GREEN + "ON");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(5, whitelist);
            }
        }
        // On Click Hoppers Menu
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "              [" + ChatColor.BLUE + "Hoppers" + ChatColor.DARK_GRAY + "]")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            // Hopper Material List
            if (e.getCurrentItem().getType().equals(Material.COBBLESTONE) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Material List")) {
                Inventory hopmatmenu = Bukkit.createInventory(p, 54, ChatColor.DARK_GRAY + "      [" + ChatColor.BLUE + "Hoppers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Material List");
                // Copy config to gui
                List<String> matlist = plugin.getConfig().getStringList("hoppers.transfer-items");
                for (int i = 0; i < matlist.size(); i++) {
                    ItemStack mat = new ItemStack(Material.matchMaterial(matlist.get(i)));
                    hopmatmenu.addItem(mat);
                }
                p.openInventory(hopmatmenu);
            }
            // Material Whitelist Enabled
            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.GREEN + "ON")) {
                plugin.getConfig().set("hoppers.whitelist-mode", false); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.RED + "OFF");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(3, whitelist);
            }
            // Material Whitelist Disabled
            else if (e.getCurrentItem().getType().equals(Material.RED_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.RED + "OFF")) {
                plugin.getConfig().set("hoppers.whitelist-mode", true); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Material Whitelist: " + ChatColor.GREEN + "ON");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(3, whitelist);
            }
            // Hopper Name List
            if (e.getCurrentItem().getType().equals(Material.NAME_TAG) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Name List")) {
                Inventory hopnamemenu = Bukkit.createInventory(p, 54, ChatColor.DARK_GRAY + "        [" + ChatColor.BLUE + "Hoppers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Name List");
                // Copy config to gui
                List<String> namelist = plugin.getConfig().getStringList("hoppers.name.transfer-items");
                for (int i = 0; i < namelist.size(); i++) {
                    ItemStack name = new ItemStack(Material.NAME_TAG);
                    ItemMeta nameMeta = name.getItemMeta();
                    nameMeta.setDisplayName(namelist.get(i));
                    name.setItemMeta(nameMeta);
                    hopnamemenu.addItem(name);
                }
                p.openInventory(hopnamemenu);
            }
            // Name Whitelist Enabled
            if (e.getCurrentItem().getType().equals(Material.GREEN_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.GREEN + "ON")) {
                plugin.getConfig().set("hoppers.name.whitelist-mode", false); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.RED_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.RED + "OFF");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(5, whitelist);
            }
            // Name Whitelist Disabled
            else if (e.getCurrentItem().getType().equals(Material.RED_WOOL) && e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.RED + "OFF")) {
                plugin.getConfig().set("hoppers.name.whitelist-mode", true); plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.GREEN_WOOL);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                ArrayList<String> whitelistLore = new ArrayList<String>();
                whitelistMeta.setDisplayName(ChatColor.YELLOW + "Name Whitelist: " + ChatColor.GREEN + "ON");
                whitelistLore.add(ChatColor.GRAY + "ON - List acts as whitelist"); whitelistLore.add(ChatColor.GRAY + "OFF - List acts as blacklist");
                whitelistMeta.setLore(whitelistLore); whitelist.setItemMeta(whitelistMeta);
                e.getClickedInventory().setItem(5, whitelist);
            }
        }
    }
}
