package me.tedesk.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.tedesk.BetterDeathScreen;
import me.tedesk.configs.Config;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PacketAPI {

    public static void cancelCameraPacket() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.NORMAL, PacketType.Play.Server.CAMERA) {
            @Override
            public void onPacketSending(PacketEvent e) {
                Player p = e.getPlayer();
                if (e.getPacketType() == PacketType.Play.Server.CAMERA) {
                    if (p.getGameMode() == GameMode.SPECTATOR && Config.DEAD_PLAYERS.contains(p.getName())) {
                        e.setCancelled(true);
                    }
                }
            }
        });
    }
}