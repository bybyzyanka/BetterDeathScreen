package com.github.victortedesco.bds.listener.bds;

import com.github.victortedesco.bds.api.events.PlayerDamageByEntityBeforeDeathEvent;
import com.github.victortedesco.bds.listener.bukkit.PlayerDeathListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class PlayerDamageBeforeEntityListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamageByEntityBeforeDeath(PlayerDamageByEntityBeforeDeathEvent event) {
        Player player = event.getPlayer();

        if (event.isCancelled()) PlayerDeathListener.KILL_ASSIST.put(player.getName(), null);
        if (!event.isCancelled()) PlayerDeathListener.KILL_ASSIST.put(player.getName(), event.getDamager());
    }
}
