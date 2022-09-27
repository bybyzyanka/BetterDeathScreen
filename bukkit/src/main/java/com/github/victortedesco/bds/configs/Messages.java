package com.github.victortedesco.bds.configs;

import com.github.victortedesco.bds.BetterDeathScreen;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Messages {
    public static List<String> INCOMPATIBLE;
    public static List<String> ENABLED;
    public static List<String> DISABLED;
    public static String COMMAND_BLOCKED;
    public static String SPECTATE_BLOCKED;
    public static String HOTBAR_TELEPORT_BLOCKED;
    public static String ACTIONBAR_DEATH;
    public static String ACTIONBAR_HARDCORE;
    public static List<String> ACTIONBAR_KILL;
    public static List<String> KILLED_TITLES;
    public static List<String> KILLED_BY_PLAYER_TITLES;
    public static List<String> KILLED_SUBTITLES;
    public static List<String> KILLED_BY_PLAYER_SUBTITLES;
    public static String SINGULAR;
    public static String PLURAL;
    public static List<String> HELP;
    public static String RELOAD;
    public static String NO_PERMISSION;
    public static String SPAWN_SET;
    public static String ACTIONBAR_ERROR;
    public static String TITLE_ERROR;
    public static String SOUND_ERROR;
    public static String SPAWN_ERROR;

    @SuppressWarnings("ConstantConditions")
    public static void loadMessages() {
        FileConfiguration messages = ConfigHandler.getConfig("messages_" + Config.LANGUAGE);
        if (BetterDeathScreen.invalidLanguage) messages = ConfigHandler.getConfig("messages_en-US");

        INCOMPATIBLE = messages.getStringList("plugin.incompatible");
        ENABLED = messages.getStringList("plugin.enabled");
        DISABLED = messages.getStringList("plugin.disabled");

        COMMAND_BLOCKED = ChatColor.translateAlternateColorCodes('&', messages.getString("misc.commands-blocked"));
        SPECTATE_BLOCKED = ChatColor.translateAlternateColorCodes('&', messages.getString("misc.spectate-blocked"));
        HOTBAR_TELEPORT_BLOCKED = ChatColor.translateAlternateColorCodes('&', messages.getString("misc.hotbar-tp-blocked"));

        ACTIONBAR_DEATH = ChatColor.translateAlternateColorCodes('&', messages.getString("actionbar.death"));
        ACTIONBAR_HARDCORE = ChatColor.translateAlternateColorCodes('&', messages.getString("actionbar.hardcore"));
        ACTIONBAR_KILL = messages.getStringList("actionbar.kill");

        KILLED_TITLES = messages.getStringList("titles.killed");
        KILLED_BY_PLAYER_TITLES = messages.getStringList("titles.killed-by-player");
        KILLED_SUBTITLES = messages.getStringList("subtitles.killed");
        KILLED_BY_PLAYER_SUBTITLES = messages.getStringList("subtitles.killed-by-player");

        SINGULAR = ChatColor.translateAlternateColorCodes('&', messages.getString("time.singular"));
        PLURAL = ChatColor.translateAlternateColorCodes('&', messages.getString("time.plural"));

        HELP = messages.getStringList("commands.help");
        RELOAD = ChatColor.translateAlternateColorCodes('&', messages.getString("commands.reload"));
        NO_PERMISSION = ChatColor.translateAlternateColorCodes('&', messages.getString("commands.no-perm"));
        SPAWN_SET = ChatColor.translateAlternateColorCodes('&', messages.getString("commands.spawn-set"));

        ACTIONBAR_ERROR = ChatColor.translateAlternateColorCodes('&', messages.getString("errors.actionbar"));
        TITLE_ERROR = ChatColor.translateAlternateColorCodes('&', messages.getString("errors.title"));
        SOUND_ERROR = ChatColor.translateAlternateColorCodes('&', messages.getString("errors.sound"));
        SPAWN_ERROR = ChatColor.translateAlternateColorCodes('&', messages.getString("errors.spawn"));
    }
}
