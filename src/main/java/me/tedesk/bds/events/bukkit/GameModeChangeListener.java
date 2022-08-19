package me.tedesk.bds.events.bukkit;

import me.tedesk.bds.configs.Config;
import me.tedesk.bds.events.Events;
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
