package me.spigot.Omnicore.Commands.Godmode;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Registres.Status.StatusRegistry;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GodmodeCommand extends OmniCommand {
    private static final Set<UUID> godPlayers = new HashSet<>();

    public GodmodeCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    public static boolean isGod(Player player) {
        return godPlayers.contains(player.getUniqueId());
    }

    public static void setGod(Player player, boolean enabled) {
        if (enabled) {
            godPlayers.add(player.getUniqueId());
        } else {
            godPlayers.remove(player.getUniqueId());
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)) {
            OmniLog.Error(sender, "Questo comando può essere eseguito solo da un giocatore.");
            return true;
        }

        if (!player.hasPermission("omnicore.god")) {
            OmniLog.Error(sender, "Non hai i permessi per usare questo comando.");
            return true;
        }

        Player target;

        if (args.length == 0) {
            target = player;
        } else if (args.length == 1) {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null || !target.isOnline()) {
                OmniLog.Error(sender, "Giocatore non trovato.");
                return true;
            }
        } else {
            OmniLog.Error(sender, "Syntax Error! use /godmode [PlayerName].");
            return true;
        }

        if (target.getGameMode() != GameMode.SURVIVAL) {
            OmniLog.Error(sender, "Puoi usare questo comando solo in modalità survival.");
            return true;
        }

        boolean newState = !isGod(target);
        setGod(target, newState);
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);

        if (target.equals(player)) {
            OmniLog.Status(sender, (newState ? "Hai attivato" : "Hai disattivato") + " la godmode.");
        } else {
            OmniLog.Status(sender, (newState ? "Hai attivato" : "Hai disattivato") + " la godmode per " + ChatColor.GRAY + target.getName());
            OmniLog.Status(target, "La godmode è stata " + (newState ? "attivata" : "disattivata") + " da " + ChatColor.GRAY + player.getName());
        }

        StatusRegistry.setStatus(target, "Godmode", newState);
        return true;
    }
}
