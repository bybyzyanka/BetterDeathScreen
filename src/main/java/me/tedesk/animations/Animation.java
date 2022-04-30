package me.tedesk.animations;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import me.tedesk.BetterDeathScreen;
import me.tedesk.configs.Config;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class Animation {


    private static void bloodAnimation(Player p) {
        p.getWorld().playEffect(p.getEyeLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
        p.getWorld().playEffect(p.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK);
    }

    private static void explosionAnimation(Player p) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        PacketContainer fakeExplosion = new PacketContainer(PacketType.Play.Server.EXPLOSION);
        fakeExplosion.getDoubles().
                write(0, p.getLocation().getX()).
                write(1, p.getLocation().getY()).
                write(2, p.getLocation().getZ());
        fakeExplosion.getFloat().write(0, 2.0F);

        try {
            protocolManager.sendServerPacket(p, fakeExplosion);
        } catch (InvocationTargetException e) {
            BetterDeathScreen.logger("§cThe death animation could not be send to: " + p.getDisplayName());
        }
    }

    private static void lightningAnimation(Player p) {
        p.getWorld().strikeLightningEffect(p.getLocation());
    }

    public static void sendAnimation(Player p) {

        if (Config.ANIMATION.equals("DISABLED")) {
            return;
        }
        if (Config.ANIMATION.equals("BLOOD")) {
            bloodAnimation(p);
        }
        if (Config.ANIMATION.equals("EXPLOSION")) {
            explosionAnimation(p);
        }
        if (Config.ANIMATION.equals("LIGHTNING")) {
            lightningAnimation(p);
        }
    }
}