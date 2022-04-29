package me.tedesk.configs;

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
    public static List<String> HELP;
    public static String RELOAD;
    public static String NO_PERM;
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

        HELP = messages.getStringList("commands.help");
        RELOAD = messages.getString("commands.reload");
        NO_PERM = messages.getString("commands.no-perm");

        AB_ERROR = messages.getString("errors.actionbar");
        TITLE_ERROR = messages.getString("errors.title");
        SOUND_ERROR = messages.getString("errors.sound");
    }
}
