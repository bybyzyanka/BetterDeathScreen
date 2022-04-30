package me.tedesk.systems;

import me.tedesk.configs.Messages;
import org.bukkit.entity.Player;

import java.util.Random;

public class Randomizer {

    public static String customTitles() {
        Random r = new Random();
        int random_item = r.nextInt(Messages.TITLES.size());
        return Messages.TITLES.get(random_item).replace("&", "§");
    }

    public static String customSubtitles() {
        Random r = new Random();
        int random_item = r.nextInt(Messages.SUBTITLES.size());
        return Messages.SUBTITLES.get(random_item).replace("&", "§");
    }

    public static String customKillActionBar(Player p) {
        Random r = new Random();
        int random_item = r.nextInt(Messages.ACTIONBAR_KILL.size());
        return Messages.ACTIONBAR_KILL.get(random_item).replace("&", "§").replace("%player%", p.getDisplayName());
    }
}
