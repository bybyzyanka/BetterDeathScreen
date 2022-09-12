package com.github.victortedesco.bds.utils;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.listener.bukkit.PlayerDeathListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class PlayerAPI {

    public static boolean isStackEmpty(ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0;
    }

    public static void incrementStatistic(Player player, Statistic stat, int value) {
        try {
            player.incrementStatistic(stat, (int) Math.min(player.getHealth(), value));
        } catch (IllegalArgumentException e) {
            player.incrementStatistic(stat, 1);
        }
    }

    public static void resetDamageBeforeDeath(Player player) {
        ArrayList<Object> default_damage = new ArrayList<>();
        default_damage.add(EntityDamageEvent.DamageCause.CUSTOM);
        default_damage.add(0);
        default_damage.add(0);
        PlayerDeathListener.LAST_DAMAGE_BEFORE_DEATH.put(player.getName(), default_damage);

        ArrayList<Object> ent_damage = new ArrayList<>();
        ent_damage.add(null);
        ent_damage.add(EntityDamageEvent.DamageCause.CUSTOM);
        ent_damage.add(0);
        ent_damage.add(0);
        PlayerDeathListener.LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.put(player.getName(), ent_damage);

        ArrayList<Object> block_damage = new ArrayList<>();
        block_damage.add(null);
        block_damage.add(EntityDamageEvent.DamageCause.CUSTOM);
        block_damage.add(0);
        block_damage.add(0);
        PlayerDeathListener.LAST_DAMAGE_BY_BLOCK_BEFORE_DEATH.put(player.getName(), block_damage);
    }

    public static void playSound(Player player, String sound, float volume, float pitch) {
        try {
            if (!sound.contains(".")) {
                player.playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
            }
            if (sound.contains(".")) {
                player.playSound(player.getLocation(), sound, volume, pitch);
            }
        } catch (IllegalArgumentException e) {
            BetterDeathScreen.sendConsoleMessage(Messages.SOUND_ERROR.replace("%sound%", sound));
        }
    }

    public static void playSoundNoExceptions(Player player, String sound, float volume, float pitch) {
        try {
            if (!sound.contains(".")) {
                player.playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
            }
            if (sound.contains(".")) {
                player.playSound(player.getLocation(), sound, volume, pitch);
            }
        } catch (IllegalArgumentException ignored) {

        }
    }

    public static void teleportSafeLocation(Player player, Location location) {
        double y = location.getWorld().getMaxHeight();
        for (double i = y; i > 0; i -= 1) {
            Location loc = new Location(location.getWorld(), location.getX(), i, location.getZ());
            Material type = loc.getBlock().getType();
            if (!type.toString().contains("AIR") && !type.toString().contains("WATER") && !type.toString().contains("LAVA")) {
                player.teleport(loc.add(0, 1.2, 0), PlayerTeleportEvent.TeleportCause.PLUGIN);
                break;
            }
        }
    }
}
