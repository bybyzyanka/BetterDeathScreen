package me.tedesk.events.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import me.tedesk.BetterDeathScreen;
import me.tedesk.animations.Animation;
import me.tedesk.api.PacketAPI;
import me.tedesk.api.SoundAPI;
import me.tedesk.api.TitleAPI;
import me.tedesk.configs.Config;
import me.tedesk.systems.Randomizer;
import me.tedesk.systems.Tasks;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class DeathPacketListener {
    private static void sendEvents(Player p, PacketEvent e) {
        int time = Config.TIME;
        if (time <= 0) {
            time = 1;
        }
        if (!Config.DEAD_PLAYERS.contains(p.getUniqueId())) {
            Config.DEAD_PLAYERS.add(p.getUniqueId());
            e.setCancelled(true);
            p.setHealth(0.1);
            if (!Config.MOVE_SPECTATOR) {
                p.setWalkSpeed(0F);
                p.setFlySpeed(0F);
            }
            for (PotionEffect pe : p.getActivePotionEffects()) {
                p.removePotionEffect(pe.getType());
            }
            if (p.getWorld().getGameRuleValue("keepInventory").equals("false")) {
                if (!p.hasPermission(Config.KEEP_XP)) {
                    p.setLevel(0);
                    p.setExp(0);
                }
            }
            p.setGameMode(GameMode.SPECTATOR);
            SoundAPI.sendSound(p, p.getLocation(), Config.SOUND_DEATH, Config.SOUND_DEATH_VOLUME, Config.SOUND_DEATH_PITCH);
            TitleAPI.sendTitle(p, 2, 20 * time, 2, Randomizer.customTitles(), Randomizer.customSubtitles());
            if (!Bukkit.getServer().isHardcore()) {
                Tasks.normalTimer(p);
            }
            if (Bukkit.getServer().isHardcore()) {
                Tasks.hardcoreTimer(p);
            }
            Animation.sendAnimation(p);
        }
    }

    @SuppressWarnings("deprecation")
    public static void cancelDeathScreen() {
        if (BetterDeathScreen.veryNewVersion()) {
            PacketAPI.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.PLAYER_COMBAT_KILL) {
                @Override
                public void onPacketSending(PacketEvent e) {
                    Player p = e.getPlayer();
                    sendEvents(p, e);
                }
            });
        }
        if (BetterDeathScreen.newVersion() || BetterDeathScreen.oldVersion()) {
            PacketAPI.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.COMBAT_EVENT) {
                @Override
                public void onPacketSending(PacketEvent e) {
                    Player p = e.getPlayer();
                    PacketContainer packet = e.getPacket();
                    if (packet.getCombatEvents().read(0) == EnumWrappers.CombatEventType.ENTITY_DIED) {
                        sendEvents(p, e);
                    }
                }
            });
            PacketAPI.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.UPDATE_HEALTH) {
                @Override
                public void onPacketSending(PacketEvent e) {
                    Player p = e.getPlayer();
                    PacketContainer packet = e.getPacket();
                    if (packet.getFloat().read(0) <= 0) {
                        sendEvents(p, e);
                    }
                }
            });
        }
    }
}
