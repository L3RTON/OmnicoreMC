package me.spigot.Omnicore.Commands.Dice;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

import static java.lang.Thread.sleep;

public class DiceCommand extends OmniCommand {

    private Random random = new Random();

    public DiceCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) {
            OmniLog.Error(sender, "Solo i giocatori possono eseguire questo comando.");
            return true;
        }


        if (args.length != 1) {
            OmniLog.Error(sender, "Syntax Error! Use /dice <Number>.");
            return true;
        }

        int max;
        try {
            max = Integer.parseInt(args[0]);
            if (max <= 1) {
                OmniLog.Error(sender, "Inserisci un numero maggiore di 1!");
                return true;
            }
        } catch (NumberFormatException e) {
            OmniLog.Error(sender, "Syntax Error! Use /dice <Number>.");
            return true;
        }

        OmniLog.Info(sender, "Hai tirato il dado...");
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_HAT, 1, 1);

        int result = random.nextInt(max) + 1;
        if (result == 69){
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, -4);

                OmniLog.Info(sender,"" + ChatColor.DARK_PURPLE + result + "...");
            }, 40L); // 2s
            return true;
        }

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                player.playSound(player.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1, 1);

                OmniLog.Info(sender,"" + ChatColor.GREEN + result + "!");
            }, 40L); // 2s

        return true;
    }
}