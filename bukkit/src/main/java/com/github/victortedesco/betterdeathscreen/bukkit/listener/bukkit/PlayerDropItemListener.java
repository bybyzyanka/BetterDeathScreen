package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();

        if (BetterDeathScreenAPI.getPlayerManager().isDead(player)) {
            event.setCancelled(true);
            player.updateInventory();
        }
    }
}
