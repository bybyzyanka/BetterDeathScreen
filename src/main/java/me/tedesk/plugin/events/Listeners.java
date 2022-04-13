package me.tedesk.plugin.events;

import me.tedesk.plugin.BetterDeathScreen;
import me.tedesk.plugin.events.death.DeathScreen;
import me.tedesk.plugin.events.death.PlayerJoin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class Listeners implements Listener {

    public static void Setup() {
        BetterDeathScreen pl = BetterDeathScreen.plugin;
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new DeathScreen(), pl);
        pm.registerEvents(new PlayerJoin(), pl);
    }
}
