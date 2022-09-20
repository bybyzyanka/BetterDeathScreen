package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class PlayerTeleportListener implements Listener {

    public static HashMap<String, Location> TELEPORT_LOCATION = new HashMap<>();

    public static Set<String> TELEPORT_MESSAGE_IMUNE = new HashSet<>();
    Set<String> TELEPORT_MESSAGE_CD = new HashSet<>();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (TELEPORT_MESSAGE_IMUNE.contains(player.getName())) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(BetterDeathScreen.getInstance(),
                    () -> TELEPORT_MESSAGE_IMUNE.remove(player.getName()), 5);
        }
        if (Config.DEAD_PLAYERS.contains(player.getName()) && !Config.HOTBAR_TELEPORT_SPECTATOR) {
            if (event.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
                event.setCancelled(true);
                if (!TELEPORT_MESSAGE_CD.contains(player.getName()) && !TELEPORT_MESSAGE_IMUNE.contains(player.getName())) {
                    TELEPORT_MESSAGE_CD.add(player.getName());
                    player.sendMessage(Messages.HOTBAR_TELEPORT_BLOCKED);
                    Bukkit.getScheduler().runTaskLaterAsynchronously(BetterDeathScreen.getInstance(),
                            () -> TELEPORT_MESSAGE_CD.remove(player.getName()), 20 * 3);
                }
                return;
            }
        }
        if (Config.DEAD_PLAYERS.contains(player.getName()) && Config.QUEUE_TELEPORT) {
            if (event.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN && event.getCause() != PlayerTeleportEvent.TeleportCause.SPECTATE) {
                TELEPORT_LOCATION.put(player.getName(), event.getTo());
                event.setCancelled(true);
            }
        }
    }
}
