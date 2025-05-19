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

        Inventory menu = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Menu");

        ItemStack godItem = new ItemStack(org.bukkit.Material.TOTEM_OF_UNDYING);
        ItemMeta meta = godItem.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Godmode");
        godItem.setItemMeta(meta);
        menu.setItem(0, godItem);

        player.openInventory(menu);
        return true;
    }
}
