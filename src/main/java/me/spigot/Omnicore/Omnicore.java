package me.spigot.Omnicore;

import me.spigot.Omnicore.API.Managers.Warn.WarnManager;
import me.spigot.Omnicore.API.Registres.Admintools.AdmintoolsRegistry;
import me.spigot.Omnicore.API.Registres.Status.StatusRegistry;
import me.spigot.Omnicore.API.Tasks.Status.StatusActionBarTask;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Listeners.AdminTools.AdmintoolsListener;
import me.spigot.Omnicore.Modules.Admintools.Data.AdminTools;
import me.spigot.Omnicore.Modules.Dice.DiceCommand;
import me.spigot.Omnicore.Modules.Fly.FlyCommand;
import me.spigot.Omnicore.Modules.Godmode.GodmodeCommand;
import me.spigot.Omnicore.Modules.Hat.HatCommand;
import me.spigot.Omnicore.Modules.Invsee.InvseeCommand;
import me.spigot.Omnicore.Modules.Jail.Helpers.JailData;
import me.spigot.Omnicore.Modules.Jail.JailCommand;
import me.spigot.Omnicore.Modules.Jail.Subcommands.Unjail.UnjailSubcommand;
import me.spigot.Omnicore.Modules.Admintools.AdmintoolsCommand;
import me.spigot.Omnicore.Modules.Ping.PingCommand;
import me.spigot.Omnicore.Modules.Report.Helper.ReportObject;
import me.spigot.Omnicore.Modules.Report.ReportCommand;
import me.spigot.Omnicore.Modules.Report.Subcommands.Clearreports.ReportsclearSubcommand;
import me.spigot.Omnicore.Modules.Report.Subcommands.Viewreports.ReportsviewSubcommand;
import me.spigot.Omnicore.Modules.Rocket.RocketCommand;
import me.spigot.Omnicore.Modules.Staffchat.StaffchatCommand;
import me.spigot.Omnicore.Listeners.Godmode.GodmodeListener;
import me.spigot.Omnicore.Modules.Heal.HealCommand;
import me.spigot.Omnicore.Modules.Help.HelpCommand;
import me.spigot.Omnicore.Listeners.Jail.JailListener;
import me.spigot.Omnicore.Modules.Vanish.VanishCommand;
import me.spigot.Omnicore.Modules.Warn.Subcommands.Unwarn.UnwarnSubcommand;
import me.spigot.Omnicore.Modules.Warn.Subcommands.Warnings.WarningsSubcommand;
import me.spigot.Omnicore.Modules.Warn.WarnCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import static me.spigot.Omnicore.Modules.Godmode.GodmodeCommand.isGod;
import static me.spigot.Omnicore.Modules.Godmode.GodmodeCommand.setGod;

public class Omnicore extends JavaPlugin implements Listener {


    //Lists//
    private final List<ReportObject> reports = new ArrayList<>();

    public List<ReportObject> getReports() {
        return reports;
    }

    @Override
    public void onEnable() {
        OmniLog.Info(getServer().getConsoleSender(), "Caricamento Omnicore in corso...");

        try {
            final String generale = "generale";
            final String funny = "funny";
            final String admin = "admin";

            WarnManager warnManager = new WarnManager(this);
            Map<UUID, JailData> jailedPlayers = new HashMap<>();

            //General Commands Registers//
            new PingCommand(this, generale, "ping", "Mostra il ping del giocatore.");
            new ReportCommand(this, generale, "report", "Invia un report agli staffer in game.");
            new HelpCommand(this, generale, "omnihelp", "Visualizza questa lista comandi.");
            //Funny Commands Registers//
            new HatCommand(this, funny, "hat", "Permette al giocatore di mettere un item come cappello.");
            new DiceCommand(this, funny, "dice", "Fa uscire un numero casuale tra 1 e 100.");
            new RocketCommand(this, funny, "rocket", "Fa diventare il giocatore una cavia della Nasa.");
            //Admin Commands Registers//
            new VanishCommand(this, admin, "vanish", "Abilita o disabilita la vanish.");
            new FlyCommand(this, admin, "fly", "Abilita o disabilita la fly.");
            new GodmodeCommand(this, admin, "god", "Abilita o disabilita la godmode.");
            new AdmintoolsCommand(this, admin, "admintools", "Apre la pagina del menu per gli staffer.");
            new HealCommand(this, admin, "heal", "Ripristina la salute e la fame.");
            new InvseeCommand(this, admin, "invsee", "Apre l'inventario di un giocatore.");
            new StaffchatCommand(this, admin,  "staffchat [/sc]", "Permette di scrivere in una chat per staff.");
            new JailCommand(this, admin, "jail", "Metti un player in jail", jailedPlayers);
            new UnjailSubcommand(this, admin, "unjail", "Fa uscire il player dallo stato di jail", jailedPlayers);
            new ReportsviewSubcommand(this, admin, "reportsview", "Fa vedere lo storico dei reports.");
            new ReportsclearSubcommand(this, admin, "reportsclear", "Cancella i reports dallo storico dei report.");
            new WarnCommand(this, admin, "warn", "Warna il player con la motivazione definita", warnManager);
            new UnwarnSubcommand(this, admin, "unwarn", "Toglie tutti gli warn al player", warnManager);
            new WarningsSubcommand(this, admin, "warnings", "Mostra i warn del player", warnManager);

            JailCommand jailCommand = new JailCommand(this, admin, "jail", "Metti un player in jail", jailedPlayers);
            //Listeners//
            new AdmintoolsListener(this);
            new GodmodeListener(this);
            new JailListener(this, jailCommand.getJailedPlayers());

            getServer().getPluginManager().registerEvents(this, this);
            //Config Save//
            saveDefaultConfig();

            //Menu Registry//
            AdmintoolsRegistry.register(new AdminTools("fly", "Abilita/disabilita la fly", Material.FEATHER,false, 0, 10));
            AdmintoolsRegistry.register(new AdminTools("god", "Attiva/disattiva la godmode", Material.TOTEM_OF_UNDYING, false, 0, 12));
            AdmintoolsRegistry.register(new AdminTools("vanish", "Attiva/disattiva la vanish", Material.SPLASH_POTION, false, 0, 14));
            AdmintoolsRegistry.register(new AdminTools("heal", "Cura il player", Material.APPLE, false, 0, 16));
            AdmintoolsRegistry.register(new AdminTools("jail", "Mette un player in prigione", Material.IRON_BARS, true, 0, 37));
            AdmintoolsRegistry.register(new AdminTools("warnings", "Mostra i warns di un player", Material.CHAIN, true, 0, 39));
            AdmintoolsRegistry.register(new AdminTools("invsee", "Mostra l'inventario di un player", Material.CHEST, true, 0, 41));
            AdmintoolsRegistry.register(new AdminTools("reportsview", "Mostra i reports di un player", Material.BLACK_BANNER, true, 0, 43));

            AdmintoolsRegistry.register(new AdminTools("ping", "Mostra il tuo ping", Material.REDSTONE, false, 1, 10));


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