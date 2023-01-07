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
import org.bstats.bukkit.Metrics;
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
            for (String incompatible : getMessages().getIncompatible())
                BetterDeathScreenAPI.getPluginManager().sendConsoleMessage(incompatible);
            getServer().getScheduler().runTaskLater(this, () -> getServer().getPluginManager().disablePlugin(this), 1L);
            return;
        }
        new EventRegistry().setupListeners();
        BetterDeathScreenAPI.getPluginManager().fixViaVersionConfiguration();
        getCommand("bds").setExecutor(new MainCommand());
        getCommand("bds").setTabCompleter(new MainTabCompleter());
        Metrics metrics = new Metrics(this, 17249);
        for (String enabled : getMessages().getEnabled())
            BetterDeathScreenAPI.getPluginManager().sendConsoleMessage(enabled);
        BetterDeathScreenAPI.getPluginManager().sendConsoleMessage("&fMinecraft " + Version.getMinecraftVersion());
    }

    @Override
    public void onDisable() {
        for (Player player : getServer().getOnlinePlayers())
            BetterDeathScreen.getRespawnTasks().performRespawn(player, false);
        for (String disabled : getMessages().getDisabled())
            BetterDeathScreenAPI.getPluginManager().sendConsoleMessage(disabled);
    }
}
