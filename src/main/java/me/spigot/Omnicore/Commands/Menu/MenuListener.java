package me.spigot.Omnicore.Commands.Menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MenuListener implements Listener {

    @EventHandler
    public void onMenuOpen(InventoryClickEvent ev) {
        Inventory inv = ev.getInventory();

        if (!(ev.getWhoClicked() instanceof Player player))
            return;

        if (!(ev.getView().getTitle().equals(ChatColor.BLUE + "Menu")))

        ev.setCancelled(true);

        ItemStack clickedItem = ev.getCurrentItem();
        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            return;

        if (clickedItem.getType() == Material.TOTEM_OF_UNDYING){


            player.closeInventory();
        }



    }
}
