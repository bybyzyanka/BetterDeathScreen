package com.github.victortedesco.betterdeathscreen.bukkit.listener;

import com.github.victortedesco.betterdeathscreen.api.utils.Version;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import com.github.victortedesco.betterdeathscreen.bukkit.listener.betterdeathscreen.PlayerDamageByEntityBeforeDeathListener;
import com.github.victortedesco.betterdeathscreen.bukkit.listener.betterdeathscreen.PlayerDropInventoryListener;
import com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit.*;
import com.github.victortedesco.betterdeathscreen.bukkit.listener.packets.LoginPacketListener;
import org.bukkit.Bukkit;

public final class EventRegistry {

    public void setupListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerDamageByEntityBeforeDeathListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerDropInventoryListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityDamageListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityMountListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityRegainHealthListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new EntityTargetListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new FoodLevelChangeListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new InventoryInteractListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerItemHeldListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerRespawnListener(), BetterDeathScreen.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), BetterDeathScreen.getInstance());

        if (Version.getServerVersion().getValue() >= Version.v1_12.getValue())
            Bukkit.getPluginManager().registerEvents(new EntityPickupItemListener(), BetterDeathScreen.getInstance());
        else
            Bukkit.getPluginManager().registerEvents(new PlayerPickupItemListener(), BetterDeathScreen.getInstance());
        new LoginPacketListener().setupListener();
    }
}
