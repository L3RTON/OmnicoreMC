package me.spigot.Omnicore.Modules.Admintools.Data;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AdminTools {
    private final String command;
    private final String description;
    private final Material icon;
    private final boolean needsArgument;
    private final int page;
    private final int slot;

    public AdminTools(String command, String description, Material icon, boolean needsArgument, int page, int slot) {
        this.command = command;
        this.description = description;
        this.icon = icon;
        this.needsArgument = needsArgument;
        this.page = page;
        this.slot = slot;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public Material getIcon() {
        return icon;
    }

    public boolean needsArgument() {
        return needsArgument;
    }

    public int getPage() {
        return page;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack toItemStack() {
        ItemStack item = new ItemStack(icon);
        var meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "/" + command);
        meta.setLore(List.of(ChatColor.GRAY + description));
        item.setItemMeta(meta);
        return item;
    }
}
