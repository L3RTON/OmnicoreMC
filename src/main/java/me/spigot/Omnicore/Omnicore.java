package me.spigot.Omnicore;

import me.spigot.Omnicore.Commands.*;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;


public class Omnicore extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getCommand("vanish").setExecutor(new VanishCommand(this));

        System.out.println("""
                
                 ▄██████▄    ▄▄▄▄███▄▄▄▄   ███▄▄▄▄    ▄█   ▄████████  ▄██████▄     ▄████████    ▄████████
                ███    ███ ▄██▀▀▀███▀▀▀██▄ ███▀▀▀██▄ ███  ███    ███ ███    ███   ███    ███   ███    ███
                ███    ███ ███   ███   ███ ███   ███ ███▌ ███    █▀  ███    ███   ███    ███   ███    █▀ 
                ███    ███ ███   ███   ███ ███   ███ ███▌ ███        ███    ███  ▄███▄▄▄▄██▀  ▄███▄▄▄    
                ███    ███ ███   ███   ███ ███   ███ ███▌ ███        ███    ███ ▀▀███▀▀▀▀▀   ▀▀███▀▀▀    
                ███    ███ ███   ███   ███ ███   ███ ███  ███    █▄  ███    ███ ▀███████████   ███    █▄ 
                ███    ███ ███   ███   ███ ███   ███ ███  ███    ███ ███    ███   ███    ███   ███    ███
                 ▀██████▀   ▀█   ███   █▀   ▀█   █▀  █▀   ████████▀   ▀██████▀    ███    ███   ██████████
                                                                                  ███    ███             \n
                Omnicore è stato caricato con successo!
                """);

        getServer().getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void onPlayerJoined(PlayerJoinEvent ev){
        ev.setJoinMessage("Benvenuto nel server!");

    }
}
