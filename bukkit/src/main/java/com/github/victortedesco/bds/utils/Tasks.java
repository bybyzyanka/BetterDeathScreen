package com.github.victortedesco.bds.utils;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.listener.bukkit.PlayerTeleportListener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Tasks {

    @SuppressWarnings("deprecation")
    public static void teleportToSpawnPoint(Player player) {
        PlayerRespawnEvent playerRespawnEvent = null;
        if (player.getBedSpawnLocation() == null) {
            playerRespawnEvent = new PlayerRespawnEvent(player, Bukkit.getWorlds().get(0).getSpawnLocation(), false);
            if (!Config.USE_DEFAULT_WORLD_SPAWN) {
                if (player.hasPermission(Config.VIP)) {
                    try {
                        playerRespawnEvent.setRespawnLocation(Config.VIP_SPAWN);
                    } catch (Exception exception) {
                        BetterDeathScreen.sendConsoleMessage(Messages.SPAWN_ERROR.replace("%player%", player.getName()).replace("%type%", "VIP"));
                    }
                }
                if (!player.hasPermission(Config.VIP)) {
                    try {
                        playerRespawnEvent.setRespawnLocation(Config.SPAWN);
                    } catch (Exception exception) {
                        BetterDeathScreen.sendConsoleMessage(Messages.SPAWN_ERROR.replace("%player%", player.getName()).replace("%type%", "Normal"));
                    }
                }
            }
        }
        if (player.getBedSpawnLocation() != null) {
            playerRespawnEvent = new PlayerRespawnEvent(player, player.getBedSpawnLocation(), true);
        }
        if (PlayerTeleportListener.TELEPORT_LOCATION.containsKey(player.getName())) {
            playerRespawnEvent = new PlayerRespawnEvent(player, PlayerTeleportListener.TELEPORT_LOCATION.get(player.getName()), false);
            PlayerTeleportListener.TELEPORT_LOCATION.remove(player.getName());
        }
        assert playerRespawnEvent != null;
        Bukkit.getPluginManager().callEvent(playerRespawnEvent);
        if (Config.USE_SAFE_TELEPORT && player.getBedSpawnLocation() == null) {
            PlayerUtils.teleportSafeLocation(player, playerRespawnEvent.getRespawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
        if (!Config.USE_SAFE_TELEPORT || player.getBedSpawnLocation() != null) {
            player.teleport(playerRespawnEvent.getRespawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    public static void performRespawn(Player player) {
        if (!PlayerUtils.isHardcore(player) || (PlayerUtils.isHardcore(player) && player.getGameMode() != GameMode.SPECTATOR)) {
            Config.DEAD_PLAYERS.remove(player.getName());
            double maxHealth = 0;
            if (BetterDeathScreen.getVersion() == Version.v1_8) maxHealth = player.getMaxHealth();
            if (BetterDeathScreen.getVersion() != Version.v1_8)
                maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            player.setHealth(maxHealth);
            player.setRemainingAir(player.getMaximumAir());
            player.setFoodLevel(20);
            if (player.getGameMode() == GameMode.SPECTATOR) player.setSpectatorTarget(null);
            Titles.sendTitle(player, 1, 1, 1, "", "");
            ActionBar.sendActionBar(player, "");
            if (!Config.MOVE_SPECTATOR) {
                player.setWalkSpeed(0.2F);
                player.setFlySpeed(0.1F);
            }
            teleportToSpawnPoint(player);
            PlayerUtils.playRandomSound(player, Config.SOUND_RESPAWN, true, false);
            if (!PlayerUtils.isHardcore(player)) player.setGameMode(Bukkit.getServer().getDefaultGameMode());
            player.updateInventory();
        }
    }

    public static void startTimer(Player player) {
        String randomSound = Randomizer.getRandomSound(Config.SOUND_COUNTDOWN);
        if (!PlayerUtils.isHardcore(player)) PlayerUtils.playSound(player, randomSound, true, true);

        new BukkitRunnable() {
            int time = Config.TIME;

            @Override
            public void run() {
                if (!player.isOnline()) cancel();
                if (!PlayerUtils.isHardcore(player)) {
                    time--;

                    if (player.hasPermission(Config.INSTANT_RESPAWN)) time = 0;

                    if (time > 1) {
                        ActionBar.sendActionBar(player, Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.PLURAL));
                        PlayerUtils.playSound(player, randomSound, false, false);
                    }
                    if (time == 1) {
                        ActionBar.sendActionBar(player, Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.SINGULAR));
                        PlayerUtils.playSound(player, randomSound, false, false);
                    }
                    if (time <= 0) {
                        performRespawn(player);
                        cancel();
                    }
                }
                if (PlayerUtils.isHardcore(player)) {
                    ActionBar.sendActionBar(player, Messages.ACTIONBAR_HARDCORE);

                    // When changing the gamemode of the player, he respawns.
                    if (player.getGameMode() != GameMode.SPECTATOR) {
                        performRespawn(player);
                        cancel();
                    }
                }
            }
        }.runTaskTimer(BetterDeathScreen.getInstance(), 20, 20);
    }
}