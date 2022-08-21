package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.api.events.Events;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlayerTeleportListener extends Events {

    public static HashMap<String, Location> TELEPORT_LOCATION = new HashMap<>();

    List<String> TELEPORT_MESSAGE_CD = new ArrayList<>();

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();

        if (!Config.HOTBAR_TELEPORT_SPECTATOR && Config.DEAD_PLAYERS.contains(p.getName())) {
            if (e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
                e.setCancelled(true);
                if (!TELEPORT_MESSAGE_CD.contains(p.getName())) {
                    TELEPORT_MESSAGE_CD.add(p.getName());
                    p.sendMessage(Messages.HOTBAR_TELEPORT_BLOCKED);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            TELEPORT_MESSAGE_CD.remove(p.getName());
                        }
                    }.runTaskLaterAsynchronously(BetterDeathScreen.plugin, 20 * 3);
                }
                return;
            }
        }
        if (Config.QUEUE_TELEPORT && Config.DEAD_PLAYERS.contains(p.getName())) {
            if (e.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN && e.getCause() != PlayerTeleportEvent.TeleportCause.SPECTATE) {
                TELEPORT_LOCATION.put(p.getName(), e.getTo());
                e.setCancelled(true);
            }
        }
    }
}
