package com.github.victortedesco.bds.api.events.bukkit;

import com.github.victortedesco.bds.api.events.Events;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.ArrayList;

public class PlayerRespawnListener extends Events {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        // Resets all event related to last damage.
        ArrayList<Object> default_damage = new ArrayList<>();
        default_damage.add(EntityDamageEvent.DamageCause.CUSTOM);
        default_damage.add(0);
        default_damage.add(0);
        PlayerDeathListener.LAST_DAMAGE_BEFORE_DEATH.put(p.getName(), default_damage);

        ArrayList<Object> ent_damage = new ArrayList<>();
        ent_damage.add(null);
        ent_damage.add(EntityDamageEvent.DamageCause.CUSTOM);
        ent_damage.add(0);
        ent_damage.add(0);
        PlayerDeathListener.LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.put(p.getName(), ent_damage);

        ArrayList<Object> block_damage = new ArrayList<>();
        block_damage.add(null);
        block_damage.add(EntityDamageEvent.DamageCause.CUSTOM);
        block_damage.add(0);
        block_damage.add(0);
        PlayerDeathListener.LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.put(p.getName(), block_damage);
    }
}
