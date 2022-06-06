package me.tedesk;

import me.tedesk.commands.MainCommand;
import me.tedesk.configs.Config;
import me.tedesk.configs.ConfigHandler;
import me.tedesk.configs.Messages;
import me.tedesk.events.Listeners;
import me.tedesk.systems.Tasks;
import me.tedesk.utils.Version;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterDeathScreen extends JavaPlugin {

    public static BetterDeathScreen plugin;
    public static Version version;
    PluginDescriptionFile pdf = this.getDescription();

    public static void logger(String text) {
        Bukkit.getConsoleSender().sendMessage("[BetterDeathScreen] " + text);
    }

    public static boolean veryNewVersion() {
        if (version == Version.v1_19)
            return true;
        if (version == Version.v1_18)
            return true;
        if (version == Version.v1_17)
            return true;
        return false;
    }

    public static boolean newVersion() {
        if (version == Version.v1_16)
            return true;
        if (version == Version.v1_15)
            return true;
        if (version == Version.v1_14)
            return true;
        if (version == Version.v1_13)
            return true;
        if (version == Version.v1_12)
            return true;
        return false;
    }

    public static boolean oldVersion() {
        if (version == Version.v1_11)
            return true;
        if (version == Version.v1_10)
            return true;
        if (version == Version.v1_9)
            return true;
        if (version == Version.v1_8)
            return true;
        return false;
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
            plugin.getPluginLoader().disablePlugin(plugin);
            return;
        }
        Messages.loadMessages();
    }

    @Override
    public void onEnable() {
        plugin = this;
        version = Version.getServerVersion();

        if (version == Version.UNKNOWN) {
            for (String incompatible : Messages.INCOMPATIBLE) {
                logger(ChatColor.translateAlternateColorCodes('&', incompatible.replace("%server_version%", "(" + Version.getServerVersion() + ")")));
            }
            plugin.getPluginLoader().disablePlugin(plugin);
            return;
        }
        createAndLoadConfigs();
        Listeners.setup();
        plugin.getCommand("bds").setExecutor(new MainCommand());
        Metrics metrics = new Metrics(this, 14729);
        for (String enabled : Messages.ENABLED) {
            logger(ChatColor.translateAlternateColorCodes('&', enabled.replace("%plugin_version%", "(v" + pdf.getVersion() + ")")));
        }
        logger("§fMinecraft " + version.toString().replace("_", ".").replace("v", ""));
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