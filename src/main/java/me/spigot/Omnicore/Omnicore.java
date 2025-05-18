package me.spigot.Omnicore;

import me.spigot.Omnicore.Commands.Vanish.VanishCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Omnicore extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getCommand("omnicore").setExecutor(this);
        getCommand("vanish").setExecutor(new VanishCommand(this));

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
}
