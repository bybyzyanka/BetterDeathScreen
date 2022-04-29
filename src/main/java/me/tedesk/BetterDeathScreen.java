package me.tedesk;

import me.tedesk.commands.MainCommand;
import me.tedesk.configs.Config;
import me.tedesk.configs.ConfigHandler;
import me.tedesk.configs.Messages;
import me.tedesk.events.Listeners;
import me.tedesk.systems.Tasks;
import me.tedesk.utils.Metrics;
import me.tedesk.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterDeathScreen extends JavaPlugin {
    /* Primeiramente, um olá para você que está lendo o código, acho que a parte mais interessante são as classes ActionBarAPI e
    TitleAPI, então se você precisar de algo assim, fique a vontade para se inspirar.
     */
    public static Version version;
    public static BetterDeathScreen plugin;
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
        Config.loadConfigs();
        ConfigHandler.createConfig("messages_" + Config.LANGUAGE);
        Messages.loadMessages();
    }

    @Override
    public void onEnable() {
        plugin = this;
        version = Version.getServerVersion();

        if (version == Version.UNKNOWN) {
            logger("§cYour server version is behind 1.8! §f" + "(" + plugin.getServer().getBukkitVersion() + ")");
            logger("§cIf you think this is an error, contact the author: " + pdf.getAuthors());
            logger("§cThe plugin will now shutdown.");
            plugin.getPluginLoader().disablePlugin(this);
            return;
        }

        if (veryNewVersion() || newVersion() || oldVersion()) {
            Listeners.setup();
            createAndLoadConfigs();

            if (!Config.CHANGE_VIEW_SPECTATOR) {
                Tasks.blockSpectatorView();
            }

            plugin.getCommand("bds").setExecutor(new MainCommand());
            Metrics metrics = new Metrics(this, 14729);
            logger("§aPlugin enabled! (v" + pdf.getVersion() + ")");
            logger("§fMinecraft " + version.toString().replace("_", ".").replace("v", ""));
        }
    }

    @Override
    public void onDisable() {
        logger("§cPlugin disabled! (v" + pdf.getVersion() + ")");
    }
}