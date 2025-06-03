package me.spigot.Omnicore;

import me.spigot.Omnicore.API.Registres.Status.StatusRegistry;
import me.spigot.Omnicore.API.Tasks.Status.StatusActionBarTask;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Commands.Dice.DiceCommand;
import me.spigot.Omnicore.Commands.Fly.FlyCommand;
import me.spigot.Omnicore.Commands.Godmode.GodmodeCommand;
import me.spigot.Omnicore.Commands.Hat.HatCommand;
import me.spigot.Omnicore.Commands.Invsee.InvseeCommand;
import me.spigot.Omnicore.Commands.Ping.PingCommand;
import me.spigot.Omnicore.Commands.Staffchat.StaffchatCommand;
import me.spigot.Omnicore.Listeners.Godmode.GodmodeListener;
import me.spigot.Omnicore.Commands.Heal.HealCommand;
import me.spigot.Omnicore.Commands.Help.HelpCommand;
import me.spigot.Omnicore.Commands.Menu.MenuCommand;
import me.spigot.Omnicore.Listeners.Menu.MenuListener;
import me.spigot.Omnicore.Commands.Vanish.VanishCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import static me.spigot.Omnicore.Commands.Godmode.GodmodeCommand.isGod;
import static me.spigot.Omnicore.Commands.Godmode.GodmodeCommand.setGod;

public class Omnicore extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        OmniLog.Info(getServer().getConsoleSender(), "Caricamento Omnicore in corso...");

        try {
            //Executors//
            getCommand("vanish").setExecutor(new VanishCommand(this, "vanish", "Abilita o disabilita la vanish."));
            getCommand("fly").setExecutor(new FlyCommand(this, "fly", "Abilita o disabilita la fly."));
            getCommand("god").setExecutor(new GodmodeCommand(this, "god", "Abilita o disabilita la godmode."));
            getCommand("menu").setExecutor(new MenuCommand(this, "menu", "Apre la pagina del menu."));
            getCommand("heal").setExecutor(new HealCommand(this, "heal", "Ripristina la salute e la fame."));
            getCommand("ping").setExecutor(new PingCommand(this, "ping", "Mostra il ping del giocatore."));
            getCommand("invsee").setExecutor(new InvseeCommand(this, "invsee", "Apre l'inventario di un giocatore."));
            getCommand("hat").setExecutor(new HatCommand(this, "hat", "Permette al giocatore di mettere un item come cappello."));
            getCommand("staffchat").setExecutor(new StaffchatCommand(this, "staffchat [/sc]", "Permette di scrivere in una chat per staff."));
            getCommand("dice").setExecutor(new DiceCommand(this, "dice", "Fa uscire un numero casuale tra 1 e 100."));
            getCommand("omnihelp").setExecutor(new HelpCommand(this, "omnihelp", "Visualizza questa lista comandi."));

            //Listeners//
            getServer().getPluginManager().registerEvents(new MenuListener(), this);
            getServer().getPluginManager().registerEvents(new GodmodeListener(), this);
            getServer().getPluginManager().registerEvents(this, this);

            //Config Save//
            saveDefaultConfig();

            //StatusBar Init//
            new StatusActionBarTask().runTaskTimer(this, 0L, 20L);
        } catch (Exception e) {
            OmniLog.Exception("Omnicore ha riscontrato un errore e non è riuscito ad avviarsi!", e);
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

        if (getConfig().getBoolean("debugMode", true)){
            OmniLog.Debug(getServer().getConsoleSender(), "Debug Mode is enabled!");
            OmniLog.Debug(getServer().getConsoleSender(), "Plugin version: " + getDescription().getVersion());
        }

        if (getConfig().getBoolean("maintenanceMode", true)){
            OmniLog.Info(getServer().getConsoleSender(), ChatColor.DARK_RED + "! ATTENZIONE ! " + ChatColor.YELLOW + "La maintenance mode è attiva!");
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent ev){
        Player player = ev.getPlayer();

        StatusRegistry.clear(player);

        if (getConfig().getBoolean("debugMode", true)){
            if (player.isInvisible()){
                OmniLog.Debug(getServer().getConsoleSender(), "Vanish set to false for " + player.getName());
            }
            if (isGod(player)){
                OmniLog.Debug(getServer().getConsoleSender(), "Godmode set to false for " + player.getName());
            }

            OmniLog.Debug(ev.getPlayer(), "StatusBar resettata.");
        }

        if (player.isInvisible()){
            player.setInvisible(false);
        }
        if (isGod(player)){
            setGod(player, false);
        }
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev){
        if (getConfig().getBoolean("maintenanceMode", true))
            ev.getPlayer().kickPlayer(ChatColor.YELLOW + "Il server è in manutenzione.");

        if (getConfig().getBoolean("debugMode", true))
            OmniLog.Debug(getServer().getConsoleSender(), "Player " + ev.getPlayer().getName() + " has been kicked because of maintenance mode");
    }
}