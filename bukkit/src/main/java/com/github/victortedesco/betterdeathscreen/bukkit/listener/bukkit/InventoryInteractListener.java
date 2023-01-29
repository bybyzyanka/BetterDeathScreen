package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryInteractListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            if (BetterDeathScreenAPI.getPlayerManager().isDead(player)) {
                event.setCancelled(true);
                player.updateInventory();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();

            if (BetterDeathScreenAPI.getPlayerManager().isDead(player)) {
                event.setCancelled(true);
                player.updateInventory();
            }
        }
    }
}
