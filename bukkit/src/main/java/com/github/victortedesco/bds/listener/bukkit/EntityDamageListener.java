package com.github.victortedesco.bds.listener.bukkit;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.animations.Animation;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.listener.Events;
import com.github.victortedesco.bds.utils.PlayerAPI;
import com.github.victortedesco.bds.utils.Randomizer;
import com.github.victortedesco.bds.utils.Tasks;
import com.github.victortedesco.bds.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
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

public class EntityDamageListener extends Events {

    private boolean noTotem(Player player, EntityDamageEvent event) {
        if (BetterDeathScreen.getVersion() != Version.v1_8) {
            Material hand = player.getInventory().getItemInMainHand().getType();
            Material off_hand = player.getInventory().getItemInOffHand().getType();

            if (!hand.toString().contains("TOTEM") && !off_hand.toString().contains("TOTEM")) return true;
            return event.getCause() == EntityDamageEvent.DamageCause.SUICIDE || event.getCause() == EntityDamageEvent.DamageCause.VOID;
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    private void sendEventsBukkit(Player player) {
        String random_death_sound = Randomizer.randomSound(Config.SOUND_DEATH);
        player.closeInventory();
        List<ItemStack> inventory = Arrays.stream(player.getInventory().getContents())
                .filter(stack -> !PlayerAPI.isStackEmpty(stack)).collect(Collectors.toList());
        if (BetterDeathScreen.getVersion() == Version.v1_8) {
            List<ItemStack> armor = Arrays.stream(player.getInventory().getArmorContents())
                    .filter(stack -> !PlayerAPI.isStackEmpty(stack))
                    .collect(Collectors.toList());
            inventory.addAll(armor);
        }
        for (PotionEffect pe : player.getActivePotionEffects()) {
            player.removePotionEffect(pe.getType());
        }
        if (!Config.MOVE_SPECTATOR) {
            player.setWalkSpeed(0F);
            player.setFlySpeed(0F);
        }
        PlayerDeathEvent death = new PlayerDeathEvent(player, inventory, 0, "BDS Handled Death");
        if (player.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            death.setKeepInventory(false);
            if (player.hasPermission(Config.KEEP_XP)) {
                death.setKeepLevel(true);
                death.setNewExp((int) player.getExp());
                death.setNewLevel(player.getLevel());
            }
            if (!player.hasPermission(Config.KEEP_XP)) {
                death.setDroppedExp(Math.min(100, player.getLevel() * 7));
                death.setKeepLevel(false);
                death.setNewExp(0);
                death.setNewLevel(0);
            }
        }
        if (player.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            death.setKeepInventory(true);
            death.setKeepLevel(true);
            death.setNewExp((int) player.getExp());
            death.setNewLevel(player.getLevel());
        }
        death.setNewTotalExp(player.getTotalExperience());
        Bukkit.getPluginManager().callEvent(death);
        player.setHealth(0.1);
        player.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
        player.incrementStatistic(Statistic.DEATHS, 1);
        PlayerAPI.playSound(player, random_death_sound, Config.SOUND_DEATH_VOLUME, Config.SOUND_DEATH_PITCH, true);
        Tasks.startTimer(player);
        Animation.sendAnimation(player);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        Entity v = e.getEntity();
        int time = Config.TIME;
        if (time <= 0) time = 1;

        if (v instanceof Player) {
            Player pv = (Player) v;
            String random_kill_sound = Randomizer.randomSound(Config.SOUND_KILL);

            if (Config.DEAD_PLAYERS.contains(pv.getName())) {
                e.setCancelled(true);
                return;
            }
            if (pv.getHealth() > e.getFinalDamage() || !noTotem(pv, e)) {
                if (!(e instanceof EntityDamageByEntityEvent) && !(e instanceof EntityDamageByBlockEvent)) {
                    ArrayList<Object> list = new ArrayList<>();
                    list.add(e.getCause());
                    list.add(e.getDamage());
                    list.add(e.getFinalDamage());

                    PlayerDeathListener.LAST_DAMAGE_BEFORE_DEATH.put(pv.getName(), list);
                }
                if (e instanceof EntityDamageByEntityEvent) {
                    ArrayList<Object> list = new ArrayList<>();
                    list.add(((EntityDamageByEntityEvent) e).getDamager());
                    list.add(e.getCause());
                    list.add(e.getDamage());
                    list.add(e.getFinalDamage());

                    PlayerDeathListener.LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.put(pv.getName(), list);
                }
                if (e instanceof EntityDamageByBlockEvent) {
                    ArrayList<Object> list = new ArrayList<>();
                    list.add(((EntityDamageByBlockEvent) e).getDamager());
                    list.add(e.getCause());
                    list.add(e.getDamage());
                    list.add(e.getFinalDamage());

                    PlayerDeathListener.LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.put(pv.getName(), list);
                }
            }
            if (pv.getHealth() <= e.getFinalDamage() && noTotem(pv, e)) {
                Config.DEAD_PLAYERS.add(v.getName());
                pv.setGameMode(GameMode.SPECTATOR);
                Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitle(pv), Randomizer.randomSubTitle(pv));
                PlayerAPI.incrementStatistic(pv, Statistic.DAMAGE_TAKEN, (int) e.getFinalDamage());
                if (e instanceof EntityDamageByEntityEvent) {
                    Entity d = ((EntityDamageByEntityEvent) e).getDamager();

                    if (Config.USE_KILL_CAM) pv.setSpectatorTarget(d);
                    if (d instanceof Player) {
                        Player pd = (Player) d;

                        Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                        ActionBar.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                        PlayerAPI.playSound(pd, random_kill_sound, Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH, true);
                        PlayerAPI.incrementStatistic(pd, Statistic.DAMAGE_DEALT, (int) e.getFinalDamage());
                        pd.incrementStatistic(Statistic.PLAYER_KILLS, 1);
                    }
                    if (d instanceof Projectile) {
                        Projectile pj = (Projectile) d;
                        if (pj.getShooter() instanceof Entity) {
                            if (Config.USE_KILL_CAM) pv.setSpectatorTarget((Entity) pj.getShooter());
                        }
                        if (pj.getShooter() instanceof Player) {
                            Player pd = (Player) pj.getShooter();

                            Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                            ActionBar.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                            PlayerAPI.playSound(pd, random_kill_sound, Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH, true);
                            PlayerAPI.incrementStatistic(pd, Statistic.DAMAGE_DEALT, (int) e.getFinalDamage());
                            pd.incrementStatistic(Statistic.PLAYER_KILLS, 1);
                        }
                    }
                }
                sendEventsBukkit(pv);
                e.setDamage(0);
            }
        }
    }
}
