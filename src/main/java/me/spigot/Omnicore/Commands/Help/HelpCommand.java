package me.spigot.Omnicore.Commands.Help;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Registres.OmniHelp.OmniHelpRegistry;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class HelpCommand extends OmniCommand {

    public HelpCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        OmniLog.Info(sender, "Lista dei comandi registrati:\n");

        for (Map.Entry<String, String> entry : OmniHelpRegistry.getRegisteredCommands().entrySet()) {
            sender.sendMessage(ChatColor.GRAY + entry.getKey() + ChatColor.WHITE + " - " + entry.getValue());
        }

        return true;
    }
}
