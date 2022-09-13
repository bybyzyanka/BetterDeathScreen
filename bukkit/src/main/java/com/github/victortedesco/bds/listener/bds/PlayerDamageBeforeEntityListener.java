package com.github.victortedesco.bds.listener.bds;

import com.github.victortedesco.bds.api.events.PlayerDamageByEntityBeforeDeathEvent;
import com.github.victortedesco.bds.listener.Events;
import com.github.victortedesco.bds.listener.bukkit.PlayerDeathListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class PlayerDamageBeforeEntityListener extends Events {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamageByEntityBeforeDeath(PlayerDamageByEntityBeforeDeathEvent e) {
        if (e.isCancelled()) PlayerDeathListener.KILL_ASSIST.put(e.getPlayer().getName(), null);
        if (!e.isCancelled()) PlayerDeathListener.KILL_ASSIST.put(e.getPlayer().getName(), e.getDamager());
    }
}
