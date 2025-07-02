package me.spigot.Omnicore.Listeners.Jail;

import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Commands.Jail.Helpers.JailData;
import me.spigot.Omnicore.Commands.Jail.JailCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;
import java.util.UUID;

public class JailListener implements Listener {
    private final JavaPlugin plugin;
    private final Map<UUID, JailData> jailedPlayers;

    public JailListener(JavaPlugin plugin, Map<UUID, JailData> jailedPlayers) {
        this.plugin = plugin;
        this.jailedPlayers = jailedPlayers;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent ev) {
        Player player = ev.getPlayer();
        JailData data = jailedPlayers.get(player.getUniqueId());
        if (data != null) {
            Bukkit.getScheduler().cancelTask(data.taskId);

            long now = System.currentTimeMillis();
            long elapsed = now - data.startTimeMs;
            data.remainingMs -= elapsed;

            if (data.remainingMs < 1000)
                data.remainingMs = 1000;

            jailedPlayers.put(player.getUniqueId(), data);

        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev) {
        Player player = ev.getPlayer();
        JailData data = jailedPlayers.get(player.getUniqueId());
        if (data != null) {
            player.teleport(data.jailCenter);
            player.setWalkSpeed(0f);
            player.setFlySpeed(0f);

            data.startTimeMs = System.currentTimeMillis();
            data.taskId = Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.setWalkSpeed(0.2f);
                player.setFlySpeed(0.1f);

                OmniLog.Status(player, "La tua pena è finita! Sei libero di vivere in pace!");
                JailCommand.destroyJail(data.jailCenter);
                jailedPlayers.remove(player.getUniqueId());

            }, data.remainingMs / 50).getTaskId();
            jailedPlayers.put(player.getUniqueId(), data);

            long remainingSeconds = Math.max(data.remainingMs / 1000, 1);
            OmniLog.Status(player, ChatColor.RED + "Pensavi di scappare così facilmente? Devi scontare ancora " + ChatColor.YELLOW + remainingSeconds + " secondi.");
            player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, -4);
        }
    }
}
