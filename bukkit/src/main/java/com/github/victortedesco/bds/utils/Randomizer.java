package com.github.victortedesco.bds.utils;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Messages;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class Randomizer {

    public static String randomSound(List<String> sound_list) {
        Random r = new Random();
        int random_item = r.nextInt(sound_list.size());
        return sound_list.get(random_item);
    }

    public static String randomTitleOnDeathByPlayer(Player player) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_BY_PLAYER_TITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_BY_PLAYER_TITLES.get(random_item));
        if (BetterDeathScreen.isPAPIActive()) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }

    public static String randomSubTitleOnDeathByPlayer(Player player) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_BY_PLAYER_SUBTITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_BY_PLAYER_SUBTITLES.get(random_item));
        if (BetterDeathScreen.isPAPIActive()) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }

    public static String randomKillActionBar(Player player) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.ACTIONBAR_KILL.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.ACTIONBAR_KILL.get(random_item));
        if (BetterDeathScreen.isPAPIActive()) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }

    public static String randomTitle(Player player) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_TITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_TITLES.get(random_item));
        if (BetterDeathScreen.isPAPIActive()) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }

    public static String randomSubTitle(Player player) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.KILLED_SUBTITLES.size());
        String message = ChatColor.translateAlternateColorCodes('&', Messages.KILLED_SUBTITLES.get(random_item));
        if (BetterDeathScreen.isPAPIActive()) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }
        return message;
    }
}
