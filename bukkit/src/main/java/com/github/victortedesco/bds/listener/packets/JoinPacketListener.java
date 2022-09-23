package com.github.victortedesco.bds.listener.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.utils.PlayerUtils;
import org.bukkit.entity.Player;

public class JoinPacketListener {

    public static void changeHeartIcon() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.getInstance(), ListenerPriority.LOW, PacketType.Play.Server.LOGIN) {
            @Override
            public void onPacketSending(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();

                packet.getBooleans().write(0, PlayerUtils.isHardcore(player));
            }
        });
    }
}
