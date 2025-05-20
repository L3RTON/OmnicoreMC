package me.spigot.Omnicore.API.Registres.Status;

import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StatusRegistry {

    private static final Map<UUID, Map<String, Boolean>> playerStatuses = new HashMap<>();

    public static void setStatus(Player player, String key, boolean value) {
        playerStatuses.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(key, value);
    }

    public static boolean getStatus(Player player, String key) {
        return playerStatuses.getOrDefault(player.getUniqueId(), new HashMap<>()).getOrDefault(key, false);
    }

    public static Map<String, Boolean> getAllStatuses(Player player) {
        return playerStatuses.getOrDefault(player.getUniqueId(), Collections.emptyMap());
    }

    public static void clear(Player player) {
        playerStatuses.remove(player.getUniqueId());
    }
}
