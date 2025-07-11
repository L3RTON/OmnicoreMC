package me.spigot.Omnicore.Listeners.Godmode;

import me.spigot.Omnicore.API.Extensions.OmniListener.OmniListener;
import me.spigot.Omnicore.Modules.Godmode.GodmodeCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class GodmodeListener extends OmniListener {

    public GodmodeListener(JavaPlugin plugin) {
        super(plugin);
    }

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