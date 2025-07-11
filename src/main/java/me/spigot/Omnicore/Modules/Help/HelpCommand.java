package me.spigot.Omnicore.Modules.Help;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Registres.OmniHelp.OmniHelpRegistry;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

public class HelpCommand extends OmniCommand {

    public HelpCommand(JavaPlugin plugin, String category, String commandName, String description) {
        super(plugin, category, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            OmniLog.Info( sender, ChatColor.BOLD + "Scegli una categoria di comandi:");
            for (String category : OmniHelpRegistry.getCategories()) {
                TextComponent msg = new TextComponent(" - " + category);
                msg.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/omnihelp " + category));
                msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("Clicca per vedere i comandi di questa categoria").create()));
                sender.spigot().sendMessage(msg);
            }
            return true;
        }

        String category = args[0].toLowerCase();
        Map<String, String> commands = OmniHelpRegistry.getCommandsInCategory(category);

        if (commands.isEmpty()) {
            OmniLog.Error(sender, "Categoria non trovata.");
            return true;
        }

        OmniLog.Info(sender, "Comandi nella categoria '" + category + "':");

        for (Map.Entry<String, String> entry : commands.entrySet()) {
            sender.sendMessage(ChatColor.GRAY + entry.getKey() + ChatColor.WHITE + " - " + entry.getValue());
        }

        return true;
    }
}
