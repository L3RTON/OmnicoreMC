package me.spigot.Omnicore.Modules.Report;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Modules.Report.Helper.ReportObject;
import me.spigot.Omnicore.Omnicore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReportCommand extends OmniCommand {

    public ReportCommand(JavaPlugin plugin, String category, String commandName, String description) {
        super(plugin, category, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player player)) {
            OmniLog.Error(sender, "Solo i player possono usare questo comando.");
            return true;
        }

        if (args.length != 1) {
            OmniLog.Error(sender, "Syntax Error! use /report <Message>");
            return true;
        }

        String reason = String.join(" ", args);
        ReportObject report = new ReportObject(player.getName(), reason);
        ((Omnicore) plugin).getReports().add(report);

        OmniLog.Status(player, "Il tuo report è stato inviato con successo!");

        Bukkit.getOnlinePlayers().stream()
                .filter(p -> p.hasPermission("omnicore.reports.receive"))
                .forEach(staff -> staff.sendMessage(
                        ChatColor.RED + "[REPORT] " + ChatColor.YELLOW + player.getName() +
                                ChatColor.WHITE + ": " + ChatColor.GRAY + reason
                ));

        return true;
    }
}
