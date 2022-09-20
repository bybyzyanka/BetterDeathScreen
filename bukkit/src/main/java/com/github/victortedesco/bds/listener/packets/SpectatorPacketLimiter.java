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

import java.util.HashSet;
import java.util.Set;

public class SpectatorPacketLimiter {

    static Set<String> MESSAGE_DELAY = new HashSet<>();

    public static void cancelSpectate() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.getInstance(), PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                Player player = event.getPlayer();

                if (Config.DEAD_PLAYERS.contains(player.getName()) && !Config.SPECTATE_ENTITY) {
                    event.setCancelled(true);
                    if (!MESSAGE_DELAY.contains(player.getName())) {
                        MESSAGE_DELAY.add(player.getName());
                        player.sendMessage(Messages.SPECTATE_BLOCKED);
                        Bukkit.getScheduler().runTaskLaterAsynchronously(BetterDeathScreen.getInstance(),
                                () -> MESSAGE_DELAY.remove(player.getName()), 20 * 3);
                    }
                }
            }
        });
    }
}
