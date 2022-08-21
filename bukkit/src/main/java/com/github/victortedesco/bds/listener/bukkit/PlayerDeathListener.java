package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.api.events.PlayerDamageBeforeDeathEvent;
import com.github.victortedesco.bds.api.events.PlayerDamageByBlockBeforeDeathEvent;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.api.events.PlayerDamageByEntityBeforeDeathEvent;
import com.github.victortedesco.bds.listener.Events;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerDeathListener extends Events {

    public static HashMap<String, ArrayList<Object>> LAST_DAMAGE_BEFORE_DEATH = new HashMap<>();
    public static HashMap<String, ArrayList<Object>> LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH = new HashMap<>();
    public static HashMap<String, ArrayList<Object>> LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeathMonitor(PlayerDeathEvent e) {
        Player p = e.getEntity();
        // For some reason, creating a new PlayerDeathEvent does not send the death message.
        if (e.getDeathMessage() != null) {
            Bukkit.broadcastMessage(e.getDeathMessage());
        }

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
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (p.hasPermission(Config.KEEP_XP)) {
            e.setDroppedExp(0);
            e.setKeepLevel(true);
        }

        if (!p.hasPermission(Config.KEEP_XP) && p.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            p.setExp(0);
            p.setLevel(0);
        }
    }
}
