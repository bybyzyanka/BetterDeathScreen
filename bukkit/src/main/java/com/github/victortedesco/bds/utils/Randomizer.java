package com.github.victortedesco.bds.utils;

import com.github.victortedesco.bds.BetterDeathScreen;
import me.clip.placeholderapi.PlaceholderAPI;
import com.github.victortedesco.bds.configs.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class Randomizer {

    public static String randomSound(List<String> sound) {
        Random r = new Random();
        int random_item = r.nextInt(sound.size());
        return sound.get(random_item);
    }

    public static String randomTitleOnDeathByPlayer(Player p) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_BY_PLAYER_TITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_BY_PLAYER_TITLES.get(random_item));
        if (BetterDeathScreen.PLACEHOLDERAPI) {
            message = PlaceholderAPI.setPlaceholders(p, message);
        }
        return message;
    }

    public static String randomSubTitleOnDeathByPlayer(Player p) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_BY_PLAYER_SUBTITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_BY_PLAYER_SUBTITLES.get(random_item));
        if (BetterDeathScreen.PLACEHOLDERAPI) {
            message = PlaceholderAPI.setPlaceholders(p, message);
        }
        return message;
    }

    public static String randomKillActionBar(Player p) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.ACTIONBAR_KILL.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.ACTIONBAR_KILL.get(random_item));
        if (BetterDeathScreen.PLACEHOLDERAPI) {
            message = PlaceholderAPI.setPlaceholders(p, message);
        }
        return message;
    }

    public static String randomTitle(Player p) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_TITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_TITLES.get(random_item));
        if (BetterDeathScreen.PLACEHOLDERAPI) {
            message = PlaceholderAPI.setPlaceholders(p, message);
        }
        return message;
    }

    public static String randomSubTitle(Player p) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_SUBTITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_SUBTITLES.get(random_item));
        if (BetterDeathScreen.PLACEHOLDERAPI) {
            message = PlaceholderAPI.setPlaceholders(p, message);
        }
        return message;
    }
}
