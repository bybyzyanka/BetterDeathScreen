package com.github.victortedesco.betterdeathscreen.bukkit.configuration;

import com.github.victortedesco.betterdeathscreen.api.configuration.Config;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class BukkitConfig extends ConfigurationHandler implements Config {

    private String language;
    private int respawnTime;
    private boolean notifyUpdates;
    private List<String> commandsOnDeath;
    private List<String> deathSounds;
    private List<String> killSounds;
    private List<String> countdownSounds;
    private List<String> respawnSounds;
    private String killMessageType;
    private String killedMessageType;
    private String killedByPlayerMessageType;
    private String countdownMessageType;
    private String instantRespawnPermission;
    private String adminPermission;
    private boolean queueTeleport;
    private boolean spectate;
    private boolean spectateKillerOnDeath;
    private boolean fly;
    private List<String> allowedCommands;

    @Override
    public void loadFields() {
        super.createFile("config");
        FileConfiguration config = super.getFileConfiguration("config");

        language = config.getString("misc.language", "en-US");
        respawnTime = config.getInt("misc.respawn-time", 10);
        notifyUpdates = config.getBoolean("misc.notify-updates", true);
        if (Bukkit.isHardcore()) respawnTime = 6;
        commandsOnDeath = config.getStringList("commands-on-death");
        deathSounds = config.getStringList("sound.death");
        countdownSounds = config.getStringList("sound.countdown");
        killSounds = config.getStringList("sound.kill");
        respawnSounds = config.getStringList("sound.respawn");
        killMessageType = config.getString("message-type.kill", "CHAT");
        killedMessageType = config.getString("message-type.killed", "TITLE");
        killedByPlayerMessageType = config.getString("message-type.killed-by-player", "TITLE");
        countdownMessageType = config.getString("message-type.countdown", "ACTIONBAR");
        instantRespawnPermission = config.getString("permissions.instant-respawn", "bds.instant-respawn");
        adminPermission = config.getString("permissions.admin", "bds.admin");
        queueTeleport = config.getBoolean("death-settings.queue-teleport", true);
        spectate = config.getBoolean("death-settings.allow-spectate", true);
        spectateKillerOnDeath = config.getBoolean("death-settings.spectate-killer-on-death", true);
        fly = config.getBoolean("death-settings.allow-fly", true);
        allowedCommands = config.getStringList("death-settings.allowed-commands");
    }

    @Override
    public String getLanguage() {
        return language;
    }

    @Override
    public int getRespawnTime() {
        return respawnTime;
    }

    @Override
    public boolean canNotifyUpdates() {
        return notifyUpdates;
    }

    @Override
    public List<String> getCommandsOnDeath() {
        return commandsOnDeath;
    }

    @Override
    public List<String> getDeathSounds() {
        return deathSounds;
    }

    @Override
    public List<String> getKillSounds() {
        return killSounds;
    }

    @Override
    public List<String> getCountdownSounds() {
        return countdownSounds;
    }

    @Override
    public List<String> getRespawnSounds() {
        return respawnSounds;
    }

    @Override
    public String getKillMessageType() {
        return killMessageType;
    }

    @Override
    public String getKilledMessageType() {
        return killedMessageType;
    }

    @Override
    public String getKilledByPlayerMessageType() {
        return killedByPlayerMessageType;
    }

    @Override
    public String getCountdownMessageType() {
        return countdownMessageType;
    }

    @Override
    public String getInstantRespawnPermission() {
        return instantRespawnPermission;
    }

    @Override
    public String getAdminPermission() {
        return adminPermission;
    }

    @Override
    public boolean useQueueTeleport() {
        return queueTeleport;
    }

    @Override
    public boolean canSpectate() {
        return spectate;
    }

    @Override
    public boolean willSpectateKillerOnDeath() {
        return spectateKillerOnDeath;
    }

    @Override
    public boolean canFly() {
        return fly;
    }

    @Override
    public List<String> getAllowedCommands() {
        return allowedCommands;
    }
}
