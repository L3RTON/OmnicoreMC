package me.spigot.Omnicore.Modules.Warn;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Managers.Warn.WarnManager;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Modules.Warn.Helpers.WarnObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class WarnCommand extends OmniCommand {

    private final WarnManager warnManager;

    public WarnCommand(JavaPlugin plugin, String category, String commandName, String description, WarnManager manager) {
        super(plugin, category, commandName, description);
        this.warnManager = manager;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("omnicore.warn")) {
            OmniLog.Error(sender, "Non hai il permesso di usare questo comando.");
            return true;
        }

        if (args.length < 2) {
            OmniLog.Error(sender, "Syntax Error! use /warn <PlayerName> <Motivation>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            OmniLog.Error(sender, "Giocatore non trovato o invalido.");
            return true;
        }

        UUID uuid = target.getUniqueId();
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));

        WarnObject warn = new WarnObject(reason, System.currentTimeMillis(), sender.getName());
        warnManager.addWarn(uuid, warn);

        OmniLog.Status(sender, "Hai warnato " + target.getName() + " per: " + ChatColor.YELLOW + reason);
        OmniLog.Info(target, ChatColor.RED + "Sei stato warnato da " + ChatColor.DARK_PURPLE + sender.getName() + ChatColor.RED + " per: " + ChatColor.YELLOW + reason);

        return true;
    }
}
