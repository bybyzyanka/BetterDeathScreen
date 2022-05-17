package me.tedesk.events.bukkit;

import me.tedesk.api.ActionBarAPI;
import me.tedesk.api.SoundAPI;
import me.tedesk.configs.Config;
import me.tedesk.events.Listeners;
import me.tedesk.systems.Randomizer;
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

        if (victim instanceof Player) {
            Player pv = (Player) victim;
            if (Config.DEAD_PLAYERS.contains(pv.getUniqueId())) {
                e.setCancelled(true);
                return;
            }
            if (e instanceof EntityDamageByEntityEvent) {
                Entity damager = ((EntityDamageByEntityEvent) e).getDamager();
                if (pv.getHealth() < e.getFinalDamage()) {
                    if (damager instanceof Player) {
                        Player pd = (Player) damager;
                        ActionBarAPI.sendActionBar(pd, Randomizer.customKillActionBar(pv));
                        SoundAPI.sendSound(pd, pd.getLocation(), Config.SOUND_KILL, Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                        return;
                    }
                    if (damager instanceof Projectile) {
                        Projectile pj = (Projectile) damager;
                        if (pj.getShooter() instanceof Player) {
                            Player pd = (Player) pj.getShooter();
                            ActionBarAPI.sendActionBar(pd, Randomizer.customKillActionBar(pv));
                            SoundAPI.sendSound(pd, pd.getLocation(), Config.SOUND_KILL, Config.SOUND_KILL_VOLUME, Config.SOUND_KILL_PITCH);
                        }
                    }
                }
            }
        }
    }
}
