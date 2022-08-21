package com.github.victortedesco.bds.listener.bds;

import com.github.victortedesco.bds.api.events.PlayerDropInventoryEvent;
import com.github.victortedesco.bds.listener.Events;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class PlayerDropInventoryListener extends Events {

    @SuppressWarnings("deprecation")
    @EventHandler(ignoreCancelled = true)
    public void onDrop(PlayerDropInventoryEvent e) {
        Player p = e.getPlayer();

        if (Bukkit.getServer().getPluginManager().getPlugin("AngelChest") != null) {
            e.setCancelled(true);
            return;
        }
        if (Bukkit.getServer().getPluginManager().getPlugin("Graves") != null) {
            e.setCancelled(true);
            return;
        }

        if (p.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            e.setDrops(Collections.emptyList());
        }
        if (p.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            for (ItemStack items : e.getDrops()) {
                p.getWorld().dropItemNaturally(p.getLocation(), items);
            }
            p.getInventory().setArmorContents(null);
            p.getInventory().clear();
            p.updateInventory();
        }
    }
}
