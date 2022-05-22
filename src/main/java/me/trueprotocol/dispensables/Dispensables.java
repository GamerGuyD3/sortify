package me.trueprotocol.dispensables;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.dvs.versioning.BasicVersioning;
import dev.dejvokep.boostedyaml.settings.dumper.DumperSettings;
import dev.dejvokep.boostedyaml.settings.general.GeneralSettings;
import dev.dejvokep.boostedyaml.settings.loader.LoaderSettings;
import dev.dejvokep.boostedyaml.settings.updater.UpdaterSettings;
import me.trueprotocol.dispensables.commands.*;
import me.trueprotocol.dispensables.listeners.CancelDispense;
import me.trueprotocol.dispensables.listeners.CancelDrop;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class Dispensables extends JavaPlugin {

    private YamlDocument config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        // Create and update config.yml
        try {
            config = YamlDocument.create(new File(getDataFolder(), "config.yml"), getResource("config.yml"),
                    GeneralSettings.DEFAULT, LoaderSettings.builder().setAutoUpdate(true).build(), DumperSettings.DEFAULT, UpdaterSettings.builder().setVersioning(new BasicVersioning("file-version")).build());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.getServer().getConsoleSender().sendMessage("Dispensables is now enabled");
        this.getServer().getPluginManager().registerEvents(new CancelDispense(), this);
        this.getServer().getPluginManager().registerEvents(new CancelDrop(), this);
        this.getServer().getPluginCommand("dispensables").setExecutor(new DispensablesCommand());
        this.getServer().getPluginCommand("dispensables").setTabCompleter(new DispensablesCommand());
        this.getServer().getPluginCommand("dispenser").setExecutor(new DispenserCommand());
        this.getServer().getPluginCommand("dispenser").setTabCompleter(new DispenserCommand());
        this.getServer().getPluginCommand("dropper").setExecutor(new DropperCommand());
        this.getServer().getPluginCommand("dropper").setTabCompleter(new DropperCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getServer().getConsoleSender().sendMessage("Dispensables is now disabled");
    }
}