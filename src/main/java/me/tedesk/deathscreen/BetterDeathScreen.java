package me.tedesk.deathscreen;

import me.tedesk.deathscreen.utils.Metrics;
import me.tedesk.deathscreen.utils.Version;
import me.tedesk.deathscreen.configs.Config;
import me.tedesk.deathscreen.configs.ConfigHandler;
import me.tedesk.deathscreen.configs.Messages;
import me.tedesk.deathscreen.events.Listeners;
import me.tedesk.deathscreen.systems.commands.ReloadConfig;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterDeathScreen extends JavaPlugin {
    /* Primeiramente, um olá para você que está lendo o código, acho que a parte mais interessante são as classes ActionBarAPI e
    TitleAPI, então se você precisar de algo assim, fique a vontade para se inspirar.
     */
    public static Version version;
    public static BetterDeathScreen plugin;
    public static void logger(String text) {
        Bukkit.getConsoleSender().sendMessage("§7[BetterDeathScreen] " + text);
    }
    PluginDescriptionFile pdf = this.getDescription();

    @Override
    public void onEnable() {
        plugin = this;
        version = Version.getServerVersion();
        this.getCommand("bdsreload").setExecutor(new ReloadConfig());

        Listeners.Setup();
        createAndLoadConfigs();

        if (version == Version.UNKNOWN){
            logger("§cYour server version is behind 1.8! §f" + "(" + plugin.getServer().getBukkitVersion() + ")");
            logger("§cIf you think this is an error, write on the review of the plugin page.");
            logger("§cThe plugin will now shutdown.");
            plugin.getPluginLoader().disablePlugin(this);
        } else {
            logger("§aPlugin enabled! (v" + pdf.getVersion() + ")");
            logger("§fMinecraft " + version.toString().replace("_", ".").replace("v", ""));
        }
        Metrics metrics = new Metrics(this, 14729);
    }

    @Override
    public void onDisable() {
        logger("§cPlugin disabled! (v" + pdf.getVersion() + ")");
    }


    public static void createAndLoadConfigs() {
        ConfigHandler.createConfig("config");
        Config.loadConfigs();
        ConfigHandler.createConfig("messages_" + Config.LANGUAGE);
        Messages.loadMessages();

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
}