package com.github.victortedesco.betterdeathscreen.api.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerDamageByBlockBeforeDeathEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private Block damager;
    private EntityDamageEvent.DamageCause cause;
    private double damage;
    private double finalDamage;
    private boolean isCancelled = false;

    public PlayerDamageByBlockBeforeDeathEvent(Player player, Block damager, EntityDamageEvent.DamageCause cause, double damage, double finalDamage) {
        this.player = player;
        this.damager = damager;
        this.cause = cause;
        this.damage = damage;
        this.finalDamage = finalDamage;
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
     * @return Damage value of the event
     */
    public double getDamage() {
        return this.damage;
    }

    /**
     * Get the amount of damage caused by the event.
     *
     * @param damage Set the damage value of the event
     */
    public void setDamage(double damage) {
        this.damage = damage;
    }

    /**
     * Get the amount of damage reduced by armor, potions caused by the event.
     *
     * @return Final damage value of the event
     */
    public double getFinalDamage() {
        return this.finalDamage;
    }

    /**
     * Change the amount of damage reduced by armor, potions caused by the event.
     *
     * @param damage Set the final damage value of the event
     */
    public void setFinalDamage(double damage) {
        this.finalDamage = damage;
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
     * Gets the block who dealt the damage.
     *
     * @return Block that dealt the damage.
     */
    @Nullable
    public Block getDamager() {
        return this.damager;
    }

    /**
     * Change the block who dealt the damage.
     *
     * @param block Set the block that dealt the damage.
     */
    @Nullable
    public Block setDamager(Block block) {
        return this.damager = block;
    }
}
