package me.tedesk.systems;

import me.tedesk.configs.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Random;

public class Randomizer {

    public static String randomSound(List<String> sound) {
        Random r = new Random();
        return sound.get(r.nextInt(sound.size()));
    }

    public static String customTitles() {
        Random r = new Random();
        int random_item = r.nextInt(Messages.TITLES.size());
        return ChatColor.translateAlternateColorCodes('&', Messages.TITLES.get(random_item));
    }

    public static String customSubtitles() {
        Random r = new Random();
        int random_item = r.nextInt(Messages.SUBTITLES.size());
        return ChatColor.translateAlternateColorCodes('&', Messages.SUBTITLES.get(random_item));
    }

    public static String customKillActionBar(Player p) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.ACTIONBAR_KILL.size());
        return ChatColor.translateAlternateColorCodes('&', Messages.ACTIONBAR_KILL.get(random_item).replace("%player%", p.getName()));
    }
}
