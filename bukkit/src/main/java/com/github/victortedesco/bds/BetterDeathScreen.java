package com.github.victortedesco.bds;

import com.github.victortedesco.bds.commands.MainCommand;
import com.github.victortedesco.bds.commands.MainTabComplete;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.ConfigHandler;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.listener.Events;
import com.github.victortedesco.bds.utils.PlayerUtils;
import com.github.victortedesco.bds.utils.Tasks;
import com.github.victortedesco.bds.utils.Version;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterDeathScreen extends JavaPlugin {

    public static BetterDeathScreen getInstance() {
        return getPlugin(BetterDeathScreen.class);
    }

    public static Version getVersion() {
        return Version.getServerVersion();
    }

    public static boolean isPlaceholderAPIActive() {
        return (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null);
    }

    public static boolean isViaVersionActive() {
        return (Bukkit.getPluginManager().getPlugin("ViaVersion") != null);
    }

    public static void sendConsoleMessage(String text) {
        Bukkit.getConsoleSender().sendMessage("[BetterDeathScreen] " + ChatColor.translateAlternateColorCodes('&', text));
    }

    public static void createAndLoadConfigs() {
        ConfigHandler.createConfig("config");
        ConfigHandler.createConfig("locations");
        Config.loadConfigs();
        try {
            ConfigHandler.createConfig("messages_" + Config.LANGUAGE);
        } catch (Exception exception) {
            sendConsoleMessage("&cThe plugin will shutdown, because &f" + Config.LANGUAGE + " &cdoes not exist on the configurations.");
            sendConsoleMessage("&cO plugin será desligado, porque &f" + Config.LANGUAGE + " &cnão existe nas configurações.");
            Bukkit.getScheduler().runTaskLater(getInstance(), () -> getInstance().getPluginLoader().disablePlugin(getInstance()), 1);
            return;
        }
        Messages.loadMessages();
    }

    @SuppressWarnings({"ConstantConditions", "unused"})
    @Override
    public void onEnable() {
        createAndLoadConfigs();
        if (getVersion() == Version.UNKNOWN) {
            for (String incompatible : Messages.INCOMPATIBLE) {
                sendConsoleMessage(incompatible.replace("%server_version%", "(" + Version.getMinecraftVersion() + ")"));
            }
            Bukkit.getScheduler().runTaskLater(this, () -> getPluginLoader().disablePlugin(this), 1);
            return;
        }
        Events.setup();
        Bukkit.getScheduler().runTaskLater(this, this::fixViaVersionConfig, 1);
        getCommand("bds").setExecutor(new MainCommand());
        getCommand("bds").setTabCompleter(new MainTabComplete());
        Metrics metrics = new Metrics(this, 14729);
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (players.getGameMode() == GameMode.SPECTATOR && (players.getHealth() == 0.1 || !players.hasPermission(Config.ADMIN)) && PlayerUtils.isHardcore(players)) {
                Config.DEAD_PLAYERS.add(players.getName());
                Tasks.startTimer(players);
            }
        }
        for (String enabled : Messages.ENABLED) {
            sendConsoleMessage(enabled.replace("%plugin_version%", "(v" + getDescription().getVersion() + ")"));
        }
        sendConsoleMessage("&fMinecraft " + Version.getMinecraftVersion());
    }

    @Override
    public void onDisable() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (Config.DEAD_PLAYERS.contains(players.getName())) {
                Tasks.performRespawn(players);
            }
        }
        for (String disabled : Messages.DISABLED) {
            sendConsoleMessage(disabled.replace("%plugin_version%", "(v" + getDescription().getVersion() + ")"));
        }
    }

    private void fixViaVersionConfig() {
        if (isViaVersionActive()) {
            AbstractViaConfig config = (AbstractViaConfig) Via.getConfig();
            config.set("use-new-deathmessages", false);
            config.saveConfig();
            config.reloadConfig();
        }
    }
}