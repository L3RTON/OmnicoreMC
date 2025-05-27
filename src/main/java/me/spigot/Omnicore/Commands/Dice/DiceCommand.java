package me.spigot.Omnicore.Commands.Dice;

import me.spigot.Omnicore.API.Interfaces.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class DiceCommand extends OmniCommand {

    private Random random = new Random();

    public DiceCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length != 0) {
            OmniLog.Error(sender, "Syntax Error! use /dice.");
            return true;
        }

        int result = random.nextInt(100) + 1;
        OmniLog.Info(sender, "Hai tirato un dado... " + ChatColor.GREEN + result + "! 🎲");

        return true;
    }
}
