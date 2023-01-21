package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (BetterDeathScreenAPI.getPlayerManager().isDead(player) && BetterDeathScreen.getConfiguration().canSpectate()) {
            if (Bukkit.isHardcore()) return;
            if (player.getSpectatorTarget() == null && player.getGameMode() == GameMode.SPECTATOR)
                player.setGameMode(Bukkit.getDefaultGameMode());
        }
    }
}
