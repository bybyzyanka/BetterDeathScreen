package com.github.victortedesco.bds.listener;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.listener.bds.PlayerDropInventoryListener;
import com.github.victortedesco.bds.listener.bukkit.*;
import com.github.victortedesco.bds.listener.packets.SpectatorPacketLimiter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class Events implements Listener {

    public static void setup() {
        BetterDeathScreen pl = BetterDeathScreen.plugin;
        PluginManager pm = Bukkit.getPluginManager();

        // Bukkit
        pm.registerEvents(new PlayerDropInventoryListener(), pl);
        pm.registerEvents(new EntityDamageListener(), pl);
        pm.registerEvents(new EntityRegainHealthListener(), pl);
        pm.registerEvents(new GameModeChangeListener(), pl);
        pm.registerEvents(new PlayerCommandListener(), pl);
        pm.registerEvents(new PlayerConnectionListener(), pl);
        pm.registerEvents(new PlayerDeathListener(), pl);
        pm.registerEvents(new PlayerMoveListener(), pl);
        pm.registerEvents(new PlayerRespawnListener(), pl);
        pm.registerEvents(new PlayerTeleportListener(), pl);

        // Packets
        SpectatorPacketLimiter.cancelSpectate();
    }
}
