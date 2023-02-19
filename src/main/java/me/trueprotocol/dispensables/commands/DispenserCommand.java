package me.trueprotocol.dispensables.commands;

import me.trueprotocol.dispensables.Dispensables;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;
import java.util.stream.Collectors;

public class DispenserCommand implements CommandExecutor, TabCompleter {

    Dispensables plugin;

    public DispenserCommand(Dispensables plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.GRAY + "----------" + ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispenser Commands" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + "----------");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser help");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser list");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser <on/off>");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser whitelist <on/off>");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser <add/remove> <MATERIAL>");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser name <on/off>");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser name whitelist <on/off>");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser name <add/remove> <string>");
        } else {
            List<String> dispense = plugin.getConfig().getStringList("dispensers.dispense-items");
            String arg0 = args[0].toLowerCase();
            switch (arg0) {
                //dispenser list
                case "list":
                    List<String> namelist = plugin.getConfig().getStringList("dispensers.name.dispense-items");
                    //list normal items
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispense Items" + ChatColor.DARK_GRAY + "]");
                    for (int i = 0; i < dispense.size(); i++)
                        sender.sendMessage(ChatColor.YELLOW + "- " + dispense.get(i));
                    //list named items
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispense Custom Names" + ChatColor.DARK_GRAY + "]");
                    for (int i = 0; i < namelist.size(); i++)
                        sender.sendMessage(ChatColor.YELLOW + "- " + namelist.get(i));
                    break;
                //dispenser enable
                case "on":
                    plugin.getConfig().set("dispensers.enabled", true);
                    plugin.saveConfig();
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Now controlling dispensers.");
                    break;
                //dispenser disable
                case "off":
                    plugin.getConfig().set("dispensers.enabled", false);
                    plugin.saveConfig();
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "No longer controlling dispensers.");
                    break;
                //dispenser whitelist
                case "whitelist":
                    boolean dispensewhitelist = plugin.getConfig().getBoolean("dispensers.whitelist-mode");
                    if (args.length == 1)
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dispenser whitelist mode is " + ChatColor.YELLOW + dispensewhitelist + ChatColor.WHITE + ".");
                        //dispenser whitelist enable
                    else if (args[1].equalsIgnoreCase("on")) {
                        plugin.getConfig().set("dispensers.whitelist-mode", true);
                        plugin.saveConfig();
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dispenser whitelist mode " + ChatColor.GREEN + "on" + ChatColor.WHITE + ".");
                    }
                    //dispenser whitelist disable
                    else if (args[1].equalsIgnoreCase("off")) {
                        plugin.getConfig().set("dispensers.whitelist-mode", false);
                        plugin.saveConfig();
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dispenser whitelist mode " + ChatColor.RED + "off" + ChatColor.WHITE + ".");
                    }
                    break;
                //dispenser add
                case "add":
                    if (args.length == 1)
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Missing Argument <MATERIAL>");
                    else if (Material.matchMaterial(args[1]) != null) {
                        //Material matches
                        dispense.add(args[1].toUpperCase());
                        plugin.getConfig().set("dispensers.dispense-items", dispense);
                        plugin.saveConfig();
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + args[1].toUpperCase() + ChatColor.WHITE + " was added to dispensers.");
                    } else {
                        //Material doesn't match
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + args[1].toUpperCase() + ChatColor.WHITE + " is not a material.");
                    }
                    break;
                //dispenser remove
                case "remove":
                    if (args.length == 1)
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Missing Argument <MATERIAL>");
                    else if (Material.matchMaterial(args[1]) != null) {
                        //Material matches
                        dispense.remove(args[1].toUpperCase());
                        plugin.getConfig().set("dispensers.dispense-items", dispense);
                        plugin.saveConfig();
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + args[1].toUpperCase() + ChatColor.WHITE + " was removed from dispensers.");
                    } else {
                        //Material doesn't match
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + args[1].toUpperCase() + ChatColor.WHITE + " is not a material.");
                    }
                    break;
                //dispenser name
                case "name":
                    List<String> names = plugin.getConfig().getStringList("dispensers.name.dispense-items");
                    if (args.length == 1) {
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispense Custom Names" + ChatColor.DARK_GRAY + "]");
                        for (int i = 0; i < names.size(); i++)
                            sender.sendMessage(ChatColor.YELLOW + "- " + names.get(i));
                    } else {
                        String arg1 = args[1].toLowerCase();
                        switch (arg1) {
                            //dispenser name enable
                            case "on":
                                plugin.getConfig().set("dispensers.name.enabled", true);
                                plugin.saveConfig();
                                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Custom Named Items are now " + ChatColor.GREEN + "enabled " + ChatColor.WHITE + "for dispensers.");
                                break;
                            //dispenser name disable
                            case "off":
                                plugin.getConfig().set("dispensers.name.enabled", false);
                                plugin.saveConfig();
                                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Custom Named Items are now " + ChatColor.RED + "disabled " + ChatColor.WHITE + "for dispensers.");
                                break;
                            //dispenser name whitelist
                            case "whitelist":
                                boolean dispensenameswhitelist = plugin.getConfig().getBoolean("dispensers.name.whitelist-mode");
                                if (args.length == 2)
                                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dispenser custom names whitelist mode is " + ChatColor.YELLOW + dispensenameswhitelist + ChatColor.WHITE + ".");
                                    //dispenser name whitelist enable
                                else if (args[2].equalsIgnoreCase("on")) {
                                    plugin.getConfig().set("dispensers.name.whitelist-mode", true);
                                    plugin.saveConfig();
                                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dispenser custom names whitelist mode " + ChatColor.GREEN + "enabled" + ChatColor.WHITE + ".");
                                }
                                //dispenser name whitelist disable
                                else if (args[2].equalsIgnoreCase("off")) {
                                    plugin.getConfig().set("dispensers.name.whitelist-mode", false);
                                    plugin.saveConfig();
                                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dispenser custom names whitelist mode " + ChatColor.RED + "disabled" + ChatColor.WHITE + ".");
                                }
                                break;
                            //dispenser name add
                            case "add":
                                if (args.length == 1)
                                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Missing Argument <ItemName>");
                                else {
                                    String name = StringUtils.join(args, " ", 2, args.length);
                                    names.add(name);
                                    plugin.getConfig().set("dispensers.name.dispense-items", names);
                                    plugin.saveConfig();
                                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + name + ChatColor.WHITE + " was added to dispensers custom names.");
                                }
                                break;
                            //dispenser name remove
                            case "remove":
                                if (args.length == 1)
                                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Missing Argument <ItemName>");
                                else {
                                    String name = StringUtils.join(args, " ", 2, args.length);
                                    names.remove(name);
                                    plugin.getConfig().set("dispensers.name.dispense-items", names);
                                    plugin.saveConfig();
                                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.GREEN + name + ChatColor.WHITE + " was removed from dispensers custom names.");
                                }
                                break;
                            default:
                                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Unknown argument! Do " + ChatColor.YELLOW + "/disp " + ChatColor.RED + "for a list of commands");
                                break;
                        }
                    }
                    break;
                default:
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Unknown argument! Do " + ChatColor.YELLOW + "/disp " + ChatColor.RED + "for a list of commands");
                    break;
            }
        }
        return true;
    }

    //@Override
    //public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
    //    List<String> suggestions = new ArrayList<>();
    //    if (args.length == 1) {
    //        suggestions.addAll(Arrays.asList("help", "list", "on", "off", "whitelist", "add", "remove", "name"));
    //    } else if (args.length == 2) {
    //        if (args[0].equalsIgnoreCase("whitelist")) {
    //            suggestions.addAll(Arrays.asList("on", "off"));
    //        } else if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove")) {
    //            suggestions.add("<MATERIAL>");
    //        } else if (args[0].equalsIgnoreCase("name")) {
    //            suggestions.addAll(Arrays.asList("on", "off", "whitelist", "add", "remove"));
    //        }
    //    } else if (args.length == 3) {
    //        if (args[0].equalsIgnoreCase("name") && args[1].equalsIgnoreCase("whitelist")) {
    //            suggestions.addAll(Arrays.asList("on", "off"));
    //        } else if (args[0].equalsIgnoreCase("name") && (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("remove"))) {
    //            suggestions.add("<string>");
    //        }
    //    }
    //    return suggestions;
    //}
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        Map<String, List<String>> tabCompletions = new HashMap<>();
        tabCompletions.put("help", Arrays.asList("help"));
        tabCompletions.put("list", Arrays.asList("list"));
        tabCompletions.put("on", Arrays.asList("on"));
        tabCompletions.put("off", Arrays.asList("off"));
        tabCompletions.put("whitelist", Arrays.asList("whitelist", "on", "off"));
        tabCompletions.put("add", Arrays.asList("add", "<MATERIAL>"));
        tabCompletions.put("remove", Arrays.asList("remove", "<MATERIAL>"));
        tabCompletions.put("name", Arrays.asList("name", "on", "off", "whitelist", "add", "remove"));
        tabCompletions.put("name whitelist", Arrays.asList("name", "whitelist", "on", "off"));
        tabCompletions.put("name add", Arrays.asList("name", "add", "<string>"));
        tabCompletions.put("name remove", Arrays.asList("name", "remove", "<string>"));

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
