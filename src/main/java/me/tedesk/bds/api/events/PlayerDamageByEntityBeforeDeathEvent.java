package me.tedesk.bds.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageByEntityBeforeDeathEvent extends PlayerDamageBeforeDeathEvent{

    private Entity damager;

    public PlayerDamageByEntityBeforeDeathEvent(final Player player, Entity entity, EntityDamageEvent.DamageCause cause, double damage, double final_damage) {
        super(player, cause, damage, final_damage);
        this.damager = entity;
    }

    /**
     * Gets the entity who dealt the damage.
     *
     * @return Entity that dealt the damage.
     */
    public Entity getDamager() {
        return this.damager;
    }

    /**
     * Change the entity who dealt the damage.
     *
     * @param entity Set the entity that dealt the damage.
     */
    public Entity setDamager(Entity entity) {
        return this.damager = entity;
    }
}
