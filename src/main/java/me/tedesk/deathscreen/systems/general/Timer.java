package me.tedesk.deathscreen.systems.general;

import me.tedesk.deathscreen.api.ActionBarAPI;
import me.tedesk.deathscreen.configs.Config;
import me.tedesk.deathscreen.configs.Messages;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import static me.tedesk.deathscreen.BetterDeathScreen.plugin;

public class Timer {

    public static void normal(Player p) {

        new BukkitRunnable() {
            int time = Config.TIME;

            @Override
            public void run() {
                time--;

                String actionbarplural = Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.PLURAL);
                actionbarplural = ChatColor.translateAlternateColorCodes('&', actionbarplural);

                ActionBarAPI.sendActionBar(p, actionbarplural);

                if (!p.isOnline()){
                    cancel();
                }

                if (time > 0) {
                    p.playSound(p.getLocation(), Sound.valueOf(Config.COUNTDOWN), 3.0F, 1.0F);
                }

                if (time == 1) {
                    String actionbarsingular = Messages.ACTIONBAR_DEATH.replace("%time%", time + Messages.SINGULAR);
                    actionbarsingular = ChatColor.translateAlternateColorCodes('&', actionbarsingular);
                    ActionBarAPI.sendActionBar(p, actionbarsingular);
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
                    p.playSound(p.getLocation(), Sound.valueOf(Config.RESPAWN), 3.0F, 1.0F);
                    p.updateInventory();
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 20, 20);
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
        }.runTaskTimer(plugin, 20, 20);
    }
}
