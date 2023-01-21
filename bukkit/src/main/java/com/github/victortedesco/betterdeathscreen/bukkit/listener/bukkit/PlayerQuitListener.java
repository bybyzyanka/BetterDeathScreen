package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // To avoid bugs, the player will respawn after disconnecting.
        if (BetterDeathScreenAPI.getPlayerManager().isDead(player))
            BetterDeathScreen.getRespawnTasks().performRespawn(player, false);
        PlayerRespawnListener.getBedNotFoundMessageSent().remove(player);
    }
}
