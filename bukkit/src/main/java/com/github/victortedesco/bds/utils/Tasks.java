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
        PlayerRespawnEvent respawn = null;
        if (player.getBedSpawnLocation() == null) {
            respawn = new PlayerRespawnEvent(player, Bukkit.getWorlds().get(0).getSpawnLocation(), false);
            if (!Config.USE_DEFAULT_WORLD_SPAWN) {
                if (player.hasPermission(Config.VIP)) {
                    try {
                        respawn.setRespawnLocation(Config.VIP_SPAWN);
                    } catch (Exception e) {
                        BetterDeathScreen.sendConsoleMessage(Messages.SPAWN_ERROR.replace("%player%", player.getName()).replace("%type%", "VIP"));
                    }
                }
                if (!player.hasPermission(Config.VIP)) {
                    try {
                        respawn.setRespawnLocation(Config.SPAWN);
                    } catch (Exception e) {
                        BetterDeathScreen.sendConsoleMessage(Messages.SPAWN_ERROR.replace("%player%", player.getName()).replace("%type%", "Normal"));
                    }
                }
            }
        }
        if (player.getBedSpawnLocation() != null) {
            respawn = new PlayerRespawnEvent(player, player.getBedSpawnLocation(), true);
        }
        if (PlayerTeleportListener.TELEPORT_LOCATION.containsKey(player.getName())) {
            respawn = new PlayerRespawnEvent(player, PlayerTeleportListener.TELEPORT_LOCATION.get(player.getName()), false);
            PlayerTeleportListener.TELEPORT_LOCATION.remove(player.getName());
        }
        assert respawn != null;
        Bukkit.getPluginManager().callEvent(respawn);
        if (Config.USE_SAFE_TELEPORT) {
            PlayerAPI.teleportSafeLocation(player, respawn.getRespawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
        if (!Config.USE_SAFE_TELEPORT) {
            player.teleport(respawn.getRespawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    public static void performRespawn(Player player) {
        String random_respawn_sound = Randomizer.randomSound(Config.SOUND_RESPAWN);

        if (!player.getWorld().isHardcore()) {
            Config.DEAD_PLAYERS.remove(player.getName());
            double max_health = 0;
            if (BetterDeathScreen.getVersion() == Version.v1_8) max_health = player.getMaxHealth();
            if (BetterDeathScreen.getVersion() != Version.v1_8)
                max_health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            player.setHealth(max_health);
            player.setFoodLevel(20);
            if (player.getGameMode() == GameMode.SPECTATOR) player.setSpectatorTarget(null);
            Titles.sendTitle(player, 1, 1, 1, "", "");
            ActionBar.sendActionBar(player, "");
            if (!Config.MOVE_SPECTATOR) {
                player.setWalkSpeed(0.2F);
                player.setFlySpeed(0.1F);
            }
            teleportToSpawnPoint(player);
            PlayerAPI.playSound(player, random_respawn_sound, Config.SOUND_RESPAWN_VOLUME, Config.SOUND_RESPAWN_PITCH, true);
            player.setGameMode(Bukkit.getServer().getDefaultGameMode());
            player.updateInventory();
        }

        if (player.getWorld().isHardcore()) {
            if (player.getGameMode() != GameMode.SPECTATOR) {
                Config.DEAD_PLAYERS.remove(player.getName());
                Titles.sendTitle(player, 1, 1, 1, "", "");
                ActionBar.sendActionBar(player, "");
                if (!Config.MOVE_SPECTATOR) {
                    player.setWalkSpeed(0.2F);
                    player.setFlySpeed(0.1F);
                }
                double max_health = 0;
                if (BetterDeathScreen.getVersion() == Version.v1_8) max_health = player.getMaxHealth();
                if (BetterDeathScreen.getVersion() != Version.v1_8)
                    max_health = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                player.setHealth(max_health);
                player.setFoodLevel(20);
                teleportToSpawnPoint(player);
                player.updateInventory();
            }
        }
    }

    public static void startTimer(Player player) {
        if (!player.getWorld().isHardcore()) {
            String random_countdown_sound = Randomizer.randomSound(Config.SOUND_COUNTDOWN);
            PlayerAPI.playSound(player, random_countdown_sound, 0, 0, true);

            new BukkitRunnable() {
                int time = Config.TIME;

                @Override
                public void run() {
                    time--;

                    if (!player.isOnline()) cancel();

                    if (player.hasPermission(Config.INSTANT_RESPAWN)) {
                        if (time <= Config.TIME) {
                            ActionBar.sendActionBar(player, "");
                            performRespawn(player);
                            cancel();
                        }
                    }
                    if (!player.hasPermission(Config.INSTANT_RESPAWN)) {
                        if (time > 1) {
                            ActionBar.sendActionBar(player, Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.PLURAL));
                            PlayerAPI.playSound(player, random_countdown_sound, Config.SOUND_COUNTDOWN_VOLUME, Config.SOUND_COUNTDOWN_PITCH, false);
                        }
                        if (time == 1) {
                            ActionBar.sendActionBar(player, Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.SINGULAR));
                            PlayerAPI.playSound(player, random_countdown_sound, Config.SOUND_COUNTDOWN_VOLUME, Config.SOUND_COUNTDOWN_PITCH, false);
                        }
                        if (time <= 0) {
                            performRespawn(player);
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(BetterDeathScreen.getInstance(), 20, 20);
        }

        if (player.getWorld().isHardcore()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!player.isOnline()) cancel();

                    ActionBar.sendActionBar(player, Messages.ACTIONBAR_HC);

                    // When changing the gamemode of the player, he respawns.
                    if (player.getGameMode() != GameMode.SPECTATOR) {
                        performRespawn(player);
                        cancel();
                    }
                }
            }.runTaskTimer(BetterDeathScreen.getInstance(), 20, 20);
        }
    }
}