package me.tedesk.events.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.tedesk.BetterDeathScreen;
import me.tedesk.api.PacketAPI;
import me.tedesk.configs.Config;
import me.tedesk.configs.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpectatorPacketLimiter {

    public static void cancelSpectate() {
        PacketAPI.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, PacketType.Play.Server.CAMERA) {
            @Override
            public void onPacketSending(PacketEvent e) {
                Player p = e.getPlayer();
                if (Config.DEAD_PLAYERS.contains(p.getName()) && !Config.SPECTATE_ENTITY) {
                    e.setCancelled(true);
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
        PacketAPI.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent e) {
                Player p = e.getPlayer();
                if (Config.DEAD_PLAYERS.contains(p.getName()) && !Config.SPECTATE_ENTITY) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.SPECTATE_BLOCKED));
                }
            }
        });
    }
}
