package com.github.victortedesco.bds.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerDamageBeforeDeathEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private EntityDamageEvent.DamageCause cause;
    private double damage;
    private double final_damage;
    private boolean isCancelled = false;

    public PlayerDamageBeforeDeathEvent(Player player, EntityDamageEvent.DamageCause cause, double damage, double final_damage) {
        this.player = player;
        this.cause = cause;
        this.damage = damage;
        this.final_damage = final_damage;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
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
    public void setCause(EntityDamageEvent.DamageCause cause) {
        this.cause = cause;
    }

    /**
     * Get the amount of damage caused by the event.
     *
     * @return Damage of the event
     */
    public double getDamage() {
        return this.damage;
    }

    /**
     * Get the amount of damage caused by the event.
     *
     * @param damage Set the damage of the event
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * Get the amount of damage reduced by armor, potions caused by the event.
     *
     * @return Final damage of the event
     */
    public double getFinalDamage() {
        return this.final_damage;
    }

    /**
     * Change the amount of damage reduced by armor, potions caused by the event.
     *
     * @param damage Set the final damage of the event
     */
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
}