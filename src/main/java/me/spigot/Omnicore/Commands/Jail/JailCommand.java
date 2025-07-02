package me.spigot.Omnicore.Commands.Jail;

import me.spigot.Omnicore.API.Interfaces.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Commands.Jail.Helpers.JailData;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public class JailCommand extends OmniCommand {
    private final Map<UUID, JailData> jailedPlayers;

    public Map<UUID, JailData> getJailedPlayers(){
        return jailedPlayers;
    }

    public JailCommand(JavaPlugin plugin, String commandName, String description, Map<UUID, JailData> jailedPlayers) {
        super(plugin, commandName, description);
        this.jailedPlayers = jailedPlayers;
    }

    public long parseTime(String input) {
        try {
            char unit = input.charAt(input.length() - 1);
            long value = Long.parseLong(input.substring(0, input.length() - 1));
            return switch (unit) {
                case 's' -> value * 1000;
                case 'm' -> value * 60 * 1000;
                case 'h' -> value * 60 * 60 * 1000;
                case 'd' -> value * 24 * 60 * 60 * 1000;
                default -> -1;
            };
        } catch (Exception e) {
            return -1;
        }
    }

    public static void buildJail(Location loc) {
        World world = loc.getWorld();
        int px = loc.getBlockX();
        int py = loc.getBlockY();
        int pz = loc.getBlockZ();

        for (int x = -2; x <= 2; x++) {
            for (int y = -1; y <= 3; y++) {
                for (int z = -2; z <= 2; z++) {
                    if (world == null) continue;

                    if (x >= -1 && x <= 1 && y >= 0 && y <= 2 && z >= -1 && z <= 1) {
                        world.getBlockAt(px + x, py + y, pz + z).setType(Material.AIR);
                    } else {
                        world.getBlockAt(px + x, py + y, pz + z).setType(Material.BEDROCK);
                    }
                }
            }
        }
    }

    public static void destroyJail(Location loc) {
        World world = loc.getWorld();
        int px = loc.getBlockX();
        int py = loc.getBlockY();
        int pz = loc.getBlockZ();

        for (int x = -2; x <= 2; x++) {
            for (int y = -1; y <= 3; y++) {
                for (int z = -2; z <= 2; z++) {
                    if (world == null) continue;
                    world.getBlockAt(px + x, py + y, pz + z).setType(Material.AIR);
                }
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)){
            OmniLog.Error(sender, "Solo un giocatore può eseguire questo comando.");
            return true;
        }

        if (!player.hasPermission("omnicore.jail")){
            OmniLog.Error(sender, "Non hai il permesso di usare questo commando.");
            return true;
        }

        if (args.length != 2) {
            OmniLog.Error(sender, "Syntax Error! use /jail <PlayerName> <Duration> ex: /jail JoeMama 10m");
            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || !target.isOnline()) {
            OmniLog.Error(sender, "Giocatore non trovato o invalido.");
            return true;
        }

        if (target == player) {
            OmniLog.Info(sender, "Complimenti, stavi per metterti in jail da solo...");
            return true;
        }

        if (jailedPlayers.containsKey(target.getUniqueId())){
            OmniLog.Error(sender, "il giocatore è già in stato di jail.");
            return true;
        }

        long durationMs = parseTime(args[1]);
        if (durationMs <= 0) {
            OmniLog.Error(sender, "Durata invalida! Usa un formato come 10s, 5m, 2h, 1d.");
            return true;
        }
        long durationTicks = durationMs / 50; // 50ms = 1 tick

        Location jailCenter = target.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);

        buildJail(jailCenter.clone());
        target.teleport(jailCenter);

        OmniLog.Status(sender, "Hai messo in jail " + target.getName() + " per " + args[1] + ".");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);
        OmniLog.Status(target, ChatColor.RED + "Sei stato messo in jail da "+ ChatColor.LIGHT_PURPLE + sender.getName() + ChatColor.RED + ". hai ancora "+ ChatColor.YELLOW + args[1] + ChatColor.RED + " da scontare.");
        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);

        target.setWalkSpeed(0f);
        target.setFlySpeed(0f);

        long startTimeMs = System.currentTimeMillis();
        int taskId = Bukkit.getScheduler().runTaskLater(plugin, () -> {
            target.setWalkSpeed(0.2f);
            target.setFlySpeed(0.1f);

            OmniLog.Status(target, "La tua pena è finita! Sei libero di vivere in pace!");
            target.playNote(target.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.C));
            destroyJail(jailCenter);
            jailedPlayers.remove(target.getUniqueId());
        }, durationTicks).getTaskId();

        jailedPlayers.put(target.getUniqueId(), new JailData(jailCenter, durationMs, startTimeMs, taskId));

        return true;
    }
}