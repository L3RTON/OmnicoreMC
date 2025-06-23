package me.spigot.Omnicore.Commands.Ping;

import me.spigot.Omnicore.API.Interfaces.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PingCommand extends OmniCommand {

    public PingCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)){
            OmniLog.Error(sender, "Questo comando può essere eseguito solo da un player.");
            return true;
        }
        
        Player target;
        if (args.length == 0) {
            target = (Player) sender;
        } else if (args.length == 1) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                OmniLog.Error(sender, "Giocatore non trovato o invalido.");
                return true;
            }
        }else{
            OmniLog.Error(sender, "Syntax Error! use /ping [PlayerName].");
            return true;
        }

        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
        if (target != sender){
            OmniLog.Info(sender, "Pong! Il player ha " + target.getPing() + " ms");
            return true;
        }

        if (player.getPing() <= 80){
            OmniLog.Info(sender, "Pong! " + ChatColor.GREEN + player.getPing() + "ms");
            return true;
        }
        else if (player.getPing() <= 260){
            OmniLog.Info(sender, "Pong! " + ChatColor.GOLD + player.getPing() + "ms");
            return true;
        }
        else{
            OmniLog.Info(sender, "Pong! " + ChatColor.DARK_RED + player.getPing() + "ms");
        }

        return true;
    }
}
