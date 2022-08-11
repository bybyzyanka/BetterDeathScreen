package me.tedesk.bds.events.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.configs.Config;
import me.tedesk.bds.configs.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpectatorPacketLimiter {

    public static List<String> SPECTATOR_MESSAGE_CD = new ArrayList<>();

    public static void cancelSpectate() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (Config.DEAD_PLAYERS.contains(p.getName()) && !Config.SPECTATE_ENTITY) {
                    e.setCancelled(true);
                    if (!SPECTATOR_MESSAGE_CD.contains(p.getName())) {
                        SPECTATOR_MESSAGE_CD.add(p.getName());
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.SPECTATE_BLOCKED));
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                SPECTATOR_MESSAGE_CD.remove(p.getName());
                            }
                        }.runTaskLaterAsynchronously(BetterDeathScreen.plugin, 20*3);
                    }
                }
            }
        });
    }
}
