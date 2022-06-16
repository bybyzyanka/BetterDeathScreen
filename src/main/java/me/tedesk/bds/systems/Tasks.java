package me.tedesk.bds.systems;

import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.api.ActionBarAPI;
import me.tedesk.bds.api.SoundAPI;
import me.tedesk.bds.api.TitleAPI;
import me.tedesk.bds.configs.Messages;
import me.tedesk.bds.configs.Config;
import me.tedesk.bds.events.bukkit.PlayerTeleportListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Tasks {

    public static void performRespawn(Player p) {
        if (!Bukkit.isHardcore()) {
            Config.DEAD_PLAYERS.remove(p.getName());
            ActionBarAPI.sendActionBar(p, "§r");
            double health = p.getMaxHealth();
            p.setHealth(health);
            p.setFoodLevel(20);
            if (!Config.MOVE_SPECTATOR) {
                p.setWalkSpeed(0.2F);
                p.setFlySpeed(0.1F);
            }
            if (!PlayerTeleportListener.WOULD_TELEPORT.containsKey(p.getName())) {
                if (Config.USE_DEFAULT_WORLD_SPAWN) {
                    if (p.getBedSpawnLocation() == null) {
                        PlayerRespawnEvent non_bed_respawn = new PlayerRespawnEvent(p, Bukkit.getWorlds().get(0).getSpawnLocation(), false);
                        Bukkit.getPluginManager().callEvent(non_bed_respawn);
                        if (Config.USE_SAFE_TELEPORT) {
                            Locations.teleportSafeLocation(p, Bukkit.getWorlds().get(0).getSpawnLocation());
                        }
                        if (!Config.USE_SAFE_TELEPORT) {
                            p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                        }
                    }
                    if (p.getBedSpawnLocation() != null) {
                        PlayerRespawnEvent bed_respawn = new PlayerRespawnEvent(p, p.getBedSpawnLocation(), true);
                        Bukkit.getPluginManager().callEvent(bed_respawn);
                        if (Config.USE_SAFE_TELEPORT) {
                            Locations.teleportSafeLocation(p, p.getBedSpawnLocation());
                        }
                        if (!Config.USE_SAFE_TELEPORT) {
                            p.teleport(p.getBedSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                        }
                    }
                }
                if (!Config.USE_DEFAULT_WORLD_SPAWN) {
                    if (p.getBedSpawnLocation() == null) {
                        if (p.hasPermission(Config.VIP)) {
                            PlayerRespawnEvent non_bed_respawn = new PlayerRespawnEvent(p, Config.VIP_SPAWN, false);
                            Bukkit.getPluginManager().callEvent(non_bed_respawn);
                            p.teleport(Config.VIP_SPAWN, PlayerTeleportEvent.TeleportCause.PLUGIN);
                        }
                        if (!p.hasPermission(Config.VIP)) {
                            PlayerRespawnEvent non_bed_respawn = new PlayerRespawnEvent(p, Config.SPAWN, false);
                            Bukkit.getPluginManager().callEvent(non_bed_respawn);
                            p.teleport(Config.SPAWN, PlayerTeleportEvent.TeleportCause.PLUGIN);
                        }
                    }
                    if (p.getBedSpawnLocation() != null) {
                        PlayerRespawnEvent bed_respawn = new PlayerRespawnEvent(p, p.getBedSpawnLocation(), true);
                        Bukkit.getPluginManager().callEvent(bed_respawn);
                        if (Config.USE_SAFE_TELEPORT) {
                            Locations.teleportSafeLocation(p, p.getBedSpawnLocation());
                        }
                        if (!Config.USE_SAFE_TELEPORT) {
                            p.teleport(p.getBedSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                        }
                    }
                }
            }
            if (PlayerTeleportListener.WOULD_TELEPORT.containsKey(p.getName())) {
                PlayerRespawnEvent queued_teleport = new PlayerRespawnEvent(p, PlayerTeleportListener.TELEPORT_LOCATION.get(p.getName()), false);
                Bukkit.getPluginManager().callEvent(queued_teleport);
                if (Config.USE_SAFE_TELEPORT) {
                    Locations.teleportSafeLocation(p, PlayerTeleportListener.TELEPORT_LOCATION.get(p.getName()));
                }
                if (!Config.USE_SAFE_TELEPORT) {
                    p.teleport(PlayerTeleportListener.TELEPORT_LOCATION.get(p.getName()), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
                PlayerTeleportListener.WOULD_TELEPORT.remove(p.getName());
                PlayerTeleportListener.TELEPORT_LOCATION.remove(p.getName());
            }
            p.setGameMode(GameMode.SURVIVAL);
            SoundAPI.sendSound(p, p.getLocation(), Randomizer.randomSound(Config.SOUND_RESPAWN), Config.SOUND_RESPAWN_VOLUME, Config.SOUND_RESPAWN_PITCH);
            p.updateInventory();
        }
        if (Bukkit.isHardcore()) {
            if (!(p.getGameMode() == GameMode.SPECTATOR)) {
                Config.DEAD_PLAYERS.remove(p.getName());
                ActionBarAPI.sendActionBar(p, "§r");
                double health = p.getMaxHealth();
                p.setHealth(health);
                p.setFoodLevel(20);
                if (Config.USE_SAFE_TELEPORT) {
                    Locations.teleportSafeLocation(p, Bukkit.getWorlds().get(0).getSpawnLocation());
                }
                if (!Config.USE_SAFE_TELEPORT) {
                    p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                }
                p.updateInventory();
            }
        }
    }
    public static void normalTimer(Player p) {
        new BukkitRunnable() {
            int time = Config.TIME;
            @Override
            public void run() {
                time--;

                if (!p.isOnline()){
                    cancel();
                }
                if (p.hasPermission(Config.INSTANT_RESPAWN)) {
                    TitleAPI.sendTitle(p, 1, 20, 1, "", "");
                    ActionBarAPI.sendActionBar(p, "");
                    performRespawn(p);
                    cancel();
                }
                if (time > 1 && !p.hasPermission(Config.INSTANT_RESPAWN)) {
                    String ab_plural = ChatColor.translateAlternateColorCodes('&', Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.PLURAL));
                    ActionBarAPI.sendActionBar(p, ab_plural);
                    SoundAPI.sendSound(p, p.getLocation(), Config.SOUND_COUNTDOWN, Config.SOUND_COUNTDOWN_VOLUME, Config.SOUND_COUNTDOWN_PITCH);
                }
                if (time == 1 && !p.hasPermission(Config.INSTANT_RESPAWN)) {
                    String ab_singular = ChatColor.translateAlternateColorCodes('&', Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.SINGULAR));
                    ActionBarAPI.sendActionBar(p, ab_singular);
                    SoundAPI.sendSound(p, p.getLocation(), Config.SOUND_COUNTDOWN, Config.SOUND_COUNTDOWN_VOLUME, Config.SOUND_COUNTDOWN_PITCH);
                }
                if (time <= 0 && !p.hasPermission(Config.INSTANT_RESPAWN)) {
                    performRespawn(p);
                    cancel();
                }
            }
        }.runTaskTimer(BetterDeathScreen.plugin, 20, 20);
    }

    // Esse temporizador nunca chegará ao fim, somente quando o jogador mudar seu modo de jogo.
    public static void hardcoreTimer(Player p) {
        new BukkitRunnable() {
            @Override
            public void run() {
                String ab_hc = ChatColor.translateAlternateColorCodes('&', Messages.ACTIONBAR_HC);
                ActionBarAPI.sendActionBar(p, ab_hc);

                if (!p.isOnline()) {
                    cancel();
                }
                // Ao mudar o modo de jogo do jogador, ele renasce.
                if (!(p.getGameMode() == GameMode.SPECTATOR)) {
                    performRespawn(p);
                    cancel();
                }
            }
        }.runTaskTimer(BetterDeathScreen.plugin, 20, 20);
    }
}