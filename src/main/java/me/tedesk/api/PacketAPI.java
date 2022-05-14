package me.tedesk.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.tedesk.BetterDeathScreen;
import me.tedesk.configs.Config;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PacketAPI {

    public static void cancelCameraAndTeleport() {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();
        manager.addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, PacketType.Play.Server.CAMERA) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Player p = event.getPlayer();
                if (Config.DEAD_PLAYERS.contains(p.getName())) {
                    event.setCancelled(true);
                    p.setSneaking(true);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            p.setSneaking(false);
                        }
                    }.runTaskLater(BetterDeathScreen.plugin, 2);
                }
            }
        });
        manager.addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player p = event.getPlayer();
                if (Config.DEAD_PLAYERS.contains(p.getName())) {
                    event.setCancelled(true);
                }
            }
        });
    }
}
