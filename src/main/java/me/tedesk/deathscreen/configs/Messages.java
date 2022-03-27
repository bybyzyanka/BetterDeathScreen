package me.tedesk.deathscreen.configs;

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
    }
}
