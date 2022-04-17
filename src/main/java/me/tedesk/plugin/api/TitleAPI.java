package me.tedesk.plugin.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.tedesk.plugin.BetterDeathScreen;
import me.tedesk.plugin.configs.Messages;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class TitleAPI {

    @SuppressWarnings("deprecation")
    public static void sendTitle(Player p, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        String title_error = ChatColor.translateAlternateColorCodes('&', Messages.TITLE_ERROR);

        // Envio da TitleBar para versões novas. (1.17 até 1.19)
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
                protocolManager.sendServerPacket(p, time_packet);
                protocolManager.sendServerPacket(p, subtitle_packet);
                protocolManager.sendServerPacket(p, title_packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                BetterDeathScreen.logger(title_error.replace("%player%", p.getDisplayName()));
            }
            return;
        }

        // Envio da TitleBar para versões antigas (1.12 até 1.16) e versões MUITO antigas (1.8 até 1.11).
        if (BetterDeathScreen.newVersion() || BetterDeathScreen.oldVersion()) {
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
                protocolManager.sendServerPacket(p, time_packet);
                protocolManager.sendServerPacket(p, subtitle_packet);
                protocolManager.sendServerPacket(p, title_packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                BetterDeathScreen.logger(title_error.replace("%player%", p.getDisplayName()));
            }
        }
    }
}