package me.tedesk.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static List<String> DEAD_PLAYERS = new ArrayList<>();
    public static Location DEFAULT_WORLD_SPAWN;
    public static String LANGUAGE;
    public static String SOUND_DEATH;
    public static String SOUND_COUNTDOWN;
    public static String SOUND_RESPAWN;
    public static String SOUND_KILL;
    public static int TIME;
    public static String KEEP_XP;
    public static String ADMIN;
    public static String ANIMATION;
    public static boolean CHANGE_VIEW_SPECTATOR;
    public static boolean HOTBAR_TELEPORT_SPECTATOR;

    public static void loadConfigs() {
        FileConfiguration config = ConfigHandler.getConfig("config");

        LANGUAGE = config.getString("misc.language");
        DEFAULT_WORLD_SPAWN = new Location(Bukkit.getWorlds().get(0), Bukkit.getWorlds().get(0).getSpawnLocation().getX(), Bukkit.getWorlds().get(0).getSpawnLocation().getY(), Bukkit.getWorlds().get(0).getSpawnLocation().getZ(), Bukkit.getWorlds().get(0).getSpawnLocation().getYaw(), Bukkit.getWorlds().get(0).getSpawnLocation().getPitch());

        SOUND_DEATH = config.getString("sound.death");
        SOUND_COUNTDOWN = config.getString("sound.countdown");
        SOUND_RESPAWN = config.getString("sound.respawn");
        SOUND_KILL = config.getString("sound.kill");

        TIME = config.getInt("time.duration");

        KEEP_XP = config.getString("perms.keep-xp");
        ADMIN = config.getString("perms.admin");

        ANIMATION = config.getString("animation.type");

        CHANGE_VIEW_SPECTATOR = config.getBoolean("spectator.change-view");
        HOTBAR_TELEPORT_SPECTATOR = config.getBoolean("spectator.teleport-with-keys");
    }
}
