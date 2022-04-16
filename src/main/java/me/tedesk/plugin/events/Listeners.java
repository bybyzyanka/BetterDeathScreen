package me.tedesk.plugin.events;

import me.tedesk.plugin.BetterDeathScreen;
import me.tedesk.plugin.events.entity.DeathScreen;
import me.tedesk.plugin.events.entity.PlayerEvent;
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
