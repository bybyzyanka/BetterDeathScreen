package com.github.victortedesco.bds.listener.bukkit;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.animations.Animation;
import com.github.victortedesco.bds.api.events.PlayerDropInventoryEvent;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.listener.Events;
import com.github.victortedesco.bds.utils.PlayerAPI;
import com.github.victortedesco.bds.utils.Randomizer;
import com.github.victortedesco.bds.utils.Tasks;
import com.github.victortedesco.bds.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityDamageListener extends Events {

    private static boolean handChecker(Player p, EntityDamageEvent event) {
        if (BetterDeathScreen.version != Version.v1_8) {
            return ((!p.getInventory().getItemInMainHand().getType().toString().contains("TOTEM") && !p.getInventory().getItemInOffHand().getType().toString().contains("TOTEM"))
                    || event.getCause() == EntityDamageEvent.DamageCause.SUICIDE || event.getCause() == EntityDamageEvent.DamageCause.VOID);
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    private static void sendEventsBukkit(Player p) {
        String random_death_sound = Randomizer.randomSound(Config.SOUND_DEATH);
        p.closeInventory();
        List<ItemStack> inventory = Arrays.stream(p.getInventory().getContents())
                .filter(stack -> !PlayerAPI.isStackEmpty(stack)).collect(Collectors.toList());
        if (BetterDeathScreen.version == Version.v1_8) {
            List<ItemStack> armor = Arrays.stream(p.getInventory().getArmorContents())
                    .filter(stack -> !PlayerAPI.isStackEmpty(stack))
                    .collect(Collectors.toList());
            inventory.addAll(armor);
        }
        for (PotionEffect pe : p.getActivePotionEffects()) {
            p.removePotionEffect(pe.getType());
        }
        if (!Config.MOVE_SPECTATOR) {
            p.setWalkSpeed(0F);
            p.setFlySpeed(0F);
        }
        PlayerDeathEvent death = new PlayerDeathEvent(p, inventory, 0, "BDS Handled Death");
        PlayerDropInventoryEvent drop_items = new PlayerDropInventoryEvent(p, inventory);
        if (p.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            death.setKeepInventory(false);
            if (p.hasPermission(Config.KEEP_XP)) {
                death.setKeepLevel(true);
                death.setNewExp((int) p.getExp());
                death.setNewLevel(p.getLevel());
                death.setNewTotalExp(p.getTotalExperience());
            }
            if (!p.hasPermission(Config.KEEP_XP)) {
                death.setDroppedExp(Math.min(100, p.getLevel() * 7));
                death.setKeepLevel(false);
                death.setNewExp(0);
                death.setNewLevel(0);
                death.setNewTotalExp(p.getTotalExperience());
                p.setExp(0);
                p.setLevel(0);
            }
        }
        if (p.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            death.setKeepInventory(true);
            death.setKeepLevel(true);
            death.setNewExp((int) p.getExp());
            death.setNewLevel(p.getLevel());
            death.setNewTotalExp(p.getTotalExperience());
            drop_items.setDrops(Collections.emptyList());
            drop_items.setCancelled(true);
        }
        Bukkit.getPluginManager().callEvent(death);
        Bukkit.getPluginManager().callEvent(drop_items);
        p.setHealth(0.1);
        p.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
        p.incrementStatistic(Statistic.DEATHS, 1);
        PlayerAPI.playSound(p, random_death_sound, Config.SOUND_DEATH_VOLUME, Config.SOUND_DEATH_PITCH);
        Tasks.startTimer(p);
        Animation.sendAnimation(p);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity victim = event.getEntity();
        int time = Config.TIME;
        if (time <= 0) {
            time = 1;
        }

        if (victim instanceof Player) {
            Player pv = (Player) victim;
            String random_kill_sound = Randomizer.randomSound(Config.SOUND_KILL);

            if (Config.DEAD_PLAYERS.contains(pv.getName()) || pv.getGameMode() == GameMode.SPECTATOR) {
                event.setCancelled(true);
                return;
            }
            if (pv.getHealth() > event.getFinalDamage()) {
                if (!(event instanceof EntityDamageByEntityEvent) && !(event instanceof EntityDamageByBlockEvent)) {
                    ArrayList<Object> list = new ArrayList<>();
                    list.add(event.getCause());
                    list.add(event.getDamage());
                    list.add(event.getFinalDamage());

                    PlayerDeathListener.LAST_DAMAGE_BEFORE_DEATH.put(pv.getName(), list);
                }
                if (event instanceof EntityDamageByEntityEvent) {
                    ArrayList<Object> list = new ArrayList<>();
                    list.add(((EntityDamageByEntityEvent) event).getDamager());
                    list.add(event.getCause());
                    list.add(event.getDamage());
                    list.add(event.getFinalDamage());

                    PlayerDeathListener.LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.put(pv.getName(), list);
                }
                if (event instanceof EntityDamageByBlockEvent) {
                    ArrayList<Object> list = new ArrayList<>();
                    list.add(((EntityDamageByBlockEvent) event).getDamager());
                    list.add(event.getCause());
                    list.add(event.getDamage());
                    list.add(event.getFinalDamage());

                    PlayerDeathListener.LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.put(pv.getName(), list);
                }
            }
            if (pv.getHealth() <= event.getFinalDamage() && handChecker(pv, event)) {
                event.setDamage(0);
                Config.DEAD_PLAYERS.add(victim.getName());
                pv.setGameMode(GameMode.SPECTATOR);
                Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitle(pv), Randomizer.randomSubTitle(pv));
                PlayerAPI.incrementStatistic(pv, Statistic.DAMAGE_TAKEN, (int) event.getFinalDamage());
                if (event instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) event).getDamager();

                    if (Config.USE_KILL_CAM) {
                        pv.setSpectatorTarget(damager);
                    }
                    if (damager instanceof Player) {
                        Player pd = (Player) damager;

                        Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                        ActionBar.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                        PlayerAPI.playSound(pd, random_kill_sound, Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                        PlayerAPI.incrementStatistic(pd, Statistic.DAMAGE_DEALT, (int) event.getFinalDamage());
                        pd.incrementStatistic(Statistic.PLAYER_KILLS, 1);
                    }
                    if (damager instanceof Projectile) {
                        Projectile pj = (Projectile) damager;
                        if (pj.getShooter() instanceof Entity) {
                            if (Config.USE_KILL_CAM) {
                                pv.setSpectatorTarget((Entity) pj.getShooter());
                            }
                        }
                        if (pj.getShooter() instanceof Player) {
                            Player pd = (Player) pj.getShooter();

                            Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                            ActionBar.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                            PlayerAPI.playSound(pd, random_kill_sound, Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                            PlayerAPI.incrementStatistic(pd, Statistic.DAMAGE_DEALT, (int) event.getFinalDamage());
                            pd.incrementStatistic(Statistic.PLAYER_KILLS, 1);
                        }
                        pj.remove();
                    }
                }
                sendEventsBukkit(pv);
            }
        }
    }
}
