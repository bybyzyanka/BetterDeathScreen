package com.github.victortedesco.bds;

import com.github.victortedesco.bds.commands.MainCommand;
import com.github.victortedesco.bds.commands.MainTabComplete;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.ConfigHandler;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.listener.Events;
import com.github.victortedesco.bds.utils.Tasks;
import com.github.victortedesco.bds.utils.Version;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterDeathScreen extends JavaPlugin {

    PluginDescriptionFile pdf = this.getDescription();

    public static BetterDeathScreen getInstance() {
        return getPlugin(BetterDeathScreen.class);
    }

    public static Version getVersion() {
        return Version.getServerVersion();
    }

    public static boolean isPAPIActive() {
        return (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null);
    }

    public static void logger(String text) {
        Bukkit.getConsoleSender().sendMessage("[BetterDeathScreen] " + text);
    }

    public static void createAndLoadConfigs() {
        ConfigHandler.createConfig("config");
        ConfigHandler.createConfig("locations");
        Config.loadConfigs();
        try {
            ConfigHandler.createConfig("messages_" + Config.LANGUAGE);
        } catch (RuntimeException e) {
            logger("The plugin will shutdown, because " + Config.LANGUAGE + " does not exist on the configurations.");
            logger("O plugin será desligado, porque " + Config.LANGUAGE + " não existe nas configurações.");
            getInstance().getPluginLoader().disablePlugin(BetterDeathScreen.getInstance());
            return;
        }
        Messages.loadMessages();
    }

    @SuppressWarnings({"ConstantConditions", "unused"})
    @Override
    public void onEnable() {
        if (getVersion() == Version.UNKNOWN) {
            for (String incompatible : Messages.INCOMPATIBLE) {
                logger(ChatColor.translateAlternateColorCodes('&', incompatible.replace("%server_version%", "(" + Version.getServerVersion() + ")")));
            }
            getPluginLoader().disablePlugin(this);
            return;
        }
        createAndLoadConfigs();
        Events.setup();
        getCommand("bds").setExecutor(new MainCommand());
        getCommand("bds").setTabCompleter(new MainTabComplete());
        Metrics metrics = new Metrics(this, 14729);
        for (String enabled : Messages.ENABLED) {
            logger(ChatColor.translateAlternateColorCodes('&', enabled.replace("%plugin_version%", "(v" + pdf.getVersion() + ")")));
        }
        logger("§fMinecraft " + getVersion().toString().replace("_", ".").replace("v", ""));
    }

    @Override
    public void onDisable() {
        for (String disabled : Messages.DISABLED) {
            logger(ChatColor.translateAlternateColorCodes('&', disabled.replace("%plugin_version%", "(v" + pdf.getVersion() + ")")));
        }
        for (Player ps : Bukkit.getOnlinePlayers()) {
            if (Config.DEAD_PLAYERS.contains(ps.getName())) {
                Tasks.performRespawn(ps);
            }
        }
    }
}