package me.tedesk.api;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerAPI {

    public static boolean isStackEmpty(ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0;
    }

    public static int dropExpOnDeath(Player player) {
        // Te amo Minecraft Wiki.
        return Math.min(player.getLevel() * 7, 100);
    }
}
