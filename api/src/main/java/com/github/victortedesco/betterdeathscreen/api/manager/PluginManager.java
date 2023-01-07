package com.github.victortedesco.betterdeathscreen.api.manager;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class PluginManager {

    public void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage("[BetterDeathScreen] " + ChatColor.translateAlternateColorCodes('&', message));
    }

    public void fixViaVersionConfiguration() {
        if (Bukkit.getServer().getPluginManager().getPlugin("ViaVersion") != null) {
            AbstractViaConfig config = (AbstractViaConfig) Via.getConfig();
            config.set("use-new-deathmessages", false);
            config.saveConfig();
            config.reloadConfig();
        }
    }
}
