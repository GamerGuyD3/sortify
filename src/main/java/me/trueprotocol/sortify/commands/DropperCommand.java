package me.trueprotocol.sortify.commands;

import me.trueprotocol.sortify.Sortify;
import me.trueprotocol.sortify.customclasses.MenuCreator;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class DropperCommand implements CommandExecutor, TabCompleter {

    Sortify plugin;

    public DropperCommand(Sortify plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelpMessage(sender);
        } else {
            String arg0 = args[0].toLowerCase();
            switch (arg0) {
                // dropper list
                case "list":
                case "gui":
                case "editor":
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "You must be a player to use this command!");
                        break;
                    }
                    Player player = (Player) sender;
                    new MenuCreator.mainMenu(plugin).openItemConfigEditor(player, "dropper");
                    break;
                // dropper enable
                case "enable":
                case "true":
                case "on":
                    setEnabled(true);
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Now controlling droppers.");
                    break;
                // dropper disable
                case "disable":
                case "false":
                case "off":
                    setEnabled(false);
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "No longer controlling droppers.");
                    break;
                // dropper whitelist
                case "whitelist":
                    if (args.length == 1) {
                        boolean dispensewhitelist = plugin.getConfig().getBoolean("droppers.whitelist-mode");
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dropper whitelist mode is " + ChatColor.YELLOW + dispensewhitelist + ChatColor.WHITE + ".");
                        break;
                    } else {
                        String arg1 = args[1].toLowerCase();
                        switch (arg1) {
                            // dropper whitelist on
                            case "enable":
                            case "true":
                            case "on":
                                setWhitelist(true);
                                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dropper whitelist mode " + ChatColor.GREEN + "on" + ChatColor.WHITE + ".");
                                break;
                            // dropper whitelist off
                            case "disable":
                            case "false":
                            case "off":
                                setWhitelist(false);
                                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Dropper whitelist mode " + ChatColor.RED + "off" + ChatColor.WHITE + ".");
                                break;
                        }
                    }
                default:
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Unknown argument! Use " + ChatColor.YELLOW + "/disp " + ChatColor.RED + "for a list of commands.");
                    break;
            }
        }
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.GRAY + "----------" + ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Dropper Commands" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + "----------");
        sender.sendMessage(ChatColor.YELLOW + "/dropper help");
        sender.sendMessage(ChatColor.YELLOW + "/dropper editor");
        sender.sendMessage(ChatColor.YELLOW + "/dropper <on/off>");
        sender.sendMessage(ChatColor.YELLOW + "/dropper whitelist <on/off>");
    }

    private void setEnabled(boolean status) {
        plugin.getConfig().set("droppers.enabled", status);
        plugin.saveConfig();
    }

    private void setWhitelist(boolean status) {
        plugin.getConfig().set("droppers.whitelist-mode", status);
        plugin.saveConfig();
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        Map<String, List<String>> tabCompletions = new HashMap<>();
        tabCompletions.put("help", Collections.singletonList("help"));
        tabCompletions.put("editor", Collections.singletonList("editor"));
        tabCompletions.put("on", Collections.singletonList("on"));
        tabCompletions.put("off", Collections.singletonList("off"));
        tabCompletions.put("whitelist", Arrays.asList("on", "off"));

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
