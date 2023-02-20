package com.github.victortedesco.betterdeathscreen.api.manager;

import com.github.victortedesco.betterdeathscreen.api.events.PlayerDamageBeforeDeathEvent;
import com.github.victortedesco.betterdeathscreen.api.events.PlayerDamageByBlockBeforeDeathEvent;
import com.github.victortedesco.betterdeathscreen.api.events.PlayerDamageByEntityBeforeDeathEvent;
import com.github.victortedesco.betterdeathscreen.api.events.PlayerDropInventoryEvent;
import com.github.victortedesco.betterdeathscreen.api.utils.DamageInfo;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EventManager {

    private final HashMap<Player, DamageInfo<?>> PLAYER_DAMAGE_BEFORE_DEATH = new HashMap<>();
    private final HashMap<Player, DamageInfo<Block>> PLAYER_DAMAGE_BY_BLOCK_BEFORE_DEATH = new HashMap<>();
    private final HashMap<Player, DamageInfo<Entity>> PLAYER_DAMAGE_BY_ENTITY_BEFORE_DEATH = new HashMap<>();

    public void setPlayerDamageBeforeDeath(Player player, EntityDamageEvent event) {
        DamageInfo<?> damageInfo = new DamageInfo<>(null, event.getCause(), event.getDamage(), event.getFinalDamage());
        this.PLAYER_DAMAGE_BEFORE_DEATH.put(player, damageInfo);
    }

    public void sendPlayerDamageBeforeDeathEvent(Player player) {
        DamageInfo<?> damageInfo = PLAYER_DAMAGE_BEFORE_DEATH.get(player);
        PlayerDamageBeforeDeathEvent event;

        try {
            event = new PlayerDamageBeforeDeathEvent(player, damageInfo.getDamageCause(), damageInfo.getDamage(), damageInfo.getFinalDamage());
        } catch (Exception exception) {
            event = new PlayerDamageBeforeDeathEvent(player, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        Bukkit.getPluginManager().callEvent(event);
    }

    public void setPlayerDamageByBlockBeforeDeath(Player player, EntityDamageByBlockEvent event) {
        DamageInfo<Block> damageInfo = new DamageInfo<>(event.getDamager(), event.getCause(), event.getDamage(), event.getFinalDamage());
        this.PLAYER_DAMAGE_BY_BLOCK_BEFORE_DEATH.put(player, damageInfo);
    }

    public void sendPlayerDamageByBlockBeforeDeathEvent(Player player) {
        DamageInfo<Block> damageInfo = PLAYER_DAMAGE_BY_BLOCK_BEFORE_DEATH.get(player);
        PlayerDamageByBlockBeforeDeathEvent event;

        try {
            event = new PlayerDamageByBlockBeforeDeathEvent(player, damageInfo.getDamager(), damageInfo.getDamageCause(), damageInfo.getDamage(), damageInfo.getFinalDamage());
        } catch (Exception exception) {
            event = new PlayerDamageByBlockBeforeDeathEvent(player, null, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        Bukkit.getPluginManager().callEvent(event);
    }

    public void setPlayerDamageByEntityBeforeDeath(Player player, EntityDamageByEntityEvent event) {
        DamageInfo<Entity> damageInfo = new DamageInfo<>(event.getDamager(), event.getCause(), event.getDamage(), event.getFinalDamage());
        this.PLAYER_DAMAGE_BY_ENTITY_BEFORE_DEATH.put(player, damageInfo);
    }

    public void sendPlayerDamageByEntityBeforeDeathEvent(Player player) {
        DamageInfo<Entity> damageInfo = this.PLAYER_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(player);
        PlayerDamageByEntityBeforeDeathEvent event;

        try {
            event = new PlayerDamageByEntityBeforeDeathEvent(player, damageInfo.getDamager(), damageInfo.getDamageCause(), damageInfo.getDamage(), damageInfo.getFinalDamage());
        } catch (Exception exception) {
            event = new PlayerDamageByEntityBeforeDeathEvent(player, null, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        Bukkit.getPluginManager().callEvent(event);
    }

    public void sendPlayerDropInventoryEvent(Player player, List<ItemStack> drops, boolean keepInventory) {
        PlayerDropInventoryEvent event;

        try {
            event = new PlayerDropInventoryEvent(player, drops);
        } catch (Exception exception) {
            event = new PlayerDropInventoryEvent(player, Collections.emptyList());
        }
        event.setCancelled(keepInventory);
        Bukkit.getPluginManager().callEvent(event);
    }

    public void resetPlayerDamage(Player player) {
        EntityDamageEvent entityDamageEvent = new EntityDamageEvent(player, EntityDamageEvent.DamageCause.CUSTOM, 0);
        EntityDamageByBlockEvent entityDamageByBlockEvent = new EntityDamageByBlockEvent(null, player, EntityDamageEvent.DamageCause.CUSTOM, 0);
        EntityDamageByEntityEvent entityDamageByEntityEvent = new EntityDamageByEntityEvent(null, player, EntityDamageEvent.DamageCause.CUSTOM, 0);

        setPlayerDamageBeforeDeath(player, entityDamageEvent);
        setPlayerDamageByBlockBeforeDeath(player, entityDamageByBlockEvent);
        setPlayerDamageByEntityBeforeDeath(player, entityDamageByEntityEvent);
    }
}
