package com.github.victortedesco.betterdeathscreen.bukkit.listener.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import org.bukkit.entity.Player;

public final class LoginPacketListener {

    public void setupListener() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.getInstance(), ListenerPriority.LOW, PacketType.Play.Server.LOGIN) {

            @Override
            public void onPacketSending(PacketEvent event) {
                Player player = event.getPlayer();
                PacketContainer packet = event.getPacket();

                packet.getBooleans().write(0, BetterDeathScreenAPI.getPlayerManager().isHardcore(player));
            }
        });
    }
}
