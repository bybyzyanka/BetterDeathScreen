package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;
import java.util.Set;

public class PlayerCommandListener implements Listener {

    public static Set<String> DISABLE_COMMANDS_IMUNE = new HashSet<>();
    Set<String> MESSAGE_DELAY = new HashSet<>();

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (Config.DEAD_PLAYERS.contains(player.getName()) && !Config.ALLOW_COMMANDS_WHILE_DEAD && !DISABLE_COMMANDS_IMUNE.contains(player.getName())) {
            event.setCancelled(true);
            if (!MESSAGE_DELAY.contains(player.getName())) {
                MESSAGE_DELAY.add(player.getName());
                player.sendMessage(Messages.COMMAND_BLOCKED);
                Bukkit.getScheduler().runTaskLaterAsynchronously(BetterDeathScreen.getInstance(),
                        () -> MESSAGE_DELAY.remove(player.getName()), 20 * 3);
            }
        }
    }
}
