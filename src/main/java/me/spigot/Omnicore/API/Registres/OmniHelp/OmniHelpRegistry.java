package me.spigot.Omnicore.API.Registres.OmniHelp;

import java.util.*;

public class OmniHelpRegistry {
    private static final Map<String, Map<String, String>> categoryCommandMap = new LinkedHashMap<>();

    public static void register(String category, String command, String description) {
        categoryCommandMap
                .computeIfAbsent(category.toLowerCase(), k -> new LinkedHashMap<>())
                .put(command, description);
    }

    public static Set<String> getCategories() {
        return categoryCommandMap.keySet();
    }

    public static Map<String, String> getCommandsInCategory(String category) {
        return categoryCommandMap.getOrDefault(category.toLowerCase(), new LinkedHashMap<>());
    }
}