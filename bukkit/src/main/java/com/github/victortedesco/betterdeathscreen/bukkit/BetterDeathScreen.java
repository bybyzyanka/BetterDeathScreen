package com.github.victortedesco.betterdeathscreen.bukkit;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.api.utils.Version;
import com.github.victortedesco.betterdeathscreen.bukkit.commands.MainCommand;
import com.github.victortedesco.betterdeathscreen.bukkit.commands.MainTabCompleter;
import com.github.victortedesco.betterdeathscreen.bukkit.configuration.BukkitConfig;
import com.github.victortedesco.betterdeathscreen.bukkit.configuration.BukkitMessages;
import com.github.victortedesco.betterdeathscreen.bukkit.listener.EventRegistry;
import com.github.victortedesco.betterdeathscreen.bukkit.utils.DeathTasks;
import com.github.victortedesco.betterdeathscreen.bukkit.utils.RespawnTasks;
import com.github.victortedesco.betterdeathscreen.bukkit.utils.updater.UpdateChecker;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.configuration.AbstractViaConfig;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterDeathScreen extends JavaPlugin {

    private static final DeathTasks DEATH_TASKS = new DeathTasks();
    private static final RespawnTasks RESPAWN_TASKS = new RespawnTasks();
    private static final BukkitConfig CONFIG = new BukkitConfig();
    private static final BukkitMessages MESSAGES = new BukkitMessages();

    public static DeathTasks getDeathTasks() {
        return DEATH_TASKS;
    }

    public static RespawnTasks getRespawnTasks() {
        return RESPAWN_TASKS;
    }

    public static BukkitConfig getConfiguration() {
        return CONFIG;
    }

    public static BukkitMessages getMessages() {
        return MESSAGES;
    }

    public static BetterDeathScreen getInstance() {
        return getPlugin(BetterDeathScreen.class);
    }

    public static void createAndLoadConfigurationsAndMessages() {
        getConfiguration().loadFields();
        getMessages().loadFields();
    }

    public static void sendConsoleMessage(String message) {
        Bukkit.getConsoleSender().sendMessage("[BetterDeathScreen] " + ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public void onEnable() {
        boolean forceDisable = false;
        createAndLoadConfigurationsAndMessages();
        try {
            Player.class.getMethod("spigot");
        } catch (NoSuchMethodException exception) {
            forceDisable = true;
        }
        if (Version.getServerVersion() == Version.UNKNOWN) forceDisable = true;
        if (forceDisable) {
            getMessages().getIncompatible().forEach(BetterDeathScreen::sendConsoleMessage);
            getServer().getScheduler().runTaskLater(this, () -> getServer().getPluginManager().disablePlugin(this), 1L);
            return;
        }
        new EventRegistry().setupListeners();
        new UpdateChecker();
        fixViaVersionConfiguration();
        getCommand("bds").setExecutor(new MainCommand());
        getCommand("bds").setTabCompleter(new MainTabCompleter());
        new Metrics(this, 17249);
        for (Player player : getServer().getOnlinePlayers()) {
            if (!getServer().isHardcore()) break;
            if (player.getGameMode() == GameMode.SPECTATOR && !player.hasPermission(getConfiguration().getAdminPermission())) {
                BetterDeathScreenAPI.getPlayerManager().getDeadPlayers().add(player);
                BetterDeathScreen.getRespawnTasks().startCountdown(player);
            }
        }
        getMessages().getEnabled().forEach(BetterDeathScreen::sendConsoleMessage);
        sendConsoleMessage("&fMinecraft " + Version.getMinecraftVersion());
    }

    @Override
    public void onDisable() {
        getServer().getOnlinePlayers().forEach(player -> BetterDeathScreen.getRespawnTasks().performRespawn(player, false));
        getMessages().getDisabled().forEach(BetterDeathScreen::sendConsoleMessage);
    }

    private void fixViaVersionConfiguration() {
        if (Bukkit.getServer().getPluginManager().getPlugin("ViaVersion") != null) {
            AbstractViaConfig config = (AbstractViaConfig) Via.getConfig();
            config.set("use-new-deathmessages", false);
            config.saveConfig();
            config.reloadConfig();
        }
    }
}
