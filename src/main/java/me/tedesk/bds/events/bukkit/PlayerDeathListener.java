package me.tedesk.bds.events.bukkit;

import me.tedesk.bds.configs.Config;
import me.tedesk.bds.events.Events;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener extends Events {

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (p.hasPermission(Config.KEEP_XP)) {
            e.setDroppedExp(0);
            e.setKeepLevel(true);
        }

        if (!p.hasPermission(Config.KEEP_XP) && p.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            e.setKeepLevel(false);
            e.setNewExp(0);
            e.setNewLevel(0);
            p.setLevel(0);
            p.setExp(0);
        }
    }
}
