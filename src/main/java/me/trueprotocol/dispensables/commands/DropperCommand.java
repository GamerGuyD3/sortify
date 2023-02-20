package me.trueprotocol.dispensables.commands;

import me.trueprotocol.dispensables.Dispensables;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class DropperCommand implements CommandExecutor, TabCompleter {

    Dispensables plugin;
    public DropperCommand(Dispensables plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.GRAY + "----------" + ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dropper Commands" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + "----------");
            sender.sendMessage(ChatColor.YELLOW + "/dropper help");
            sender.sendMessage(ChatColor.YELLOW + "/dropper list");
            sender.sendMessage(ChatColor.YELLOW + "/dropper <on/off>");
            sender.sendMessage(ChatColor.YELLOW + "/dropper whitelist <on/off>");
            sender.sendMessage(ChatColor.YELLOW + "/dropper <add/remove> <MATERIAL>");
            sender.sendMessage(ChatColor.YELLOW + "/dropper name <on/off>");
            sender.sendMessage(ChatColor.YELLOW + "/dropper name whitelist <on/off>");
            sender.sendMessage(ChatColor.YELLOW + "/dropper name <add/remove> <string>");
        }
        else
        {
            List<String> dispense = plugin.getConfig().getStringList("droppers.dispense-items");
            String arg0 = args[0].toLowerCase();
            switch (arg0)
            {
                //dispenser list
                case "list":
                    List<String> namelist = plugin.getConfig().getStringList("droppers.name.dispense-items");
                    //list normal items
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Drop Items" + ChatColor.DARK_GRAY + "]");
                    for (int i = 0; i < dispense.size(); i++) sender.sendMessage(ChatColor.YELLOW + "- " + dispense.get(i));
                    //list named items
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Drop Custom Names" + ChatColor.DARK_GRAY + "]");
                    for (int i = 0; i < namelist.size(); i++) sender.sendMessage(ChatColor.YELLOW + "- " + namelist.get(i));
                    break;
                //dispenser enable
                case "on":
                    plugin.getConfig().set("droppers.enabled", true); plugin.saveConfig();
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Now controlling droppers.");
                    break;
                //dispenser disable
                case "off":
                    plugin.getConfig().set("droppers.enabled", false); plugin.saveConfig();
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "No longer controlling droppers.");
                    break;
                //dispenser whitelist
                case "whitelist":
                    boolean dispensewhitelist = plugin.getConfig().getBoolean("droppers.whitelist-mode");
                    if (args.length == 1) sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dropper whitelist mode is " + ChatColor.YELLOW + dispensewhitelist + ChatColor.WHITE + ".");
                        //dispenser whitelist enable
                    else if (args[1].equalsIgnoreCase("on")) {
                        plugin.getConfig().set("droppers.whitelist-mode", true); plugin.saveConfig();
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dropper whitelist mode " + ChatColor.GREEN + "on" + ChatColor.WHITE + ".");
                    }
                    //dispenser whitelist disable
                    else if (args[1].equalsIgnoreCase("off")) {
                        plugin.getConfig().set("droppers.whitelist-mode", false); plugin.saveConfig();
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dropper whitelist mode " + ChatColor.RED + "off" + ChatColor.WHITE + ".");
                    } break;
                //dispenser add
                case "add":
                    if (args.length == 1) sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Missing Argument <MATERIAL>");
                    else if (Material.matchMaterial(args[1]) != null) {
                        //Material matches
                        dispense.add(args[1].toUpperCase()); plugin.getConfig().set("droppers.dispense-items", dispense); plugin.saveConfig();
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + args[1].toUpperCase() + ChatColor.WHITE + " was added to droppers.");
                    }
                    else
                    {
                        //Material doesn't match
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + args[1].toUpperCase() + ChatColor.WHITE + " is not a material.");
                    } break;
                //dispenser remove
                case "remove":
                    if (args.length == 1) sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Missing Argument <MATERIAL>");
                    else if (Material.matchMaterial(args[1]) != null) {
                        //Material matches
                        dispense.remove(args[1].toUpperCase()); plugin.getConfig().set("droppers.dispense-items", dispense); plugin.saveConfig();
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + args[1].toUpperCase() + ChatColor.WHITE + " was removed from droppers.");
                    }
                    else
                    {
                        //Material doesn't match
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + args[1].toUpperCase() + ChatColor.WHITE + " is not a material.");
                    }
                    break;
                default:
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Unknown argument! Do " + ChatColor.YELLOW + "/drop " + ChatColor.RED + "for a list of commands");
                    break;
            }
        }return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> arg0 = new ArrayList<>();
            arg0.add("help");
            arg0.add("list");
            arg0.add("on");
            arg0.add("off");
            arg0.add("whitelist");
            arg0.add("add");
            arg0.add("remove");
            arg0.add("name");
            return arg0;
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("whitelist")) {
                List<String> arg1 = new ArrayList<>();
                arg1.add("on");
                arg1.add("off");
                return arg1;
            }
            if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
                List<String> arg1 = new ArrayList<>();
                arg1.add("<MATERIAL>");
                return arg1;
            }
            if (args[0].equalsIgnoreCase("name")) {
                List<String> arg1 = new ArrayList<>();
                arg1.add("on");
                arg1.add("off");
                arg1.add("whitelist");
                arg1.add("add");
                arg1.add("remove");
                return arg1;
            }
        }
        else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("name") && args[1].equalsIgnoreCase("whitelist")) {
                List<String> arg2 = new ArrayList<>();
                arg2.add("on");
                arg2.add("off");
                return arg2;
            }
            if (args[0].equalsIgnoreCase("name") && args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove")) {
                List<String> arg2 = new ArrayList<>();
                arg2.add("<string>");
                return arg2;
            }
        }return null;
    }
}
