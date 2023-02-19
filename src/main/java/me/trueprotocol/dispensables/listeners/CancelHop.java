package me.trueprotocol.dispensables.listeners;

import me.trueprotocol.dispensables.Dispensables;
import me.trueprotocol.dispensables.runnables.RemoveFromHopper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

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

        ItemStack item = e.getItem();
        Material itemType = item.getType();
        ItemMeta itemMeta = item.getItemMeta();
        boolean matchCheck = false;
        assert itemMeta != null;
        int i = 0;

        // Get the config values for the specific item type
        boolean whitelist = plugin.getConfig().getBoolean("hoppers.whitelist-mode");
        Set<String> keySet = Objects.requireNonNull(plugin.getConfig().getConfigurationSection("hoppers.items")).getKeys(false);

        // Compares item being hopped with keys in 'items'
        for (String key : keySet) {
            ConfigurationSection itemConfig = plugin.getConfig().getConfigurationSection("hoppers.items." + key);
            // Material Check
            assert itemConfig != null;
            if (itemConfig.contains("material") && itemConfig.getString("material") != null) {
                Material materialConfig = Material.valueOf(itemConfig.getString("material"));
                if (itemType == materialConfig) matchCheck = true;
            }
            // Display Name Check
            if (itemConfig.contains("display-name") && itemConfig.getString("display-name") != null) {
                String displayNameConfig = itemConfig.getString("display-name");
                if (!itemMeta.hasDisplayName()) matchCheck = false;
                else if (!(itemMeta.getDisplayName().equals(displayNameConfig) && matchCheck)) matchCheck = false;
            }
            // Lore Check
            if (itemConfig.contains("lore") && itemConfig.getString("lore") != null) {
                List<String> loreConfig = itemConfig.getStringList("lore");
                if (!itemMeta.hasLore()) matchCheck = false;
                else if (!(Objects.equals(itemMeta.getLore(), loreConfig) && matchCheck)) matchCheck = false;
            }
            // Enchantment Check
            if (itemConfig.contains("enchantments") && itemConfig.getString("enchantments") != null) {
                Map<Enchantment, Integer> enchantmentsConfig = new HashMap<>();
                ConfigurationSection enchantmentSection = itemConfig.getConfigurationSection("enchantments");
                if (enchantmentSection != null) {
                    for (String enchantmentName : enchantmentSection.getKeys(false)) {
                        Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName.toLowerCase()));
                        if (enchantment != null) {
                            int level = enchantmentSection.getInt(enchantmentName);
                            enchantmentsConfig.put(enchantment, level);
                        }
                    }
                }
                if (!itemMeta.hasEnchants()) matchCheck = false;
                else if (!(itemMeta.getEnchants().equals(enchantmentsConfig) && matchCheck)) matchCheck = false;
            }

            // Get default hop amount from config
            int amountDefault = plugin.getConfig().getInt("hoppers.default-amount");

            // Change behavior based on 'whitelist-mode' and whether it matches with an item in 'items'
            if (whitelist) {
                // Whitelist and item matches
                if (matchCheck) {
                    e.setCancelled(false);
                    // Set amount to hop
                    int amount = itemConfig.getInt("amount");
                    if (itemConfig.contains("amount")) item.setAmount(amount);
                    else item.setAmount(amountDefault);
                    // Whitelist and item doesn't match
                } else { e.setCancelled(true); continue; }
            } else {
                // Blacklist and item matches
                if (matchCheck) { e.setCancelled(true); return; }
                // Blacklist and item doesn't match
                else {
                    e.setCancelled(false);
                    // Check if comparing to last key
                    i++;
                    if(i < keySet.size()) continue;
                    item.setAmount(amountDefault);
                }
            }

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
            break;
        }
    }

    //@EventHandler
    //public void onInventoryPickupItem(InventoryPickupItemEvent e) {
//
    //    if (!(e.getInventory().getType().equals(InventoryType.HOPPER))) return;
    //    // Check if enabled in config
    //    if (!plugin.getConfig().getBoolean("hoppers.enabled")) return;
//
    //    ItemStack item = e.getItem().getItemStack();
    //    Material itemType = item.getType();
    //    ItemMeta itemMeta = item.getItemMeta();
    //    boolean matchCheck = false;
    //    assert itemMeta != null;
    //    int i = 0;
//
    //    // Get the config values for the specific item type
    //    boolean whitelist = plugin.getConfig().getBoolean("hoppers.whitelist-mode");
    //    Set<String> keySet = Objects.requireNonNull(plugin.getConfig().getConfigurationSection("hoppers.items")).getKeys(false);
//
    //    // Compares item being hopped with keys in 'items'
    //    for (String key : keySet) {
    //        ConfigurationSection itemConfig = plugin.getConfig().getConfigurationSection("hoppers.items." + key);
    //        // Material Check
    //        assert itemConfig != null;
    //        if (itemConfig.contains("material") && itemConfig.getString("material") != null) {
    //            Material materialConfig = Material.valueOf(itemConfig.getString("material"));
    //            if (itemType == materialConfig) matchCheck = true;
    //        }
    //        // Display Name Check
    //        if (itemConfig.contains("display-name") && itemConfig.getString("display-name") != null) {
    //            String displayNameConfig = itemConfig.getString("display-name");
    //            if (!itemMeta.hasDisplayName()) matchCheck = false;
    //            else if (!(itemMeta.getDisplayName().equals(displayNameConfig) && matchCheck)) matchCheck = false;
    //        }
    //        // Lore Check
    //        if (itemConfig.contains("lore") && itemConfig.getString("lore") != null) {
    //            List<String> loreConfig = itemConfig.getStringList("lore");
    //            if (!itemMeta.hasLore()) matchCheck = false;
    //            else if (!(Objects.equals(itemMeta.getLore(), loreConfig) && matchCheck)) matchCheck = false;
    //        }
    //        // Enchantment Check
    //        if (itemConfig.contains("enchantments") && itemConfig.getString("enchantments") != null) {
    //            Map<Enchantment, Integer> enchantmentsConfig = new HashMap<>();
    //            ConfigurationSection enchantmentSection = itemConfig.getConfigurationSection("enchantments");
    //            if (enchantmentSection != null) {
    //                for (String enchantmentName : enchantmentSection.getKeys(false)) {
    //                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName.toLowerCase()));
    //                    if (enchantment != null) {
    //                        int level = enchantmentSection.getInt(enchantmentName);
    //                        enchantmentsConfig.put(enchantment, level);
    //                    }
    //                }
    //            }
    //            if (!itemMeta.hasEnchants()) matchCheck = false;
    //            else if (!(itemMeta.getEnchants().equals(enchantmentsConfig) && matchCheck)) matchCheck = false;
    //        }
//
    //        // Get default hop amount from config
    //        int amountDefault = plugin.getConfig().getInt("hoppers.default-amount");
//
    //        // Change behavior based on 'whitelist-mode' and whether it matches with an item in 'items'
    //        if (whitelist) {
    //            // Whitelist and item matches
    //            if (matchCheck) {
    //                e.setCancelled(false);
    //                // Set amount to hop
    //                int amount = itemConfig.getInt("amount");
    //                if (itemConfig.contains("amount")) item.setAmount(amount);
    //                else item.setAmount(amountDefault);
    //                // Whitelist and item doesn't match
    //            } else { e.setCancelled(true); continue; }
    //        } else {
    //            // Blacklist and item matches
    //            if (matchCheck) { e.setCancelled(true); return; }
    //            // Blacklist and item doesn't match
    //            else {
    //                e.setCancelled(false);
    //                // Check if comparing to last key
    //                i++;
    //                if(i < keySet.size()) continue;
    //                item.setAmount(amountDefault);
    //            }
    //        }
//
    //        // Set Amount to Remove from Hopper
    //        ItemStack itemToRemove = e.getItem().getItemStack();
    //        itemToRemove.setAmount(item.getAmount() - 1);
//
    //        // Remove item from Hopper
    //        Inventory hopperInv = e.getInventory();
    //        // Runs when items in hopper is less than amount trying to be hopped
    //        if (!hopperInv.containsAtLeast(item, item.getAmount() - 1)) {
    //            int amountLeft = 1;
    //            for (ItemStack slot : hopperInv.getContents()) {
    //                if (slot != null && slot.isSimilar(item)) amountLeft += slot.getAmount();
    //            }
    //            item.setAmount(amountLeft);
    //            if (amountLeft > 1) new RemoveFromHopper(hopperInv, itemToRemove).runTaskLater(plugin, 1);
    //            // Normal item removal
    //        } else new RemoveFromHopper(hopperInv, itemToRemove).runTaskLater(plugin, 1);
//
    //        hopperInv.addItem(item);
    //        break;
    //    }
    //}
}