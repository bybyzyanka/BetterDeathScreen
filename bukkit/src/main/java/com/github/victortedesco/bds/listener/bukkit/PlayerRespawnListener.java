package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.listener.Events;
import com.github.victortedesco.bds.utils.PlayerAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerRespawnListener extends Events {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        // Resets all events related to last damage.
        PlayerAPI.resetDamageBeforeDeath(p);
    }
}
