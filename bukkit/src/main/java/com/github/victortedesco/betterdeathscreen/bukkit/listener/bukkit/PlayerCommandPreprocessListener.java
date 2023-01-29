package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().split(" ")[0];

        if (command.equalsIgnoreCase("/bds") || command.equalsIgnoreCase("/betterdeathscreen")) return;
        if (BetterDeathScreenAPI.getPlayerManager().isDead(player)) {
            if (!BetterDeathScreen.getConfiguration().getAllowedCommands().contains(command)) {
                event.setCancelled(true);
                player.sendMessage(BetterDeathScreen.getMessages().getBlockedCommand());
            }
        }
    }
}
