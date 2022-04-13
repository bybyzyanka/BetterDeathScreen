package me.tedesk.plugin.events.death;

import me.tedesk.plugin.animations.Animation;
import me.tedesk.plugin.api.ActionBarAPI;
import me.tedesk.plugin.api.SoundAPI;
import me.tedesk.plugin.api.TitleAPI;
import me.tedesk.plugin.configs.Config;
import me.tedesk.plugin.configs.Messages;
import me.tedesk.plugin.events.Listeners;
import me.tedesk.plugin.systems.FakeMechanics;
import me.tedesk.plugin.systems.Randomizer;
import me.tedesk.plugin.systems.Timer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class DeathScreen extends Listeners {

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDeathByEntity(EntityDamageByEntityEvent e) {

        Entity victim = e.getEntity();
        Entity damager = e.getDamager();

        int time = Config.TIME;
        if (time <= 0) {
            time = 1;
        }

        if (e.getEntity() instanceof Player) {
            Player pv = (Player) victim;

            if (e.getFinalDamage() >= pv.getHealth()) {
                if (pv.getGameMode() == GameMode.SPECTATOR) {
                    e.setCancelled(true);
                    return;
                }
                if (!(pv.getGameMode() == GameMode.SPECTATOR)) {
                    e.setCancelled(true);
                    EntityDamageByEntityEvent fakehit = new EntityDamageByEntityEvent(damager, victim, e.getCause(), 0);
                    Bukkit.getPluginManager().callEvent(fakehit);
                    FakeMechanics.sendDeath(pv);
                    FakeMechanics.changeStatisticsVictim(pv);
                    FakeMechanics.dropInventory(pv);
                    pv.setGameMode(GameMode.SPECTATOR);
                    Animation.sendAnimation(pv);
                    SoundAPI.sendSound(pv, pv.getLocation(), Config.SOUND_DEATH, 3, 1);
                    TitleAPI.sendTitle(pv, 2, 20 * time, 2, Randomizer.customtitles(), Randomizer.customsubtitles());
                    if (!Bukkit.getServer().isHardcore()) {
                        Timer.normal(pv);
                    }
                    if (Bukkit.getServer().isHardcore()) {
                        Timer.hardcore(pv);
                    }
                    if (e.getDamager() instanceof Player) {
                        Player killer = (Player) e.getDamager();

                        String killab = Messages.ACTIONBAR_KILL.replace("%player%", pv.getDisplayName());
                        killab = ChatColor.translateAlternateColorCodes('&', killab);
                        ActionBarAPI.sendActionBar(killer, killab);
                        FakeMechanics.changeStatisticsKiller(killer);
                    }
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDeathWithoutEntity(EntityDamageEvent e) {

        Entity victim = e.getEntity();

        int time = Config.TIME;
        if (time <= 0) {
            time = 1;
        }

        if (e.getEntity() instanceof Player) {
            Player pv = (Player) victim;

            if (e.getFinalDamage() >= pv.getHealth()) {
                if (pv.getGameMode() == GameMode.SPECTATOR) {
                    e.setCancelled(true);
                    return;
                }
                if (!(pv.getGameMode() == GameMode.SPECTATOR)) {
                    e.setCancelled(true);
                    EntityDamageEvent fakedamage = new EntityDamageEvent(victim, e.getCause(), 0);
                    Bukkit.getPluginManager().callEvent(fakedamage);
                    FakeMechanics.sendDeath(pv);
                    FakeMechanics.changeStatisticsVictim(pv);
                    FakeMechanics.dropInventory(pv);
                    pv.setGameMode(GameMode.SPECTATOR);
                    Animation.sendAnimation(pv);
                    SoundAPI.sendSound(pv, pv.getLocation(), Config.SOUND_DEATH, 3, 1);
                    TitleAPI.sendTitle(pv, 2, 20 * time, 2, Randomizer.customtitles(), Randomizer.customsubtitles());
                    if (!Bukkit.getServer().isHardcore()) {
                        Timer.normal(pv);
                    }
                    if (Bukkit.getServer().isHardcore()) {
                        Timer.hardcore(pv);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void disableSpectatorTeleport(PlayerTeleportEvent e) {
        Player p = e.getPlayer();
        // Caso ocorra interefencias com plugins de teleporte. Adicione 'e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE &&' no if.
        if (p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission(Config.ADMIN)) {
            e.setCancelled(true);
        }
    }
}
