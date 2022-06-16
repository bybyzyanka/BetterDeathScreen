package me.tedesk.bds.events.packets;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import com.viaversion.viaversion.api.ViaManager;
import com.viaversion.viaversion.api.configuration.ViaVersionConfig;
import com.viaversion.viaversion.api.platform.ViaInjector;
import com.viaversion.viaversion.api.platform.ViaPlatformLoader;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.animations.Animation;
import me.tedesk.bds.api.PacketAPI;
import me.tedesk.bds.api.SoundAPI;
import me.tedesk.bds.configs.Config;
import me.tedesk.bds.systems.Randomizer;
import me.tedesk.bds.systems.Tasks;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class DeathPacketListener {

    private static void sendEvents(Player p) {
        if (!Config.DEAD_PLAYERS.contains(p.getName())) {
            p.setHealth(0.1);
            Config.DEAD_PLAYERS.add(p.getName());
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
            SoundAPI.sendSound(p, p.getLocation(), Randomizer.randomSound(Config.SOUND_DEATH), Config.SOUND_DEATH_VOLUME, Config.SOUND_DEATH_PITCH);
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

                    e.setCancelled(true);
                    sendEvents(p);
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
                        sendEvents(p);
                    }
                }
            });
        }
        // For some reason, 1.8 seems to not use Combat Event packet.
        PacketAPI.getProtocolManager().addPacketListener(new PacketAdapter(BetterDeathScreen.plugin, ListenerPriority.HIGHEST, PacketType.Play.Server.UPDATE_HEALTH) {
            @Override
            public void onPacketSending(PacketEvent e) {
                PacketContainer packet = e.getPacket();
                Player p = e.getPlayer();

                if (packet.getFloat().read(0) <= 0) {
                    e.setCancelled(true);
                    sendEvents(p);
                }
            }
        });
    }
}
