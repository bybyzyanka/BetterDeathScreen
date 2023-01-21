package com.github.victortedesco.betterdeathscreen.api.animations;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.cryptomorin.xseries.XSound;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Animations {

    public void createBloodAnimation(Player player) {
        player.getWorld().playEffect(player.getEyeLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        player.getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
    }

    public void createExplosionAnimation(Player player) {
        PacketContainer particle = new PacketContainer(PacketType.Play.Server.WORLD_PARTICLES);

        particle.getParticles().write(0, EnumWrappers.Particle.EXPLOSION_LARGE);
        particle.getFloat().write(0, (float) player.getLocation().getX());
        particle.getFloat().write(1, (float) player.getLocation().getY());
        particle.getFloat().write(2, (float) player.getLocation().getZ());
        particle.getIntegers().write(0, 1);

        for (Player players : player.getWorld().getPlayers()) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(players, particle);
            } catch (InvocationTargetException ignored) {
            }
        }
        assert XSound.ENTITY_GENERIC_EXPLODE.parseSound() != null;
        player.getWorld().playSound(player.getLocation(), XSound.ENTITY_GENERIC_EXPLODE.parseSound(), 1, 1);
    }

    public void createLightningAnimation(Player player) {
        player.getWorld().strikeLightningEffect(player.getLocation());
    }

    public void sendAnimation(Player player, String type) {
        AnimationType animation;
        try {
            animation = AnimationType.valueOf(type);
        } catch (IllegalArgumentException exception) {
            animation = AnimationType.NONE;
            exception.printStackTrace();
        }
        if (animation == AnimationType.NONE) return;
        if (animation == AnimationType.RANDOM)
            animation = AnimationType.values()[new Random().nextInt(3)];
        if (animation == AnimationType.BLOOD) createBloodAnimation(player);
        if (animation == AnimationType.EXPLOSION) createExplosionAnimation(player);
        if (animation == AnimationType.LIGHTNING) createLightningAnimation(player);
    }

    public enum AnimationType {
        BLOOD,
        LIGHTNING,
        EXPLOSION,
        RANDOM,
        NONE,
    }
}
