package me.tedesk.plugin.events.entity;

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
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathScreen extends Listeners {

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {

        Entity ent_victim = event.getEntity();

        int time = Config.TIME;
        if (time <= 0) {
            time = 1;
        }

        if (event.getEntity() instanceof Player) {
            Player pv = (Player) ent_victim;

            if (pv.getGameMode() == GameMode.SPECTATOR || Config.DEAD_PLAYERS.contains(pv.getName())) {
                event.setCancelled(true);
                return;
            }

            if (event.getFinalDamage() >= pv.getHealth()) {
                if (!(pv.getGameMode() == GameMode.SPECTATOR) || !(Config.DEAD_PLAYERS.contains(pv.getName()))) {
                    event.setCancelled(true);
                    Config.DEAD_PLAYERS.add(pv.getName());
                    pv.setHealth(0.1); // Algo coméstico, utilizado para alterar as placeholders relacionadas à vida do jogador.
                    EntityDamageEvent fake_damage = new EntityDamageEvent(event.getEntity(), event.getCause(), event.getDamage());
                    Bukkit.getPluginManager().callEvent(fake_damage);
                    // Usado caso o dano que mata o jogador é causado por um bloco.
                    if (event instanceof EntityDamageByBlockEvent) {
                        Block b_damager = ((EntityDamageByBlockEvent) event).getDamager();
                        EntityDamageByBlockEvent fake_block_damage = new EntityDamageByBlockEvent(b_damager, ent_victim, event.getCause(), event.getDamage());
                        Bukkit.getPluginManager().callEvent(fake_block_damage);
                    }
                    // Usado caso o dano que mata o jogador é causado por um entidade.
                    if (event instanceof EntityDamageByEntityEvent) {
                        Entity ent_damager = ((EntityDamageByEntityEvent) event).getDamager();
                        EntityDamageByEntityEvent fake_entity_damage = new EntityDamageByEntityEvent(ent_damager, ent_victim, event.getCause(), event.getDamage());
                        Bukkit.getPluginManager().callEvent(fake_entity_damage);
                        // Usado caso o dano que mata o jogador é causado por outro jogador.
                        if (ent_damager instanceof Player) {
                            Player pd = (Player) ent_damager;

                            String kill_ab = Messages.ACTIONBAR_KILL.replace("&", "§").replace("%player%", pv.getDisplayName());
                            ActionBarAPI.sendActionBar(pd, kill_ab);
                            FakeMechanics.changeStatisticsKiller(pd);
                        }
                    }
                    FakeMechanics.sendDeath(pv);
                    FakeMechanics.changeStatisticsVictim(pv);
                    FakeMechanics.dropInventory(pv);
                    Animation.sendAnimation(pv);
                    pv.setGameMode(GameMode.SPECTATOR);
                    SoundAPI.sendSound(pv, pv.getLocation(), Config.SOUND_DEATH, 3, 1);
                    TitleAPI.sendTitle(pv, 2, 20 * time, 2, Randomizer.customtitles(), Randomizer.customsubtitles());
                    if (Bukkit.getServer().isHardcore()) {
                        Timer.hardcore(pv);
                    }
                    if (!Bukkit.getServer().isHardcore()) {
                        Timer.normal(pv);
                    }
                }
            }
        }
    }
}