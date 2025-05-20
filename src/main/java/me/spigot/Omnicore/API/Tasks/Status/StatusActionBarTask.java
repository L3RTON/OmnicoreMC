package me.spigot.Omnicore.API.Tasks.Status;

import me.spigot.Omnicore.API.Registres.Status.StatusRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.Map;

public class StatusActionBarTask extends BukkitRunnable {
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Map<String, Boolean> statuses = StatusRegistry.getAllStatuses(player);

            if (statuses.isEmpty())
                continue;

            StringBuilder statusBar = new StringBuilder(ChatColor.YELLOW + "Status: ");
            boolean hasValidStatus = false;

            for (Map.Entry<String, Boolean> entry : statuses.entrySet()) {
                String key = entry.getKey();
                boolean value = entry.getValue();
                if (value) {
                    hasValidStatus = true;
                    statusBar.append(ChatColor.GRAY)
                            .append(key).append(": ").append(ChatColor.GREEN).append("ON")
                            .append(ChatColor.GRAY).append(" | ");
                }
            }

            if (!hasValidStatus) {
                // Se non ci sono status validi, non mostra nulla nella status bar
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(""));
                continue;
            }

            if (statusBar.length() >= 3)
                statusBar.setLength(statusBar.length() - 3);

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(statusBar.toString()));
        }
    }
}
