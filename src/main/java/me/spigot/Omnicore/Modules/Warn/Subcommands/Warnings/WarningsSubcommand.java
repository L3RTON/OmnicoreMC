package me.spigot.Omnicore.Modules.Warn.Subcommands.Warnings;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Managers.Warn.WarnManager;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Modules.Warn.Helpers.WarnObject;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

public class WarningsSubcommand extends OmniCommand {
    private final WarnManager warnManager;

    public WarningsSubcommand(JavaPlugin plugin, String category, String commandName, String description, WarnManager manager) {
        super(plugin, category, commandName, description);
        this.warnManager = manager;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("omnicore.warn")) {
            OmniLog.Error(sender, "Non hai il permesso di usare questo comando.");
            return true;
        }

        if (args.length != 1) {
            OmniLog.Error(sender, "Syntax Error! use /warnings <Player>");
            return true;
        }

        OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
        UUID uuid = target.getUniqueId();
        List<WarnObject> warns = warnManager.getWarns(uuid);

        if (warns.isEmpty()) {
            OmniLog.Error(sender, "Il player " + ChatColor.DARK_PURPLE + target.getName() + ChatColor.DARK_RED + " non ha nessun warn.");
            return true;
        }

        OmniLog.Status(sender, ChatColor.YELLOW + "Warns per " + ChatColor.RED + target.getName() + ":");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        int index = 1;
        for (WarnObject warn : warns) {
            String time = format.format(warn.getTimestamp());
            sender.sendMessage(ChatColor.GRAY + "" + index++ + ") " + ChatColor.GOLD + warn.getReason() +
                    ChatColor.GRAY + " | " + ChatColor.AQUA + warn.getStaff() +
                    ChatColor.GRAY + " | " + ChatColor.LIGHT_PURPLE + time);
        }

        return true;
    }
}

