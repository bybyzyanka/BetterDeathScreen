package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.listener.Events;
import com.github.victortedesco.bds.utils.Tasks;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener extends Events {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if ((p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission(Config.ADMIN)) || Config.DEAD_PLAYERS.contains(p.getName())) {
            if (Bukkit.getServer().isHardcore()) {
                Config.DEAD_PLAYERS.add(p.getName());
                Tasks.startTimer(p);
            }
        }
        // Desbugando o respawn em outras dimensões.
        if (p.getBedSpawnLocation() != null) {
            if (p.getBedSpawnLocation().getWorld().getEnvironment() != World.Environment.NORMAL) {
                p.setBedSpawnLocation(null);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        // Para evitar bugs, o jogador renasce ao desconectar.
        if ((p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission(Config.ADMIN)) || Config.DEAD_PLAYERS.contains(p.getName())) {
            if (!Bukkit.getServer().isHardcore()) {
                Tasks.performRespawn(p);
            }
        }
    }
}
