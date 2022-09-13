package com.github.victortedesco.bds.api.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerDamageByEntityBeforeDeathEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private Entity damager;
    private EntityDamageEvent.DamageCause cause;
    private double damage;
    private double final_damage;
    private boolean isCancelled = false;

    public PlayerDamageByEntityBeforeDeathEvent(Player player, Entity damager, EntityDamageEvent.DamageCause cause, double damage, double final_damage) {
        this.player = player;
        this.damager = damager;
        this.cause = cause;
        this.damage = damage;
        this.final_damage = final_damage;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets the player who receive the damage.
     *
     * @return Player who took the damage.
     */
    @NotNull
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get the damage cause of the event
     *
     * @return Cause of the event
     */
    @NotNull
    public EntityDamageEvent.DamageCause getCause() {
        return this.cause;
    }

    /**
     * Change the damage cause of the event.
     *
     * @param cause Set the cause of the event
     */
    @NotNull
    public void setCause(EntityDamageEvent.DamageCause cause) {
        this.cause = cause;
    }

    /**
     * Get the amount of damage caused by the event.
     *
     * @return Damage of the event
     */
    @NotNull
    public double getDamage() {
        return this.damage;
    }

    /**
     * Get the amount of damage caused by the event.
     *
     * @param damage Set the damage of the event
     */
    @NotNull
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * Get the amount of damage reduced by armor, potions caused by the event.
     *
     * @return Final damage of the event
     */
    @NotNull
    public double getFinalDamage() {
        return this.final_damage;
    }

    /**
     * Change the amount of damage reduced by armor, potions caused by the event.
     *
     * @param damage Set the final damage of the event
     */
    @NotNull
    public void setFinalDamage(double damage) {
        this.final_damage = damage;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Whether this event should be cancelled.
     *
     * @param cancel Cancel the event.
     */
    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }

    /**
     * Gets the entity who dealt the damage.
     *
     * @return Entity that dealt the damage.
     */
    @Nullable
    public Entity getDamager() {
        return this.damager;
    }

    /**
     * Change the entity who dealt the damage.
     *
     * @param entity Set the entity that dealt the damage.
     */
    @Nullable
    public Entity setDamager(Entity entity) {
        return this.damager = entity;
    }
}
