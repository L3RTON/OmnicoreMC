package me.spigot.Omnicore.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class VanishCommand implements CommandExecutor {

    private JavaPlugin plugin;

    public VanishCommand(JavaPlugin plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.DARK_RED + "Sintassi non corretta (Uso corretto: /vanish <PlayerName>)");

            return true;
        }

        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) {
            sender.sendMessage(ChatColor.DARK_RED + "Giocatore non trovato.");

            return true;
        }

        boolean isVanished = target.isInvisible();
        target.setInvisible(!isVanished);

        if (!isVanished)
            sender.sendMessage(ChatColor.DARK_GREEN + "Ora sei invisibile.\n");
        else
            sender.sendMessage(ChatColor.DARK_RED + "Sei tornato visibile!\n");

        return true;
    }
}
