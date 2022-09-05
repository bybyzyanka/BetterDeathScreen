package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.listener.Events;
import com.github.victortedesco.bds.utils.PlayerAPI;
import com.github.victortedesco.bds.utils.Tasks;
import com.github.victortedesco.bds.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerConnectionListener extends Events {

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if ((p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission(Config.ADMIN)) || Config.DEAD_PLAYERS.contains(p.getName())) {
            if (Bukkit.getServer().isHardcore()) {
                Config.DEAD_PLAYERS.add(p.getName());
                Tasks.startTimer(p);
            }
        }
        // Fixing respawning in another dimensions.
        if (p.getBedSpawnLocation() != null) {
            if (p.getBedSpawnLocation().getWorld().getEnvironment() == World.Environment.NETHER || p.getBedSpawnLocation().getWorld().getEnvironment() == World.Environment.THE_END) {
                p.setBedSpawnLocation(null);
            }
        }
        // A primitive combat log.
        new BukkitRunnable() {
            int combat_timer = 100;

            @Override
            public void run() {
                if (!p.isOnline()) {
                    PlayerAPI.resetDamageBeforeDeath(p);
                    cancel();
                }
                if (p.getLastDamageCause() != null) {
                    if (BetterDeathScreen.version == Version.v1_8) {
                        if (p.getMaxHealth() - p.getLastDamageCause().getDamage() < p.getHealth() && !p.hasPotionEffect(PotionEffectType.REGENERATION)) {
                            combat_timer -= 1;
                        }
                    }
                    if (BetterDeathScreen.version != Version.v1_8) {
                        if (p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - p.getLastDamageCause().getDamage() < p.getHealth() && !p.hasPotionEffect(PotionEffectType.REGENERATION)) {
                            combat_timer -= 1;
                        }
                    }
                }
                if (combat_timer <= 0) {
                    PlayerAPI.resetDamageBeforeDeath(p);
                    combat_timer = 100;
                }
            }
        }.runTaskTimer(BetterDeathScreen.plugin, 1, 20);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        // To avoid bugs, the player will respawn after disconnecting.
        if ((p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission(Config.ADMIN)) || Config.DEAD_PLAYERS.contains(p.getName())) {
            if (!Bukkit.getServer().isHardcore()) {
                Tasks.performRespawn(p);
            }
        }
    }
}
