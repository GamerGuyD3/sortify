package me.trueprotocol.dispensables.listeners;

import me.trueprotocol.dispensables.Dispensables;
import me.trueprotocol.dispensables.runnables.RemoveFromDispenser;
import me.trueprotocol.dispensables.runnables.RemoveFromDropper;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CancelDispense implements Listener {

    Dispensables plugin;

    public CancelDispense(Dispensables plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent e) {

        // Check if block is Dispenser or Dropper
        String blockTypeString;
        if (e.getBlock().getType().equals(Material.DISPENSER)) blockTypeString = "dispensers";
        else if (e.getBlock().getType().equals(Material.DROPPER)) blockTypeString = "droppers";
        else return;

        // Check if enabled in config
        if (!plugin.getConfig().getBoolean(blockTypeString + ".enabled")) return;

        ItemStack item = e.getItem();
        Material itemType = item.getType();
        ItemMeta itemMeta = item.getItemMeta();
        boolean matchCheck = false;
        assert itemMeta != null;
        int i = 0;

        // Get the config values for the specific item type
        boolean whitelist = plugin.getConfig().getBoolean(blockTypeString + ".whitelist-mode");
        Set<String> keySet = Objects.requireNonNull(plugin.getConfig().getConfigurationSection(blockTypeString + ".items")).getKeys(false);

        // Compares item being dispensed with keys in 'items'
        for (String key : keySet) {
            ConfigurationSection itemConfig = plugin.getConfig().getConfigurationSection(blockTypeString + ".items." + key);
            assert itemConfig != null;
            // Material Check
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

            // Get default dispense amount from config
            int amountDefault = plugin.getConfig().getInt(blockTypeString + ".default-amount");

            // Change behavior based on 'whitelist-mode' and whether it matches with an item in 'items'
            if (whitelist) {
                // Whitelist and item matches
                if (matchCheck) {
                    e.setCancelled(false);
                    // Set amount to dispense
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

            // Set Amount to Remove from Dispenser
            ItemStack itemToRemove = e.getItem();
            itemToRemove.setAmount(item.getAmount() - 1);

            // Remove item from Dispenser
            if (blockTypeString.equals("dispensers")) {
                Dispenser dispenser = (Dispenser) e.getBlock().getState();
                Inventory dispenserInv = dispenser.getInventory();
                // Runs when items in dispenser is less than amount trying to be dispensed
                if (!dispenserInv.containsAtLeast(item, item.getAmount() - 1)) {
                    int amountLeft = 1;
                    for (ItemStack slot : dispenserInv.getContents()) {
                        if (slot != null && slot.isSimilar(item)) amountLeft += slot.getAmount();
                    }
                    item.setAmount(amountLeft);
                    if (amountLeft > 1) new RemoveFromDispenser(dispenser, itemToRemove).runTaskLater(plugin, 1);
                // Normal item removal
                } else new RemoveFromDispenser(dispenser, itemToRemove).runTaskLater(plugin, 1);

            // Remove item from Dropper
            } else {
                Dropper dropper = (Dropper) e.getBlock().getState();
                Inventory dropperInv = dropper.getInventory();
                // Runs when items in dropper is less than amount trying to be dispensed
                if (!dropperInv.containsAtLeast(item, item.getAmount() - 1)) {
                    int amountLeft = 1;
                    for (ItemStack slot : dropperInv.getContents()) {
                        if (slot != null && slot.isSimilar(item)) amountLeft += slot.getAmount();
                    }
                    item.setAmount(amountLeft);
                    if (amountLeft > 1) new RemoveFromDropper(dropper, itemToRemove).runTaskLater(plugin, 1);
                // Normal item removal
                } else new RemoveFromDropper(dropper, itemToRemove).runTaskLater(plugin, 1);
            }

            e.setItem(item);
            break;
        }
    }
}