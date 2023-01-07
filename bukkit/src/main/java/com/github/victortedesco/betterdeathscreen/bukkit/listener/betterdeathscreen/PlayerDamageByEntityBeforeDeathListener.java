package com.github.victortedesco.betterdeathscreen.bukkit.listener.betterdeathscreen;

import com.github.victortedesco.betterdeathscreen.api.events.PlayerDamageByEntityBeforeDeathEvent;
import com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit.PlayerDeathListener;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerDamageByEntityBeforeDeathListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamageByEntityBeforeDeath(PlayerDamageByEntityBeforeDeathEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getDamager();
        if (event.isCancelled()) entity = null;

        PlayerDeathListener.getKillAssists().put(player, entity);
    }
}
