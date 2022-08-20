package me.tedesk.bds.events.bukkit;

import me.tedesk.bds.configs.Config;
import me.tedesk.bds.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener extends Events {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeathMonitor(PlayerDeathEvent e) {
        // For some reason, creating a new PlayerDeathEvent does not send the death message.
        if (e.getDeathMessage() != null) {
            Bukkit.broadcastMessage(e.getDeathMessage());
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (p.hasPermission(Config.KEEP_XP)) {
            e.setDroppedExp(0);
            e.setKeepLevel(true);
        }

        if (!p.hasPermission(Config.KEEP_XP) && p.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            p.setExp(0);
            p.setLevel(0);
        }
    }
}
