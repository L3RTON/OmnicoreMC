package me.spigot.Omnicore;

import me.spigot.Omnicore.API.Registres.Status.StatusRegistry;
import me.spigot.Omnicore.API.Tasks.Status.StatusActionBarTask;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Commands.Dice.DiceCommand;
import me.spigot.Omnicore.Commands.Fly.FlyCommand;
import me.spigot.Omnicore.Commands.Godmode.GodmodeCommand;
import me.spigot.Omnicore.Commands.Hat.HatCommand;
import me.spigot.Omnicore.Commands.Invsee.InvseeCommand;
import me.spigot.Omnicore.Commands.Jail.Helpers.JailData;
import me.spigot.Omnicore.Commands.Jail.JailCommand;
import me.spigot.Omnicore.Commands.Jail.Subcommands.Unjail.UnjailSubcommand;
import me.spigot.Omnicore.Commands.Ping.PingCommand;
import me.spigot.Omnicore.Commands.Report.Helper.ReportObject;
import me.spigot.Omnicore.Commands.Report.ReportCommand;
import me.spigot.Omnicore.Commands.Report.Subcommands.Clearreports.ClearreportsSubcommand;
import me.spigot.Omnicore.Commands.Report.Subcommands.Viewreports.ViewreportsSubcommand;
import me.spigot.Omnicore.Commands.Staffchat.StaffchatCommand;
import me.spigot.Omnicore.Listeners.Godmode.GodmodeListener;
import me.spigot.Omnicore.Commands.Heal.HealCommand;
import me.spigot.Omnicore.Commands.Help.HelpCommand;
import me.spigot.Omnicore.Commands.Menu.MenuCommand;
import me.spigot.Omnicore.Listeners.Jail.JailListener;
import me.spigot.Omnicore.Listeners.Menu.MenuListener;
import me.spigot.Omnicore.Commands.Vanish.VanishCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import static me.spigot.Omnicore.Commands.Godmode.GodmodeCommand.isGod;
import static me.spigot.Omnicore.Commands.Godmode.GodmodeCommand.setGod;

public class Omnicore extends JavaPlugin implements Listener {
    //Report List//
    private final List<ReportObject> reports = new ArrayList<>();

    public List<ReportObject> getReports() {
        return reports;
    }

    @Override
    public void onEnable() {
        OmniLog.Info(getServer().getConsoleSender(), "Caricamento Omnicore in corso...");

        try {
            Map<UUID, JailData> jailedPlayers = new HashMap<>();
            //Command Registers//
            new VanishCommand(this, "vanish", "Abilita o disabilita la vanish.");
            new FlyCommand(this, "fly", "Abilita o disabilita la fly.");
            new GodmodeCommand(this, "god", "Abilita o disabilita la godmode.");
            new MenuCommand(this, "menu", "Apre la pagina del menu.");
            new HealCommand(this, "heal", "Ripristina la salute e la fame.");
            new PingCommand(this, "ping", "Mostra il ping del giocatore.");
            new InvseeCommand(this, "invsee", "Apre l'inventario di un giocatore.");
            new HatCommand(this, "hat", "Permette al giocatore di mettere un item come cappello.");
            new StaffchatCommand(this, "staffchat [/sc]", "Permette di scrivere in una chat per staff.");
            new DiceCommand(this, "dice", "Fa uscire un numero casuale tra 1 e 100.");
            new JailCommand(this, "jail", "Metti un player in jail", jailedPlayers);
            new UnjailSubcommand(this, "unjail", "Fa uscire il player dallo stato di jail", jailedPlayers);
            new ReportCommand(this, "report", "Invia un report agli staffer in game.");
            new ViewreportsSubcommand(this, "viewreports", "Fa vedere lo storico dei reports.");
            new ClearreportsSubcommand(this, "clearreports", "Cancella i reports dallo storico dei report.");

            new HelpCommand(this, "omnihelp", "Visualizza questa lista comandi.");


            JailCommand jailCommand = new JailCommand(this, "jail", "Metti un player in jail", jailedPlayers);
            //Listeners//
            new MenuListener(this);
            new GodmodeListener(this);
            new JailListener(this, jailCommand.getJailedPlayers());

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
        if (getConfig().getBoolean("maintenanceMode", true)){
            ev.getPlayer().kickPlayer(ChatColor.YELLOW + "Il server è in manutenzione.");

            if (getConfig().getBoolean("debugMode", true))
                OmniLog.Debug(getServer().getConsoleSender(), "Player " + ev.getPlayer().getName() + " has been kicked because of maintenance mode");
        }

    }
}