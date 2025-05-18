package me.spigot.Omnicore.Commands.Tpa;

import me.spigot.Omnicore.API.Utils.OmniLog.OmniLog;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TpaCommand implements CommandExecutor {

    JavaPlugin plugin;
    public TpaCommand(JavaPlugin plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Map<Player, Player> tpaRequests = new HashMap<>();
        final Map<UUID, Map<UUID, Long>> denyCooldowns = new HashMap<>();

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.DARK_RED + "Solo i player possono usare questo comando.");
            return true;
        }
        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("tpa")) {
            if (args.length != 1) {
                OmniLog.Error(sender, "Syntax Error! use /tpa <PlayerName>.");
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);
            if (target == null || target == player) {
                player.sendMessage(ChatColor.RED + "§cGiocatore non trovato o invalido.");
                return true;
            }

            Map<UUID, Long> cooldowns = denyCooldowns.getOrDefault(target.getUniqueId(), new HashMap<>());
            long now = System.currentTimeMillis();
            if (cooldowns.containsKey(player.getUniqueId()) && now < cooldowns.get(player.getUniqueId())) {
                long remaining = (cooldowns.get(player.getUniqueId()) - now) / 1000;
                player.sendMessage(ChatColor.GRAY + "§cQuesto giocatore ha rifiutato di recente. Riprova tra " + remaining + " secondi.");
                return true;
            }

            tpaRequests.put(target, player);
            target.sendMessage(ChatColor.YELLOW + "§eHai ricevuto una richiesta di teleport da §a" + player.getName());
            target.sendMessage(ChatColor.GRAY + "§eUsa §a/tpaccept §eper accettare o §c/tpdeny §eper rifiutare.");
            player.sendMessage(ChatColor.YELLOW + "§aRichiesta di teleport inviata a " + target.getName());

            return true;
        }

        if (label.equalsIgnoreCase("tpaccept")) {
            Player senderPlayer = tpaRequests.remove(player);
            if (senderPlayer == null) {
                player.sendMessage(ChatColor.RED + "§cNon hai richieste in sospeso.");
                return true;
            }

            senderPlayer.teleport(player.getLocation());
            senderPlayer.sendMessage(ChatColor.GREEN + "§aTeleport eseguito con successo a " + player.getName());
            player.sendMessage(ChatColor.GREEN + "§aHai accettato la richiesta di " + senderPlayer.getName());
            return true;
        }

        if (label.equalsIgnoreCase("tpdeny")) {
            Player senderPlayer = tpaRequests.remove(player);
            if (senderPlayer == null) {
                player.sendMessage(ChatColor.RED + "§cNon hai richieste di tpa in sospeso.");
                return true;
            }

            long blockUntil = System.currentTimeMillis() + (10 * 60 * 1000); // 10 minuti
            denyCooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>())
                    .put(senderPlayer.getUniqueId(), blockUntil);

            senderPlayer.sendMessage(ChatColor.RED + "§cLa tua richiesta è stata rifiutata. Puoi riprovare tra 10 minuti.");
            player.sendMessage(ChatColor.RED + "§eHai rifiutato la richiesta di " + ChatColor.GREEN + senderPlayer.getName());
            return true;
        }

        return false;
    }
}
