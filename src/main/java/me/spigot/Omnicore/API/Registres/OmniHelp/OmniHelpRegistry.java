package me.spigot.Omnicore.API.Registres.OmniHelp;

import java.util.LinkedHashMap;
import java.util.Map;

public class OmniHelpRegistry {
    private static final Map<String, String> commandHelpMap = new LinkedHashMap<>();

    public static void register(String command, String description) {
        commandHelpMap.put(command, description);
    }

    public static Map<String, String> getRegisteredCommands() {
        return commandHelpMap;
    }
}