package me.tedesk.bds.events.bukkit;

import me.tedesk.bds.configs.Config;
import me.tedesk.bds.events.Listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EntityRegainHealthListener extends Listeners {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRegen(EntityRegainHealthEvent e) {
        if (Config.DEAD_PLAYERS.contains(e.getEntity().getName()) && e.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
            // Why the player would regain health naturally since he is dead?
            e.setCancelled(true);
        }
    }
}
