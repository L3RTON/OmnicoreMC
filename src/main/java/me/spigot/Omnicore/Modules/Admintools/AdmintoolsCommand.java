package me.spigot.Omnicore.Modules.Admintools;


import me.spigot.Omnicore.API.Extensions.OmniCommand.OmniCommand;
import me.spigot.Omnicore.API.Registres.Admintools.AdmintoolsRegistry;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Modules.Admintools.Data.AdminTools;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class AdmintoolsCommand extends OmniCommand {
    private static AdmintoolsCommand instance;

    public AdmintoolsCommand(JavaPlugin plugin, String category, String commandName, String description) {
        super(plugin, category, commandName, description);
        instance = this;
    }

    public static AdmintoolsCommand getInstance() {
        return instance;
    }

    private ItemStack createNavItem(String name, Material material, ChatColor color) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(color + name);
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            OmniLog.Error(sender, "Solo i giocatori possono eseguire questo comando.");
            return true;
        }

        openMenu(player, 0);
        return true;
    }

    public void openMenu(Player player, int page) {
        Inventory menu = Bukkit.createInventory(null, 54, ChatColor.BLUE + "Admin Tools - Pagina " + (page + 1));

        for (AdminTools tool : AdmintoolsRegistry.getAll()) {
            if (tool.getPage() == page) {
                menu.setItem(tool.getSlot(), tool.toItemStack());
            }
        }

        boolean hasPrevious = AdmintoolsRegistry.getAll().stream().anyMatch(t -> t.getPage() == page - 1);
        boolean hasNext = AdmintoolsRegistry.getAll().stream().anyMatch(t -> t.getPage() == page + 1);

        if (hasPrevious) {
            menu.setItem(45, createNavItem("Pagina precedente", Material.ARROW, ChatColor.GRAY));
        }

        if (hasNext) {
            menu.setItem(53, createNavItem("Pagina successiva", Material.ARROW, ChatColor.GRAY));
        }

        player.openInventory(menu);
    }
}
