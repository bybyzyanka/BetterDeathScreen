package com.github.victortedesco.bds.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerDropInventoryEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private boolean isCancelled = false;
    private List<ItemStack> drops;

    public PlayerDropInventoryEvent(Player player, List<ItemStack> drops) {
        this.player = player;
        this.drops = drops;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    /**
     * Gets the player who died
     *
     * @return Player who died
     */
    @NotNull
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get the drops of the event
     *
     * @return List of drops of the event
     */
    @NotNull
    public List<ItemStack> getDrops() {
        return this.drops;
    }

    /**
     * Change the drops of the event
     *
     * @param drops Change the list of drops of the event
     */
    public void setDrops(List<ItemStack> drops) {
        this.drops = drops;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * Whether this event should be cancelled.
     *
     * @param cancel Whether BetterDeathScreen should handle the player's inventory drops.
     */
    @Override
    public void setCancelled(boolean cancel) {
        isCancelled = cancel;
    }
}
