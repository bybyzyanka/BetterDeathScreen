package me.tedesk.bds.events.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.animations.Animation;
import me.tedesk.bds.configs.Config;
import me.tedesk.bds.configs.Messages;
import me.tedesk.bds.systems.Randomizer;
import me.tedesk.bds.systems.Tasks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class DeathPacketListener {

    private static void sendEventsPackets(Player p) {
        p.setHealth(1);
        Config.DEAD_PLAYERS.add(p.getName());
        for (PotionEffect pe : p.getActivePotionEffects()) {
            p.removePotionEffect(pe.getType());
        }
        p.setGameMode(GameMode.SPECTATOR);
        if (!Config.MOVE_SPECTATOR) {
            p.setWalkSpeed(0F);
            p.setFlySpeed(0F);
        }
        if (p.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            if (!p.hasPermission(Config.KEEP_XP)) {
                p.setLevel(0);
                p.setExp(0);
            }
        }
        try {
            p.playSound(p.getLocation(), Sound.valueOf(Randomizer.randomSound(Config.SOUND_DEATH)), Config.SOUND_DEATH_VOLUME, Config.SOUND_DEATH_PITCH);
        } catch (Exception e) {
            BetterDeathScreen.logger(ChatColor.translateAlternateColorCodes('&', Messages.SOUND_ERROR).replace("%sound%", "DEATH"));
        }
        if (!Bukkit.getServer().isHardcore()) {
            Tasks.normalTimer(p);
        }
        if (Bukkit.getServer().isHardcore()) {
            Tasks.hardcoreTimer(p);
        }
        Animation.sendAnimation(p);
    }

    @SuppressWarnings("deprecation")
    public static void cancelDeathScreen() {
        if (BetterDeathScreen.veryNewVersion()) {
            ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.PLAYER_COMBAT_KILL) {
                @Override
                public void onPacketSending(PacketEvent e) {
                    Player p = e.getPlayer();

                    if (Config.USE_PACKET_EVENT_HANDLER) {
                        e.setCancelled(true);
                        sendEventsPackets(p);
                    }
                }
            });
        }
        if (BetterDeathScreen.newVersion() || BetterDeathScreen.oldVersion()) {
            ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.COMBAT_EVENT) {
                @Override
                public void onPacketSending(PacketEvent e) {
                    PacketContainer packet = e.getPacket();
                    Player p = e.getPlayer();

                    if (packet.getCombatEvents().read(0) == EnumWrappers.CombatEventType.ENTITY_DIED) {
                        if (Config.USE_PACKET_EVENT_HANDLER) {
                            e.setCancelled(true);
                            sendEventsPackets(p);
                        }
                    }
                }
            });
        }
        // Por algum motivo, a 1.8 não usa o pacote Combat Event.
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.UPDATE_HEALTH) {
            @Override
            public void onPacketSending(PacketEvent e) {
                PacketContainer packet = e.getPacket();
                Player p = e.getPlayer();

                if (packet.getFloat().read(0) <= 0) {
                    if (Config.USE_PACKET_EVENT_HANDLER) {
                        e.setCancelled(true);
                        sendEventsPackets(p);
                    }
                }
            }
        });
    }
}
