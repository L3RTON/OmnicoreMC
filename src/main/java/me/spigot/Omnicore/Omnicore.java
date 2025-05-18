package me.spigot.Omnicore;

import me.spigot.Omnicore.Commands.Tpa.TpaCommand;
import me.spigot.Omnicore.Commands.Vanish.VanishCommand;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.EventHandler;


public class Omnicore extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {

        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("tpa").setExecutor(new TpaCommand(this));
        getCommand("tpaccept").setExecutor(this);
        getCommand("tpdeny").setExecutor(this);

        System.out.println("""
                 
                 ██████╗ ███╗   ███╗███╗   ██╗██╗ ██████╗ ██████╗ ██████╗ ███████╗
                ██╔═══██╗████╗ ████║████╗  ██║██║██╔════╝██╔═══██╗██╔══██╗██╔════╝
                ██║   ██║██╔████╔██║██╔██╗ ██║██║██║     ██║   ██║██████╔╝█████╗ 
                ██║   ██║██║╚██╔╝██║██║╚██╗██║██║██║     ██║   ██║██╔══██╗██╔══╝ 
                ╚██████╔╝██║ ╚═╝ ██║██║ ╚████║██║╚██████╗╚██████╔╝██║  ██║███████╗
                 ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═══╝╚═╝ ╚═════╝ ╚═════╝ ╚═╝  ╚═╝╚══════╝
                Omnicore è stato caricato con successo!
                """);

        getServer().getPluginManager().registerEvents(this, this);
    }


    @EventHandler
    public void onPlayerJoined(PlayerJoinEvent ev){
        ev.setJoinMessage("");

    }
}
