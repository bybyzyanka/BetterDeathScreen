package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.cryptomorin.xseries.ReflectionUtils;
import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerDeathListener implements Listener {

    private static final HashMap<Player, Entity> KILL_ASSISTS = new HashMap<>();
    private static final HashMap<Player, ItemStack[]> EQUIPPED_ARMOR = new HashMap<>();
    private static final HashMap<Player, ItemStack> ITEM_IN_MAIN_HAND = new HashMap<>();
    private static final HashMap<Player, ItemStack> ITEM_IN_OFF_HAND = new HashMap<>();

    public static HashMap<Player, Entity> getKillAssists() {
        return KILL_ASSISTS;
    }

    public static HashMap<Player, ItemStack[]> getEquippedArmor() {
        return EQUIPPED_ARMOR;
    }

    public static HashMap<Player, ItemStack> getItemInMainHand() {
        return ITEM_IN_MAIN_HAND;
    }

    public static HashMap<Player, ItemStack> getItemInOffHand() {
        return ITEM_IN_OFF_HAND;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();

        BetterDeathScreenAPI.getEventManager().sendPlayerDamageBeforeDeathEvent(player);
        BetterDeathScreenAPI.getEventManager().sendPlayerDamageByBlockBeforeDeathEvent(player);
        BetterDeathScreenAPI.getEventManager().sendPlayerDamageByEntityBeforeDeathEvent(player);
        BetterDeathScreenAPI.getEventManager().sendPlayerDropInventoryEvent(player, event.getDrops(), event.getKeepInventory());

        if (!event.getKeepInventory()) {
            player.getInventory().setArmorContents(null);
            player.getInventory().clear();
        } else {
            getEquippedArmor().put(player, player.getInventory().getArmorContents());
            player.getInventory().setArmorContents(null);
            if (ReflectionUtils.VER < 9) {
                getItemInMainHand().put(player, player.getItemInHand());
                player.setItemInHand(null);
            } else {
                getItemInMainHand().put(player, player.getInventory().getItemInMainHand());
                player.getInventory().setItemInMainHand(null);
                getItemInOffHand().put(player, player.getInventory().getItemInOffHand());
                player.getInventory().setItemInOffHand(null);
            }
        }
        player.updateInventory();
        if (!event.getKeepLevel()) {
            player.setLevel(0);
            player.setExp(0);
        }

        if (event.getDeathMessage() != null && player.getWorld().getGameRuleValue("showDeathMessages").equalsIgnoreCase("true")) {
            if (event.getDeathMessage().equals("BetterDeathScreen")) {
                Bukkit.getScheduler().runTaskLaterAsynchronously(BetterDeathScreen.getInstance(), () -> {
                    BetterDeathScreenAPI.getDeathMessageCreator().sendDeathMessage(player, getKillAssists().get(player));
                    getKillAssists().remove(player);
                }, 1L);
            } else {
                Bukkit.getConsoleSender().sendMessage(event.getDeathMessage());
                Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(event.getDeathMessage()));
            }
        }
    }
}
