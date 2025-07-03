package me.spigot.Omnicore.API.Extensions.OmniListener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class OmniListener implements Listener {
    protected JavaPlugin plugin;

    public OmniListener(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
}