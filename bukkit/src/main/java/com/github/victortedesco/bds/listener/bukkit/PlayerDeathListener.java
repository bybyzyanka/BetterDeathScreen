package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.api.events.PlayerDamageBeforeDeathEvent;
import com.github.victortedesco.bds.api.events.PlayerDamageByBlockBeforeDeathEvent;
import com.github.victortedesco.bds.api.events.PlayerDamageByEntityBeforeDeathEvent;
import com.github.victortedesco.bds.api.events.PlayerDropInventoryEvent;
import com.github.victortedesco.bds.listener.Events;
import com.github.victortedesco.bds.utils.DeathMessage;
import com.github.victortedesco.bds.utils.Version;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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
import java.util.List;

public class PlayerDeathListener extends Events {

    public static List<String> BED_MESSAGE_SENT = new ArrayList<>();
    public static HashMap<String, ArrayList<Object>> LAST_DAMAGE_BEFORE_DEATH = new HashMap<>();
    public static HashMap<String, ArrayList<Object>> LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH = new HashMap<>();
    public static HashMap<String, ArrayList<Object>> LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH = new HashMap<>();
    public static HashMap<String, Entity> KILL_ASSIST = new HashMap<>();

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeathMonitor(PlayerDeathEvent e) {
        Player p = e.getEntity();

        PlayerDamageBeforeDeathEvent pdbd;
        PlayerDamageByEntityBeforeDeathEvent pdebd;
        PlayerDamageByBlockBeforeDeathEvent pdbbd;

        // Damage without blocks and entities.
        try {
            pdbd = new PlayerDamageBeforeDeathEvent(p, (EntityDamageEvent.DamageCause) LAST_DAMAGE_BEFORE_DEATH.get(p.getName()).get(0), (double) LAST_DAMAGE_BEFORE_DEATH.get(p.getName()).get(1), (double) LAST_DAMAGE_BEFORE_DEATH.get(p.getName()).get(2));
        } catch (Exception ex) {
            pdbd = new PlayerDamageBeforeDeathEvent(p, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        // Damage by entity
        try {
            pdebd = new PlayerDamageByEntityBeforeDeathEvent(p, (Entity) LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(p.getName()).get(0), (EntityDamageEvent.DamageCause) LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(p.getName()).get(1), (double) LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(p.getName()).get(2), (double) LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(p.getName()).get(3));
        } catch (Exception ex) {
            pdebd = new PlayerDamageByEntityBeforeDeathEvent(p, null, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        // Damage by block
        try {
            pdbbd = new PlayerDamageByBlockBeforeDeathEvent(p, (Block) LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.get(p.getName()).get(0), (EntityDamageEvent.DamageCause) LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.get(p.getName()).get(1), (double) LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.get(p.getName()).get(2), (double) LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.get(p.getName()).get(3));
        } catch (Exception ex) {
            pdbbd = new PlayerDamageByBlockBeforeDeathEvent(p, null, EntityDamageEvent.DamageCause.CUSTOM, 0, 0);
        }
        Bukkit.getPluginManager().callEvent(pdbd);
        Bukkit.getPluginManager().callEvent(pdebd);
        Bukkit.getPluginManager().callEvent(pdbbd);
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
            if (e.getDeathMessage().equals("BDS Handled Death")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (p.getWorld().getGameRuleValue("showDeathMessages").equals("true")) {
                            Bukkit.getConsoleSender().spigot().sendMessage(DeathMessage.getMessage(p, KILL_ASSIST.get(p.getName())));
                            for (Player ps : Bukkit.getOnlinePlayers()) {
                                ps.spigot().sendMessage(DeathMessage.getMessage(p, KILL_ASSIST.get(p.getName())));
                            }
                            KILL_ASSIST.remove(p.getName());
                        }
                    }
                }.runTaskLater(BetterDeathScreen.getInstance(), 1);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (p.getGameMode() != GameMode.SPECTATOR) {
                            if (!BED_MESSAGE_SENT.contains(p.getName()) && p.getBedSpawnLocation() == null) {
                                TranslatableComponent no_bed = new TranslatableComponent("tile.bed.notValid");
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    no_bed = new TranslatableComponent("block.minecraft.bed.not_valid");
                                }
                                if (BetterDeathScreen.getVersion().value >= Version.v1_16.value) {
                                    no_bed = new TranslatableComponent("block.minecraft.spawn.not_valid");
                                }
                                p.spigot().sendMessage(no_bed);
                                BED_MESSAGE_SENT.add(p.getName());
                            }
                            cancel();
                        }
                    }
                }.runTaskTimer(BetterDeathScreen.getInstance(), 1, 20);
            }
        }
    }
}
