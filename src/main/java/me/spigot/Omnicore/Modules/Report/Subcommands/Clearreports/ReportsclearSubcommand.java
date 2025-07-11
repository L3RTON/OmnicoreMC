package me.spigot.Omnicore.Modules.Report.Subcommands.Clearreports;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Omnicore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ReportsclearSubcommand extends OmniCommand {

    public ReportsclearSubcommand(JavaPlugin plugin, String category, String commandName, String description) {
        super(plugin, category, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!sender.hasPermission("omnicore.reports.clear")){
            OmniLog.Error(sender, "Non hai il permesso di usare questo comando");
            return true;
        }

        Omnicore main = (Omnicore) plugin;
        main.getReports().clear();

        if (sender.equals(Bukkit.getConsoleSender())) {
            for (Player online : Bukkit.getOnlinePlayers()) {
                OmniLog.Info(online, "Il proprietario del server ha cancellato lo storico dei reports dalla console.");
            }
        } else {
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.equals(sender)) {
                    OmniLog.Info(online, "Il player " + sender.getName() + " ha cancellato lo storico dei reports.");
                }
            }
        }

        OmniLog.Status(sender, "Lo storico dei reports è stato resettato con successo.");

        if (plugin.getConfig().getBoolean("debugMode", false)) {
            if (sender == Bukkit.getConsoleSender()){
                return true;
            }

            OmniLog.Debug(Bukkit.getConsoleSender(), "Il player " + sender.getName() + " ha cancellato tutti i report.");
        }
        return true;
    }
}
