package com.github.victortedesco.bds.listener.bds;

import com.github.victortedesco.bds.api.events.PlayerDropInventoryEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class PlayerDropInventoryListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDrop(PlayerDropInventoryEvent event) {
        Player player = event.getPlayer();

        if (Bukkit.getServer().getPluginManager().getPlugin("AngelChest") != null) {
            event.setCancelled(true);
            return;
        }
        if (Bukkit.getServer().getPluginManager().getPlugin("Graves") != null) {
            event.setCancelled(true);
            return;
        }

        for (ItemStack items : event.getDrops()) {
            player.getWorld().dropItemNaturally(player.getLocation(), items);
        }
    }
}
