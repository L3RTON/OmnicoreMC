package me.spigot.Omnicore.API.Interfaces.OmniCommand;

import me.spigot.Omnicore.API.Registres.OmniHelp.OmniHelpRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class OmniCommand implements CommandExecutor {
    protected JavaPlugin plugin;

    private final String commandName;
    private final String description;

    public OmniCommand(JavaPlugin plugin, String commandName, String description) {
        this.plugin = plugin;
        this.commandName = commandName;
        this.description = description;

        OmniHelpRegistry.register("/" + commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return true;
    }
}
