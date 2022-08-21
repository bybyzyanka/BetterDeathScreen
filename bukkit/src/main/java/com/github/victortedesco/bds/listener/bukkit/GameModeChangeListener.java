package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.api.events.Events;
import com.github.victortedesco.bds.configs.Config;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

public class GameModeChangeListener extends Events {

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent e) {
        Player p = e.getPlayer();

        // To prevent exploits, the player cannot change gamemode when dead.
        if (Config.DEAD_PLAYERS.contains(p.getName()) && e.getNewGameMode() != GameMode.SPECTATOR) {
            e.setCancelled(true);
        }
    }
}
