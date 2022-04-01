package me.tedesk.plugin.systems.general;

import me.tedesk.plugin.api.ActionBarAPI;
import me.tedesk.plugin.api.SoundAPI;
import me.tedesk.plugin.configs.Config;
import me.tedesk.plugin.configs.Messages;
import me.tedesk.plugin.BetterDeathScreen;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {

    public static void normal(Player p) {

        new BukkitRunnable() {
            int time = Config.TIME;

            @Override
            public void run() {
                time--;

                if (!p.isOnline()){
                    cancel();
                }
                if (time > 1) {
                    String actionbarplural = Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.PLURAL);
                    actionbarplural = ChatColor.translateAlternateColorCodes('&', actionbarplural);
                    ActionBarAPI.sendActionBar(p, actionbarplural);
                    SoundAPI.sendSound(p, p.getLocation(), Config.SOUND_COUNTDOWN, 3, 1);
                }
                if (time == 1) {
                    String actionbarsingular = Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.SINGULAR);
                    actionbarsingular = ChatColor.translateAlternateColorCodes('&', actionbarsingular);
                    ActionBarAPI.sendActionBar(p, actionbarsingular);
                    SoundAPI.sendSound(p, p.getLocation(), Config.SOUND_COUNTDOWN, 3, 1);
                }
                if (time <= 0) {
                    ActionBarAPI.sendActionBar(p, "§r");
                    double health = p.getMaxHealth();
                    p.setHealth(health);
                    p.setGameMode(GameMode.SURVIVAL);
                    try {
                        Location bedspawn = new Location(p.getBedSpawnLocation().getWorld(), p.getBedSpawnLocation().getX(), p.getBedSpawnLocation().getY(), p.getBedSpawnLocation().getZ(), p.getBedSpawnLocation().getYaw(), p.getBedSpawnLocation().getPitch());
                        p.teleport(bedspawn);
                        PlayerRespawnEvent respawn = new PlayerRespawnEvent(p, bedspawn, true);
                        Bukkit.getPluginManager().callEvent(respawn);
                    } catch (NullPointerException e) {
                        p.teleport(Config.DEFAULT_WORLD_SPAWN);
                        PlayerRespawnEvent respawn = new PlayerRespawnEvent(p, Config.DEFAULT_WORLD_SPAWN, false);
                        Bukkit.getPluginManager().callEvent(respawn);
                    }
                    SoundAPI.sendSound(p, p.getLocation(), Config.SOUND_RESPAWN, 3, 1);
                    p.updateInventory();
                    cancel();
                }
            }
        }.runTaskTimer(BetterDeathScreen.plugin, 20, 20);
    }

    // Esse timer nunca chegará ao fim, somente quando o jogador mudar seu modo de jogo.
    public static void hardcore(Player p) {

        new BukkitRunnable() {

            @Override
            public void run() {

                String actionbarhc = Messages.ACTIONBAR_HC;
                actionbarhc = ChatColor.translateAlternateColorCodes('&', actionbarhc);
                ActionBarAPI.sendActionBar(p, actionbarhc);

                if (!p.isOnline()){
                    cancel();
                }
                // Ao mudar o modo de jogo do jogador, ele renasce.
                if (!(p.getGameMode() == GameMode.SPECTATOR)) {
                    ActionBarAPI.sendActionBar(p, "§r");
                    double health = p.getMaxHealth();
                    p.setHealth(health);
                    p.updateInventory();
                    cancel();
                }
            }
        }.runTaskTimer(BetterDeathScreen.plugin, 20, 20);
    }
}
