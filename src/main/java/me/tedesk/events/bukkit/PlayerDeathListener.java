package me.tedesk.events.bukkit;

import me.tedesk.configs.Config;
import me.tedesk.events.Listeners;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener extends Listeners {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (p.hasPermission(Config.KEEP_XP) || p.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            e.setDroppedExp(0);
        }
    }
}
