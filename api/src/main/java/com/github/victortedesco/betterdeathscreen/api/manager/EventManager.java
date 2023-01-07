package com.github.victortedesco.betterdeathscreen.api.manager;

import com.github.victortedesco.betterdeathscreen.api.events.PlayerDamageBeforeDeathEvent;
import com.github.victortedesco.betterdeathscreen.api.events.PlayerDamageByBlockBeforeDeathEvent;
import com.github.victortedesco.betterdeathscreen.api.events.PlayerDamageByEntityBeforeDeathEvent;
import com.github.victortedesco.betterdeathscreen.api.events.PlayerDropInventoryEvent;
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

    private final HashMap<Player, Object[]> PLAYER_DAMAGE_BEFORE_DEATH = new HashMap<>();
    private final HashMap<Player, Object[]> PLAYER_DAMAGE_BY_BLOCK_BEFORE_DEATH = new HashMap<>();
    private final HashMap<Player, Object[]> PLAYER_DAMAGE_BY_ENTITY_BEFORE_DEATH = new HashMap<>();

    public void setPlayerDamageBeforeDeath(Player player, EntityDamageEvent event) {
        Object[] array = new Object[3];

        array[0] = event.getCause();
        array[1] = event.getDamage();
        array[2] = event.getFinalDamage();
        this.PLAYER_DAMAGE_BEFORE_DEATH.put(player, array);
    }

    public void sendPlayerDamageBeforeDeathEvent(Player player) {
        Object[] array = PLAYER_DAMAGE_BEFORE_DEATH.get(player);
        PlayerDamageBeforeDeathEvent event;

        try {
            event = new PlayerDamageBeforeDeathEvent(player, (EntityDamageEvent.DamageCause) array[0], (double) array[1], (double) array[2]);
        } catch (Exception exception) {
            event = new PlayerDamageBeforeDeathEvent(player, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        Bukkit.getPluginManager().callEvent(event);
    }

    public void setPlayerDamageByBlockBeforeDeath(Player player, EntityDamageByBlockEvent event) {
        Object[] array = new Object[4];

        array[0] = event.getDamager();
        array[1] = event.getCause();
        array[2] = event.getDamage();
        array[3] = event.getFinalDamage();
        this.PLAYER_DAMAGE_BY_BLOCK_BEFORE_DEATH.put(player, array);
    }

    public void sendPlayerDamageByBlockBeforeDeathEvent(Player player) {
        Object[] array = PLAYER_DAMAGE_BY_BLOCK_BEFORE_DEATH.get(player);
        PlayerDamageByBlockBeforeDeathEvent event;

        try {
            event = new PlayerDamageByBlockBeforeDeathEvent(player, (Block) array[0], (EntityDamageEvent.DamageCause) array[1], (double) array[2], (double) array[3]);
        } catch (Exception exception) {
            event = new PlayerDamageByBlockBeforeDeathEvent(player, null, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        Bukkit.getPluginManager().callEvent(event);
    }

    public void setPlayerDamageByEntityBeforeDeath(Player player, EntityDamageByEntityEvent event) {
        Object[] array = new Object[4];

        array[0] = event.getDamager();
        array[1] = event.getCause();
        array[2] = event.getDamage();
        array[3] = event.getFinalDamage();
        this.PLAYER_DAMAGE_BY_ENTITY_BEFORE_DEATH.put(player, array);
    }

    public void sendPlayerDamageByEntityBeforeDeathEvent(Player player) {
        Object[] array = this.PLAYER_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(player);
        PlayerDamageByEntityBeforeDeathEvent event;

        try {
            event = new PlayerDamageByEntityBeforeDeathEvent(player, (Entity) array[0], (EntityDamageEvent.DamageCause) array[1], (double) array[2], (double) array[3]);
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
