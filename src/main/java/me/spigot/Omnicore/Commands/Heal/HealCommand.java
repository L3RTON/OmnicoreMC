package me.spigot.Omnicore.Commands.Heal;

import me.spigot.Omnicore.API.Interfaces.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class HealCommand extends OmniCommand {
    public HealCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            OmniLog.Error(sender, "Questo comando può essere usato solo da un giocatore.");
            return true;
        }

        if (!player.hasPermission("omnicore.heal")) {
            OmniLog.Error(sender, "Non hai i permessi per usare questo comando.");
            return true;
        }

        if (player.getGameMode() != GameMode.SURVIVAL) {
            OmniLog.Error(sender, "Puoi usare questo comando solo in modalità survival.");
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
            OmniLog.Error(sender, "Syntax Error! use /heal [PlayerName].");
            return true;
        }

        if (target != sender){
            target.setHealth(20);
            target.setFoodLevel(20);
            target.setSaturation(20f);
            OmniLog.Status(sender, "Hai ripristinato la salute e la fame di " + target.getName() + ".");
        }
        else{
            target.setHealth(20);
            target.setFoodLevel(20);
            target.setSaturation(20f);
            OmniLog.Status(sender, "Hai ripristinato la salute e la fame.");
        }


        return true;
    }
}
