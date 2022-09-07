package com.github.victortedesco.bds.listener.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpectatorPacketLimiter {

    public static List<String> SPECTATOR_MESSAGE_CD = new ArrayList<>();

    public static void cancelSpectate() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.getInstance(), PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (Config.DEAD_PLAYERS.contains(p.getName()) && !Config.SPECTATE_ENTITY) {
                    e.setCancelled(true);
                    if (!SPECTATOR_MESSAGE_CD.contains(p.getName())) {
                        SPECTATOR_MESSAGE_CD.add(p.getName());
                        p.sendMessage(Messages.SPECTATE_BLOCKED);
                        Bukkit.getScheduler().runTaskLaterAsynchronously(BetterDeathScreen.getInstance(),
                                () -> SPECTATOR_MESSAGE_CD.remove(p.getName()), 20 * 3);
                    }
                }
            }
        });
    }
}
