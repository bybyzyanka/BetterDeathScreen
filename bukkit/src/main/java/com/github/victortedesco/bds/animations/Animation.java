package com.github.victortedesco.bds.animations;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.utils.Version;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class Animation {

    private static void bloodAnimation(Player player) {
        player.getWorld().playEffect(player.getEyeLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        player.getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
    }

    private static void explosionAnimation(Player player) {
        PacketContainer particle = new PacketContainer(PacketType.Play.Server.WORLD_PARTICLES);

        particle.getParticles().write(0, EnumWrappers.Particle.EXPLOSION_LARGE);
        particle.getFloat().write(0, (float) player.getLocation().getX());
        particle.getFloat().write(1, (float) player.getLocation().getY());
        particle.getFloat().write(2, (float) player.getLocation().getZ());
        particle.getIntegers().write(0, 1);

        for (Player ps : Bukkit.getOnlinePlayers()) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(ps, particle);
            } catch (InvocationTargetException ignored) {
            }
        }
        if (BetterDeathScreen.getVersion() != Version.v1_8) {
            player.getWorld().playSound(player.getLocation(), Sound.valueOf("ENTITY_GENERIC_EXPLODE"), 1, 1);
        }
        if (BetterDeathScreen.getVersion() == Version.v1_8) {
            player.getWorld().playSound(player.getLocation(), Sound.valueOf("EXPLODE"), 1, 1);
        }
    }

    private static void lightningAnimation(Player player) {
        player.getWorld().strikeLightningEffect(player.getLocation());
    }

    public static void sendAnimation(Player player) {
        if (Config.ANIMATION.toUpperCase().contains("BLOOD")) {
            bloodAnimation(player);
        }
        if (Config.ANIMATION.toUpperCase().contains("EXPLOSION")) {
            explosionAnimation(player);
        }
        if (Config.ANIMATION.toUpperCase().contains("LIGHTNING")) {
            lightningAnimation(player);
        }
    }
}