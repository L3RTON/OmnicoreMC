package me.spigot.Omnicore.Listeners.AdminTools;

import me.spigot.Omnicore.API.Extensions.OmniListener.OmniListener;
import me.spigot.Omnicore.API.Registres.Admintools.AdmintoolsRegistry;
import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import me.spigot.Omnicore.Modules.Admintools.AdmintoolsCommand;
import me.spigot.Omnicore.Modules.Admintools.Data.AdminTools;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AdmintoolsListener extends OmniListener {

    private final Map<UUID, Consumer<String>> awaitingInput = new HashMap<>();

    public AdmintoolsListener(JavaPlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onAdminMenuClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player))
            return;
        if (!event.getView().getTitle().startsWith(ChatColor.BLUE + "Admin Tools"))
            return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta())
            return;

        String displayName = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());

        if (displayName.equalsIgnoreCase("Pagina precedente")){
            int currentPage = 0;
            try {
                String title = ChatColor.stripColor(event.getView().getTitle());
                currentPage = Integer.parseInt(title.replace("Admin Tools - Pagina ", "")) - 1;
            } catch (NumberFormatException ignored) {}

            int newPage = Math.max(currentPage - 1, 0);
            AdmintoolsCommand.getInstance().openMenu(player, newPage);
            return;
        }


        if (displayName.equalsIgnoreCase("Pagina successiva")) {
            int currentPage = 0;
            try {
                String title = ChatColor.stripColor(event.getView().getTitle());
                currentPage = Integer.parseInt(title.replace("Admin Tools - Pagina ", "")) - 1;
            } catch (NumberFormatException ignored) {}

            int newPage = currentPage + 1;
            AdmintoolsCommand.getInstance().openMenu(player, newPage);
            return;
        }


        for (AdminTools tool : AdmintoolsRegistry.getAll()) {
            if (displayName.equalsIgnoreCase("/" + tool.getCommand())) {
                player.closeInventory();

                if (tool.needsArgument()) {
                    awaitingInput.put(player.getUniqueId(), (arg) -> {
                        player.performCommand(tool.getCommand() + " " + arg);
                    });
                    OmniLog.Info(player, "Inserisci in chat l'argomento per /" + tool.getCommand() + ":");
                } else {
                    player.performCommand(tool.getCommand());
                }

                return;
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (awaitingInput.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
            Consumer<String> callback = awaitingInput.remove(player.getUniqueId());
            Bukkit.getScheduler().runTask(plugin, () -> callback.accept(event.getMessage()));
        }
    }
}
