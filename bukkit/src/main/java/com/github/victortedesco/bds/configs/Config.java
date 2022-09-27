package com.github.victortedesco.bds.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Config {

    public static Set<String> DEAD_PLAYERS = new HashSet<>();
    public static String LANGUAGE;
    public static boolean USE_DEFAULT_WORLD_SPAWN;
    public static boolean USE_SAFE_TELEPORT;
    public static boolean USE_KILL_CAM;
    public static Location SPAWN;
    public static Location VIP_SPAWN;
    public static List<String> SOUND_DEATH;
    public static List<String> SOUND_COUNTDOWN;
    public static List<String> SOUND_RESPAWN;
    public static List<String> SOUND_KILL;
    public static int TIME;
    public static String INSTANT_RESPAWN;
    public static String VIP;
    public static String KEEP_XP;
    public static String ADMIN;
    public static String ANIMATION;
    public static boolean QUEUE_TELEPORT;
    public static boolean SPECTATE_ENTITY;
    public static boolean IGNORE_WALLS;
    public static boolean HOTBAR_TELEPORT_SPECTATOR;
    public static boolean MOVE_SPECTATOR;
    public static boolean ALLOW_COMMANDS_WHILE_DEAD;

    @SuppressWarnings("ConstantConditions")
    public static void loadConfigs() {
        FileConfiguration config = ConfigHandler.getConfig("config");
        FileConfiguration locations = ConfigHandler.getConfig("locations");

        LANGUAGE = config.getString("misc.language");
        TIME = config.getInt("misc.respawn-time");
        USE_DEFAULT_WORLD_SPAWN = config.getBoolean("misc.use-default-world-spawn");
        USE_SAFE_TELEPORT = config.getBoolean("misc.use-safe-teleport");
        USE_KILL_CAM = config.getBoolean("misc.use-kill-camera");

        try {
            SPAWN = new Location(Bukkit.getWorld(locations.getString("normal.world")), locations.getDouble("normal.X"), locations.getDouble("normal.Y"), locations.getDouble("normal.Z"), (float) locations.getDouble("normal.yaw"), (float) locations.getDouble("normal.pitch"));
        } catch (Exception exception) {
            SPAWN = null;
        }
        try {
            VIP_SPAWN = new Location(Bukkit.getWorld(locations.getString("vip.world")), locations.getDouble("vip.X"), locations.getDouble("vip.Y"), locations.getDouble("vip.Z"), (float) locations.getDouble("vip.yaw"), (float) locations.getDouble("vip.pitch"));
        } catch (Exception exception) {
            VIP_SPAWN = null;
        }

        SOUND_DEATH = config.getStringList("sound.death");
        SOUND_COUNTDOWN = config.getStringList("sound.countdown");
        SOUND_RESPAWN = config.getStringList("sound.respawn");
        SOUND_KILL = config.getStringList("sound.kill");

        INSTANT_RESPAWN = config.getString("permissions.instant-respawn");
        VIP = config.getString("permissions.vip-spawn");
        KEEP_XP = config.getString("permissions.keep-xp");
        ADMIN = config.getString("permissions.admin");

        ANIMATION = config.getString("animation.type");

        QUEUE_TELEPORT = config.getBoolean("spectator-settings.queue-teleport");
        SPECTATE_ENTITY = config.getBoolean("spectator-settings.allow-spectate");
        HOTBAR_TELEPORT_SPECTATOR = config.getBoolean("spectator-settings.allow-teleport-with-keys");
        IGNORE_WALLS = config.getBoolean("spectator-settings.ignore-walls");
        MOVE_SPECTATOR = config.getBoolean("spectator-settings.allow-move");
        ALLOW_COMMANDS_WHILE_DEAD = config.getBoolean("spectator-settings.allow-commands");
    }
}
