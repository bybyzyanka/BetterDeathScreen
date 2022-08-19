package me.tedesk.bds.api;

import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.configs.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerAPI {

    public static boolean isStackEmpty(ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0;
    }

    public static void incrementStatistic(Player p, Statistic stat, int value, int safe_value) {
        try {
            p.incrementStatistic(stat, (int) Math.min(p.getHealth(), value));
        } catch (IllegalArgumentException e) {
            p.incrementStatistic(stat, safe_value);
        }
    }

    public static void playSound(Player p, String sound, float volume, float pitch) {
        try {
            if (!sound.contains(".")) {
                p.playSound(p.getLocation(), Sound.valueOf(sound), volume, pitch);
            }
            if (sound.contains(".")) {
                p.playSound(p.getLocation(), sound, volume, pitch);
            }
        } catch (IllegalArgumentException e) {
            BetterDeathScreen.logger(Messages.SOUND_ERROR.replace("%sound%", sound));
        }
    }

    public static void teleportSafeLocation(Player p, Location location) {
        double y = location.getWorld().getMaxHeight();
        for (double i = y; i > 0; i -= 1) {
            Location loc = new Location(location.getWorld(), location.getX(), i, location.getZ());
            Material type = loc.getBlock().getType();
            if (!type.toString().contains("AIR") && !type.toString().contains("WATER") && !type.toString().contains("LAVA")) {
                p.teleport(loc.add(0, 1.2, 0), PlayerTeleportEvent.TeleportCause.PLUGIN);
                break;
            }
        }
    }
}
