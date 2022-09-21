package com.github.victortedesco.bds.listener;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.listener.bds.PlayerDamageBeforeEntityListener;
import com.github.victortedesco.bds.listener.bds.PlayerDropInventoryListener;
import com.github.victortedesco.bds.listener.bukkit.*;
import com.github.victortedesco.bds.listener.packets.JoinPacketListener;
import com.github.victortedesco.bds.listener.packets.SpectatorPacketLimiter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class Events {

    public static void setup() {
        BetterDeathScreen plugin = BetterDeathScreen.getInstance();
        PluginManager pluginManager = Bukkit.getPluginManager();

        // Bukkit
        pluginManager.registerEvents(new PlayerDamageBeforeEntityListener(), plugin);
        pluginManager.registerEvents(new PlayerDropInventoryListener(), plugin);
        pluginManager.registerEvents(new EntityDamageListener(), plugin);
        pluginManager.registerEvents(new EntityRegainHealthListener(), plugin);
        pluginManager.registerEvents(new GameModeChangeListener(), plugin);
        pluginManager.registerEvents(new PlayerCommandListener(), plugin);
        pluginManager.registerEvents(new PlayerConnectionListener(), plugin);
        pluginManager.registerEvents(new PlayerDeathListener(), plugin);
        pluginManager.registerEvents(new PlayerMoveListener(), plugin);
        pluginManager.registerEvents(new PlayerRespawnListener(), plugin);
        pluginManager.registerEvents(new PlayerTeleportListener(), plugin);

        JoinPacketListener.changeHeartIcon();
        SpectatorPacketLimiter.cancelSpectate();
    }
}
