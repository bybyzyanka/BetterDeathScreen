package com.github.victortedesco.bds.listener.bukkit;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.animations.Animation;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.utils.PlayerAPI;
import com.github.victortedesco.bds.utils.Randomizer;
import com.github.victortedesco.bds.utils.Tasks;
import com.github.victortedesco.bds.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityDamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        int time = Config.TIME;
        if (time <= 0) time = 1;

        if (entity instanceof Player) {
            Player playerVictim = (Player) entity;

            if (PlayerAPI.isHardcore(playerVictim)) time = 5;
            if (Config.DEAD_PLAYERS.contains(playerVictim.getName())) {
                event.setCancelled(true);
                return;
            }
            if (playerVictim.getHealth() > event.getFinalDamage() || !noTotem(playerVictim, event)) {
                sendDamageInfo(playerVictim, event);
            }
            if (playerVictim.getHealth() <= event.getFinalDamage() && noTotem(playerVictim, event)) {
                Config.DEAD_PLAYERS.add(playerVictim.getName());
                PlayerTeleportListener.TELEPORT_MESSAGE_IMUNE.add(playerVictim.getName());
                Bukkit.getScheduler().runTaskLaterAsynchronously(BetterDeathScreen.getInstance(),
                        () -> PlayerTeleportListener.TELEPORT_MESSAGE_IMUNE.remove(playerVictim.getName()), 5);
                playerVictim.setGameMode(GameMode.SPECTATOR);
                Titles.sendTitle(playerVictim, 2, 20 * time, 2, Randomizer.getRandomMessage(playerVictim, null, Messages.KILLED_TITLES), Randomizer.getRandomMessage(playerVictim, null, Messages.KILLED_SUBTITLES));
                PlayerAPI.incrementStatistic(playerVictim, Statistic.DAMAGE_TAKEN, (int) event.getFinalDamage(), true);
                if (event instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) event).getDamager();

                    if (Config.USE_KILL_CAM) playerVictim.setSpectatorTarget(damager);
                    if (damager instanceof Player) killedByPlayer(playerVictim, (Player) damager, 20 * time, event);
                    if (damager instanceof Projectile) {
                        Projectile projectile = (Projectile) damager;
                        if (projectile.getShooter() instanceof Entity) {
                            if (Config.USE_KILL_CAM) playerVictim.setSpectatorTarget((Entity) projectile.getShooter());
                        }
                        if (projectile.getShooter() instanceof Player)
                            killedByPlayer(playerVictim, (Player) projectile.getShooter(), 20 * time, event);
                    }
                }
                sendDeath(playerVictim);
                event.setDamage(0);
            }
        }
    }

    private boolean noTotem(Player player, EntityDamageEvent event) {
        if (BetterDeathScreen.getVersion() != Version.v1_8) {
            Material hand = player.getInventory().getItemInMainHand().getType();
            Material off_hand = player.getInventory().getItemInOffHand().getType();

            if (!hand.toString().contains("TOTEM") && !off_hand.toString().contains("TOTEM")) return true;
            return event.getCause() == EntityDamageEvent.DamageCause.SUICIDE || event.getCause() == EntityDamageEvent.DamageCause.VOID;
        }
        return true;
    }

    private void sendDamageInfo(Player player, EntityDamageEvent event) {
        if (!(event instanceof EntityDamageByEntityEvent) && !(event instanceof EntityDamageByBlockEvent)) {
            ArrayList<Object> list = new ArrayList<>();
            list.add(event.getCause());
            list.add(event.getDamage());
            list.add(event.getFinalDamage());

            PlayerDeathListener.DAMAGE_BEFORE_DEATH.put(player.getName(), list);
        }
        if (event instanceof EntityDamageByEntityEvent) {
            ArrayList<Object> list = new ArrayList<>();
            Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
            if (damager instanceof LivingEntity) {
                list.add(damager);
            }
            if (damager instanceof Projectile) {
                list.add(((Projectile) damager).getShooter());
            }
            list.add(event.getCause());
            list.add(event.getDamage());
            list.add(event.getFinalDamage());

            PlayerDeathListener.DAMAGE_BY_ENTITY_BEFORE_DEATH.put(player.getName(), list);
        }
        if (event instanceof EntityDamageByBlockEvent) {
            ArrayList<Object> list = new ArrayList<>();
            list.add(((EntityDamageByBlockEvent) event).getDamager());
            list.add(event.getCause());
            list.add(event.getDamage());
            list.add(event.getFinalDamage());

            PlayerDeathListener.DAMAGE_BY_BLOCK_BEFORE_DEATH.put(player.getName(), list);
        }
    }

    @SuppressWarnings("deprecation")
    private void sendDeath(Player player) {
        player.closeInventory();
        List<ItemStack> inventory = Arrays.stream(player.getInventory().getContents())
                .filter(stack -> !PlayerAPI.isStackEmpty(stack)).collect(Collectors.toList());
        if (BetterDeathScreen.getVersion() == Version.v1_8) {
            List<ItemStack> armor = Arrays.stream(player.getInventory().getArmorContents())
                    .filter(stack -> !PlayerAPI.isStackEmpty(stack))
                    .collect(Collectors.toList());
            inventory.addAll(armor);
        }
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        if (!Config.MOVE_SPECTATOR) {
            player.setWalkSpeed(0F);
            player.setFlySpeed(0F);
        }
        PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(player, inventory, 0, "BDS Handled Death");
        if (player.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            playerDeathEvent.setKeepInventory(false);
            if (player.hasPermission(Config.KEEP_XP)) {
                playerDeathEvent.setKeepLevel(true);
                playerDeathEvent.setNewExp((int) player.getExp());
                playerDeathEvent.setNewLevel(player.getLevel());
            }
            if (!player.hasPermission(Config.KEEP_XP)) {
                playerDeathEvent.setDroppedExp(Math.min(100, player.getLevel() * 7));
                playerDeathEvent.setKeepLevel(false);
                playerDeathEvent.setNewExp(0);
                playerDeathEvent.setNewLevel(0);
            }
        }
        if (player.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            playerDeathEvent.setKeepInventory(true);
            playerDeathEvent.setKeepLevel(true);
            playerDeathEvent.setNewExp((int) player.getExp());
            playerDeathEvent.setNewLevel(player.getLevel());
        }
        playerDeathEvent.setNewTotalExp(player.getTotalExperience());
        Bukkit.getPluginManager().callEvent(playerDeathEvent);
        PlayerAPI.playSoundFromConfig(player, Config.SOUND_DEATH, true, false);
        player.setHealth(0.1);
        player.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
        player.incrementStatistic(Statistic.DEATHS, 1);
        Tasks.startTimer(player);
        Animation.sendAnimation(player);
    }

    private void killedByPlayer(Player victim, Player damager, int time, EntityDamageEvent event) {
        Titles.sendTitle(victim, 2, 20 * time, 2, Randomizer.getRandomMessage(victim, damager, Messages.KILLED_BY_PLAYER_TITLES), Randomizer.getRandomMessage(victim, damager, Messages.KILLED_BY_PLAYER_SUBTITLES));
        ActionBar.sendActionBar(damager, Randomizer.getRandomMessage(victim, null, Messages.ACTIONBAR_KILL));
        PlayerAPI.playSoundFromConfig(damager, Config.SOUND_KILL, true, false);
        PlayerAPI.incrementStatistic(damager, Statistic.DAMAGE_DEALT, (int) event.getFinalDamage(), true);
        damager.incrementStatistic(Statistic.PLAYER_KILLS, 1);
    }
}
