package me.trueprotocol.dispensables;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.trueprotocol.dispensables.commands.*;
import me.trueprotocol.dispensables.gui.ClickInventory;
import me.trueprotocol.dispensables.gui.CloseInventory;
import me.trueprotocol.dispensables.listeners.CancelDispense;
import me.trueprotocol.dispensables.listeners.CancelDrop;
import me.trueprotocol.dispensables.listeners.CancelHop;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Dispensables extends JavaPlugin {
    private YamlDocument config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        // bStats
        int pluginId = 15323; // <-- Id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);
        // BoostedYAML: Update config.yml
        try {
            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version")).build());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // Main
        this.getServer().getConsoleSender().sendMessage("Dispensables is now enabled");
        this.getServer().getPluginManager().registerEvents(new CancelDispense(this), this);
        this.getServer().getPluginManager().registerEvents(new CancelDrop(this), this);
        this.getServer().getPluginManager().registerEvents(new CancelHop(this), this);
        this.getServer().getPluginManager().registerEvents(new ClickInventory(this), this);
        this.getServer().getPluginManager().registerEvents(new CloseInventory(this), this);
        this.getServer().getPluginCommand("dispensables").setExecutor(new DispensablesCommand(this));
        this.getServer().getPluginCommand("dispensables").setTabCompleter(new DispensablesCommand(this));
        this.getServer().getPluginCommand("dispenser").setExecutor(new DispenserCommand(this));
        this.getServer().getPluginCommand("dispenser").setTabCompleter(new DispenserCommand(this));
        this.getServer().getPluginCommand("dropper").setExecutor(new DropperCommand(this));
        this.getServer().getPluginCommand("dropper").setTabCompleter(new DropperCommand(this));
        this.getServer().getPluginCommand("hopper").setExecutor(new HopperCommand(this));
        this.getServer().getPluginCommand("hopper").setTabCompleter(new HopperCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getServer().getConsoleSender().sendMessage("Dispensables is now disabled");
    }
}
