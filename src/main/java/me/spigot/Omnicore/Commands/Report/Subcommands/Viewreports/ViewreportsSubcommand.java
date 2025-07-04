package me.spigot.Omnicore.Commands.Report.Subcommands.Viewreports;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Commands.Report.Helper.ReportObject;
import me.spigot.Omnicore.Omnicore;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewreportsSubcommand extends OmniCommand {
    public ViewreportsSubcommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("omnicore.reports.view")) {
            OmniLog.Error(sender, "Non hai il permesso di vedere i report.");
            return true;
        }

        var reports = ((Omnicore) plugin).getReports();
        if (reports.isEmpty()) {
            OmniLog.Info(sender, ChatColor.GRAY + "Non ci sono report al momento.");
            return true;
        }

        sender.sendMessage(ChatColor.GOLD + "==== Report attivi ====");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (ReportObject r : reports) {
            String time = sdf.format(new Date(r.getTimestamp()));
            sender.sendMessage(ChatColor.YELLOW + "- " + ChatColor.RED + r.getReporterName()
                    + ChatColor.WHITE + " (" + time + "): " + ChatColor.GRAY + r.getMessage());
        }

        return true;
    }
}
