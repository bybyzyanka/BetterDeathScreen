package me.tedesk.systems;

import me.tedesk.BetterDeathScreen;
import me.tedesk.api.ActionBarAPI;
import me.tedesk.api.SoundAPI;
import me.tedesk.configs.Config;
import me.tedesk.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Tasks {

    public static void normalTimer(Player p) {

        new BukkitRunnable() {
            int time = Config.TIME;

            @Override
            public void run() {
                time--;

                if (!p.isOnline()){
                    cancel();
                }
                if (time > 1) {
                    String ab_plural = Messages.ACTIONBAR_DEATH.replace("&", "§").replace("%time%", time + Messages.PLURAL);
                    ActionBarAPI.sendActionBar(p, ab_plural);
                    SoundAPI.sendSound(p, p.getLocation(), Config.SOUND_COUNTDOWN, Config.SOUND_COUNTDOWN_VOLUME, Config.SOUND_COUNTDOWN_PITCH);
                }
                if (time == 1) {
                    String ab_singular = Messages.ACTIONBAR_DEATH.replace("&", "§").replace("%time%", time + Messages.SINGULAR);
                    ActionBarAPI.sendActionBar(p, ab_singular);
                    SoundAPI.sendSound(p, p.getLocation(), Config.SOUND_COUNTDOWN, Config.SOUND_COUNTDOWN_VOLUME, Config.SOUND_COUNTDOWN_PITCH);
                }
                if (time <= 0) {
                    Config.DEAD_PLAYERS.remove(p.getName());
                    ActionBarAPI.sendActionBar(p, "§r");
                    double health = p.getMaxHealth();
                    p.setHealth(health);
                    p.setFoodLevel(20);
                    p.setGameMode(GameMode.SURVIVAL);
                    if (p.getBedSpawnLocation() == null) {
                        PlayerRespawnEvent world_respawn = new PlayerRespawnEvent(p, Bukkit.getWorlds().get(0).getSpawnLocation(), false);
                        Bukkit.getPluginManager().callEvent(world_respawn);
                        p.teleport(Bukkit.getWorlds().get(0).getSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                    if (p.getBedSpawnLocation() != null) {
                        PlayerRespawnEvent bed_respawn = new PlayerRespawnEvent(p, p.getBedSpawnLocation(), true);
                        Bukkit.getPluginManager().callEvent(bed_respawn);
                        p.teleport(p.getBedSpawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                    }
                    SoundAPI.sendSound(p, p.getLocation(), Config.SOUND_RESPAWN, Config.SOUND_RESPAWN_VOLUME, Config.SOUND_RESPAWN_PITCH);
                    p.updateInventory();
                    cancel();
                }
            }
        }.runTaskTimer(BetterDeathScreen.plugin, 20, 20);
    }

    // Esse timer nunca chegará ao fim, somente quando o jogador mudar seu modo de jogo.
    public static void hardcoreTimer(Player p) {

        new BukkitRunnable() {

            @Override
            public void run() {

                String ab_hc = Messages.ACTIONBAR_HC.replace("&", "§");
                ActionBarAPI.sendActionBar(p, ab_hc);

                if (!p.isOnline()) {
                    cancel();
                }
                // Ao mudar o modo de jogo do jogador, ele renasce.
                if (!(p.getGameMode() == GameMode.SPECTATOR)) {
                    Config.DEAD_PLAYERS.remove(p.getName());
                    ActionBarAPI.sendActionBar(p, "§r");
                    double health = p.getMaxHealth();
                    p.setHealth(health);
                    p.setFoodLevel(20);
                    p.updateInventory();
                    cancel();
                }
            }
        }.runTaskTimer(BetterDeathScreen.plugin, 20, 20);
    }
}