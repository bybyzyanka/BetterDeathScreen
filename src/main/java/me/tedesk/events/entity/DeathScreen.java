package me.tedesk.events.entity;

import me.tedesk.api.SoundAPI;
import me.tedesk.configs.Config;
import me.tedesk.events.Listeners;
import me.tedesk.animations.Animation;
import me.tedesk.api.ActionBarAPI;
import me.tedesk.api.TitleAPI;
import me.tedesk.configs.Messages;
import me.tedesk.systems.FakeMechanics;
import me.tedesk.systems.Randomizer;
import me.tedesk.systems.Tasks;
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
    public void onEntityDamage(EntityDamageEvent e) {

        Entity ent_victim = e.getEntity();

        int time = Config.TIME;
        if (time <= 0) {
            time = 1;
        }

        if (e.getEntity() instanceof Player) {
            Player pv = (Player) ent_victim;

            if (pv.getGameMode() == GameMode.SPECTATOR || Config.DEAD_PLAYERS.contains(pv.getName())) {
                e.setCancelled(true);
                return;
            }

            if (e.getFinalDamage() >= pv.getHealth()) {
                if (!(pv.getGameMode() == GameMode.SPECTATOR) || !(Config.DEAD_PLAYERS.contains(pv.getName()))) {
                    e.setCancelled(true);
                    Config.DEAD_PLAYERS.add(pv.getName());
                    pv.setHealth(0.1); // Algo coméstico, utilizado para alterar as placeholders relacionadas à vida do jogador.
                    EntityDamageEvent fake_damage = new EntityDamageEvent(e.getEntity(), e.getCause(), e.getDamage());
                    Bukkit.getPluginManager().callEvent(fake_damage);
                    // Usado caso o dano que mata o jogador é causado por um bloco.
                    if (e instanceof EntityDamageByBlockEvent) {
                        Block b_damager = ((EntityDamageByBlockEvent) e).getDamager();
                        EntityDamageByBlockEvent fake_block_damage = new EntityDamageByBlockEvent(b_damager, ent_victim, e.getCause(), e.getDamage());
                        Bukkit.getPluginManager().callEvent(fake_block_damage);
                    }
                    // Usado caso o dano que mata o jogador é causado por uma entidade.
                    if (e instanceof EntityDamageByEntityEvent) {
                        Entity ent_damager = ((EntityDamageByEntityEvent) e).getDamager();
                        EntityDamageByEntityEvent fake_entity_damage = new EntityDamageByEntityEvent(ent_damager, ent_victim, e.getCause(), e.getDamage());
                        Bukkit.getPluginManager().callEvent(fake_entity_damage);
                        // Usado caso a entidade é um jogador.
                        if (ent_damager instanceof Player) {
                            Player pd = (Player) ent_damager;

                            String kill_ab = Messages.ACTIONBAR_KILL.replace("&", "§").replace("%player%", pv.getDisplayName());
                            ActionBarAPI.sendActionBar(pd, kill_ab);
                            FakeMechanics.changeStatisticsKiller(pd);
                            SoundAPI.sendSound(pd, pd.getLocation(), Config.SOUND_KILL, 1,1);
                        }
                    }
                    FakeMechanics.sendDeath(pv);
                    FakeMechanics.changeStatisticsVictim(pv);
                    FakeMechanics.dropInventory(pv);
                    Animation.sendAnimation(pv);
                    pv.setGameMode(GameMode.SPECTATOR);
                    SoundAPI.sendSound(pv, pv.getLocation(), Config.SOUND_DEATH, 3, 1);
                    TitleAPI.sendTitle(pv, 2, 20 * time, 2, Randomizer.customtitles(), Randomizer.customsubtitles());
                    if (!Bukkit.getServer().isHardcore()) {
                        Tasks.normalTimer(pv);
                        return;
                    }
                    if (Bukkit.getServer().isHardcore()) {
                        Tasks.hardcoreTimer(pv);
                    }
                }
            }
        }
    }
}