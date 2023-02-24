package me.trueprotocol.sortify.gui;

import me.trueprotocol.sortify.Sortify;
import me.trueprotocol.sortify.commands.SortifyCommand;
import me.trueprotocol.sortify.customclasses.MenuCreator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ClickInventory implements Listener {

    Sortify plugin;

    public ClickInventory(Sortify plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        // On Click Main Menu
        if (e.getView().getTitle().equals(ChatColor.DARK_GRAY + "              [" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "]")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            // Dispensers Menu
            if (e.getCurrentItem().getType().equals(Material.DISPENSER)) {
                new MenuCreator.mainMenu(plugin).openItemConfigEditor(player, "dispenser");
                return;
            }
            // Droppers Menu
            if (e.getCurrentItem().getType().equals(Material.DROPPER)) {
                new MenuCreator.mainMenu(plugin).openItemConfigEditor(player, "dropper");
                return;
            }
            // Hoppers Menu
            if (e.getCurrentItem().getType().equals(Material.HOPPER)) {
                new MenuCreator.mainMenu(plugin).openItemConfigEditor(player, "hopper");
                return;
            }
            // Enable Buttons
            if (e.getCurrentItem().getType().equals(Material.LIME_DYE)) {
                int slot = e.getSlot();
                switch (slot) {
                    case 11:
                        plugin.getConfig().set("dispensers.enabled", false);
                        break;
                    case 13:
                        plugin.getConfig().set("droppers.enabled", false);
                        break;
                    case 15:
                        plugin.getConfig().set("hoppers.enabled", false);
                        break;
                }
                plugin.saveConfig();
                ItemStack enable = new ItemStack(Material.GRAY_DYE);
                ItemMeta enableMeta = enable.getItemMeta();
                Objects.requireNonNull(enableMeta).setDisplayName(ChatColor.RED + "Disabled");
                enableMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to toggle"));
                enable.setItemMeta(enableMeta);
                Objects.requireNonNull(e.getClickedInventory()).setItem(slot, enable);
                return;
            }
            // Disable Buttons
            if (e.getCurrentItem().getType().equals(Material.GRAY_DYE)) {
                int slot = e.getSlot();
                switch (slot) {
                    case 11:
                        plugin.getConfig().set("dispensers.enabled", true);
                        break;
                    case 13:
                        plugin.getConfig().set("droppers.enabled", true);
                        break;
                    case 15:
                        plugin.getConfig().set("hoppers.enabled", true);
                        break;
                }
                plugin.saveConfig();
                ItemStack disable = new ItemStack(Material.LIME_DYE);
                ItemMeta disableMeta = disable.getItemMeta();
                assert disableMeta != null;
                disableMeta.setDisplayName(ChatColor.GREEN + "Enabled");
                disableMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to toggle"));
                disable.setItemMeta(disableMeta);
                Objects.requireNonNull(e.getClickedInventory()).setItem(slot, disable);
                return;
            }
            // Whitelist Buttons
            if (e.getCurrentItem().getType().equals(Material.WHITE_BANNER)) {
                int slot = e.getSlot();
                switch (slot) {
                    case 20:
                        plugin.getConfig().set("dispensers.whitelist-mode", false);
                        break;
                    case 22:
                        plugin.getConfig().set("droppers.whitelist-mode", false);
                        break;
                    case 24:
                        plugin.getConfig().set("hoppers.whitelist-mode", false);
                        break;
                }
                plugin.saveConfig();
                ItemStack whitelist = new ItemStack(Material.BLACK_BANNER);
                ItemMeta whitelistMeta = whitelist.getItemMeta();
                Objects.requireNonNull(whitelistMeta).setDisplayName(ChatColor.WHITE + "Blacklist Mode");
                whitelistMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to toggle"));
                whitelist.setItemMeta(whitelistMeta);
                Objects.requireNonNull(e.getClickedInventory()).setItem(slot, whitelist);
                return;
            }
            // Blacklist Buttons
            if (e.getCurrentItem().getType().equals(Material.BLACK_BANNER)) {
                int slot = e.getSlot();
                switch (slot) {
                    case 20:
                        plugin.getConfig().set("dispensers.whitelist-mode", true);
                        break;
                    case 22:
                        plugin.getConfig().set("droppers.whitelist-mode", true);
                        break;
                    case 24:
                        plugin.getConfig().set("hoppers.whitelist-mode", true);
                        break;
                }
                plugin.saveConfig();
                ItemStack blacklist = new ItemStack(Material.WHITE_BANNER);
                ItemMeta blacklistMeta = blacklist.getItemMeta();
                Objects.requireNonNull(blacklistMeta).setDisplayName(ChatColor.WHITE + "Whitelist Mode");
                blacklistMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to toggle"));
                blacklist.setItemMeta(blacklistMeta);
                Objects.requireNonNull(e.getClickedInventory()).setItem(slot, blacklist);
                return;
            }
            // Amount Buttons
            if (e.getCurrentItem().getType().equals(Material.SLIME_BALL)) {
                int slot = e.getSlot();
                int addAmount = 0;
                if (e.getClick().isLeftClick()) {
                    addAmount = 1;
                } else if (e.getClick().isRightClick()) {
                    addAmount = -1;
                }
                String configPath = "dispensers.default-amount";
                switch (slot) {
                    case 29:
                        plugin.getConfig().set("dispensers.default-amount", plugin.getConfig().getInt("dispensers.default-amount") + addAmount);
                        configPath = "dispensers.default-amount";
                        break;
                    case 31:
                        plugin.getConfig().set("droppers.default-amount", plugin.getConfig().getInt("droppers.default-amount") + addAmount);
                        configPath = "droppers.default-amount";
                        break;
                    case 33:
                        plugin.getConfig().set("hoppers.default-amount", plugin.getConfig().getInt("hoppers.default-amount") + addAmount);
                        configPath = "hoppers.default-amount";
                        break;
                }
                plugin.saveConfig();
                ItemStack amount = new ItemStack(Material.SLIME_BALL);
                ItemMeta amountMeta = amount.getItemMeta();
                Objects.requireNonNull(amountMeta).setDisplayName(ChatColor.YELLOW + "Default Amount: " + ChatColor.RED + plugin.getConfig().getInt(configPath));
                List<String> lore = new ArrayList<>(Collections.singletonList(ChatColor.GRAY + "Left-click to increase"));
                lore.add(ChatColor.GRAY + "Right-click to decrease");
                amountMeta.setLore(lore);
                amount.setItemMeta(amountMeta);
                Objects.requireNonNull(e.getClickedInventory()).setItem(slot, amount);
                return;
            }
            // Reload Config Button
            if (e.getCurrentItem().getType().equals(Material.STRING)) {
                new SortifyCommand(plugin).reloadPluginConfig(player);
            }
        }
    }
}