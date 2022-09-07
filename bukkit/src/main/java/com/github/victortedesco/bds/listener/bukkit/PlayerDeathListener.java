package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.api.events.PlayerDamageBeforeDeathEvent;
import com.github.victortedesco.bds.api.events.PlayerDamageByBlockBeforeDeathEvent;
import com.github.victortedesco.bds.api.events.PlayerDamageByEntityBeforeDeathEvent;
import com.github.victortedesco.bds.api.events.PlayerDropInventoryEvent;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.listener.Events;
import com.github.victortedesco.bds.utils.DeathMessage;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PlayerDeathListener extends Events {

    public static HashMap<String, ArrayList<Object>> LAST_DAMAGE_BEFORE_DEATH = new HashMap<>();
    public static HashMap<String, ArrayList<Object>> LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH = new HashMap<>();
    public static HashMap<String, ArrayList<Object>> LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH = new HashMap<>();

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeathMonitor(PlayerDeathEvent e) {
        Player p = e.getEntity();

        // Damage without blocks and entities.
        try {
            PlayerDamageBeforeDeathEvent pdbd = new PlayerDamageBeforeDeathEvent(p, (EntityDamageEvent.DamageCause) LAST_DAMAGE_BEFORE_DEATH.get(p.getName()).get(0), (double) LAST_DAMAGE_BEFORE_DEATH.get(p.getName()).get(1), (double) LAST_DAMAGE_BEFORE_DEATH.get(p.getName()).get(2));
            Bukkit.getPluginManager().callEvent(pdbd);
        } catch (Exception ex) {
            PlayerDamageBeforeDeathEvent pdbd = new PlayerDamageBeforeDeathEvent(p, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
            Bukkit.getPluginManager().callEvent(pdbd);
        }
        // Damage by entity
        try {
            PlayerDamageByEntityBeforeDeathEvent pdebd = new PlayerDamageByEntityBeforeDeathEvent(p, (Entity) LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(p.getName()).get(0), (EntityDamageEvent.DamageCause) LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(p.getName()).get(1), (double) LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(p.getName()).get(2), (double) LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(p.getName()).get(3));
            Bukkit.getPluginManager().callEvent(pdebd);
        } catch (Exception ex) {
            PlayerDamageByEntityBeforeDeathEvent pdebd = new PlayerDamageByEntityBeforeDeathEvent(p, null, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
            Bukkit.getPluginManager().callEvent(pdebd);
        }
        // Damage by block
        try {
            PlayerDamageByBlockBeforeDeathEvent pdbbd = new PlayerDamageByBlockBeforeDeathEvent(p, (Block) LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.get(p.getName()).get(0), (EntityDamageEvent.DamageCause) LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.get(p.getName()).get(1), (double) LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.get(p.getName()).get(2), (double) LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.get(p.getName()).get(3));
            Bukkit.getPluginManager().callEvent(pdbbd);
        } catch (Exception ex) {
            PlayerDamageByBlockBeforeDeathEvent pdbbd = new PlayerDamageByBlockBeforeDeathEvent(p, null, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
            Bukkit.getPluginManager().callEvent(pdbbd);
        }

        PlayerDropInventoryEvent drop_inv = new PlayerDropInventoryEvent(p, e.getDrops());
        if (e.getKeepInventory()) {
            drop_inv.setCancelled(true);
            drop_inv.setDrops(Collections.emptyList());
        }
        if (!e.getKeepInventory()) {
            p.getInventory().setArmorContents(null);
            p.getInventory().clear();
            p.updateInventory();
        }
        Bukkit.getPluginManager().callEvent(drop_inv);
        if (!e.getKeepLevel()) {
            p.setLevel(0);
            p.setExp(0);
        }

        // For some reason, creating a new PlayerDeathEvent does not send the death message.
        if (e.getDeathMessage() != null) {
            if (e.getDeathMessage().equals("BDS Handled Death") && p.getWorld().getGameRuleValue("showDeathMessages").equals("true")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.getConsoleSender().spigot().sendMessage(DeathMessage.getMessage(p));
                        for (Player ps : Bukkit.getOnlinePlayers()) {
                            ps.spigot().sendMessage(DeathMessage.getMessage(p));
                        }
                    }
                }.runTaskLater(BetterDeathScreen.getInstance(), 1);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDeathLowest(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (p.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            e.setKeepInventory(false);
            if (p.hasPermission(Config.KEEP_XP)) {
                e.setKeepLevel(true);
                e.setNewExp((int) p.getExp());
                e.setNewLevel(p.getLevel());
            }
            if (!p.hasPermission(Config.KEEP_XP)) {
                e.setDroppedExp(Math.min(100, p.getLevel() * 7));
                e.setKeepLevel(false);
                e.setNewExp(0);
                e.setNewLevel(0);
            }
        }
        if (p.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            e.setKeepInventory(true);
            e.setKeepLevel(true);
            e.setNewExp((int) p.getExp());
            e.setNewLevel(p.getLevel());
        }
        e.setNewTotalExp(p.getTotalExperience());
    }
}
