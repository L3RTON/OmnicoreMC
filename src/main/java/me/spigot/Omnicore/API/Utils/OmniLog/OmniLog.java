package me.spigot.Omnicore.API.Utils.OmniLog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class OmniLog{
    public static void Error(CommandSender sender, String message){
        String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "OmniError" + ChatColor.GRAY + "] " + ChatColor.DARK_RED;
        String finalMessage = prefix + message;

        sender.sendMessage(finalMessage);
    }

    public static void Exception(String message, Exception e){
        String prefix = ChatColor.DARK_RED + "[OmniException] ";

        Bukkit.getConsoleSender().sendMessage(prefix + "❌ " + message + " ❌");
        Bukkit.getConsoleSender().sendMessage(prefix + "Message: " + e.getMessage());
        Bukkit.getConsoleSender().sendMessage(prefix + "Caused by: " + e.getCause());
        Bukkit.getConsoleSender().sendMessage(prefix + "Stacktrace:");

        for (StackTraceElement element : e.getStackTrace()) {
            Bukkit.getConsoleSender().sendMessage(prefix + "    at " + element.getClassName() + "." + element.getMethodName() + ChatColor.RED + "(" + element.getFileName() + ":" + element.getLineNumber() + ")");
        }
    }

    public static void Info(CommandSender sender, String message){
        String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "OmniInfo" + ChatColor.GRAY + "] " + ChatColor.YELLOW;
        String finalMessage = prefix + message;

        sender.sendMessage(finalMessage);
    }

    public static void Status(CommandSender sender, String message){
        String prefix = ChatColor.GRAY + "[" + ChatColor.DARK_PURPLE + "OmniStatus" + ChatColor.GRAY + "] " + ChatColor.GREEN;
        String finalMessage = prefix + message;

        sender.sendMessage(finalMessage);
    }

    public static void Debug(CommandSender sender, String message){
        String prefix = ChatColor.BLUE + "[OmniDebug] ";
        String finalMessage = prefix + message;

        Bukkit.getConsoleSender().sendMessage(finalMessage);
    }
}
