package me.tedesk.bds.events.bukkit;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.animations.Animation;
import me.tedesk.bds.api.PlayerAPI;
import me.tedesk.bds.configs.Config;
import me.tedesk.bds.events.Listeners;
import me.tedesk.bds.systems.Randomizer;
import me.tedesk.bds.systems.Tasks;
import me.tedesk.bds.api.Version;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.block.Block;
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

public class EntityDamageListener extends Listeners {

    private static boolean handChecker(Player player, EntityDamageEvent event) {
        if (!(BetterDeathScreen.version == Version.v1_8)) {
            return (!(player.getInventory().getItemInMainHand().getType() == XMaterial.TOTEM_OF_UNDYING.parseMaterial()) || !(player.getInventory().getItemInOffHand().getType() == XMaterial.TOTEM_OF_UNDYING.parseMaterial()) || event.getCause() == EntityDamageEvent.DamageCause.SUICIDE || event.getCause() == EntityDamageEvent.DamageCause.VOID);
        }
        return true;
    }

    private static void sendEventsBukkit(Player victim) {
        Config.DEAD_PLAYERS.add(victim.getName());
        victim.setHealth(0.1);
        victim.setGameMode(GameMode.SPECTATOR);
        if (victim.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            List<ItemStack> filtered_inventory = Arrays.stream(victim.getInventory().getContents())
                    .filter(stack -> !PlayerAPI.isStackEmpty(stack)).collect(Collectors.toList());
            if (BetterDeathScreen.version == Version.v1_8) {
                List<ItemStack> armor = Arrays.stream(victim.getInventory().getArmorContents())
                        .filter(stack -> !PlayerAPI.isStackEmpty(stack))
                        .collect(Collectors.toList());
                filtered_inventory.addAll(armor);
            }
            PlayerDeathEvent death = new PlayerDeathEvent(victim, filtered_inventory, Math.min(100, victim.getLevel() * 7), "");
            Bukkit.getPluginManager().callEvent(death);
            if (!victim.hasPermission(Config.KEEP_XP)) {
                victim.setLevel(0);
                victim.setExp(0);
            }
            PlayerAPI.dropInventory(victim);
        }
        if (victim.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            PlayerDeathEvent death = new PlayerDeathEvent(victim, new ArrayList<>(), 0, "");
            Bukkit.getPluginManager().callEvent(death);
        }
        if (!Config.MOVE_SPECTATOR) {
            victim.setWalkSpeed(0F);
            victim.setFlySpeed(0F);
        }
        victim.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
        victim.incrementStatistic(Statistic.DEATHS, 1);
        for (PotionEffect pe : victim.getActivePotionEffects()) {
            victim.removePotionEffect(pe.getType());
        }
        victim.playSound(victim.getLocation(), Randomizer.randomSound(Config.SOUND_DEATH), Config.SOUND_DEATH_VOLUME, Config.SOUND_DEATH_PITCH);
        if (!Bukkit.getServer().isHardcore()) {
            Tasks.normalTimer(victim);
        }
        if (Bukkit.getServer().isHardcore()) {
            Tasks.hardcoreTimer(victim);
        }
        Animation.sendAnimation(victim);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity victim = event.getEntity();
        int time = Config.TIME;
        if (time <= 0) {
            time = 1;
        }

        if (victim instanceof Player) {
            Player pv = (Player) victim;
            if (Config.DEAD_PLAYERS.contains(pv.getName())) {
                event.setCancelled(true);
                return;
            }
            if (pv.getHealth() <= event.getFinalDamage() && handChecker(pv, event)) {
                if (!Config.USE_PACKET_EVENT_HANDLER) {
                    event.setCancelled(true);
                    EntityDamageEvent fake_event = new EntityDamageEvent(victim, event.getCause(), event.getDamage());
                    Bukkit.getPluginManager().callEvent(fake_event);
                    try {
                        pv.incrementStatistic(Statistic.DAMAGE_TAKEN, (int) Math.abs(event.getFinalDamage()));
                    } catch (Exception ignored) {
                    }
                }
                if (event instanceof EntityDamageByBlockEvent) {
                    Block damager = ((EntityDamageByBlockEvent) event).getDamager();
                    Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitle(pv), Randomizer.randomSubTitle(pv));
                    if (!Config.USE_PACKET_EVENT_HANDLER) {
                        try {
                            EntityDamageByBlockEvent block_damage = new EntityDamageByBlockEvent(damager, victim, event.getCause(), event.getDamage());
                            Bukkit.getPluginManager().callEvent(block_damage);
                        } catch (Exception ignored) {

                        }
                    }
                }
                if (event instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
                    if (damager instanceof Player) {
                        Player pd = (Player) damager;
                        Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                        ActionBar.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                        pd.playSound(pd.getLocation(), Randomizer.randomSound(Config.SOUND_KILL), Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                        if (!Config.USE_PACKET_EVENT_HANDLER) {
                            EntityDamageByEntityEvent entity_damage = new EntityDamageByEntityEvent(damager, victim, event.getCause(), event.getDamage());
                            Bukkit.getPluginManager().callEvent(entity_damage);
                            pd.incrementStatistic(Statistic.PLAYER_KILLS, 1);
                            pd.incrementStatistic(Statistic.DAMAGE_DEALT, (int) event.getFinalDamage());
                        }
                    }
                    if (damager instanceof Projectile) {
                        Projectile pj = (Projectile) damager;
                        if (pj.getShooter() instanceof Player) {
                            Player pd = (Player) pj.getShooter();
                            Titles.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                            ActionBar.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                            pd.playSound(pd.getLocation(), Randomizer.randomSound(Config.SOUND_KILL), Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                            if (!Config.USE_PACKET_EVENT_HANDLER) {
                                pd.incrementStatistic(Statistic.PLAYER_KILLS, 1);
                                pd.incrementStatistic(Statistic.DAMAGE_DEALT, (int) event.getFinalDamage());
                                EntityDamageByEntityEvent entity_damage = new EntityDamageByEntityEvent(pd, victim, event.getCause(), event.getDamage());
                                Bukkit.getPluginManager().callEvent(entity_damage);
                            }
                        }
                        pj.remove();
                    }
                }
                if (!Config.USE_PACKET_EVENT_HANDLER) {
                    sendEventsBukkit(pv);
                }
            }
        }
    }
}
