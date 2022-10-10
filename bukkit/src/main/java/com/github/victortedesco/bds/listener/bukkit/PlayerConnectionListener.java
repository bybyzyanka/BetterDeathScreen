package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.utils.PlayerUtils;
import com.github.victortedesco.bds.utils.Tasks;
import com.github.victortedesco.bds.utils.Version;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerConnectionListener implements Listener {

    @SuppressWarnings("ConstantConditions")
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if ((player.getGameMode() == GameMode.SPECTATOR && (player.getHealth() == 0.1 || !player.hasPermission(Config.ADMIN))) || Config.DEAD_PLAYERS.contains(player.getName())) {
            if (PlayerUtils.isHardcore(player)) {
                Config.DEAD_PLAYERS.add(player.getName());
                Tasks.startTimer(player);
            }
        }
        // Fixing respawning in another dimensions.
        if (player.getBedSpawnLocation() != null) {
            if (player.getBedSpawnLocation().getWorld().getEnvironment() == World.Environment.THE_END || (BetterDeathScreen.getVersion().value < Version.v1_16.value && player.getBedSpawnLocation().getWorld().getEnvironment() == World.Environment.NETHER)) {
                player.setBedSpawnLocation(null);
            }
        }
        startPrimitiveCombatTimer(player);
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        // To avoid bugs, the player will respawn after disconnecting.
        if ((player.getGameMode() == GameMode.SPECTATOR && (player.getHealth() == 0.1 || !player.hasPermission(Config.ADMIN))) || Config.DEAD_PLAYERS.contains(player.getName())) {
            if (!PlayerUtils.isHardcore(player)) {
                Tasks.performRespawn(player);
            }
        }
        PlayerRespawnListener.BED_MESSAGE.remove(player.getName());
    }

    @SuppressWarnings({"deprecation"})
    private void startPrimitiveCombatTimer(Player player) {
        new BukkitRunnable() {
            int combat_timer = 30;

            @SuppressWarnings("ConstantConditions")
            @Override
            public void run() {
                if (!player.isOnline()) {
                    PlayerUtils.resetDamageBeforeDeath(player);
                    cancel();
                }
                if (player.getLastDamageCause() != null) {
                    if (BetterDeathScreen.getVersion() == Version.v1_8) {
                        if (player.getMaxHealth() - player.getLastDamageCause().getDamage() < player.getHealth() && !player.hasPotionEffect(PotionEffectType.REGENERATION)) {
                            combat_timer--;
                        }
                    }
                    if (BetterDeathScreen.getVersion() != Version.v1_8) {
                        if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - player.getLastDamageCause().getDamage() < player.getHealth() && !player.hasPotionEffect(PotionEffectType.REGENERATION)) {
                            combat_timer--;
                        }
                    }
                }
                if (combat_timer <= 0) {
                    PlayerUtils.resetDamageBeforeDeath(player);
                    combat_timer = 30;
                }
            }
        }.runTaskTimer(BetterDeathScreen.getInstance(), 1, 20);
    }
}
