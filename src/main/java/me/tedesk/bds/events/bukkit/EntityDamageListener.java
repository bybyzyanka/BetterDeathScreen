package me.tedesk.bds.events.bukkit;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.animations.Animation;
import me.tedesk.bds.api.PlayerAPI;
import me.tedesk.bds.api.Version;
import me.tedesk.bds.api.events.PlayerDropInventoryEvent;
import me.tedesk.bds.configs.Config;
import me.tedesk.bds.events.Events;
import me.tedesk.bds.utils.DeathMessage;
import me.tedesk.bds.utils.Randomizer;
import me.tedesk.bds.utils.Tasks;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EntityDamageListener extends Events {

    private static boolean handChecker(Player p, EntityDamageEvent event) {
        if (BetterDeathScreen.version != Version.v1_8) {
            return ((!p.getInventory().getItemInMainHand().getType().toString().contains("TOTEM") && !p.getInventory().getItemInOffHand().getType().toString().contains("TOTEM")) || event.getCause() == EntityDamageEvent.DamageCause.SUICIDE || event.getCause() == EntityDamageEvent.DamageCause.VOID);
        }
        return true;
    }

    @SuppressWarnings("deprecation")
    private static void sendEventsBukkit(Player p, EntityDamageEvent e) {
        String random_death_sound = Randomizer.randomSound(Config.SOUND_DEATH);
        p.closeInventory();
        List<ItemStack> filtered_inventory = Arrays.stream(p.getInventory().getContents())
                .filter(stack -> !PlayerAPI.isStackEmpty(stack)).collect(Collectors.toList());
        if (BetterDeathScreen.version == Version.v1_8) {
            List<ItemStack> armor = Arrays.stream(p.getInventory().getArmorContents())
                    .filter(stack -> !PlayerAPI.isStackEmpty(stack))
                    .collect(Collectors.toList());
            filtered_inventory.addAll(armor);
        }

        for (PotionEffect pe : p.getActivePotionEffects()) {
            p.removePotionEffect(pe.getType());
        }
        if (!Config.MOVE_SPECTATOR) {
            p.setWalkSpeed(0F);
            p.setFlySpeed(0F);
        }

        if (p.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            if (p.hasPermission(Config.KEEP_XP)) {
                EntityDeathEvent ent_death = new EntityDeathEvent(p, filtered_inventory, 0);
                Bukkit.getPluginManager().callEvent(ent_death);
                PlayerDeathEvent death = new PlayerDeathEvent(p, filtered_inventory, 0, (int) p.getExp(), p.getTotalExperience(), p.getLevel(), DeathMessage.sendMessage(p, e));
                Bukkit.getPluginManager().callEvent(death);
            }
            if (!p.hasPermission(Config.KEEP_XP)) {
                EntityDeathEvent ent_death = new EntityDeathEvent(p, filtered_inventory, Math.min(100, p.getLevel() * 7));
                Bukkit.getPluginManager().callEvent(ent_death);
                PlayerDeathEvent death = new PlayerDeathEvent(p, filtered_inventory, Math.min(100, p.getLevel() * 7), 0, p.getTotalExperience(), 0, DeathMessage.sendMessage(p, e));
                Bukkit.getPluginManager().callEvent(death);
            }
            PlayerDropInventoryEvent drop_items = new PlayerDropInventoryEvent(p, filtered_inventory);
            Bukkit.getPluginManager().callEvent(drop_items);
        }
        if (p.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            EntityDeathEvent ent_death = new EntityDeathEvent(p, Collections.emptyList(), 0);
            Bukkit.getPluginManager().callEvent(ent_death);
            PlayerDeathEvent death = new PlayerDeathEvent(p, Collections.emptyList(), 0, DeathMessage.sendMessage(p, e));
            Bukkit.getPluginManager().callEvent(death);
            PlayerDropInventoryEvent drop_items = new PlayerDropInventoryEvent(p, Collections.emptyList());
            Bukkit.getPluginManager().callEvent(drop_items);
        }
        p.setHealth(0.1);
        p.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
        p.incrementStatistic(Statistic.DEATHS, 1);
        PlayerAPI.playSound(p, random_death_sound, Config.SOUND_DEATH_VOLUME, Config.SOUND_DEATH_PITCH);
        Tasks.startTimer(p);
        Animation.sendAnimation(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity victim = event.getEntity();
        int time = Config.TIME;
        if (time <= 0) {
            time = 1;
        }

        if (victim instanceof Player) {
            Player pv = (Player) victim;
            String random_kill_sound = Randomizer.randomSound(Config.SOUND_KILL);

            if (Config.DEAD_PLAYERS.contains(pv.getName())) {
                event.setCancelled(true);
                return;
            }

            if (pv.getHealth() <= event.getFinalDamage() && handChecker(pv, event)) {
                event.setCancelled(true);
                Config.DEAD_PLAYERS.add(victim.getName());
                pv.setGameMode(GameMode.SPECTATOR);
                Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitle(pv), Randomizer.randomSubTitle(pv));
                PlayerAPI.incrementStatistic(pv, Statistic.DAMAGE_TAKEN, (int) event.getFinalDamage());
                try {
                    EntityDamageEvent fake_damage = new EntityDamageEvent(victim, event.getCause(), event.getDamage());
                    Bukkit.getPluginManager().callEvent(fake_damage);
                } catch (IllegalArgumentException e) {
                    EntityDamageEvent fake_damage = new EntityDamageEvent(victim, event.getCause(), pv.getHealth());
                    Bukkit.getPluginManager().callEvent(fake_damage);
                }
                if (event instanceof EntityDamageByBlockEvent) {
                    Block damager = ((EntityDamageByBlockEvent) event).getDamager();
                    try {
                        EntityDamageByBlockEvent block_damage = new EntityDamageByBlockEvent(damager, victim, event.getCause(), event.getDamage());
                        Bukkit.getPluginManager().callEvent(block_damage);
                    } catch (IllegalArgumentException e) {
                        EntityDamageByBlockEvent block_damage = new EntityDamageByBlockEvent(damager, victim, event.getCause(), pv.getHealth());
                        Bukkit.getPluginManager().callEvent(block_damage);
                    }
                }
                if (event instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
                    if (Config.USE_KILL_CAM) {
                        pv.setSpectatorTarget(damager);
                    }
                    if (damager instanceof Player) {
                        Player pd = (Player) damager;
                        EntityDamageByEntityEvent entity_damage = new EntityDamageByEntityEvent(damager, victim, event.getCause(), event.getDamage());
                        Bukkit.getPluginManager().callEvent(entity_damage);

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
                            EntityDamageByEntityEvent entity_damage = new EntityDamageByEntityEvent(pd, victim, event.getCause(), event.getDamage());
                            Bukkit.getPluginManager().callEvent(entity_damage);

                            Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                            ActionBar.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                            PlayerAPI.playSound(pd, random_kill_sound, Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                            PlayerAPI.incrementStatistic(pd, Statistic.DAMAGE_DEALT, (int) event.getFinalDamage());
                            pd.incrementStatistic(Statistic.PLAYER_KILLS, 1);
                        }
                        pj.remove();
                    }
                }
                sendEventsBukkit(pv, event);
            }
        }
    }
}
