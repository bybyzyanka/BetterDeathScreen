package com.github.victortedesco.bds.utils;

import com.github.victortedesco.bds.BetterDeathScreen;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class Randomizer {

    public static String getRandomSound(List<String> list) {
        Random r = new Random();
        int random_item = r.nextInt(list.size());
        return list.get(random_item);
    }

    public static String getRandomMessage(Player player, Player killer, List<String> list) {
        Random r = new Random();
        int random_item = r.nextInt(list.size());
        String message = ChatColor.translateAlternateColorCodes('&', list.get(random_item));
        if (BetterDeathScreen.isPlaceholderAPIActive()) {
            if (killer == null) message = PlaceholderAPI.setPlaceholders(player, message);
            if (killer != null) message = PlaceholderAPI.setPlaceholders(killer, message);
        }
        return message;
    }
}
