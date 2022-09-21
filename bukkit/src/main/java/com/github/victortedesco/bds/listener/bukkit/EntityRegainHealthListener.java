package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.configs.Config;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    public void onRegen(EntityRegainHealthEvent event) {
        Entity entity = event.getEntity();

        // Why the player would regain health since he is dead?
        if (Config.DEAD_PLAYERS.contains(entity.getName())) event.setCancelled(true);
    }
}
