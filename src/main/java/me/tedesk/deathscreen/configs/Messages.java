package me.tedesk.deathscreen.configs;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Messages {
    public static String ACTIONBAR_DEATH;
    public static String ACTIONBAR_HC;
    public static String ACTIONBAR_KILL;
    public static List<String> TITLES;
    public static List<String> SUBTITLES;
    public static String SINGULAR;
    public static String PLURAL;
    public static String RELOAD;
    public static String NO_PERM;
    public static String SET_SPAWN;

    public static String AB_ERROR;
    public static String TITLE_ERROR;
    public static String SOUND_ERROR;


    public static void loadMessages() {
        FileConfiguration messages = ConfigHandler.getConfig("messages_" + Config.LANGUAGE);

        ACTIONBAR_DEATH = messages.getString("actionbar.death");
        ACTIONBAR_KILL = messages.getString("actionbar.kill");
        ACTIONBAR_HC = messages.getString("actionbar.hardcore");
        TITLES = messages.getStringList("titles");
        SUBTITLES = messages.getStringList("subtitles");
        SINGULAR = messages.getString("time.singular");
        PLURAL = messages.getString("time.plural");
        RELOAD = messages.getString("perms.reload");
        NO_PERM = messages.getString("perms.no-perm");
        SET_SPAWN = messages.getString("perms.set-respawn");
        AB_ERROR = messages.getString("errors.actionbar");
        AB_ERROR = ChatColor.translateAlternateColorCodes('&', AB_ERROR);
        TITLE_ERROR = messages.getString("errors.title");
        TITLE_ERROR = ChatColor.translateAlternateColorCodes('&', TITLE_ERROR);
        SOUND_ERROR = messages.getString("errors.sound");
        SOUND_ERROR = ChatColor.translateAlternateColorCodes('&', SOUND_ERROR);
    }
}
