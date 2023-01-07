package com.github.victortedesco.betterdeathscreen.bukkit.utils;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.api.configuration.Config;
import com.github.victortedesco.betterdeathscreen.api.configuration.Messages;
import com.github.victortedesco.betterdeathscreen.api.manager.PlayerManager;
import com.github.victortedesco.betterdeathscreen.api.utils.Version;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import com.github.victortedesco.betterdeathscreen.bukkit.configuration.BukkitConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnTasks {

    public void startCountdown(Player player) {
        Config config = BetterDeathScreen.getConfiguration();
        Messages messages = BetterDeathScreen.getMessages();

        new BukkitRunnable() {
            final String randomSound = BetterDeathScreenAPI.getRandomizer().getRandomItemFromList(config.getCountdownSounds());
            int time = config.getRespawnTime();

            @Override
            public void run() {
                if (!player.isOnline()) cancel();
                if (!BetterDeathScreenAPI.getPlayerManager().isHardcore(player)) {
                    time--;
                    if (player.hasPermission(config.getInstantRespawnPermission())) time = 0;
                    if (time > 1) {
                        BetterDeathScreenAPI.getPlayerManager().sendCustomMessage(player, null, config.getNonHardcoreCountdownMessageType(), messages.getNonHardcoreCountdown().replace("%time%", time + messages.getTimePlural()), 1);
                        BetterDeathScreenAPI.getPlayerManager().playSound(player, randomSound, false, false);
                    }
                    if (time == 1) {
                        BetterDeathScreenAPI.getPlayerManager().sendCustomMessage(player, null, config.getNonHardcoreCountdownMessageType(), messages.getNonHardcoreCountdown().replace("%time%", time + messages.getTimeSingular()), 1);
                        BetterDeathScreenAPI.getPlayerManager().playSound(player, randomSound, false, false);
                    }
                    if (time <= 0) {
                        BetterDeathScreen.getRespawnTasks().performRespawn(player, false);
                        cancel();
                    }
                }
                if (BetterDeathScreenAPI.getPlayerManager().isHardcore(player)) {
                    if (!config.getHardcoreCountdownMessageType().equalsIgnoreCase("CHAT")) {
                        BetterDeathScreenAPI.getPlayerManager().sendCustomMessage(player, null, config.getHardcoreCountdownMessageType(), messages.getHardcoreCountdown(), 1);
                    }
                    // When changing the gamemode of the player, he respawns.
                    if (player.getGameMode() != GameMode.SPECTATOR) {
                        performRespawn(player, false);
                        cancel();
                    }
                }
            }
        }.runTaskTimer(BetterDeathScreen.getInstance(), 20L, 20L);
        if (BetterDeathScreenAPI.getPlayerManager().isHardcore(player)) {
            if (config.getHardcoreCountdownMessageType().equalsIgnoreCase("CHAT")) {
                BetterDeathScreenAPI.getPlayerManager().sendCustomMessage(player, null, "CHAT", messages.getHardcoreCountdown(), 0);
            }
        }
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
        if (Version.isVeryNewVersion())
            playerRespawnEvent = new PlayerRespawnEvent(player, respawnLocation, bedSpawn, anchorSpawn);
        else playerRespawnEvent = new PlayerRespawnEvent(player, respawnLocation, bedSpawn);

        Bukkit.getPluginManager().callEvent(playerRespawnEvent);
    }

    public void performRespawn(Player player, boolean forceRespawn) {
        if (player == null) return;
        PlayerManager playerManager = BetterDeathScreenAPI.getPlayerManager();
        BukkitConfig config = BetterDeathScreen.getConfiguration();

        if (playerManager.isHardcore(player) && !forceRespawn) return;
        if (playerManager.isDead(player)) {
            playerManager.getDeadPlayers().remove(player);
            player.setGameMode(Bukkit.getDefaultGameMode());
            player.setHealth(playerManager.getMaxHealth(player));
            player.setRemainingAir(20);
            this.sendPlayerRespawnEvent(player);
            playerManager.playSound(player, config.getRespawnSounds(), true, false);
        }
    }
}
