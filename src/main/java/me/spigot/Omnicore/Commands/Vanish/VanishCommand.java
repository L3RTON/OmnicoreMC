package me.spigot.Omnicore.Commands.Vanish;

import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VanishCommand implements CommandExecutor {

    private JavaPlugin plugin;

    public VanishCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("omnicore.vanish")) {

            sender.sendMessage(ChatColor.DARK_RED + "Non hai i permessi necessari per eseguire questo comando.");
            return false;
        }

        if (args.length != 1) {
            OmniLog.Error(sender, "Syntax Error! use /vanish <PlayerName>.");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            OmniLog.Error(sender, "Giocatore non trovato o invalido.");
            return true;
        }

        boolean isVanished = target.isInvisible();
        target.setInvisible(!isVanished);

        if (!isVanished){
            if (target != sender){
                OmniLog.Info(sender, "Il Giocatore " + ChatColor.GRAY + target.getName() + ChatColor.GREEN + " è ora invisibile.");

                for (Player p : Bukkit.getOnlinePlayers())
                    p.hidePlayer(plugin, target);
                return true;
            }
            sender.sendMessage(ChatColor.GREEN + "Ora sei invisibile.");

            for (Player p : Bukkit.getOnlinePlayers())
                p.hidePlayer(plugin, target);
        }
        else{
            if (target != sender){
                OmniLog.Info(sender, "Il Giocatore " + ChatColor.GRAY + target.getName() + ChatColor.RED + " è ora visibile.");

                for (Player p : Bukkit.getOnlinePlayers())
                    p.hidePlayer(plugin, target);
                return true;
            }
            sender.sendMessage(ChatColor.RED + "Sei tornato visibile!");

            for (Player p : Bukkit.getOnlinePlayers())
                p.showPlayer(plugin, target);
        }

        return true;
    }
}
