package me.spigot.Omnicore.Commands.Invsee;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class InvseeCommand extends OmniCommand {

    public InvseeCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player player)){
            OmniLog.Error(sender, "Solo un giocatore può eseguire questo comando.");
            return true;
        }

        if (!player.hasPermission("omnicore.invsee")) {
            OmniLog.Error(sender, "Non hai i permessi per usare questo comando.");
            return true;
        }

        if (args.length != 1) {
            OmniLog.Error(sender, "Syntax Error! use /invsee <PlayerName>.");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline()) {
            OmniLog.Error(sender, "Giocatore non trovato o invalido.");
            return true;
        }

        if (target == player) {
            OmniLog.Error(sender, "Non puoi visualizzare l'inventario di te stesso.");
            return true;
        }

        player.openInventory(target.getInventory());
        OmniLog.Status(player, "Stai visualizzando l'inventario di " + target.getName() + ".");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
        return true;
    }
}
