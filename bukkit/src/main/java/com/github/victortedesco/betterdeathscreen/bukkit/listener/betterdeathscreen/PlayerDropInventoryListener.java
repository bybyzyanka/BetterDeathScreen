package com.github.victortedesco.betterdeathscreen.bukkit.listener.betterdeathscreen;

import com.github.victortedesco.betterdeathscreen.api.events.PlayerDropInventoryEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerDropInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onDropInventory(PlayerDropInventoryEvent event) {
        Player player = event.getPlayer();

        if (Bukkit.getServer().getPluginManager().getPlugin("AngelChest") != null) return;
        if (Bukkit.getServer().getPluginManager().getPlugin("Graves") != null) return;

        for (ItemStack item : event.getDrops()) player.getWorld().dropItemNaturally(player.getLocation(), item);
    }
}
