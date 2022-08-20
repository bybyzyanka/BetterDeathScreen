package me.tedesk.bds.api.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageByBlockBeforeDeathEvent extends PlayerDamageBeforeDeathEvent{

    private Block damager;

    public PlayerDamageByBlockBeforeDeathEvent(final Player player, Block block, EntityDamageEvent.DamageCause cause, double damage, double final_damage) {
        super(player, cause, damage, final_damage);
        this.damager = block;
    }

    /**
     * Gets the block who dealt the damage.
     *
     * @return Block that dealt the damage.
     */
    public Block getDamager() {
        return this.damager;
    }

    /**
     * Change the block who dealt the damage.
     *
     * @param block Set the block that dealt the damage.
     */
    public Block setDamager(Block block) {
        return this.damager = block;
    }
}
