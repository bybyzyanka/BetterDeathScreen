package com.github.victortedesco.betterdeathscreen.bukkit.configuration;


import com.github.victortedesco.betterdeathscreen.api.configuration.Config;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class BukkitConfig extends ConfigurationHandler implements Config {

    private String language;
    private int respawnTime;
    private boolean safeTeleport;
    private List<String> commandsOnDeath;
    private List<String> deathSounds;
    private List<String> killSounds;
    private List<String> countdownSounds;
    private List<String> respawnSounds;
    private String killMessageType;
    private String killedMessageType;
    private String killedByPlayerMessageType;
    private String hardcoreCountdownMessageType;
    private String nonHardcoreCountdownMessageType;
    private String animationType;
    private String instantRespawnPermission;
    private String adminPermission;
    private boolean queueTeleport;
    private boolean spectate;
    private boolean fly;
    private List<String> allowedCommands;

    @Override
    public void loadFields() {
        super.createFile("config");
        FileConfiguration config = super.getFileConfiguration("config");

        language = config.getString("misc.language", "en-US");
        respawnTime = config.getInt("misc.respawn-time", 10);
        safeTeleport = config.getBoolean("misc.use-safe-teleport", true);
        commandsOnDeath = config.getStringList("commands-on-death");
        deathSounds = config.getStringList("sound.death");
        countdownSounds = config.getStringList("sound.countdown");
        killSounds = config.getStringList("sound.kill");
        respawnSounds = config.getStringList("sound.respawn");
        killMessageType = config.getString("message-type.kill", "CHAT");
        killedMessageType = config.getString("message-type.killed", "TITLE");
        killedByPlayerMessageType = config.getString("message-type.killed-by-player", "TITLE");
        nonHardcoreCountdownMessageType = config.getString("message-type.non-hardcore-countdown", "ACTIONBAR");
        hardcoreCountdownMessageType = config.getString("message-type.hardcore-countdown", "ACTIONBAR");
        animationType = config.getString("animation.type", "BLOOD");
        instantRespawnPermission = config.getString("permissions.instant-respawn", "bds.instant-respawn");
        adminPermission = config.getString("permissions.admin", "bds.admin");
        queueTeleport = config.getBoolean("death-settings.queue-teleport", true);
        spectate = config.getBoolean("death-settings.allow-spectate", true);
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
    public boolean useSafeTeleport() {
        return safeTeleport;
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
    public String getHardcoreCountdownMessageType() {
        return hardcoreCountdownMessageType;
    }

    @Override
    public String getNonHardcoreCountdownMessageType() {
        return nonHardcoreCountdownMessageType;
    }

    @Override
    public String getAnimationType() {
        return animationType;
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
    public boolean canFly() {
        return fly;
    }

    @Override
    public List<String> getAllowedCommands() {
        return allowedCommands;
    }
}