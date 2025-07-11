package me.spigot.Omnicore.API.Registres.Admintools;

import me.spigot.Omnicore.Modules.Admintools.Data.AdminTools;

import java.util.ArrayList;
import java.util.List;

public class AdmintoolsRegistry {
    private static final List<AdminTools> tools = new ArrayList<>();

    public static void register(AdminTools tool) {
        tools.add(tool);
    }

    public static List<AdminTools> getAll() {
        return tools;
    }
}
