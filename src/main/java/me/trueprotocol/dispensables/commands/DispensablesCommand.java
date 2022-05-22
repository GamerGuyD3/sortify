package me.trueprotocol.dispensables.commands;

import me.trueprotocol.dispensables.Dispensables;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class DispensablesCommand implements CommandExecutor, TabCompleter {

    Dispensables plugin = Dispensables.getPlugin(Dispensables.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "v" + plugin.getDescription().getVersion() + " by TrueProtocol");
            sender.sendMessage(ChatColor.YELLOW + "/dispensables help");
            sender.sendMessage(ChatColor.YELLOW + "/dispensables reload");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser");
            sender.sendMessage(ChatColor.YELLOW + "/dispenser");
        }
        else
        {
            if (args[0].equalsIgnoreCase("reload")) {
                plugin.reloadConfig();
                sender.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.BLUE + "Dispensables" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE + "Reload complete!");
            }
        } return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.add("help");
            arguments.add("reload");
            return arguments;
        }return null;
    }
}