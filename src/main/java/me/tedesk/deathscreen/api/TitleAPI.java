package me.tedesk.deathscreen.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.tedesk.deathscreen.BetterDeathScreen;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class TitleAPI {

    @SuppressWarnings("deprecation")
    public static void sendTitle(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        if (BetterDeathScreen.veryNewVersion()) {
            PacketContainer title_packet = new PacketContainer(PacketType.Play.Server.SET_TITLE_TEXT);
            title_packet.getTitleActions().write(0, EnumWrappers.TitleAction.TITLE);
            title_packet.getChatComponents().write(0, WrappedChatComponent.fromText(title));

            PacketContainer time_packet = new PacketContainer(PacketType.Play.Server.SET_TITLES_ANIMATION);
            time_packet.getTitleActions().write(0, EnumWrappers.TitleAction.TIMES);
            time_packet.getIntegers().write(0, fadeIn);
            time_packet.getIntegers().write(1, stay);
            time_packet.getIntegers().write(2, fadeOut);

            PacketContainer subtitle_packet = new PacketContainer(PacketType.Play.Server.SET_SUBTITLE_TEXT);
            subtitle_packet.getTitleActions().write(0, EnumWrappers.TitleAction.SUBTITLE);
            subtitle_packet.getChatComponents().write(0, WrappedChatComponent.fromText(subtitle));

            try {
                protocolManager.sendServerPacket(player, time_packet);
                protocolManager.sendServerPacket(player, subtitle_packet);
                protocolManager.sendServerPacket(player, title_packet);
            } catch (InvocationTargetException e) {
                BetterDeathScreen.logger("§cThe death message could not be send to: " + player.getDisplayName());
                e.printStackTrace();
            }
        // Envio da TitleBar para versões antigas: pré-1.17
        } else {
            PacketContainer title_packet = new PacketContainer(PacketType.Play.Server.TITLE);
            title_packet.getTitleActions().write(0, EnumWrappers.TitleAction.TITLE);
            title_packet.getChatComponents().write(0, WrappedChatComponent.fromText(title));

            PacketContainer time_packet = new PacketContainer(PacketType.Play.Server.TITLE);
            time_packet.getTitleActions().write(0, EnumWrappers.TitleAction.TIMES);
            time_packet.getIntegers().write(0, fadeIn);
            time_packet.getIntegers().write(1, stay);
            time_packet.getIntegers().write(2, fadeOut);

            PacketContainer subtitle_packet = new PacketContainer(PacketType.Play.Server.TITLE);
            subtitle_packet.getTitleActions().write(0, EnumWrappers.TitleAction.SUBTITLE);
            subtitle_packet.getChatComponents().write(0, WrappedChatComponent.fromText(subtitle));

            try {
                protocolManager.sendServerPacket(player, time_packet);
                protocolManager.sendServerPacket(player, subtitle_packet);
                protocolManager.sendServerPacket(player, title_packet);
            } catch (InvocationTargetException e) {
                BetterDeathScreen.logger("§cThe death message could not be send to: " + player.getDisplayName());
                e.printStackTrace();
            }
        }
    }
}