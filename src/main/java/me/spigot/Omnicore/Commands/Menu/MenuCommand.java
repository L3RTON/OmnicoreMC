package me.spigot.Omnicore.Commands.Menu;


import me.spigot.Omnicore.API.Interface.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class MenuCommand extends OmniCommand {
    public MenuCommand(JavaPlugin plugin, String commandName, String description) {
        super(plugin, commandName, description);
    }

    @Override
    public boolean onCommand(org.bukkit.command.CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (!(sender instanceof Player player)){
            OmniLog.Error(sender, "Solo i giocatori possono eseguire questo comando.");
            return true;
        }

        Inventory menu = Bukkit.createInventory(null, 45, ChatColor.BLUE + "Menu");


        //Creazione degli items//
        ItemStack flyItem = new ItemStack(org.bukkit.Material.FEATHER);
        ItemMeta flyMeta = flyItem.getItemMeta();
        flyMeta.setDisplayName(ChatColor.GRAY + "Fly");
        flyItem.setItemMeta(flyMeta);

        ItemStack godItem = new ItemStack(org.bukkit.Material.TOTEM_OF_UNDYING);
        ItemMeta godMeta = godItem.getItemMeta();
        godMeta.setDisplayName(ChatColor.GOLD + "Godmode");
        godItem.setItemMeta(godMeta);

        ItemStack healItem = new ItemStack(org.bukkit.Material.APPLE);
        ItemMeta healMeta = healItem.getItemMeta();
        healMeta.setDisplayName(ChatColor.RED + "Heal");
        healItem.setItemMeta(healMeta);

        //Assegnazione degli items//
        menu.setItem(20, flyItem);
        menu.setItem(22, godItem);
        menu.setItem(24, healItem);

        player.openInventory(menu);
        return true;
    }
}
