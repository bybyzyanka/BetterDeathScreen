package me.tedesk.events.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.tedesk.BetterDeathScreen;
import me.tedesk.api.PacketAPI;
import org.bukkit.entity.Player;

public class DeathPacketListener {

    @SuppressWarnings("deprecation")
    public static void cancelDeathScreen() {
        if (BetterDeathScreen.veryNewVersion()) {
            PacketAPI.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.PLAYER_COMBAT_KILL) {
                @Override
                public void onPacketSending(PacketEvent e) {
                    Player p = e.getPlayer();

                    e.setCancelled(true);
                    p.setHealth(0.1);
                }
            });
        }
        if (BetterDeathScreen.newVersion() || BetterDeathScreen.oldVersion()) {
            PacketAPI.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.COMBAT_EVENT) {
                @Override
                public void onPacketSending(PacketEvent e) {
                    PacketContainer packet = e.getPacket();
                    Player p = e.getPlayer();

                    if (packet.getCombatEvents().read(0) == EnumWrappers.CombatEventType.ENTITY_DIED) {
                        e.setCancelled(true);
                        p.setHealth(0.1);
                    }
                }
            });
            PacketAPI.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.UPDATE_HEALTH) {
                @Override
                public void onPacketSending(PacketEvent e) {
                    PacketContainer packet = e.getPacket();
                    Player p = e.getPlayer();

                    if (packet.getFloat().read(0) <= 0) {
                        e.setCancelled(true);
                        p.setHealth(0.1);
                    }
                }
            });
        }
    }
}
