package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.api.manager.EventManager;
import com.github.victortedesco.betterdeathscreen.api.manager.PlayerManager;
import com.github.victortedesco.betterdeathscreen.api.utils.Randomizer;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import com.github.victortedesco.betterdeathscreen.bukkit.configuration.BukkitConfig;
import com.github.victortedesco.betterdeathscreen.bukkit.configuration.BukkitMessages;
import com.github.victortedesco.betterdeathscreen.bukkit.utils.DeathTasks;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.HashMap;

public class EntityDamageListener implements Listener {

    private static final HashMap<Player, Boolean> FLY_STATE = new HashMap<>();

    public static HashMap<Player, Boolean> getFlyState() {
        return FLY_STATE;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if (BetterDeathScreenAPI.getPlayerManager().isDead(player)) {
                event.setCancelled(true);
                if (event.getEntity() instanceof LivingEntity) {
                    if (BetterDeathScreen.getConfiguration().canSpectate()) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.setSpectatorTarget(event.getEntity());
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        PlayerManager playerManager = BetterDeathScreenAPI.getPlayerManager();
        EventManager eventManager = BetterDeathScreenAPI.getEventManager();
        DeathTasks deathTasks = BetterDeathScreen.getDeathTasks();
        Randomizer randomizer = BetterDeathScreenAPI.getRandomizer();
        BukkitConfig config = BetterDeathScreen.getConfiguration();
        BukkitMessages messages = BetterDeathScreen.getMessages();

        int time = config.getRespawnTime();
        if (time < 0) time = 1;

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player playerKiller = null;

            if (playerManager.isHardcore(player)) time = 5;
            if (playerManager.getDeadPlayers().contains(player)) {
                event.setCancelled(true);
                if (player.getFireTicks() > 0) player.setFireTicks(0);
                return;
            }
            if (player.getHealth() > event.getFinalDamage() || BetterDeathScreenAPI.getPlayerManager().usingTotem(player)) {
                if (!(event instanceof EntityDamageByBlockEvent) && !(event instanceof EntityDamageByEntityEvent))
                    eventManager.setPlayerDamageBeforeDeath(player, event);
                if (event instanceof EntityDamageByBlockEvent)
                    eventManager.setPlayerDamageByBlockBeforeDeath(player, (EntityDamageByBlockEvent) event);
                if (event instanceof EntityDamageByEntityEvent)
                    eventManager.setPlayerDamageByEntityBeforeDeath(player, (EntityDamageByEntityEvent) event);
            }
            if (player.getHealth() <= event.getFinalDamage() && !BetterDeathScreenAPI.getPlayerManager().usingTotem(player)) {
                event.setDamage(0);
                playerManager.sendCustomMessage(player, player, config.getKilledMessageType(), randomizer.getRandomItemFromList(messages.getKilled()), time);
                if (event instanceof EntityDamageByEntityEvent) {
                    Entity killer = ((EntityDamageByEntityEvent) event).getDamager();
                    if (killer instanceof Projectile) {
                        Projectile projectile = (Projectile) killer;
                        if (projectile.getShooter() instanceof Entity) killer = (Entity) projectile.getShooter();
                        if (projectile instanceof Arrow) projectile.remove();
                    }
                    if (killer instanceof Player) {
                        playerKiller = (Player) killer;

                        playerKiller.incrementStatistic(Statistic.PLAYER_KILLS, 1);
                        playerKiller.incrementStatistic(Statistic.DAMAGE_DEALT, (int) Math.max(player.getHealth(), 1));
                        playerManager.playSound(player, config.getKillSounds(), true, false);
                        playerManager.sendCustomMessage(player, playerKiller, config.getKilledByPlayerMessageType(), randomizer.getRandomItemFromList(messages.getKilledByPlayer()), time);
                        playerManager.sendCustomMessage(playerKiller, player, config.getKillMessageType(), randomizer.getRandomItemFromList(messages.getKill()), 1);
                    }
                }
                if (config.canFly()) {
                    getFlyState().put(player, player.getAllowFlight());
                    player.setAllowFlight(true);
                    player.setFlying(true);
                }
                deathTasks.performDeath(player);
                deathTasks.sendCommandsOnDeath(player, playerKiller);
            }
        }
    }
}
