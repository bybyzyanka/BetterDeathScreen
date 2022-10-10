package com.github.victortedesco.bds.utils;

import com.cryptomorin.xseries.XSound;
import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.listener.bukkit.PlayerDeathListener;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerUtils {

    public static boolean isStackEmpty(ItemStack stack) {
        return stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0;
    }

    public static void incrementStatistic(Player player, Statistic statistic, int value) {
        try {
            player.incrementStatistic(statistic, value);
        } catch (Exception exception) {
            player.incrementStatistic(statistic, (int) player.getHealth());
        }
    }

    public static void resetDamageBeforeDeath(Player player) {
        Object[] defaultDamage = new Object[3];
        defaultDamage[0] = EntityDamageEvent.DamageCause.CUSTOM;
        defaultDamage[1] = 0;
        defaultDamage[2] = 0;
        PlayerDeathListener.DAMAGE_BEFORE_DEATH.put(player.getName(), defaultDamage);

        Object[] damageByEntityOrBlock = new Object[4];
        damageByEntityOrBlock[0] = null;
        damageByEntityOrBlock[1] = EntityDamageEvent.DamageCause.CUSTOM;
        damageByEntityOrBlock[2] = 0;
        damageByEntityOrBlock[3] = 0;

        PlayerDeathListener.DAMAGE_BY_ENTITY_BEFORE_DEATH.put(player.getName(), damageByEntityOrBlock);
        PlayerDeathListener.DAMAGE_BY_BLOCK_BEFORE_DEATH.put(player.getName(), damageByEntityOrBlock);
    }

    public static void playSound(Player player, List<String> list, boolean throwable, boolean silent) {
        String string = Randomizer.getRandomSound(list);
        try {
            String sound = StringUtils.substringBefore(string, ";");
            float volume = Float.parseFloat(StringUtils.substringBetween(string, ";", ";"));
            float pitch = Float.parseFloat(StringUtils.substringAfterLast(string, ";"));
            if (silent) volume = 0;
            if (sound.contains(".")) {
                player.playSound(player.getLocation(), sound, volume, pitch);
                return;
            }
            Sound bukkitSound = XSound.matchXSound(sound).orElse(null).parseSound();
            player.playSound(player.getLocation(), bukkitSound, volume, pitch);
        } catch (Exception exception) {
            if (throwable) BetterDeathScreen.sendConsoleMessage(Messages.SYNTAX_ERROR.replace("%syntax%", string));
        }
    }

    public static void playSound(Player player, String string, boolean throwable, boolean silent) {
        try {
            String sound = StringUtils.substringBefore(string, ";");
            float volume = Float.parseFloat(StringUtils.substringBetween(string, ";", ";"));
            float pitch = Float.parseFloat(StringUtils.substringAfterLast(string, ";"));
            if (silent) volume = 0;
            if (sound.contains(".")) {
                player.playSound(player.getLocation(), sound, volume, pitch);
                return;
            }
            Sound bukkitSound = XSound.matchXSound(sound).orElse(null).parseSound();
            player.playSound(player.getLocation(), bukkitSound, volume, pitch);
        } catch (Exception exception) {
            if (throwable) BetterDeathScreen.sendConsoleMessage(Messages.SYNTAX_ERROR.replace("%syntax%", string));
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
        if (BetterDeathScreen.getVersion().value >= Version.v1_16.value) {
            return player.getWorld().isHardcore();
        }
        return Bukkit.isHardcore();
    }
}
