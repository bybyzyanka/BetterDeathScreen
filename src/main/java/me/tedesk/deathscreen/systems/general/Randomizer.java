package me.tedesk.deathscreen.systems.general;

import me.tedesk.deathscreen.configs.Messages;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.Random;

public class Randomizer {
    public static String customtitles() {

        List<String> title = Messages.TITLES;

        for (int i = 0; i < title.size(); i++)
            title.set(i, ChatColor.translateAlternateColorCodes('&', title.get(i)));

        Random r = new Random();
        int randomitem = r.nextInt(title.size());
        return title.get(randomitem);
    }

    public static String customsubtitles() {

        List<String> subtitle = Messages.SUBTITLES;

        for (int i = 0; i < subtitle.size(); i++)
            subtitle.set(i, ChatColor.translateAlternateColorCodes('&', subtitle.get(i)));

        Random r = new Random();
        int randomitem = r.nextInt(subtitle.size());
        return subtitle.get(randomitem);
    }

}
