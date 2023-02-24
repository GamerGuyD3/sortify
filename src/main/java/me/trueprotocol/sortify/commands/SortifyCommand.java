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

import java.util.ArrayList;
import java.util.List;

public class SortifyCommand implements CommandExecutor, TabCompleter {

    Sortify plugin;
    public SortifyCommand(Sortify plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelpMessage(sender);
        } else {
            Player player = (Player) sender;
            switch (args[0].toLowerCase()) {
                case "reload":
                    reloadPluginConfig(player);
                    break;
                case "gui":
                case "editor":
                    if (!(sender instanceof Player)) {
                        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "You must be a player to use this command!");
                        break;
                    }
                    new MenuCreator.mainMenu(plugin).openSortifyEditor(player);
                    break;
                default:
                    sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.RED + "Invalid command.");
                    break;
            }
        }
        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "v" + plugin.getDescription().getVersion() + " by TrueProtocol");
        sender.sendMessage(ChatColor.YELLOW + "/sortify help");
        sender.sendMessage(ChatColor.YELLOW + "/sortify reload");
        sender.sendMessage(ChatColor.YELLOW + "/sortify editor");
        sender.sendMessage(ChatColor.YELLOW + "/dispenser");
        sender.sendMessage(ChatColor.YELLOW + "/dropper");
        sender.sendMessage(ChatColor.YELLOW + "/hopper");
    }

    public void reloadPluginConfig(Player player) {
        plugin.reloadConfig();
        player.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Reload complete!");
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("help");
            arguments.add("reload");
            arguments.add("editor");
            return arguments;
        } return null;
    }
}