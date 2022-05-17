package me.tedesk.configs;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Config {

    public static List<UUID> DEAD_PLAYERS = new ArrayList<>();
    public static Location DEFAULT_WORLD_SPAWN;
    public static String LANGUAGE;
    public static String SOUND_DEATH;
    public static String SOUND_COUNTDOWN;
    public static String SOUND_RESPAWN;
    public static String SOUND_KILL;
    public static Float SOUND_DEATH_VOLUME;
    public static Float SOUND_COUNTDOWN_VOLUME;
    public static Float SOUND_RESPAWN_VOLUME;
    public static Float SOUND_KILL_VOLUME;
    public static Float SOUND_DEATH_PITCH;
    public static Float SOUND_COUNTDOWN_PITCH;
    public static Float SOUND_RESPAWN_PITCH;
    public static Float SOUND_KILL_PITCH;
    public static int TIME;
    public static String KEEP_XP;
    public static String ADMIN;
    public static String ANIMATION;
    public static boolean SPECTATE_ENTITY;
    public static boolean HOTBAR_TELEPORT_SPECTATOR;
    public static boolean MOVE_SPECTATOR;

    public static void loadConfigs() {
        FileConfiguration config = ConfigHandler.getConfig("config");

        LANGUAGE = config.getString("misc.language");
        DEFAULT_WORLD_SPAWN = new Location(Bukkit.getWorlds().get(0), Bukkit.getWorlds().get(0).getSpawnLocation().getX(), Bukkit.getWorlds().get(0).getSpawnLocation().getY(), Bukkit.getWorlds().get(0).getSpawnLocation().getZ(), Bukkit.getWorlds().get(0).getSpawnLocation().getYaw(), Bukkit.getWorlds().get(0).getSpawnLocation().getPitch());

        SOUND_DEATH = config.getString("sound.type.death");
        SOUND_COUNTDOWN = config.getString("sound.type.countdown");
        SOUND_RESPAWN = config.getString("sound.type.respawn");
        SOUND_KILL = config.getString("sound.type.kill");

        SOUND_DEATH_VOLUME = (float) config.getDouble("sound.volume.death");
        SOUND_COUNTDOWN_VOLUME = (float) config.getDouble("sound.volume.countdown");
        SOUND_RESPAWN_VOLUME = (float) config.getDouble("sound.volume.respawn");
        SOUND_KILL_VOLUME = (float) config.getDouble("sound.volume.kill");

        SOUND_DEATH_PITCH = (float) config.getDouble("sound.pitch.death");
        SOUND_COUNTDOWN_PITCH = (float) config.getDouble("sound.pitch.countdown");
        SOUND_RESPAWN_PITCH = (float) config.getDouble("sound.pitch.respawn");
        SOUND_KILL_PITCH = (float) config.getDouble("sound.pitch.kill");

        TIME = config.getInt("time.duration");

        KEEP_XP = config.getString("permissions.keep-xp");
        ADMIN = config.getString("permissions.admin");

        ANIMATION = config.getString("animation.type");

        SPECTATE_ENTITY = config.getBoolean("spectator-settings.allow-spectate");
        HOTBAR_TELEPORT_SPECTATOR = config.getBoolean("spectator-settings.allow-teleport-with-keys");
        MOVE_SPECTATOR = config.getBoolean("spectator-settings.allow-move");
    }
}
