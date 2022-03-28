package me.tedesk.deathscreen.api;

import me.tedesk.deathscreen.BetterDeathScreen;
import me.tedesk.deathscreen.configs.Messages;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundAPI {

    public static void sendSound (Player p, Location loc, String sound, float volume, float pitch) {
        try {
            p.playSound(loc, Sound.valueOf(sound), volume, pitch);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            BetterDeathScreen.logger(Messages.SOUND_ERROR);
        }
    }
}
