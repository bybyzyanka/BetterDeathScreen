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

    private static void bloodAnimation(Player p) {
        p.getWorld().playEffect(p.getEyeLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
    }

    private static void explosionAnimation(Player p) {
        PacketContainer particle = new PacketContainer(PacketType.Play.Server.WORLD_PARTICLES);

        particle.getParticles().write(0, EnumWrappers.Particle.EXPLOSION_LARGE);
        particle.getFloat().write(0, (float) p.getLocation().getX());
        particle.getFloat().write(1, (float) p.getLocation().getY());
        particle.getFloat().write(2, (float) p.getLocation().getZ());
        particle.getIntegers().write(0, 1);

        try {
            for (Player ps : Bukkit.getOnlinePlayers()) {
                ProtocolLibrary.getProtocolManager().sendServerPacket(ps, particle);
            }
        } catch (InvocationTargetException ignored) {

        }
        if (BetterDeathScreen.getVersion() != Version.v1_8) {
            p.getWorld().playSound(p.getLocation(), Sound.valueOf("ENTITY_GENERIC_EXPLODE"), 1, 1);
        }
        if (BetterDeathScreen.getVersion() == Version.v1_8) {
            p.getWorld().playSound(p.getLocation(), Sound.valueOf("EXPLODE"), 1, 1);
        }
    }

    private static void lightningAnimation(Player p) {
        p.getWorld().strikeLightningEffect(p.getLocation());
    }

    public static void sendAnimation(Player p) {
        if (Config.ANIMATION.equalsIgnoreCase("BLOOD")) {
            bloodAnimation(p);
        }
        if (Config.ANIMATION.equalsIgnoreCase("EXPLOSION")) {
            explosionAnimation(p);
        }
        if (Config.ANIMATION.equalsIgnoreCase("LIGHTNING")) {
            lightningAnimation(p);
        }
    }
}