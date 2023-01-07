package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.api.utils.Version;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (BetterDeathScreenAPI.getPlayerManager().isDead(player) && BetterDeathScreenAPI.getPlayerManager().isHardcore(player)) {
            BetterDeathScreenAPI.getPlayerManager().getDeadPlayers().add(player);
            BetterDeathScreen.getRespawnTasks().startCountdown(player);
        }
        if (player.getBedSpawnLocation() != null) {
            if (player.getBedSpawnLocation().getWorld().getEnvironment() == World.Environment.THE_END || (!Version.isVeryNewVersion() && player.getBedSpawnLocation().getWorld().getEnvironment() == World.Environment.NETHER)) {
                player.setBedSpawnLocation(null);
            }
        }
    }
}
