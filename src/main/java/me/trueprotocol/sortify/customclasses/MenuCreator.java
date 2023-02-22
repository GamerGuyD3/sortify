package me.trueprotocol.sortify.customclasses;

import me.trueprotocol.sortify.Sortify;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class MenuCreator {

    public static class mainMenu {
        private final Sortify plugin;

        public mainMenu(Sortify plugin) {
            this.plugin = plugin;
        }

        public void openSortifyEditor(Player player) {
            Inventory mainMenu = Bukkit.createInventory(player, 36, ChatColor.DARK_GRAY + "              [" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "]");
            addDispenserMenuItem(mainMenu);
            addDropperMenuItem(mainMenu);
            addHopperMenuItem(mainMenu);
            player.openInventory(mainMenu);
        }

        private void addDispenserMenuItem(Inventory inventory) {
            ItemStack dispenser = new ItemStack(Material.DISPENSER);
            ItemMeta dispenserMeta = dispenser.getItemMeta();
            Objects.requireNonNull(dispenserMeta).setDisplayName(ChatColor.YELLOW + "Dispensers");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Click to Open Editor");
            lore.add(ChatColor.GRAY + "Editing via config.yml is recommended");
            dispenserMeta.setLore(lore);
            dispenser.setItemMeta(dispenserMeta);
            inventory.setItem(2, dispenser);
            addToggleableMenuItems(inventory, 2, "dispensers");
        }

        private void addDropperMenuItem(Inventory inventory) {
            ItemStack dropper = new ItemStack(Material.DROPPER);
            ItemMeta dropperMeta = dropper.getItemMeta();
            Objects.requireNonNull(dropperMeta).setDisplayName(ChatColor.YELLOW + "Droppers");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Click to Open Editor");
            lore.add(ChatColor.GRAY + "Editing via config.yml is recommended");
            dropperMeta.setLore(lore);
            dropper.setItemMeta(dropperMeta);
            inventory.setItem(4, dropper);
            addToggleableMenuItems(inventory, 4, "droppers");
        }

        private void addHopperMenuItem(Inventory inventory) {
            ItemStack hopper = new ItemStack(Material.HOPPER);
            ItemMeta hopperMeta = hopper.getItemMeta();
            Objects.requireNonNull(hopperMeta).setDisplayName(ChatColor.YELLOW + "Hoppers");
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Click to Open Editor");
            lore.add(ChatColor.GRAY + "Editing via config.yml is recommended");
            hopperMeta.setLore(lore);
            hopper.setItemMeta(hopperMeta);
            inventory.setItem(6, hopper);
            addToggleableMenuItems(inventory, 6, "hoppers");
        }

        private void addToggleableMenuItems(Inventory inventory, int index, String configPath) {
            // Enable Buttons
            ItemStack enabled;
            ItemMeta enabledMeta;
            if (plugin.getConfig().getBoolean(configPath + ".enabled")) {
                enabled = new ItemStack(Material.LIME_DYE);
                enabledMeta = enabled.getItemMeta();
                Objects.requireNonNull(enabledMeta).setDisplayName(ChatColor.GREEN + "Enabled");
                enabledMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to toggle"));
            } else {
                enabled = new ItemStack(Material.GRAY_DYE);
                enabledMeta = enabled.getItemMeta();
                Objects.requireNonNull(enabledMeta).setDisplayName(ChatColor.RED + "Disabled");
                enabledMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to toggle"));
            }
            enabled.setItemMeta(enabledMeta);
            inventory.setItem(index + 9, enabled);
            // Whitelist Buttons
            ItemStack whitelist;
            ItemMeta whitelistMeta;
            if (plugin.getConfig().getBoolean(configPath + ".whitelist-mode")) {
                whitelist = new ItemStack(Material.WHITE_BANNER);
                whitelistMeta = whitelist.getItemMeta();
                Objects.requireNonNull(whitelistMeta).setDisplayName(ChatColor.WHITE + "Whitelist Mode");
                whitelistMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to toggle"));
            } else {
                whitelist = new ItemStack(Material.BLACK_BANNER);
                whitelistMeta = whitelist.getItemMeta();
                Objects.requireNonNull(whitelistMeta).setDisplayName(ChatColor.WHITE + "Blacklist Mode");
                whitelistMeta.setLore(Collections.singletonList(ChatColor.GRAY + "Click to toggle"));
            }
            whitelist.setItemMeta(whitelistMeta);
            inventory.setItem(index + 18, whitelist);
            // Amount Buttons
            ItemStack amount = new ItemStack(Material.SLIME_BALL);
            ItemMeta amountMeta = amount.getItemMeta();
            Objects.requireNonNull(amountMeta).setDisplayName(ChatColor.YELLOW + "Default Amount: " + ChatColor.RED + plugin.getConfig().getInt(configPath + ".default-amount"));
            List<String> lore = new ArrayList<>(Collections.singletonList(ChatColor.GRAY + "Left-click to increase"));
            lore.add(ChatColor.GRAY + "Right-click to decrease");
            amountMeta.setLore(lore);
            amount.setItemMeta(amountMeta);
            inventory.setItem(index + 27, amount);
        }

        public void openItemConfigEditor(Player player, String blockTypeString) {
            Inventory menu;
            switch (blockTypeString) {
                case "dispenser":
                    menu = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "             [" + ChatColor.GREEN + "Dispenser" + ChatColor.DARK_GRAY + "]");
                    break;
                case "dropper":
                    menu = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "              [" + ChatColor.GREEN + "Dropper" + ChatColor.DARK_GRAY + "]");
                    break;
                case "hopper":
                    menu = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "               [" + ChatColor.GREEN + "Hopper" + ChatColor.DARK_GRAY + "]");
                    break;
                default:
                    menu = Bukkit.createInventory(player, 54, ChatColor.DARK_GRAY + "                [" + ChatColor.GREEN + "Null" + ChatColor.DARK_GRAY + "]");
                    break;
            }
            // Copy config to menu
            Set<String> keySet = Objects.requireNonNull(plugin.getConfig().getConfigurationSection(blockTypeString + "s.items")).getKeys(false);
            for (String key : keySet) {
                ConfigurationSection itemConfig = plugin.getConfig().getConfigurationSection(blockTypeString + "s.items." + key);
                assert itemConfig != null;
                boolean materialCheck = true;
                Material itemType;

                if ((itemConfig.contains("material")) && itemConfig.getString("material") != null) {
                    itemType = Material.valueOf(itemConfig.getString("material"));
                } else {
                    itemType = Material.BARRIER;
                    materialCheck = false;
                }

                ItemStack item = new ItemStack(Objects.requireNonNull(itemType));
                ItemMeta itemMeta = item.getItemMeta();

                if (!materialCheck) {
                    Objects.requireNonNull(itemMeta).setDisplayName("Material N/A");
                }
                if (itemConfig.contains("display-name") && itemConfig.getString("display-name") != null) {
                    Objects.requireNonNull(itemMeta).setDisplayName(itemConfig.getString("display-name"));
                }
                if (itemConfig.contains("lore") && itemConfig.getString("lore") != null) {
                    Objects.requireNonNull(itemMeta).setLore(itemConfig.getStringList("lore"));
                }
                if (itemConfig.contains("enchantments") && itemConfig.getString("enchantments") != null) {
                    ConfigurationSection enchantmentSection = itemConfig.getConfigurationSection("enchantments");
                    if (enchantmentSection != null) {
                        for (String enchantmentName : enchantmentSection.getKeys(false)) {
                            Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(enchantmentName.toLowerCase()));
                            if (enchantment != null) {
                                int level = enchantmentSection.getInt(enchantmentName);
                                Objects.requireNonNull(itemMeta).addEnchant(enchantment, level, true);
                            }
                        }
                    }
                }
                if (itemConfig.contains("amount") && itemConfig.getString("amount") != null) {
                    item.setAmount(itemConfig.getInt("amount"));
                }
                item.setItemMeta(itemMeta);
                menu.addItem(item);
            }
            player.openInventory(menu);
        }

        public void saveItemConfigEditor(String blockTypeString, Inventory inventory) {
            plugin.getConfig().set(blockTypeString + "s.items", null);
            Map<Integer, ConfigurationSection> items = new HashMap<>();
            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack item = inventory.getItem(i);
                if (item != null) {
                    ConfigurationSection itemData = plugin.getConfig().createSection(blockTypeString + "s.items." + (i + 1));
                    itemData.set("material", item.getType().toString());
                    if (item.hasItemMeta()) {
                        if (Objects.requireNonNull(item.getItemMeta()).hasDisplayName()) {
                            itemData.set("display-name", ChatColor.stripColor(item.getItemMeta().getDisplayName()));
                        }
                        if (item.getItemMeta().hasLore()) {
                            List<String> lore = item.getItemMeta().getLore();
                            itemData.set("lore", lore);
                        }
                        if (item.getItemMeta().hasEnchants()) {
                            ConfigurationSection enchantments = itemData.createSection("enchantments");
                            for (Enchantment enchantment : item.getEnchantments().keySet()) {
                                enchantments.set(enchantment.getKey().getKey(), item.getItemMeta().getEnchantLevel(enchantment));
                            }
                        }
                    }
                    itemData.set("amount", item.getAmount());
                    items.put(i, itemData);
                }
            }
            plugin.saveConfig();
        }
    }
}
