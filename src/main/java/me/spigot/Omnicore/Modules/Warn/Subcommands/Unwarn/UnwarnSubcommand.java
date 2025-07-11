package me.spigot.Omnicore.Modules.Warn.Subcommands.Unwarn;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Managers.Warn.WarnManager;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UnwarnSubcommand extends OmniCommand {

    private final WarnManager warnManager;

    public UnwarnSubcommand(JavaPlugin plugin, String category, String commandName, String description, WarnManager manager) {
        super(plugin, category, commandName, description);
        this.warnManager = manager;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("omnicore.unwarn")) {
            OmniLog.Error(sender, "Non hai il permesso di usare questo comando.");
            return true;
        }

        if (args.length != 1) {
            OmniLog.Error(sender, "Syntax Error! use /unwarn <PlayerName>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            OmniLog.Error(sender, "Giocatore non trovato o invalido.");
            return true;
        }

        warnManager.clearWarns(target.getUniqueId());

        OmniLog.Status(sender, "Hai rimosso tutti i warn da " + ChatColor.DARK_PURPLE + target.getName());
        OmniLog.Info(target, "Tutti i tuoi warn sono stati rimossi da " + ChatColor.DARK_PURPLE + sender.getName());

        return true;
    }
    }
