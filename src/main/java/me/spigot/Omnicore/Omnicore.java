package me.spigot.Omnicore;

import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Commands.Help.HelpCommand;
import me.spigot.Omnicore.Commands.Vanish.VanishCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Omnicore extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        OmniLog.Info(getServer().getConsoleSender(), "Caricamento Omnicore in corso...");

        try {
            //getCommand("omnicore").setExecutor(this);
            getCommand("vanish").setExecutor(new VanishCommand(this, "vanish", "Rende il player invisibile."));
            getCommand("omnihelp").setExecutor(new HelpCommand(this, "omnihelp", "Visualizza questa lista comandi."));

            saveDefaultConfig();
        } catch (Exception e) {
            OmniLog.LogException("Omnicore ha riscontrato un errore e non è riuscito ad avviarsi!", e);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        OmniLog.Info(getServer().getConsoleSender(), """
                
                 ██████  ███    ███ ███    ██ ██  ██████  ██████  ██████  ███████
                ██    ██ ████  ████ ████   ██ ██ ██      ██    ██ ██   ██ ██     
                ██    ██ ██ ████ ██ ██ ██  ██ ██ ██      ██    ██ ██████  █████  
                ██    ██ ██  ██  ██ ██  ██ ██ ██ ██      ██    ██ ██   ██ ██     
                 ██████  ██      ██ ██   ████ ██  ██████  ██████  ██   ██ ███████
                 
                            Omnicore è stato caricato con successo!
                """);

        getServer().getPluginManager().registerEvents(this, this);

        FileConfiguration config = getConfig();
        String debugMode = config.getString("debugMode");
        if (debugMode.contains("true")){
            OmniLog.Debug(getServer().getConsoleSender(), "Debug Mode is enabled!");
            OmniLog.Debug(getServer().getConsoleSender(), "Plugin version: " + getDescription().getVersion());
        }
    }
}