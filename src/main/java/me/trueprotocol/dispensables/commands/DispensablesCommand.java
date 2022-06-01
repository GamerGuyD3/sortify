package me.trueprotocol.dispensables.commands;

import me.trueprotocol.dispensables.Dispensables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DispensablesCommand implements CommandExecutor, TabCompleter {

    Dispensables plugin;
    public DispensablesCommand(Dispensables plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "v" + plugin.getDescription().getVersion() + " by TrueProtocol");
            sender.sendMessage(ChatColor.YELLOW + "/dispensables help");
            sender.sendMessage(ChatColor.YELLOW + "/dispensables reload");
            sender.sendMessage(ChatColor.YELLOW + "/dispensables gui");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser");
            sender.sendMessage(ChatColor.YELLOW + "/dropper");
            sender.sendMessage(ChatColor.YELLOW + "/hopper");
        }
        else
        {
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Reload complete!");
            }
            if (args[0].equalsIgnoreCase("gui")) {
                // Main Menu
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    Inventory mainmenu = Bukkit.createInventory(p, 18, ChatColor.DARK_GRAY + "           [" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "]");
                    // Dispensers
                    ItemStack disp = new ItemStack(Material.DISPENSER);
                    ItemMeta dispMeta = disp.getItemMeta();
                    dispMeta.setDisplayName(ChatColor.YELLOW + "Dispensers");
                    disp.setItemMeta(dispMeta);
                    mainmenu.setItem(11, disp);
                    // Dispensers Enabled
                    if (plugin.getConfig().getBoolean("dispensers.enabled")) {
                        ItemStack dispenabled = new ItemStack(Material.GREEN_WOOL);
                        ItemMeta dispenabledMeta = dispenabled.getItemMeta();
                        dispenabledMeta.setDisplayName(ChatColor.YELLOW + "Dispensers: " + ChatColor.GREEN + "Enabled");
                        dispenabled.setItemMeta(dispenabledMeta);
                        mainmenu.setItem(2, dispenabled);
                    }
                    else if (!plugin.getConfig().getBoolean("dispensers.enabled")) {
                        ItemStack dispenabled = new ItemStack(Material.RED_WOOL);
                        ItemMeta dispenabledMeta = dispenabled.getItemMeta();
                        dispenabledMeta.setDisplayName(ChatColor.YELLOW + "Dispensers: " + ChatColor.RED + "Disabled");
                        dispenabled.setItemMeta(dispenabledMeta);
                        mainmenu.setItem(2, dispenabled);
                    }
                    // Droppers
                    ItemStack drop = new ItemStack(Material.DROPPER);
                    ItemMeta dropMeta = drop.getItemMeta();
                    dropMeta.setDisplayName(ChatColor.YELLOW + "Droppers");
                    drop.setItemMeta(dropMeta);
                    mainmenu.setItem(13, drop);
                    // Droppers Enabled
                    if (plugin.getConfig().getBoolean("droppers.enabled")) {
                        ItemStack dropenabled = new ItemStack(Material.GREEN_WOOL);
                        ItemMeta dropenabledMeta = dropenabled.getItemMeta();
                        dropenabledMeta.setDisplayName(ChatColor.YELLOW + "Droppers: " + ChatColor.GREEN + "Enabled");
                        dropenabled.setItemMeta(dropenabledMeta);
                        mainmenu.setItem(4, dropenabled);
                    }
                    else if (!plugin.getConfig().getBoolean("droppers.enabled")) {
                        ItemStack dropenabled = new ItemStack(Material.RED_WOOL);
                        ItemMeta dropenabledMeta = dropenabled.getItemMeta();
                        dropenabledMeta.setDisplayName(ChatColor.YELLOW + "Droppers: " + ChatColor.RED + "Disabled");
                        dropenabled.setItemMeta(dropenabledMeta);
                        mainmenu.setItem(4, dropenabled);
                    }
                    // Hoppers
                    ItemStack hop = new ItemStack(Material.HOPPER);
                    ItemMeta hopMeta = hop.getItemMeta();
                    hopMeta.setDisplayName(ChatColor.YELLOW + "Hoppers");
                    hop.setItemMeta(hopMeta);
                    mainmenu.setItem(15, hop);
                    // Hoppers Enabled
                    if (plugin.getConfig().getBoolean("hoppers.enabled")) {
                        ItemStack hopenabled = new ItemStack(Material.GREEN_WOOL);
                        ItemMeta hopenabledMeta = hopenabled.getItemMeta();
                        hopenabledMeta.setDisplayName(ChatColor.YELLOW + "Hoppers: " + ChatColor.GREEN + "Enabled");
                        hopenabled.setItemMeta(hopenabledMeta);
                        mainmenu.setItem(6, hopenabled);
                    }
                    else if (!plugin.getConfig().getBoolean("hoppers.enabled")) {
                        ItemStack hopenabled = new ItemStack(Material.RED_WOOL);
                        ItemMeta hopenabledMeta = hopenabled.getItemMeta();
                        hopenabledMeta.setDisplayName(ChatColor.YELLOW + "Hoppers: " + ChatColor.RED + "Disabled");
                        hopenabled.setItemMeta(hopenabledMeta);
                        mainmenu.setItem(6, hopenabled);
                    }
                    p.openInventory(mainmenu);
                }
                else
                {
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "You must be a player to use this command!");
                }
            }
        } return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("help");
            arguments.add("reload");
            arguments.add("gui");
            return arguments;
        }return null;
    }
}