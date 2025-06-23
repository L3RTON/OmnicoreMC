package me.spigot.Omnicore.Commands.Fly;

import me.spigot.Omnicore.API.Interfaces.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Registres.Status.StatusRegistry;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyCommand extends OmniCommand {

    public FlyCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        Player player = (Player) sender;
        if (!player.hasPermission("omnicore.fly")) {
            OmniLog.Error(sender, "Non hai i permessi per usare questo comando.");
            return true;
        }

        if (player.getGameMode() != GameMode.SURVIVAL) {
            OmniLog.Error(sender, "Puoi usare /fly solo in modalità survival.");
            return true;
        }

        Player target;
        if (args.length == 0) {
            target = player;
        } else if (args.length == 1) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                OmniLog.Error(sender, "Giocatore non trovato o invalido.");
                return true;
            }
        }else{
            OmniLog.Error(sender, "Syntax Error! use /fly [PlayerName].");
            return true;
        }

        boolean enabled = !player.getAllowFlight();
        player.setAllowFlight(enabled);
        player.setFlying(enabled);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
        if(enabled){
            if (target != sender){
                OmniLog.Status(sender, "Il Giocatore " + ChatColor.GRAY + target.getName() + ChatColor.GREEN + " non ha più la fly attiva");

                return true;
            }
            OmniLog.Status(sender, "Hai attivato la fly.");
        }
        else{
            if (target != sender){
                OmniLog.Status(sender, "Il Giocatore " + ChatColor.GRAY + target.getName() + ChatColor.GREEN + " ha ora la fly");

                return true;
            }
            OmniLog.Status(sender, "Hai disattivato la fly.");
        }

        StatusRegistry.setStatus(target, "Fly", enabled);
        return true;
    }
}
