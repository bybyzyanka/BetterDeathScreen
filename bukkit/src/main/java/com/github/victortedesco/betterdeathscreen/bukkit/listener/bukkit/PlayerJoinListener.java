package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.cryptomorin.xseries.ReflectionUtils;
import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import com.github.victortedesco.betterdeathscreen.bukkit.configuration.BukkitConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BukkitConfig config = BetterDeathScreen.getConfiguration();

        if (Bukkit.isHardcore()) {
            if ((player.getGameMode() == GameMode.SPECTATOR && !player.hasPermission(config.getAdminPermission())
                    || BetterDeathScreenAPI.getPlayerManager().isDead(player))) {
                Bukkit.getScheduler().runTaskLater(BetterDeathScreen.getInstance(),
                        () -> player.setGameMode(GameMode.SPECTATOR), 2L); //MV Compatibility
                BetterDeathScreenAPI.getPlayerManager().getDeadPlayers().add(player);
                BetterDeathScreen.getRespawnTasks().startCountdown(player);
            }
        }
        if (player.getBedSpawnLocation() != null) {
            if (player.getBedSpawnLocation().getWorld().getEnvironment() == World.Environment.THE_END || (ReflectionUtils.VER < 16 && player.getBedSpawnLocation().getWorld().getEnvironment() == World.Environment.NETHER)) {
                player.setBedSpawnLocation(null);
            }
        }
    }
}
