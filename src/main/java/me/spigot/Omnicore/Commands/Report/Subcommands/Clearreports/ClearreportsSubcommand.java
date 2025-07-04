package me.spigot.Omnicore.Commands.Report.Subcommands.Clearreports;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Omnicore;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearreportsSubcommand extends OmniCommand {
    public ClearreportsSubcommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!sender.hasPermission("omnicore.reports.clear")){
            OmniLog.Error(sender, "Non hai il permesso di usare questo comando");
            return true;
        }

        Omnicore main = (Omnicore) plugin;
        main.getReports().clear();

        OmniLog.Status(sender, "Lo storico dei reports è stato resettato con successo.");

        return true;
    }
}
