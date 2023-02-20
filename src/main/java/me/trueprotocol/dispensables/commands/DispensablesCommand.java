package me.trueprotocol.dispensables.commands;

import me.trueprotocol.dispensables.Dispensables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class DispensablesCommand implements CommandExecutor, TabCompleter {

    Dispensables plugin;
    public DispensablesCommand(Dispensables plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelpMessage(sender);
        } else {
            switch (args[0].toLowerCase()) {
                case "reload":
                    reloadPluginConfig(sender);
                    break;
                case "gui":
                    openDispensablesGUI(sender);
                    break;
                default:
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Invalid command.");
                    break;
            }
        }
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "v" + plugin.getDescription().getVersion() + " by TrueProtocol");
        sender.sendMessage(ChatColor.YELLOW + "/dispensables help");
        sender.sendMessage(ChatColor.YELLOW + "/dispensables reload");
        sender.sendMessage(ChatColor.YELLOW + "/dispensables gui");
        sender.sendMessage(ChatColor.YELLOW + "/dispenser");
        sender.sendMessage(ChatColor.YELLOW + "/dropper");
        sender.sendMessage(ChatColor.YELLOW + "/hopper");
    }

    private void reloadPluginConfig(CommandSender sender) {
        plugin.reloadConfig();
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Reload complete!");
    }

    private void openDispensablesGUI(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "You must be a player to use this command!");
            return;
        }
        Player player = (Player) sender;
        Inventory mainMenu = Bukkit.createInventory(player, 18, ChatColor.DARK_GRAY + "           [" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "]");
        addDispenserMenuItems(mainMenu);
        addDropperMenuItems(mainMenu);
        addHopperMenuItems(mainMenu);
        player.openInventory(mainMenu);
    }

    private void addDispenserMenuItems(Inventory inventory) {
        ItemStack dispenser = new ItemStack(Material.DISPENSER);
        ItemMeta dispenserMeta = dispenser.getItemMeta();
        dispenserMeta.setDisplayName(ChatColor.YELLOW + "Dispensers");
        dispenser.setItemMeta(dispenserMeta);
        inventory.setItem(11, dispenser);
        addToggleableMenuItem(inventory, 11, "dispensers.enabled");
    }

    private void addDropperMenuItems(Inventory inventory) {
        ItemStack dropper = new ItemStack(Material.DROPPER);
        ItemMeta dropperMeta = dropper.getItemMeta();
        dropperMeta.setDisplayName(ChatColor.YELLOW + "Droppers");
        dropper.setItemMeta(dropperMeta);
        inventory.setItem(13, dropper);
        addToggleableMenuItem(inventory, 13, "droppers.enabled");
    }

    private void addHopperMenuItems(Inventory inventory) {
        ItemStack hopper = new ItemStack(Material.HOPPER);
        ItemMeta hopperMeta = hopper.getItemMeta();
        hopperMeta.setDisplayName(ChatColor.YELLOW + "Hoppers");
        hopper.setItemMeta(hopperMeta);
        inventory.setItem(15, hopper);
        addToggleableMenuItem(inventory, 15, "hoppers.enabled");
    }

    private void addToggleableMenuItem(Inventory inventory, int index, String configPath) {
        ItemStack enabled;
        ItemMeta enabledMeta;
        if (plugin.getConfig().getBoolean(configPath)) {
            enabled = new ItemStack(Material.GREEN_WOOL);
            enabledMeta = enabled.getItemMeta();
            enabledMeta.setDisplayName(ChatColor.GREEN + "Enabled");
        } else {
            enabled = new ItemStack(Material.RED_WOOL);
            enabledMeta = enabled.getItemMeta();
            enabledMeta.setDisplayName(ChatColor.RED + "Disabled");
        }
        enabled.setItemMeta(enabledMeta);
        inventory.setItem(index - 9, enabled);
    }

    //@Override
    //public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
    //    if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
    //        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "v" + plugin.getDescription().getVersion() + " by TrueProtocol");
    //        sender.sendMessage(ChatColor.YELLOW + "/dispensables help");
    //        sender.sendMessage(ChatColor.YELLOW + "/dispensables reload");
    //        sender.sendMessage(ChatColor.YELLOW + "/dispensables gui");
    //        sender.sendMessage(ChatColor.YELLOW + "/dispenser");
    //        sender.sendMessage(ChatColor.YELLOW + "/dropper");
    //        sender.sendMessage(ChatColor.YELLOW + "/hopper");
    //    } else {
    //        switch (args[0].toLowerCase()) {
    //            case "reload":
    //                plugin.reloadConfig();
    //                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Reload complete!");
    //            case "gui":
    //                // Main Menu
    //                if (sender instanceof Player) {
    //                    Player p = (Player) sender;
    //                    Inventory mainMenu = Bukkit.createInventory(p, 18, ChatColor.DARK_GRAY + "           [" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "]");
    //                    // Enabled Buttons
    //                    ItemStack enabled = new ItemStack(Material.GREEN_WOOL);
    //                    ItemMeta enabledMeta = enabled.getItemMeta();
    //                    assert enabledMeta != null;
    //                    ItemStack disabled = new ItemStack(Material.GREEN_WOOL);
    //                    ItemMeta disabledMeta = enabled.getItemMeta();
    //                    assert disabledMeta != null;
    //                    // Dispensers
    //                    ItemStack disp = new ItemStack(Material.DISPENSER);
    //                    ItemMeta dispMeta = disp.getItemMeta();
    //                    assert dispMeta != null;
    //                    dispMeta.setDisplayName(ChatColor.YELLOW + "Dispensers");
    //                    disp.setItemMeta(dispMeta);
    //                    mainMenu.setItem(11, disp);
    //                    // Dispensers Enabled
    //                    if (plugin.getConfig().getBoolean("dispensers.enabled")) {
    //                        enabledMeta.setDisplayName(ChatColor.YELLOW + "Dispensers: " + ChatColor.GREEN + "Enabled");
    //                        enabled.setItemMeta(enabledMeta);
    //                        mainMenu.setItem(2, enabled);
    //                    } else {
    //                        disabledMeta.setDisplayName(ChatColor.YELLOW + "Dispensers: " + ChatColor.RED + "Disabled");
    //                        disabled.setItemMeta(disabledMeta);
    //                        mainMenu.setItem(2, disabled);
    //                    }
    //                    // Droppers
    //                    ItemStack drop = new ItemStack(Material.DROPPER);
    //                    ItemMeta dropMeta = drop.getItemMeta();
    //                    assert dropMeta != null;
    //                    dropMeta.setDisplayName(ChatColor.YELLOW + "Droppers");
    //                    drop.setItemMeta(dropMeta);
    //                    mainMenu.setItem(13, drop);
    //                    // Droppers Enabled
    //                    if (plugin.getConfig().getBoolean("droppers.enabled")) {
    //                        enabledMeta.setDisplayName(ChatColor.YELLOW + "Droppers: " + ChatColor.GREEN + "Enabled");
    //                        enabled.setItemMeta(enabledMeta);
    //                        mainMenu.setItem(4, enabled);
    //                    } else {
    //                        disabledMeta.setDisplayName(ChatColor.YELLOW + "Droppers: " + ChatColor.RED + "Disabled");
    //                        disabled.setItemMeta(disabledMeta);
    //                        mainMenu.setItem(4, disabled);
    //                    }
    //                    // Hoppers
    //                    ItemStack hop = new ItemStack(Material.HOPPER);
    //                    ItemMeta hopMeta = hop.getItemMeta();
    //                    assert hopMeta != null;
    //                    hopMeta.setDisplayName(ChatColor.YELLOW + "Hoppers");
    //                    hop.setItemMeta(hopMeta);
    //                    mainMenu.setItem(15, hop);
    //                    // Hoppers Enabled
    //                    if (plugin.getConfig().getBoolean("hoppers.enabled")) {
    //                        enabledMeta.setDisplayName(ChatColor.YELLOW + "Hoppers: " + ChatColor.GREEN + "Enabled");
    //                        enabled.setItemMeta(enabledMeta);
    //                        mainMenu.setItem(6, enabled);
    //                    } else {
    //                        disabledMeta.setDisplayName(ChatColor.YELLOW + "Hoppers: " + ChatColor.RED + "Disabled");
    //                        disabled.setItemMeta(disabledMeta);
    //                        mainMenu.setItem(6, disabled);
    //                    }
    //                    p.openInventory(mainMenu);
    //                } else sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "You must be a player to use this command!");
    //        }
    //    } return true;
    //}

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("help");
            arguments.add("reload");
            arguments.add("gui");
            return arguments;
        } return null;
    }
}