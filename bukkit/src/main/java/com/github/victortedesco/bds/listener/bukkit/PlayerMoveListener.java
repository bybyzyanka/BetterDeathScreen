package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.utils.PlayerAPI;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();

        if (from.getBlockX() == to.getBlockX() && from.getBlockY() == to.getBlockY() && from.getBlockZ() == to.getBlockZ())
            return;

        if (Config.DEAD_PLAYERS.contains(player.getName()) && !Config.IGNORE_WALLS) {
            if (to.getBlock().getType().isSolid()) {
                player.setVelocity(event.getFrom().getDirection().multiply(-1).multiply(0.25));
            }
            if (player.getEyeLocation().getBlock().getType().isSolid()) {
                PlayerAPI.teleportSafeLocation(player, player.getLocation(), PlayerTeleportEvent.TeleportCause.UNKNOWN);
            }
        }
        if (Config.MOVE_SPECTATOR && player.getWalkSpeed() == 0 && player.getFlySpeed() == 0) {
            player.setWalkSpeed(0.2F);
            player.setFlySpeed(0.1F);
        }
    }
}
