package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItemListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickup(EntityPickupItemEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) event.getEntity();
            if (BetterDeathScreenAPI.getPlayerManager().isDead(player)) event.setCancelled(true);
        }
    }
}
