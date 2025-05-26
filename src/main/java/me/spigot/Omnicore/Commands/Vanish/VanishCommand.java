package me.spigot.Omnicore.Commands.Vanish;

import me.spigot.Omnicore.API.Interfaces.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Registres.Status.StatusRegistry;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VanishCommand extends OmniCommand {

    public VanishCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description); this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("omnicore.vanish")) {
            OmniLog.Error(sender, "Non hai i permessi per usare questo comando.");
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
            OmniLog.Error(sender, "Syntax Error! use /vanish [PlayerName].");
            return true;
        }

        boolean isVanished = target.isInvisible();
        target.setInvisible(!isVanished);
        if (!isVanished){
            if (target != sender){
                OmniLog.Status(sender, "Il Giocatore " + ChatColor.GRAY + target.getName() + ChatColor.GREEN + " è ora invisibile.");

                for (Player p : Bukkit.getOnlinePlayers())
                    p.hidePlayer(plugin, target);
                return true;
            }
            OmniLog.Status(sender, "Hai attivato la vanish.");

            for (Player p : Bukkit.getOnlinePlayers())
                p.hidePlayer(plugin, target);
        }
        else{
            if (target != sender){
                OmniLog.Status(sender, "Il Giocatore " + ChatColor.GRAY + target.getName() + ChatColor.RED + " è ora visibile.");

                for (Player p : Bukkit.getOnlinePlayers())
                    p.hidePlayer(plugin, target);
                return true;
            }
            OmniLog.Status(sender, "Hai disattivato la vanish.");

            for (Player p : Bukkit.getOnlinePlayers())
                p.showPlayer(plugin, target);
        }

        StatusRegistry.setStatus(target, "Vanish", !isVanished);
        return true;
    }
}
