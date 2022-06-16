package me.tedesk.bds.events;

import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.events.bukkit.*;
import me.tedesk.bds.events.packets.DeathPacketListener;
import me.tedesk.bds.events.packets.SpectatorPacketLimiter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class Listeners implements Listener {

    public static void setup() {
        BetterDeathScreen pl = BetterDeathScreen.plugin;
        PluginManager pm = Bukkit.getPluginManager();
        // Bukkit
        pm.registerEvents(new EntityDamageListener(), pl);
        pm.registerEvents(new PlayerCommandListener(), pl);
        pm.registerEvents(new PlayerDeathListener(), pl);
        pm.registerEvents(new PlayerConnectionListener(), pl);
        pm.registerEvents(new PlayerTeleportListener(), pl);
        // Packets
        DeathPacketListener.cancelDeathScreen();
        SpectatorPacketLimiter.cancelSpectate();
    }
}
