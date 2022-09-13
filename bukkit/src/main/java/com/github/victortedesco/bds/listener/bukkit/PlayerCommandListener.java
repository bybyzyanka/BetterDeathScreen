package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.listener.Events;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerCommandListener extends Events {

    List<String> COMMAND_MESSAGE_CD = new ArrayList<>();

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();

        if (Config.DEAD_PLAYERS.contains(p.getName()) && !Config.ALLOW_COMMANDS_WHILE_DEAD) {
            e.setCancelled(true);
            if (!COMMAND_MESSAGE_CD.contains(p.getName())) {
                COMMAND_MESSAGE_CD.add(p.getName());
                p.sendMessage(Messages.COMMAND_BLOCKED);
                Bukkit.getScheduler().runTaskLaterAsynchronously(BetterDeathScreen.getInstance(),
                        () -> COMMAND_MESSAGE_CD.remove(p.getName()), 20 * 3);
            }
        }
    }
}
