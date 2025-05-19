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

    public static void Exception(String message, Exception e){
        String prefix = ChatColor.DARK_RED + "[OmniException] ";
        String finalMessage = prefix + message;
        Bukkit.getConsoleSender().sendMessage(finalMessage);

        Bukkit.getLogger().severe(e.toString());
        for (StackTraceElement element : e.getStackTrace()) {
            Bukkit.getLogger().severe("    at " + element.toString());
        }
    }

    public static void Info(CommandSender sender, String message){
        String prefix = ChatColor.YELLOW + "[OmniInfo] ";
        String finalMessage = prefix + message;

        sender.sendMessage(finalMessage);
    }

    public static void Debug(CommandSender sender, String message){
        String prefix = ChatColor.BLUE + "[OmniDebug] ";
        String finalMessage = prefix + message;


        Bukkit.getConsoleSender().sendMessage(finalMessage);
    }
}
