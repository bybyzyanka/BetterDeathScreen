package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.listener.Events;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener extends Events {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegen(EntityRegainHealthEvent e) {
        Entity ent = e.getEntity();

        // Why the player would regain health naturally since he is dead?
        if (Config.DEAD_PLAYERS.contains(ent.getName()) && e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
            e.setCancelled(true);
        }
    }
}
