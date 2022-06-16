package me.tedesk.bds.events.bukkit;

import me.tedesk.bds.configs.Messages;
import me.tedesk.bds.configs.Config;
import me.tedesk.bds.events.Listeners;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandListener extends Listeners {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        if (Config.DEAD_PLAYERS.contains(p.getName()) && !Config.ALLOW_COMMANDS_WHILE_DEAD) {
            e.setCancelled(true);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.COMMAND_BLOCKED));
        }
    }
}
