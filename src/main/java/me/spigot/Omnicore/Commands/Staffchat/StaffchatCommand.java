package me.spigot.Omnicore.Commands.Staffchat;

import me.spigot.Omnicore.API.Interfaces.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StaffchatCommand extends OmniCommand {

    public StaffchatCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!sender.hasPermission("omnicore.staffchat")) {
            OmniLog.Error(sender, "Non hai i permessi per usare questo comando.");
            return true;
        }

        if (args.length == 0) {
            OmniLog.Error(sender, "Syntax Error! use /staffchat <Message> or /sc <Message>.");
            return true;
        }

        String rawMessage = String.join(" ", args);
        String formattedMessage = ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + "StaffChat" + ChatColor.GRAY + "] "
                + ChatColor.YELLOW + sender.getName() + ChatColor.WHITE + " ➡ " + ChatColor.GRAY  + rawMessage;

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.hasPermission("omnicore.staffchat")) {
                player.sendMessage(formattedMessage);
            }
        }

        Bukkit.getConsoleSender().sendMessage(formattedMessage);

        return true;
    }
}