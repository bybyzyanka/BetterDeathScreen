package me.tedesk.bds.systems;

import com.cryptomorin.xseries.XMaterial;
import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.configs.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class Locations {

    public static void teleportSafeLocation(Player p, Location location) {
        double y = location.getWorld().getMaxHeight();
        for (double i = y; i > 0; i-=1) {
            Location loc = new Location(location.getWorld(), location.getX(), i, location.getZ());
            Material type = loc.getBlock().getType();
            if (type != XMaterial.AIR.parseMaterial() && type != XMaterial.LAVA.parseMaterial() && type != XMaterial.WATER.parseMaterial()) {
                p.teleport(loc.add(0,1.2,0), PlayerTeleportEvent.TeleportCause.PLUGIN);
                break;
            }
        }
    }
}