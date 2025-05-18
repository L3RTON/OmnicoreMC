package me.spigot.Omnicore.API.Utils.OmniLog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class OmniLog {
    public static void Error(CommandSender sender, String message){
        String prefix = ChatColor.DARK_RED + "[OmniError] ";
        String finalMessage = prefix + message;

        sender.sendMessage(finalMessage);
    }

    public static void Info(CommandSender sender, String message){
        String prefix = ChatColor.YELLOW + "[OmniInfo] ";
        String finalMessage = prefix + message;

        sender.sendMessage(finalMessage);
    }

    public static void Debug(CommandSender sender, String message){
        String prefix = ChatColor.DARK_BLUE + "[OmniDebug] ";
        String finalMessage = prefix + message;

        Bukkit.getConsoleSender().sendMessage(finalMessage);
    }
}
