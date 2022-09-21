package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.api.events.PlayerDamageBeforeDeathEvent;
import com.github.victortedesco.bds.api.events.PlayerDamageByBlockBeforeDeathEvent;
import com.github.victortedesco.bds.api.events.PlayerDamageByEntityBeforeDeathEvent;
import com.github.victortedesco.bds.api.events.PlayerDropInventoryEvent;
import com.github.victortedesco.bds.utils.DeathMessage;
import com.github.victortedesco.bds.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class PlayerDeathListener implements Listener {

    public static HashMap<String, ArrayList<Object>> DAMAGE_BEFORE_DEATH = new HashMap<>();
    public static HashMap<String, ArrayList<Object>> DAMAGE_BY_BLOCK_BEFORE_DEATH = new HashMap<>();
    public static HashMap<String, ArrayList<Object>> DAMAGE_BY_ENTITY_BEFORE_DEATH = new HashMap<>();
    public static HashMap<String, Entity> KILL_ASSIST = new HashMap<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeathMonitor(PlayerDeathEvent event) {
        Player player = event.getEntity();

        sendDamageEvents(player);

        PlayerDropInventoryEvent playerDropInventoryEvent = new PlayerDropInventoryEvent(player, event.getDrops());
        if (event.getKeepInventory()) {
            playerDropInventoryEvent.setCancelled(true);
            playerDropInventoryEvent.setDrops(Collections.emptyList());
        }
        if (!event.getKeepInventory()) {
            player.getInventory().setArmorContents(null);
            player.getInventory().clear();
            player.updateInventory();
        }
        Bukkit.getPluginManager().callEvent(playerDropInventoryEvent);
        if (!event.getKeepLevel()) {
            player.setLevel(0);
            player.setExp(0);
        }

        sendDeathMessage(player, event);
    }

    private void sendDamageEvents(Player player) {
        // Damage without blocks and entities.
        PlayerDamageBeforeDeathEvent playerDamageBeforeDeathEvent;
        try {
            playerDamageBeforeDeathEvent = new PlayerDamageBeforeDeathEvent(player, (EntityDamageEvent.DamageCause) DAMAGE_BEFORE_DEATH.get(player.getName()).get(0), (double) DAMAGE_BEFORE_DEATH.get(player.getName()).get(1), (double) DAMAGE_BEFORE_DEATH.get(player.getName()).get(2));
        } catch (Exception ex) {
            playerDamageBeforeDeathEvent = new PlayerDamageBeforeDeathEvent(player, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        // Damage by entity
        PlayerDamageByEntityBeforeDeathEvent playerDamageByEntityBeforeDeathEvent;
        try {
            playerDamageByEntityBeforeDeathEvent = new PlayerDamageByEntityBeforeDeathEvent(player, (Entity) DAMAGE_BY_ENTITY_BEFORE_DEATH.get(player.getName()).get(0), (EntityDamageEvent.DamageCause) DAMAGE_BY_ENTITY_BEFORE_DEATH.get(player.getName()).get(1), (double) DAMAGE_BY_ENTITY_BEFORE_DEATH.get(player.getName()).get(2), (double) DAMAGE_BY_ENTITY_BEFORE_DEATH.get(player.getName()).get(3));
        } catch (Exception ex) {
            playerDamageByEntityBeforeDeathEvent = new PlayerDamageByEntityBeforeDeathEvent(player, null, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        // Damage by block
        PlayerDamageByBlockBeforeDeathEvent playerDamageByBlockBeforeDeathEvent;
        try {
            playerDamageByBlockBeforeDeathEvent = new PlayerDamageByBlockBeforeDeathEvent(player, (Block) DAMAGE_BY_BLOCK_BEFORE_DEATH.get(player.getName()).get(0), (EntityDamageEvent.DamageCause) DAMAGE_BY_BLOCK_BEFORE_DEATH.get(player.getName()).get(1), (double) DAMAGE_BY_BLOCK_BEFORE_DEATH.get(player.getName()).get(2), (double) DAMAGE_BY_BLOCK_BEFORE_DEATH.get(player.getName()).get(3));
        } catch (Exception ex) {
            playerDamageByBlockBeforeDeathEvent = new PlayerDamageByBlockBeforeDeathEvent(player, null, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        Bukkit.getPluginManager().callEvent(playerDamageBeforeDeathEvent);
        Bukkit.getPluginManager().callEvent(playerDamageByEntityBeforeDeathEvent);
        Bukkit.getPluginManager().callEvent(playerDamageByBlockBeforeDeathEvent);
    }

    @SuppressWarnings("deprecation")
    private void sendDeathMessage(Player player, PlayerDeathEvent event) {
        // For some reason, creating a new PlayerDeathEvent does not send the death message.
        if (event.getDeathMessage() != null && player.getWorld().getGameRuleValue("showDeathMessages").equals("true")) {
            if (event.getDeathMessage().equals("BDS Handled Death")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_12.value) {
                            // 1.11 and before does not have this method.
                            Bukkit.getConsoleSender().spigot().sendMessage(DeathMessage.getMessage(player, KILL_ASSIST.get(player.getName())));
                        } else {
                            Bukkit.getConsoleSender().sendMessage(player.getName() + " died");
                        }
                        for (Player players : Bukkit.getOnlinePlayers()) {
                            players.spigot().sendMessage(DeathMessage.getMessage(player, KILL_ASSIST.get(player.getName())));
                        }
                    }
                }.runTaskLater(BetterDeathScreen.getInstance(), 1);
            } else {
                Bukkit.getConsoleSender().sendMessage(event.getDeathMessage());
                for (Player players : Bukkit.getOnlinePlayers()) {
                    players.sendMessage(event.getDeathMessage());
                }
            }
        }
    }
}
