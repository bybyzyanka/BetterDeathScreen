package me.tedesk.api;

import me.tedesk.BetterDeathScreen;
import me.tedesk.configs.Messages;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundAPI {

    public static void sendSound(Player p, Location loc, String sound, float volume, float pitch) {
        try {
            p.playSound(loc, Sound.valueOf(sound), volume, pitch);
        } catch (IllegalArgumentException e) {
            BetterDeathScreen.logger(ChatColor.translateAlternateColorCodes('&', Messages.SOUND_ERROR.replace("%sound%", sound)));
        }
    }
}