package com.github.victortedesco.betterdeathscreen.bukkit.configuration;

import com.github.victortedesco.betterdeathscreen.api.configuration.Messages;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class BukkitMessages extends ConfigurationHandler implements Messages {

    private List<String> incompatible;
    private List<String> enabled;
    private List<String> disabled;
    private List<String> updateAvailable;
    private String reloaded;
    private String blockedCommand;
    private String withoutPermission;
    private List<String> help;
    private String nonHardcoreCountdown;
    private String hardcoreCountdown;
    private String timeSingular;
    private String timePlural;
    private List<String> killed;
    private List<String> killedByPlayer;
    private List<String> kill;

    @Override
    public void loadFields() {
        FileConfiguration messages;
        try {
            super.createFile("messages_" + BetterDeathScreen.getConfiguration().getLanguage());
            messages = super.getFileConfiguration("messages_" + BetterDeathScreen.getConfiguration().getLanguage());
        } catch (Exception exception) {
            exception.printStackTrace();
            super.createFile("messages_en-US");
            messages = super.getFileConfiguration("messages_en-US");
        }
        incompatible = messages.getStringList("plugin.incompatible");
        enabled = messages.getStringList("plugin.enabled");
        disabled = messages.getStringList("plugin.disabled");
        updateAvailable = messages.getStringList("plugin.update-available");
        blockedCommand = ChatColor.translateAlternateColorCodes('&', messages.getString("misc.command-blocked", "&cYou can´t use that command, because you are dead."));
        reloaded = ChatColor.translateAlternateColorCodes('&', messages.getString("commands.reloaded", "&aPlugin successfully reloaded."));
        withoutPermission = ChatColor.translateAlternateColorCodes('&', messages.getString("commands.without-permission", "&cYou don´t have permission to use this command."));
        help = messages.getStringList("commands.help");
        nonHardcoreCountdown = ChatColor.translateAlternateColorCodes('&', messages.getString("respawn.non-hardcore-countdown", "&7You will respawn in &6%time%."));
        hardcoreCountdown = ChatColor.translateAlternateColorCodes('&', messages.getString("respawn.hardcore-countdown", "&cYou will never respawn again..."));
        timeSingular = ChatColor.translateAlternateColorCodes('&', messages.getString("misc.time.singular", " second"));
        timePlural = ChatColor.translateAlternateColorCodes('&', messages.getString("misc.time.plural", " seconds"));
        killed = messages.getStringList("death.killed");
        killedByPlayer = messages.getStringList("death.killed-by-player");
        kill = messages.getStringList("death.kill");
    }

    @Override
    public List<String> getIncompatible() {
        return incompatible;
    }

    @Override
    public List<String> getEnabled() {
        return enabled;
    }

    @Override
    public List<String> getDisabled() {
        return disabled;
    }

    @Override
    public List<String> getUpdateAvailable() {
        return updateAvailable;
    }

    @Override
    public String getReloaded() {
        return reloaded;
    }

    @Override
    public String getWithoutPermission() {
        return withoutPermission;
    }

    @Override
    public List<String> getHelp() {
        return help;
    }

    @Override
    public String getNonHardcoreCountdown() {
        return nonHardcoreCountdown;
    }

    @Override
    public String getHardcoreCountdown() {
        return hardcoreCountdown;
    }

    @Override
    public String getTimeSingular() {
        return timeSingular;
    }

    @Override
    public String getTimePlural() {
        return timePlural;
    }

    @Override
    public List<String> getKilled() {
        return killed;
    }

    @Override
    public List<String> getKilledByPlayer() {
        return killedByPlayer;
    }

    @Override
    public List<String> getKill() {
        return kill;
    }

    @Override
    public String getBlockedCommand() {
        return blockedCommand;
    }
}
