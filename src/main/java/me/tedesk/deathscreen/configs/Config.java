package me.tedesk.deathscreen.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class Config {

    public static Location DEFAULT_WORLD_SPAWN;
    public static String LANGUAGE;
    public static String DEATH;
    public static String COUNTDOWN;
    public static String RESPAWN;
    public static int TIME;
    public static String KEEPXP;
    public static String ADMIN;
    public static String ANIMATION;

    public static void loadConfigs() {
        FileConfiguration config = ConfigHandler.getConfig("config");

        LANGUAGE = config.getString("misc.language");
        DEFAULT_WORLD_SPAWN = new Location(Bukkit.getWorlds().get(0), Bukkit.getWorlds().get(0).getSpawnLocation().getX(), Bukkit.getWorlds().get(0).getSpawnLocation().getY(), Bukkit.getWorlds().get(0).getSpawnLocation().getZ(), Bukkit.getWorlds().get(0).getSpawnLocation().getYaw(), Bukkit.getWorlds().get(0).getSpawnLocation().getPitch());
        DEATH = config.getString("sound.death");
        COUNTDOWN = config.getString("sound.countdown");
        RESPAWN = config.getString("sound.respawn");

        TIME = config.getInt("time.duration");

        KEEPXP = config.getString("perms.keepxp");
        ADMIN = config.getString("perms.admin");

        ANIMATION = config.getString("animation.type");
    }
}
