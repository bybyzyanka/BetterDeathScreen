package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.utils.PlayerAPI;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class GameModeChangeListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onGameModeChange(PlayerGameModeChangeEvent event) {
        Player player = event.getPlayer();

        // To prevent exploits, the player cannot change gamemode when dead.
        if (Config.DEAD_PLAYERS.contains(player.getName()) && event.getNewGameMode() != GameMode.SPECTATOR) {
            if (!PlayerAPI.isHardcore(player)) {
                event.setCancelled(true);
            }
        }
    }
}
