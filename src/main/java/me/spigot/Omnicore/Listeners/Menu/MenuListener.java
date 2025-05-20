package me.spigot.Omnicore.Listeners.Menu;

import org.bukkit.ChatColor;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.UUID;

public class MenuListener implements Listener {

    private static final HashSet<UUID> godmodePlayers = new HashSet<>();

    @EventHandler
    public void onMenuOpen(InventoryClickEvent ev) {
        Inventory inv = ev.getInventory();

        if (!(ev.getWhoClicked() instanceof Player player))
            return;

        if (!(ev.getView().getTitle().equals(ChatColor.BLUE + "Menu")))
            ev.setCancelled(true);


        ItemStack clickedItem = ev.getCurrentItem();
        Material god = Material.TOTEM_OF_UNDYING;
        Material fly = Material.FEATHER;
        Material heal = Material.APPLE;

        if (clickedItem == null || clickedItem.getType() == Material.AIR)
            return;

        if (clickedItem.getType() == god){
            player.performCommand("god");
            player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(0, Note.Tone.C));

            player.closeInventory();
        }

        if (clickedItem.getType() == fly){
            player.performCommand("fly");
            player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(0, Note.Tone.C));

            player.closeInventory();
        }

        if (clickedItem.getType() == heal){
            player.performCommand("heal");
            player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.C));

            player.closeInventory();
        }
    }
}
