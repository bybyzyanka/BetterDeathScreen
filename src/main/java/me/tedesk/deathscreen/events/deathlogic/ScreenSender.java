package me.tedesk.deathscreen.events.deathlogic;

import me.tedesk.deathscreen.animations.Animation;
import me.tedesk.deathscreen.api.ActionBarAPI;
import me.tedesk.deathscreen.api.TitleAPI;
import me.tedesk.deathscreen.api.SoundAPI;
import me.tedesk.deathscreen.configs.Config;
import me.tedesk.deathscreen.configs.Messages;
import me.tedesk.deathscreen.events.Listeners;
import me.tedesk.deathscreen.systems.general.*;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class ScreenSender extends Listeners {

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

            if (e.getFinalDamage() >= pv.getHealth() && !Bukkit.getServer().isHardcore() && !(pv.getGameMode() == GameMode.SPECTATOR)) {
                e.setCancelled(true);
                pv.setGameMode(GameMode.SPECTATOR);
                EntityDamageByEntityEvent fakehit = new EntityDamageByEntityEvent(damager, victim, e.getCause(), e.getFinalDamage());
                Bukkit.getPluginManager().callEvent(fakehit);
                FakeMechanics.sendDeath(pv);
                FakeMechanics.changeStatisticsVictim(pv);
                FakeMechanics.dropInventory(pv);
                Animation.sendAnimation(pv);
                SoundAPI.sendSound(pv, pv.getLocation(), Config.SOUND_DEATH, 3, 1);
                TitleAPI.sendTitle(pv, 1, 20 * time, 1, Randomizer.customtitles(), Randomizer.customsubtitles());
                Timer.normal(pv);

                if (e.getDamager() instanceof Player) {
                    Player killer = (Player) e.getDamager();
                    String killab = Messages.ACTIONBAR_KILL.replace("%player%", pv.getDisplayName());
                    killab = ChatColor.translateAlternateColorCodes('&', killab);
                    ActionBarAPI.sendActionBar(killer, killab);
                    FakeMechanics.changeStatisticsKiller(killer, e);
                }

            } else {
                if (e.getFinalDamage() >= pv.getHealth() && Bukkit.getServer().isHardcore() && !(pv.getGameMode() == GameMode.SPECTATOR)) {
                    e.setCancelled(true);
                    pv.setGameMode(GameMode.SPECTATOR);
                    EntityDamageByEntityEvent fakehit = new EntityDamageByEntityEvent(damager, victim, e.getCause(), e.getFinalDamage());
                    Bukkit.getPluginManager().callEvent(fakehit);
                    FakeMechanics.sendDeath(pv);
                    FakeMechanics.changeStatisticsVictim(pv);
                    FakeMechanics.dropInventory(pv);
                    Animation.sendAnimation(pv);
                    SoundAPI.sendSound(pv, pv.getLocation(), Config.SOUND_DEATH, 3, 1);
                    TitleAPI.sendTitle(pv, 2, 20 * time, 2, Randomizer.customtitles(), Randomizer.customsubtitles());
                    Timer.hardcore(pv);

                    if (e.getDamager() instanceof Player) {
                        Player killer = (Player) e.getDamager();
                        String killab = Messages.ACTIONBAR_KILL.replace("%player%", pv.getDisplayName());
                        killab = ChatColor.translateAlternateColorCodes('&', killab);
                        ActionBarAPI.sendActionBar(killer, killab);
                        FakeMechanics.changeStatisticsKiller(killer, e);
                    } else {
                        if (pv.getGameMode() == GameMode.SPECTATOR) {
                            e.setCancelled(true);
                        }
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

            if (e.getFinalDamage() >= pv.getHealth() && !Bukkit.getServer().isHardcore() && !(pv.getGameMode() == GameMode.SPECTATOR)) {
                e.setCancelled(true);
                pv.setGameMode(GameMode.SPECTATOR);
                EntityDamageEvent fakedamage = new EntityDamageEvent(victim, e.getCause(), e.getFinalDamage());
                Bukkit.getPluginManager().callEvent(fakedamage);
                FakeMechanics.sendDeath(pv);
                FakeMechanics.changeStatisticsVictim(pv);
                FakeMechanics.dropInventory(pv);
                Animation.sendAnimation(pv);
                SoundAPI.sendSound(pv, pv.getLocation(), Config.SOUND_DEATH, 3, 1);
                TitleAPI.sendTitle(pv, 1, 20 * time, 1, Randomizer.customtitles(), Randomizer.customsubtitles());
                Timer.normal(pv);
            } else {
                if (e.getFinalDamage() >= pv.getHealth() && Bukkit.getServer().isHardcore() && !(pv.getGameMode() == GameMode.SPECTATOR)) {
                    e.setCancelled(true);
                    pv.setGameMode(GameMode.SPECTATOR);
                    EntityDamageEvent fakedamage = new EntityDamageEvent(victim, e.getCause(), e.getFinalDamage());
                    Bukkit.getPluginManager().callEvent(fakedamage);
                    FakeMechanics.sendDeath(pv);
                    FakeMechanics.changeStatisticsVictim(pv);
                    FakeMechanics.dropInventory(pv);
                    Animation.sendAnimation(pv);
                    SoundAPI.sendSound(pv, pv.getLocation(), Config.SOUND_DEATH, 3, 1);
                    TitleAPI.sendTitle(pv, 1, 20 * time, 1, Randomizer.customtitles(), Randomizer.customsubtitles());
                    Timer.hardcore(pv);
                } else {
                    if (pv.getGameMode() == GameMode.SPECTATOR) {
                        e.setCancelled(true);
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