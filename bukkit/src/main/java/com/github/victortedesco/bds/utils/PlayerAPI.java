package com.github.victortedesco.bds.utils;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.listener.bukkit.PlayerDeathListener;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerAPI {

    public static boolean isStackEmpty(ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0;
    }

    public static void incrementStatistic(Player player, Statistic statistic, int value, boolean safe) {
        try {
            if (safe) {
                player.incrementStatistic(statistic, (int) Math.min(player.getHealth(), value));
                return;
            }
            player.incrementStatistic(statistic, value);
        } catch (IllegalArgumentException illegalArgumentException) {
            player.incrementStatistic(statistic, 1);
        }
    }

    public static void resetDamageBeforeDeath(Player player) {
        ArrayList<Object> default_damage = new ArrayList<>();
        default_damage.add(EntityDamageEvent.DamageCause.CUSTOM);
        default_damage.add(0);
        default_damage.add(0);
        PlayerDeathListener.DAMAGE_BEFORE_DEATH.put(player.getName(), default_damage);

        ArrayList<Object> caused_by_damage = new ArrayList<>();
        caused_by_damage.add(null);
        caused_by_damage.add(EntityDamageEvent.DamageCause.CUSTOM);
        caused_by_damage.add(0);
        caused_by_damage.add(0);

        PlayerDeathListener.DAMAGE_BY_ENTITY_BEFORE_DEATH.put(player.getName(), caused_by_damage);
        PlayerDeathListener.DAMAGE_BY_BLOCK_BEFORE_DEATH.put(player.getName(), caused_by_damage);
    }

    public static void playSoundFromConfig(Player player, List<String> type, boolean throwable, boolean silent) {
        String randomSound = Randomizer.getRandomSound(type);
        try {
            String sound = StringUtils.substringBefore(randomSound, ";");
            float volume = Float.parseFloat(StringUtils.substringBetween(randomSound, ";", ";"));
            float pitch = Float.parseFloat(StringUtils.substringAfterLast(randomSound, ";"));
            if (silent) volume = 0;
            if (sound.contains(".")) {
                player.playSound(player.getLocation(), sound, volume, pitch);
                return;
            }
            player.playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
        } catch (IllegalArgumentException illegalArgumentException) {
            if (throwable) BetterDeathScreen.sendConsoleMessage(Messages.SOUND_ERROR.replace("%sound%", randomSound));
        }
    }

    public static void teleportSafeLocation(Player player, Location location, PlayerTeleportEvent.TeleportCause teleportCause) {
        double y = location.getWorld().getMaxHeight();
        for (double i = y; i > 0; i -= 1) {
            Location loc = new Location(location.getWorld(), location.getX(), i, location.getZ());
            Material type = loc.getBlock().getType();
            if (!type.toString().contains("AIR") && !type.toString().contains("WATER") && !type.toString().contains("LAVA")) {
                player.teleport(loc.add(0, 1.2, 0), teleportCause);
                break;
            }
        }
    }

    public static boolean isHardcore(Player player) {
        if (BetterDeathScreen.getVersion().value < Version.v1_16.value) {
            return Bukkit.isHardcore();
        }
        if (BetterDeathScreen.getVersion().value >= Version.v1_16.value) {
            return player.getWorld().isHardcore();
        }
        return false;
    }
}
