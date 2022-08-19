package me.tedesk.bds.events;

import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.events.bds.PlayerDropInventoryListener;
import me.tedesk.bds.events.bukkit.*;
import me.tedesk.bds.events.packets.SpectatorPacketLimiter;
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
        pm.registerEvents(new PlayerTeleportListener(), pl);

        // Packets
        SpectatorPacketLimiter.cancelSpectate();
    }
}
