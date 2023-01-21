package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (BetterDeathScreenAPI.getPlayerManager().isDead(player)) {
            event.setCancelled(true);
            player.updateInventory();
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();

        if (BetterDeathScreenAPI.getPlayerManager().isDead(player)) {
            event.setCancelled(true);
            player.updateInventory();
            if (BetterDeathScreen.getConfiguration().canSpectate()) {
                player.setGameMode(GameMode.SPECTATOR);
                player.setSpectatorTarget(event.getRightClicked());
            }
        }
    }
}
