package me.tedesk.plugin.api;

import me.tedesk.plugin.BetterDeathScreen;
import me.tedesk.plugin.configs.Messages;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundAPI {

    public static void sendSound(Player p, Location loc, String sound, float volume, float pitch) {
        try {
            p.playSound(loc, Sound.valueOf(sound), volume, pitch);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            BetterDeathScreen.logger(Messages.SOUND_ERROR);
        }
    }
}
