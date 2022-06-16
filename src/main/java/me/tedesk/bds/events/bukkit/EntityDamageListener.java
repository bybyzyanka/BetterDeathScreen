package me.tedesk.bds.events.bukkit;

import me.tedesk.bds.api.ActionBarAPI;
import me.tedesk.bds.api.SoundAPI;
import me.tedesk.bds.api.TitleAPI;
import me.tedesk.bds.configs.Config;
import me.tedesk.bds.events.Listeners;
import me.tedesk.bds.systems.Randomizer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener extends Listeners {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent e) {
        Entity victim = e.getEntity();
        int time = Config.TIME;
        if (time <= 0) {
            time = 1;
        }

        if (victim instanceof Player) {
            Player pv = (Player) victim;
            if (Config.DEAD_PLAYERS.contains(pv.getName())) {
                e.setCancelled(true);
                return;
            }
            if (pv.getHealth() <= e.getFinalDamage()) {
                if (!(e instanceof EntityDamageByEntityEvent)) {
                    TitleAPI.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitle(pv), Randomizer.randomSubTitle(pv));
                }
                if (e instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) e).getDamager();
                    if (damager instanceof Player) {
                        Player pd = (Player) damager;
                        TitleAPI.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                        ActionBarAPI.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                        SoundAPI.sendSound(pd, pd.getLocation(), Randomizer.randomSound(Config.SOUND_KILL), Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                    }
                    if (damager instanceof Projectile) {
                        Projectile pj = (Projectile) damager;
                        if (pj.getShooter() instanceof Player) {
                            Player pd = (Player) pj.getShooter();
                            TitleAPI.sendTitle(pv, 2, 20 * time, 2, Randomizer.randomTitleOnDeathByPlayer(pd), Randomizer.randomSubTitleOnDeathByPlayer(pd));
                            ActionBarAPI.sendActionBar(pd, Randomizer.randomKillActionBar(pv));
                            SoundAPI.sendSound(pd, pd.getLocation(), Randomizer.randomSound(Config.SOUND_KILL), Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                        }
                    }
                }
            }
        }
    }
}
