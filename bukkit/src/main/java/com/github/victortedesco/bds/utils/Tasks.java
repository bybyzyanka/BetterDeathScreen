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
    public static void teleportToSpawnPoint(Player p) {
        if (p.getBedSpawnLocation() == null) {
            PlayerRespawnEvent non_bed_respawn = new PlayerRespawnEvent(p, p.getWorld().getSpawnLocation(), false);
            if (!PlayerTeleportListener.TELEPORT_LOCATION.containsKey(p.getName())) {
                if (Config.USE_DEFAULT_WORLD_SPAWN) {
                    non_bed_respawn.setRespawnLocation(Bukkit.getWorlds().get(0).getSpawnLocation());
                }
                if (!Config.USE_DEFAULT_WORLD_SPAWN) {
                    if (p.hasPermission(Config.VIP)) {
                        try {
                            non_bed_respawn.setRespawnLocation(Config.VIP_SPAWN);
                        } catch (Exception e) {
                            non_bed_respawn.setRespawnLocation(Bukkit.getWorlds().get(0).getSpawnLocation());
                            BetterDeathScreen.logger(Messages.SPAWN_ERROR.replace("%player%", p.getName()).replace("%type%", "VIP"));
                        }
                    }
                    if (!p.hasPermission(Config.VIP)) {
                        try {
                            non_bed_respawn.setRespawnLocation(Config.SPAWN);
                        } catch (Exception e) {
                            non_bed_respawn.setRespawnLocation(Bukkit.getWorlds().get(0).getSpawnLocation());
                            BetterDeathScreen.logger(Messages.SPAWN_ERROR.replace("%player%", p.getName()).replace("%type%", "VIP"));
                        }
                    }
                }
            }
            if (PlayerTeleportListener.TELEPORT_LOCATION.containsKey(p.getName())) {
                non_bed_respawn.setRespawnLocation(PlayerTeleportListener.TELEPORT_LOCATION.get(p.getName()));
                PlayerTeleportListener.TELEPORT_LOCATION.remove(p.getName());
            }
            Bukkit.getPluginManager().callEvent(non_bed_respawn);
            if (Config.USE_SAFE_TELEPORT) {
                PlayerAPI.teleportSafeLocation(p, non_bed_respawn.getRespawnLocation());
            }
            if (!Config.USE_SAFE_TELEPORT) {
                p.teleport(non_bed_respawn.getRespawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }
        if (p.getBedSpawnLocation() != null) {
            PlayerRespawnEvent bed_respawn = new PlayerRespawnEvent(p, p.getBedSpawnLocation(), true);
            if (PlayerTeleportListener.TELEPORT_LOCATION.containsKey(p.getName())) {
                bed_respawn.setRespawnLocation(PlayerTeleportListener.TELEPORT_LOCATION.get(p.getName()));
                PlayerTeleportListener.TELEPORT_LOCATION.remove(p.getName());
            }
            Bukkit.getPluginManager().callEvent(bed_respawn);
            if (Config.USE_SAFE_TELEPORT) {
                PlayerAPI.teleportSafeLocation(p, bed_respawn.getRespawnLocation());
            }
            if (!Config.USE_SAFE_TELEPORT) {
                p.teleport(bed_respawn.getRespawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
        }
    }

    @SuppressWarnings("deprecation")
    public static void performRespawn(Player p) {
        String random_respawn_sound = Randomizer.randomSound(Config.SOUND_RESPAWN);

        if (!Bukkit.isHardcore()) {
            Config.DEAD_PLAYERS.remove(p.getName());
            double max_health = 0;
            if (BetterDeathScreen.version == Version.v1_8) {
                max_health = p.getMaxHealth();
            }
            if (BetterDeathScreen.version != Version.v1_8) {
                max_health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            }
            p.setHealth(max_health);
            p.setFoodLevel(20);
            if (p.getGameMode() == GameMode.SPECTATOR) p.setSpectatorTarget(null);
            Titles.sendTitle(p, 1, 1, 1, "", "");
            ActionBar.sendActionBar(p, "");
            if (!Config.MOVE_SPECTATOR) {
                p.setWalkSpeed(0.2F);
                p.setFlySpeed(0.1F);
            }
            teleportToSpawnPoint(p);
            PlayerAPI.playSound(p, random_respawn_sound, Config.SOUND_RESPAWN_VOLUME, Config.SOUND_RESPAWN_PITCH);
            p.setGameMode(GameMode.SURVIVAL);
            p.updateInventory();
        }

        if (Bukkit.isHardcore()) {
            if (!(p.getGameMode() == GameMode.SPECTATOR)) {
                Config.DEAD_PLAYERS.remove(p.getName());
                ActionBar.sendActionBar(p, "");
                double max_health = 0;
                if (BetterDeathScreen.version == Version.v1_8) {
                    max_health = p.getMaxHealth();
                }
                if (BetterDeathScreen.version != Version.v1_8) {
                    max_health = p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
                }
                p.setHealth(max_health);
                p.setFoodLevel(20);
                teleportToSpawnPoint(p);
                p.updateInventory();
            }
        }
    }

    public static void startTimer(Player p) {
        if (Bukkit.getServer().isHardcore()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!p.isOnline()) cancel();

                    ActionBar.sendActionBar(p, Messages.ACTIONBAR_HC);

                    // When changing the gamemode of the player, he respawns.
                    if (!(p.getGameMode() == GameMode.SPECTATOR)) {
                        performRespawn(p);
                        cancel();
                    }
                }
            }.runTaskTimer(BetterDeathScreen.plugin, 20, 20);
        }

        if (!Bukkit.getServer().isHardcore()) {
            String random_countdown_sound = Randomizer.randomSound(Config.SOUND_COUNTDOWN);
            PlayerAPI.playSound(p, random_countdown_sound, 0, 0);

            new BukkitRunnable() {
                int time = Config.TIME;

                @Override
                public void run() {
                    time--;

                    if (!p.isOnline()) cancel();

                    if (p.hasPermission(Config.INSTANT_RESPAWN)) {
                        if (time <= Config.TIME) {
                            ActionBar.sendActionBar(p, "");
                            performRespawn(p);
                            cancel();
                        }
                    }
                    if (!p.hasPermission(Config.INSTANT_RESPAWN)) {
                        if (time > 1) {
                            ActionBar.sendActionBar(p, Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.PLURAL));
                            PlayerAPI.playSoundNoExceptions(p, random_countdown_sound, Config.SOUND_COUNTDOWN_VOLUME, Config.SOUND_COUNTDOWN_PITCH);
                        }
                        if (time == 1) {
                            ActionBar.sendActionBar(p, Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.SINGULAR));
                            PlayerAPI.playSoundNoExceptions(p, random_countdown_sound, Config.SOUND_COUNTDOWN_VOLUME, Config.SOUND_COUNTDOWN_PITCH);
                        }
                        if (time <= 0) {
                            performRespawn(p);
                            cancel();
                        }
                    }
                }
            }.runTaskTimer(BetterDeathScreen.plugin, 20, 20);
        }
    }
}