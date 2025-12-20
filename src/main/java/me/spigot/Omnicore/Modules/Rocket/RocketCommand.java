package me.spigot.Omnicore.Modules.Rocket;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RocketCommand extends OmniCommand {
    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public RocketCommand(JavaPlugin plugin, String category, String commandName, String description) {
        super(plugin, category, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("omnicore.rocket")) {
            OmniLog.Error(sender, "Non hai il permesso di usare questo comando.");
            return true;
        }

        if (!(sender instanceof Player player)) {
            OmniLog.Error(sender, "Solo un giocatore può eseguire questo comando.");
            return true;
        }

        if (args.length != 1) {
            OmniLog.Error(sender, "Syntax Error! Usa /rocket <PlayerName>");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline()) {
            OmniLog.Error(sender, "Giocatore non trovato o invalido.");
            return true;
        }

        UUID playerId = player.getUniqueId();
        long now = System.currentTimeMillis();
        if (cooldowns.containsKey(playerId)) {
            long lastUsed = cooldowns.get(playerId);
            long remaining = 5000 - (now - lastUsed);
            if (remaining > 0) {
                OmniLog.Error(player, "Devi aspettare ancora " + (remaining / 1000) + " secondi prima di riutilizzare il comando.");
                return true;
            }
        }

        cooldowns.put(playerId, now);

        Location loc = target.getLocation();
        loc.getWorld().playEffect(loc, Effect.SMOKE, 1);
        loc.getWorld().playSound(loc, Sound.ENTITY_GENERIC_EXPLODE, 1.5f, 1.2f);

        target.setVelocity(new Vector(0, 3.5, 0));
        OmniLog.Info(target, ChatColor.DARK_PURPLE + sender.getName() + ChatColor.YELLOW + " ti ha usato come test per la " + ChatColor.BLUE + "NASA.");
        if (!sender.equals(target)) {
            OmniLog.Status(sender, "Hai usato " + ChatColor.YELLOW + target.getName() + ChatColor.GREEN + " come test per la " + ChatColor.BLUE + "NASA.");
        }

        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                if (!target.isOnline()) {
                    cancel();
                    return;
                }

                Location below = target.getLocation().clone().subtract(0, 1, 0);
                Material type = below.getBlock().getType();

                if (type.isSolid() || ticks > 60) {
                    cancel();
                    return;
                }

                target.getWorld().spawnParticle(Particle.FLAME, below, 10, 0.2, 0.1, 0.2, 0.01);
                target.getWorld().spawnParticle(Particle.LARGE_SMOKE, below, 5, 0.3, 0.1, 0.3, 0.02);
                ticks++;
            }
        }.runTaskTimer(plugin, 0, 1);

        return true;
    }
}
