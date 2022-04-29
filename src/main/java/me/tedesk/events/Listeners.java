package me.tedesk.events;

import me.tedesk.BetterDeathScreen;
import me.tedesk.events.entity.DeathScreen;
import me.tedesk.events.entity.PlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class Listeners implements Listener {

    public static void setup() {
        BetterDeathScreen pl = BetterDeathScreen.plugin;
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DeathScreen(), pl);
        pm.registerEvents(new PlayerEvent(), pl);
    }
}
