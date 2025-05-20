package me.spigot.Omnicore.Listeners.Godmode;

import me.spigot.Omnicore.Commands.Godmode.GodmodeCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;

public class GodmodeListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && GodmodeCommand.isGod(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        if (event.getTarget() instanceof Player player && GodmodeCommand.isGod(player)) {
            event.setCancelled(true);
        }
    }
}