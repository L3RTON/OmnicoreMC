package me.spigot.Omnicore.Commands.Hat;

import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class HatCommand extends OmniCommand {

    public HatCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player player)){
            OmniLog.Error(sender, "Questo comando può essere usato solo da un giocatore.");
            return true;
        }

        ItemStack handItem = player.getInventory().getItemInMainHand();
        if (handItem == null || handItem.getType().isAir()){
            OmniLog.Error(sender, "Devi avere un oggetto in mano per usare questo comando.");
            return true;
        }

        if (handItem.getAmount() > 1){
            OmniLog.Error(sender, "Devi avere un solo oggetto in mano per usare questo comando.");
            return true;
        }

        if (!handItem.getType().isBlock() || handItem.getType().isEdible()){
            OmniLog.Error(sender, "Non puoi usare questo oggetto come cappello.");
            return true;
        }

        Player target;
        if (args.length == 0)
            target = (Player) sender;
        else if (args.length == 1){
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                OmniLog.Error(sender, "Giocatore non trovato o invalido.");
                return true;
            }

        }else{
            OmniLog.Error(sender, "Syntax Error! use /hat.");
            return true;
        }

        if (args.length != 0){
            OmniLog.Error(sender, "Non puoi specificare un player con questo comando.");
            return true;
        }


        ItemStack currentHelmet = player.getInventory().getHelmet();

        player.getInventory().setHelmet(handItem);
        player.getInventory().setItemInMainHand(currentHelmet != null ? currentHelmet : new ItemStack(Material.AIR));

        OmniLog.Status(sender, "Hai messo l'oggetto " + handItem.getType().name().toLowerCase().replace("_", " ") + " come cappello!");

        return true;
    }
}
