package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.listener.Events;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerTeleportListener extends Events {

    public static HashMap<String, Location> TELEPORT_LOCATION = new HashMap<>();

    List<String> TELEPORT_MESSAGE_CD = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();

        if (Config.DEAD_PLAYERS.contains(p.getName()) && !Config.HOTBAR_TELEPORT_SPECTATOR) {
            if (e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
                e.setCancelled(true);
                if (!Config.USE_KILL_CAM) {
                    if (!TELEPORT_MESSAGE_CD.contains(p.getName())) {
                        TELEPORT_MESSAGE_CD.add(p.getName());
                        p.sendMessage(Messages.HOTBAR_TELEPORT_BLOCKED);
                        Bukkit.getScheduler().runTaskLaterAsynchronously(BetterDeathScreen.getInstance(),
                                () -> TELEPORT_MESSAGE_CD.remove(p.getName()), 20 * 3);
                    }
                }
                return;
            }
        }
        if (Config.DEAD_PLAYERS.contains(p.getName()) && Config.QUEUE_TELEPORT) {
            if (e.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN && e.getCause() != PlayerTeleportEvent.TeleportCause.SPECTATE) {
                TELEPORT_LOCATION.put(p.getName(), e.getTo());
                e.setCancelled(true);
            }
        }
    }
}
