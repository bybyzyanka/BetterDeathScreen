package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.listener.Events;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class GameModeChangeListener extends Events {

    @EventHandler(priority = EventPriority.LOW)
    public void onGameModeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();

        // To prevent exploits, the player cannot change gamemode when dead.
        if (Config.DEAD_PLAYERS.contains(p.getName()) && e.getNewGameMode() != GameMode.SPECTATOR) {
            if (!Bukkit.getServer().isHardcore()) {
                e.setCancelled(true);
            }
        }
    }
}
