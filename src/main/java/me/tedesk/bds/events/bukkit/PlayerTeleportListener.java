package me.tedesk.bds.events.bukkit;

import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.configs.Config;
import me.tedesk.bds.configs.Messages;
import me.tedesk.bds.events.Listeners;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerTeleportListener extends Listeners {

    public static HashMap<String, Boolean> WOULD_TELEPORT = new HashMap<>();
    public static HashMap<String, Location> TELEPORT_LOCATION = new HashMap<>();
    public static List<String> TELEPORT_MESSAGE_CD = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();

        if (!Config.HOTBAR_TELEPORT_SPECTATOR) {
            if (Config.DEAD_PLAYERS.contains(p.getName()) && e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
                e.setCancelled(true);
                if (!TELEPORT_MESSAGE_CD.contains(p.getName())) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.HOTBAR_TELEPORT_BLOCKED));
                    TELEPORT_MESSAGE_CD.add(p.getName());
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            TELEPORT_MESSAGE_CD.remove(p.getName());
                        }
                    }.runTaskLaterAsynchronously(BetterDeathScreen.plugin, 20*3);
                }
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
