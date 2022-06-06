package me.tedesk.events.bukkit;

import me.tedesk.configs.Config;
import me.tedesk.configs.Messages;
import me.tedesk.events.Listeners;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;

public class PlayerTeleportListener extends Listeners {

    public static HashMap<String, Boolean> WOULD_TELEPORT = new HashMap<>();
    public static HashMap<String, Location> TELEPORT_LOCATION = new HashMap<>();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();

        if (!Config.HOTBAR_TELEPORT_SPECTATOR) {
            if (Config.DEAD_PLAYERS.contains(p.getName()) && e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
                e.setCancelled(true);
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.HOTBAR_TELEPORT_BLOCKED));
                return;
            }
        }
        if (Config.QUEUE_TELEPORT) {
            if (Config.DEAD_PLAYERS.contains(p.getName())) {
                e.setCancelled(true);
                WOULD_TELEPORT.put(p.getName(), true);
                TELEPORT_LOCATION.put(p.getName(), e.getTo());
            }
        }
    }
}
