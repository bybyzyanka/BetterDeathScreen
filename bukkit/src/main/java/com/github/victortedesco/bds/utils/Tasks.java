package com.github.victortedesco.bds.utils;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.api.events.bukkit.PlayerTeleportListener;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Tasks {

    public static void teleportToSpawnPoint(Player p) {
        if (!PlayerTeleportListener.TELEPORT_LOCATION.containsKey(p.getName())) {
            if (Config.USE_DEFAULT_WORLD_SPAWN) {
                if (p.getBedSpawnLocation() == null) {
                    PlayerRespawnEvent non_bed_respawn = new PlayerRespawnEvent(p, Bukkit.getWorlds().get(0).getSpawnLocation(), false);
                    Bukkit.getPluginManager().callEvent(non_bed_respawn);
                    if (Config.USE_SAFE_TELEPORT) {
                        PlayerAPI.teleportSafeLocation(p, Bukkit.getWorlds().get(0).getSpawnLocation());
                    }
                    if (!Config.USE_SAFE_TELEPORT) {
                        p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }
                if (p.getBedSpawnLocation() != null) {
                    PlayerRespawnEvent bed_respawn = new PlayerRespawnEvent(p, p.getBedSpawnLocation(), true);
                    Bukkit.getPluginManager().callEvent(bed_respawn);
                    if (Config.USE_SAFE_TELEPORT) {
                        PlayerAPI.teleportSafeLocation(p, p.getBedSpawnLocation());
                    }
                    if (!Config.USE_SAFE_TELEPORT) {
                        p.teleport(p.getBedSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }
            }
            if (!Config.USE_DEFAULT_WORLD_SPAWN) {
                if (p.getBedSpawnLocation() == null) {
                    if (p.hasPermission(Config.VIP)) {
                        try {
                            PlayerRespawnEvent non_bed_respawn = new PlayerRespawnEvent(p, Config.VIP_SPAWN, false);
                            Bukkit.getPluginManager().callEvent(non_bed_respawn);
                            p.teleport(Config.VIP_SPAWN, PlayerTeleportEvent.TeleportCause.PLUGIN);
                        } catch (Exception e) {
                            PlayerRespawnEvent non_bed_respawn = new PlayerRespawnEvent(p, Bukkit.getWorlds().get(0).getSpawnLocation(), false);
                            Bukkit.getPluginManager().callEvent(non_bed_respawn);
                            if (Config.USE_SAFE_TELEPORT) {
                                PlayerAPI.teleportSafeLocation(p, Bukkit.getWorlds().get(0).getSpawnLocation());
                            }
                            if (!Config.USE_SAFE_TELEPORT) {
                                p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                            }
                            BetterDeathScreen.logger(Messages.SPAWN_ERROR.replace("%player%", p.getName()).replace("%type%", "VIP"));
                        }
                    }
                    if (!p.hasPermission(Config.VIP)) {
                        try {
                            PlayerRespawnEvent non_bed_respawn = new PlayerRespawnEvent(p, Config.SPAWN, false);
                            Bukkit.getPluginManager().callEvent(non_bed_respawn);
                            p.teleport(Config.SPAWN, PlayerTeleportEvent.TeleportCause.PLUGIN);
                        } catch (Exception e) {
                            PlayerRespawnEvent non_bed_respawn = new PlayerRespawnEvent(p, Bukkit.getWorlds().get(0).getSpawnLocation(), false);
                            Bukkit.getPluginManager().callEvent(non_bed_respawn);
                            if (Config.USE_SAFE_TELEPORT) {
                                PlayerAPI.teleportSafeLocation(p, Bukkit.getWorlds().get(0).getSpawnLocation());
                            }
                            if (!Config.USE_SAFE_TELEPORT) {
                                p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                            }
                            BetterDeathScreen.logger(Messages.SPAWN_ERROR.replace("%player%", p.getName()).replace("%type%", "VIP"));
                        }
                    }
                }
                if (p.getBedSpawnLocation() != null) {
                    PlayerRespawnEvent bed_respawn = new PlayerRespawnEvent(p, p.getBedSpawnLocation(), true);
                    Bukkit.getPluginManager().callEvent(bed_respawn);
                    if (Config.USE_SAFE_TELEPORT) {
                        PlayerAPI.teleportSafeLocation(p, p.getBedSpawnLocation());
                    }
                    if (!Config.USE_SAFE_TELEPORT) {
                        p.teleport(p.getBedSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                }
            }
        }
        if (PlayerTeleportListener.TELEPORT_LOCATION.containsKey(p.getName())) {
            PlayerRespawnEvent queued_teleport = new PlayerRespawnEvent(p, PlayerTeleportListener.TELEPORT_LOCATION.get(p.getName()), false);
            Bukkit.getPluginManager().callEvent(queued_teleport);
            if (Config.USE_SAFE_TELEPORT) {
                PlayerAPI.teleportSafeLocation(p, PlayerTeleportListener.TELEPORT_LOCATION.get(p.getName()));
            }
            if (!Config.USE_SAFE_TELEPORT) {
                p.teleport(PlayerTeleportListener.TELEPORT_LOCATION.get(p.getName()), PlayerTeleportEvent.TeleportCause.PLUGIN);
            }
            PlayerTeleportListener.TELEPORT_LOCATION.remove(p.getName());
        }
    }

    @SuppressWarnings("deprecation")
    public static void performRespawn(Player p) {
        String random_respawn_sound = Randomizer.randomSound(Config.SOUND_RESPAWN);

        if (!Bukkit.isHardcore()) {
            Config.DEAD_PLAYERS.remove(p.getName());
            double health = p.getMaxHealth();
            p.setHealth(health);
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
                double health = p.getMaxHealth();
                p.setHealth(health);
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

                    // When chaning the gamemode of the player, he respawns.
                    if (!(p.getGameMode() == GameMode.SPECTATOR)) {
                        performRespawn(p);
                        cancel();
                    }
                }
            }.runTaskTimer(BetterDeathScreen.plugin, 1, 20);
        }

        if (!Bukkit.getServer().isHardcore()) {
            String random_countdown_sound = Randomizer.randomSound(Config.SOUND_COUNTDOWN);
            try {
                if (!random_countdown_sound.contains(".")) {
                    p.playSound(new Location(Bukkit.getWorlds().get(0), 0, 2048, 0), Sound.valueOf(random_countdown_sound), 1, 1);
                }
            } catch (Exception e) {
                BetterDeathScreen.logger(Messages.SOUND_ERROR.replace("%sound%", random_countdown_sound));
            }

            new BukkitRunnable() {
                int time = Config.TIME;

                @Override
                public void run() {
                    time--;

                    if (!p.isOnline()) cancel();

                    if (time == Config.TIME - 1 && p.hasPermission(Config.INSTANT_RESPAWN)) {
                        ActionBar.sendActionBar(p, "");
                        performRespawn(p);
                        cancel();
                    }
                    if (time > 1 && !p.hasPermission(Config.INSTANT_RESPAWN)) {
                        ActionBar.sendActionBar(p, Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.PLURAL));
                        PlayerAPI.playSound(p, random_countdown_sound, Config.SOUND_COUNTDOWN_VOLUME, Config.SOUND_COUNTDOWN_PITCH);
                    }
                    if (time == 1 && !p.hasPermission(Config.INSTANT_RESPAWN)) {
                        ActionBar.sendActionBar(p, Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.SINGULAR));
                        PlayerAPI.playSound(p, random_countdown_sound, Config.SOUND_COUNTDOWN_VOLUME, Config.SOUND_COUNTDOWN_PITCH);
                    }
                    if (time <= 0 && !p.hasPermission(Config.INSTANT_RESPAWN)) {
                        performRespawn(p);
                        cancel();
                    }
                }
            }.runTaskTimer(BetterDeathScreen.plugin, 20, 20);
        }
    }
}