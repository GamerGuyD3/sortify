package me.trueprotocol.sortify.listeners;

import me.trueprotocol.sortify.Sortify;
import me.trueprotocol.sortify.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    Sortify plugin;
    public PlayerJoin(Sortify plugin) { this.plugin = plugin; }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new UpdateChecker(plugin, 61856).getVersion(version -> {
            if (!plugin.getDescription().getVersion().equals(version) && p.hasPermission("dispensables.admin")) {
                    p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Sortify" + ChatColor.DARK_GRAY + "] " + ChatColor.YELLOW +"v" + version + " is now available! Download now:\n" + ChatColor.GRAY + "https://www.spigotmc.org/resources/dispensables.61856/");
            }
        });
    }
}
