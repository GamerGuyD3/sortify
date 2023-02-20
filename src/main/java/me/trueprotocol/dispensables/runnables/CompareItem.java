package me.trueprotocol.dispensables.runnables;

import me.trueprotocol.dispensables.Dispensables;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class CompareItem {

    public static class ItemFilter {

        private final boolean cancelEvent;
        private final ItemStack itemFiltered;

        public ItemFilter(boolean cancelEvent, ItemStack itemFiltered) {
            this.cancelEvent = cancelEvent;
            this.itemFiltered = itemFiltered;
        }

        public boolean isEventCancelled() {
            return cancelEvent;
        }

        public ItemStack getFilteredItemStack() {
            return itemFiltered;
        }

        public static ItemFilter compareItem(Dispensables plugin, ItemStack item, String blockTypeString) {
            // Get the config values for the specific item type
            boolean whitelist = plugin.getConfig().getBoolean(blockTypeString + ".whitelist-mode");

            Material itemType = item.getType();
            ItemMeta itemMeta = item.getItemMeta();
            boolean matchCheck;
            assert itemMeta != null;
            int i = 0;
            boolean cancelEvent = false;

            Set<String> keySet = Objects.requireNonNull(plugin.getConfig().getConfigurationSection(blockTypeString + ".items")).getKeys(false);

            // Compares item being dispensed with keys in 'items'
            for (String key : keySet) {
                matchCheck = true;
                ConfigurationSection itemConfig = plugin.getConfig().getConfigurationSection(blockTypeString + ".items." + key);
                assert itemConfig != null;
                // Material Check
                if (itemConfig.contains("material") && itemConfig.getString("material") != null) {
                    Material materialConfig = Material.valueOf(itemConfig.getString("material"));
                    if (!(itemType == materialConfig)) matchCheck = false;
                }
                // Display Name Check
                if (itemConfig.contains("display-name") && itemConfig.getString("display-name") != null) {
                    String displayNameConfig = itemConfig.getString("display-name");
                    if (!itemMeta.hasDisplayName()) matchCheck = false;
                    else if (!(itemMeta.getDisplayName().equals(displayNameConfig))) matchCheck = false;
                }
                // Lore Check
                if (itemConfig.contains("lore") && itemConfig.getString("lore") != null) {
                    List<String> loreConfig = itemConfig.getStringList("lore");
                    if (!itemMeta.hasLore()) matchCheck = false;
                    else if (!(Objects.equals(itemMeta.getLore(), loreConfig))) matchCheck = false;
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
                    else if (!(itemMeta.getEnchants().equals(enchantmentsConfig))) matchCheck = false;
                }

                // Get default dispense amount from config
                int amountDefault = plugin.getConfig().getInt(blockTypeString + ".default-amount");

                // Change behavior based on 'whitelist-mode' and whether it matches with an item in 'items'
                if (whitelist) {
                    // Whitelist and item matches
                    if (matchCheck) {
                        cancelEvent = false;
                        // Set amount to dispense
                        int amount = itemConfig.getInt("amount");
                        if (itemConfig.contains("amount")) {
                            item.setAmount(amount);
                        } else {
                            item.setAmount(amountDefault);
                        }
                        // Whitelist and item don't match
                    } else {
                        cancelEvent = true;
                    }
                } else {
                    // Blacklist and item matches
                    if (matchCheck) {
                        cancelEvent = true;
                        break;
                    }
                    // Blacklist and item don't match
                    else {
                        cancelEvent = false;
                        // Check if comparing to last key
                        i++;
                        if (i < keySet.size()) continue;
                        item.setAmount(amountDefault);
                    }
                }
            }
            ItemStack itemFiltered = new ItemStack(item);

            return new ItemFilter(cancelEvent, itemFiltered);
        }
    }
}