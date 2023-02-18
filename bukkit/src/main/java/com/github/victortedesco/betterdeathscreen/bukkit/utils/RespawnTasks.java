package com.github.victortedesco.betterdeathscreen.bukkit.utils;

import com.cryptomorin.xseries.ReflectionUtils;
import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.api.configuration.Config;
import com.github.victortedesco.betterdeathscreen.api.configuration.Messages;
import com.github.victortedesco.betterdeathscreen.api.manager.PlayerManager;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import com.github.victortedesco.betterdeathscreen.bukkit.configuration.BukkitConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public final class RespawnTasks {

    public void startCountdown(Player player) {
        Config config = BetterDeathScreen.getConfiguration();
        Messages messages = BetterDeathScreen.getMessages();

        new BukkitRunnable() {
            final String randomCountdownSound = BetterDeathScreenAPI.getRandomizer().getRandomItemFromList(config.getCountdownSounds());
            int time = config.getRespawnTime();

            @Override
            public void run() {
                time--;
                if (!player.isOnline() || !BetterDeathScreenAPI.getPlayerManager().isDead(player)) {
                    cancel();
                    return;
                }
                if (!Bukkit.isHardcore()) {
                    if (player.hasPermission(config.getInstantRespawnPermission())) time = 0;
                    if (time > 1) {
                        BetterDeathScreenAPI.getPlayerManager().sendCustomMessage(player, null, config.getCountdownMessageType(), messages.getNonHardcoreCountdown().replace("%time%", time + messages.getTimePlural()), 1);
                        BetterDeathScreenAPI.getPlayerManager().playSound(player, randomCountdownSound, false);
                    }
                    if (time == 1) {
                        BetterDeathScreenAPI.getPlayerManager().sendCustomMessage(player, null, config.getCountdownMessageType(), messages.getNonHardcoreCountdown().replace("%time%", time + messages.getTimeSingular()), 1);
                        BetterDeathScreenAPI.getPlayerManager().playSound(player, randomCountdownSound, false);
                    }
                    if (time <= 0) {
                        BetterDeathScreen.getRespawnTasks().performRespawn(player, false);
                        cancel();
                    }
                }
                if (Bukkit.isHardcore()) {
                    // When changing the gamemode of the player, he respawns.
                    if (player.getGameMode() != GameMode.SPECTATOR) {
                        performRespawn(player, true);
                        cancel();
                        return;
                    }
                    if (config.getCountdownMessageType().equalsIgnoreCase("ACTIONBAR") && time <= 0) {
                        BetterDeathScreenAPI.getPlayerManager().sendCustomMessage(player, null, "ACTIONBAR", messages.getHardcoreCountdown(), 0);
                    }
                    if (!config.getCountdownMessageType().equalsIgnoreCase("ACTIONBAR") && time == 0) {
                        BetterDeathScreenAPI.getPlayerManager().sendCustomMessage(player, null, config.getCountdownMessageType(), messages.getHardcoreCountdown(), 86400);
                    }
                }
            }
        }.runTaskTimer(BetterDeathScreen.getInstance(), 20L, 20L);
    }

    public void sendPlayerRespawnEvent(Player player) {
        PlayerRespawnEvent playerRespawnEvent;
        Location respawnLocation = Bukkit.getWorlds().get(0).getSpawnLocation();
        boolean bedSpawn = false;
        boolean anchorSpawn = false;

        if (player.getBedSpawnLocation() != null) {
            respawnLocation = player.getBedSpawnLocation();
            bedSpawn = true;
            if (player.getBedSpawnLocation().getWorld().getEnvironment() == World.Environment.NETHER) {
                bedSpawn = false;
                anchorSpawn = true;
            }
        }
        if (ReflectionUtils.VER > 15)
            playerRespawnEvent = new PlayerRespawnEvent(player, respawnLocation, bedSpawn, anchorSpawn);
        else playerRespawnEvent = new PlayerRespawnEvent(player, respawnLocation, bedSpawn);

        Bukkit.getPluginManager().callEvent(playerRespawnEvent);
    }

    public void performRespawn(Player player, boolean forceRespawn) {
        if (player == null) return;
        BukkitConfig config = BetterDeathScreen.getConfiguration();
        List<String> respawnSounds = config.getRespawnSounds();
        PlayerManager playerManager = BetterDeathScreenAPI.getPlayerManager();

        if (Bukkit.isHardcore() && !forceRespawn) return;
        if (playerManager.isDead(player) || forceRespawn) {
            playerManager.getDeadPlayers().remove(player);
            player.setGameMode(Bukkit.getDefaultGameMode());
            BetterDeathScreen.getDeathTasks().changeAttributes(player);
            sendPlayerRespawnEvent(player);
            playerManager.playSound(player, BetterDeathScreenAPI.getRandomizer().getRandomItemFromList(respawnSounds), false);
        }
    }
}
