package me.spigot.Omnicore.Modules.Jail.Subcommands.Unjail;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Modules.Jail.Helpers.JailData;
import me.spigot.Omnicore.Modules.Jail.JailCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public class UnjailSubcommand extends OmniCommand {
    private final Map<UUID, JailData> jailedPlayers;

    public UnjailSubcommand(JavaPlugin plugin,String category, String commandName, String description, Map<UUID, JailData> jailedPlayers) {
        super(plugin, category, commandName, description);
        this.jailedPlayers = jailedPlayers;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player player)){
            OmniLog.Error(sender, "Solo un giocatore può eseguire questo comando.");
            return true;
        }

        if (args.length != 1){
            OmniLog.Error(sender, "Syntax Error! use /unjail <PlayerName>");
            return true;
        }

        if (!player.hasPermission("omnicore.jail")){
            OmniLog.Error(sender, "Non hai il permesso di usare questo commando.");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null){
            OmniLog.Error(sender, "Giocatore non trovato o invalido.");
            return true;
        }

        JailData data = jailedPlayers.get(target.getUniqueId());
        if (data == null){
            OmniLog.Error(sender, "Il giocatore non si trova in stato di jail.");
            return true;
        }

        if (target == sender){
            OmniLog.Info(sender, "Se potessi scappare di prigione sarebbe un degrado la fuori non trovi?");
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, -4);
            return true;
        }
        Bukkit.getScheduler().cancelTask(data.taskId);

        target.setWalkSpeed(0.2f);
        target.setFlySpeed(0.1f);

        JailCommand.destroyJail(data.jailCenter);

        jailedPlayers.remove(target.getUniqueId());

        OmniLog.Status(sender, "Il player " + ChatColor.YELLOW + target.getName() + ChatColor.GREEN + " è stato assolto dal giudice a quanto pare.");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);

        OmniLog.Status(target, "Sei stato assolto dal giudice " + ChatColor.YELLOW + sender.getName() + ChatColor.GREEN + ". Come ci si sente ora?");
        target.playSound(target.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 1);

        return true;
    }
}
