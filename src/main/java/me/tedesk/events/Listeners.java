package me.tedesk.events;

import me.tedesk.BetterDeathScreen;
import me.tedesk.configs.Config;
import me.tedesk.events.packets.DeathPacketListener;
import me.tedesk.events.packets.SpectatorPacketLimiter;
import me.tedesk.events.bukkit.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class Listeners implements Listener {

    public static void setup() {
        BetterDeathScreen pl = BetterDeathScreen.plugin;
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new EntityDamageListener(), pl);
        pm.registerEvents(new PlayerDeathListener(), pl);
        pm.registerEvents(new PlayerJoinListener(), pl);
        pm.registerEvents(new PlayerTeleportListener(), pl);
        DeathPacketListener.cancelDeathScreen();
        if (!Config.SPECTATE_ENTITY) {
            SpectatorPacketLimiter.cancelSpectate();
        }
    }
}
