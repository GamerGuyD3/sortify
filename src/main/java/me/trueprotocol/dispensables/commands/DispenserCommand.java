package me.trueprotocol.dispensables.commands;

import me.trueprotocol.dispensables.Dispensables;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class DispenserCommand implements CommandExecutor, TabCompleter {

    Dispensables plugin;

    public DispenserCommand(Dispensables plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelpMessage(sender);
        } else {
            String arg0 = args[0].toLowerCase();
            switch (arg0) {
                // dispenser list
                case "list":
                case "gui":
                case "editor":
                    openDispenserGUI(sender);
                    break;
                // dispenser enable
                case "on":
                    setEnabled(true);
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Now controlling dispensers.");
                    break;
                // dispenser disable
                case "off":
                    setEnabled(false);
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "No longer controlling dispensers.");
                    break;
                // dispenser whitelist
                case "whitelist":
                    if (args.length == 1) {
                        boolean dispensewhitelist = plugin.getConfig().getBoolean("dispensers.whitelist-mode");
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dispenser whitelist mode is " + ChatColor.YELLOW + dispensewhitelist + ChatColor.WHITE + ".");
                    } else {
                        // dispenser whitelist enable
                        if (args[1].equalsIgnoreCase("on")) {
                            setWhitelist(true);
                            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dispenser whitelist mode " + ChatColor.GREEN + "on" + ChatColor.WHITE + ".");
                        }
                        // dispenser whitelist disable
                        else if (args[1].equalsIgnoreCase("off")) {
                            setWhitelist(false);
                            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dispenser whitelist mode " + ChatColor.RED + "off" + ChatColor.WHITE + ".");
                        }
                    }
                    break;
                default:
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Unknown argument! Use " + ChatColor.YELLOW + "/disp " + ChatColor.RED + "for a list of commands.");
                    break;
            }
        }
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "----------" + ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispenser Commands" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + "----------");
        sender.sendMessage(ChatColor.YELLOW + "/dispenser help");
        sender.sendMessage(ChatColor.YELLOW + "/dispenser gui");
        sender.sendMessage(ChatColor.YELLOW + "/dispenser <on/off>");
        sender.sendMessage(ChatColor.YELLOW + "/dispenser whitelist <on/off>");
        sender.sendMessage(ChatColor.YELLOW + "/dispenser <add/remove> <MATERIAL>");
    }

    private void openDispenserGUI(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "You must be a player to use this command!");
            return;
        }
        Player p = (Player) sender;
        Inventory menu = Bukkit.createInventory(p, 54, ChatColor.DARK_GRAY + "    [" + ChatColor.BLUE + "Dispensers" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW + "Material List");
        // Copy config to menu
        Set<String> keySet = Objects.requireNonNull(plugin.getConfig().getConfigurationSection("dispensers.items")).getKeys(false);
        for (String key : keySet) {
            ConfigurationSection itemConfig = plugin.getConfig().getConfigurationSection("dispensers.items." + key);
            assert itemConfig != null;
            if (!(itemConfig.contains("material"))) {
                ItemStack error = new ItemStack(Material.BARRIER);
                error.getItemMeta().setDisplayName(ChatColor.RED + "NO MATERIAL FOUND: CHECK CONFIG");
                error.getItemMeta().setLore(Collections.singletonList("NO MATERIAL FOUND"));
                menu.addItem();
            }
            Material itemType = Material.valueOf(itemConfig.getString("material"));
            ItemStack item = new ItemStack(Objects.requireNonNull(itemType));
            ItemMeta itemMeta = item.getItemMeta();
            if (itemConfig.contains("display-name")) {
                itemMeta.setDisplayName(itemConfig.getString("display-name"));
            }
            if (itemConfig.contains("display-name")) {
                itemMeta.setDisplayName(itemConfig.getString("display-name"));
            }

            item.setItemMeta(itemMeta);
            menu.addItem(item);
        }
        p.openInventory(menu);
    }

    private void setEnabled(boolean status) {
        plugin.getConfig().set("dispensers.enabled", status);
        plugin.saveConfig();
    }

    private void setWhitelist(boolean status) {
        plugin.getConfig().set("dispensers.whitelist-mode", status);
        plugin.saveConfig();
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        Map<String, List<String>> tabCompletions = new HashMap<>();
        tabCompletions.put("help", Arrays.asList("help"));
        tabCompletions.put("list", Arrays.asList("list"));
        tabCompletions.put("on", Arrays.asList("on"));
        tabCompletions.put("off", Arrays.asList("off"));
        tabCompletions.put("whitelist", Arrays.asList("on", "off"));
        tabCompletions.put("add", Arrays.asList("add"));
        tabCompletions.put("remove", Arrays.asList("remove"));

        List<String> suggestions = new ArrayList<>();
        if (args.length == 1) {
            suggestions.addAll(tabCompletions.keySet());
        } else {
            String commandKey = String.join(" ", Arrays.copyOfRange(args, 0, args.length - 1));
            List<String> commandSuggestions = tabCompletions.get(commandKey);
            if (commandSuggestions != null) {
                String lastArgument = args[args.length - 1];
                suggestions.addAll(commandSuggestions.stream()
                        .filter(s -> s.startsWith(lastArgument))
                        .collect(Collectors.toList()));
            }
        }
        return suggestions;
    }
}
