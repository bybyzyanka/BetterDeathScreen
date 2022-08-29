package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.listener.Events;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.util.Vector;

public class PlayerMoveListener extends Events {

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Location from = e.getFrom();
        Location to = e.getTo();

        if (from.getBlockX() == to.getBlockX()
                && from.getBlockY() == to.getBlockY()
                && from.getBlockZ() == to.getBlockZ()) {
            return;
        }

        if (Config.DEAD_PLAYERS.contains(p.getName()) && !Config.IGNORE_WALLS) {
            if (p.getEyeLocation().getBlock().getType().isSolid() || to.getBlock().getType().isSolid()) {
                p.setVelocity(new Vector());
                p.teleport(e.getFrom(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            }
        }
    }
}
