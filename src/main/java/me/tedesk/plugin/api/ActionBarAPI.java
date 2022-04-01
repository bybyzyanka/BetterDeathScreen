package me.tedesk.plugin.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.tedesk.plugin.BetterDeathScreen;
import me.tedesk.plugin.configs.Messages;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class ActionBarAPI {

    @SuppressWarnings("deprecation")
    public static void sendActionBar(Player p, String message) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        // Envio da ActionBar para versões novas. (1.17 até 1.19)
        if (BetterDeathScreen.veryNewVersion()) {
            PacketContainer ab_packet = new PacketContainer(PacketType.Play.Server.SET_ACTION_BAR_TEXT);
            ab_packet.getTitleActions().write(0, EnumWrappers.TitleAction.ACTIONBAR);
            ab_packet.getChatComponents().write(0, WrappedChatComponent.fromText(message));
            try {
                protocolManager.sendServerPacket(p, ab_packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                BetterDeathScreen.logger(Messages.AB_ERROR.replace("%player%", p.getDisplayName()));
            }
        }

        // Envio da ActionBar para versões antigas. (1.12 até 1.16)
        if (BetterDeathScreen.newVersion()) {
            PacketContainer ab_packet = new PacketContainer(PacketType.Play.Server.TITLE);
            ab_packet.getTitleActions().write(0, EnumWrappers.TitleAction.ACTIONBAR);
            ab_packet.getChatComponents().write(0, WrappedChatComponent.fromText(message));
            try {
                protocolManager.sendServerPacket(p, ab_packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                BetterDeathScreen.logger(Messages.AB_ERROR.replace("%player%", p.getDisplayName()));
            }
        }

        // Envio da ActionBar para versões MUITO antigas. (1.8 até 1.11)
        if (BetterDeathScreen.oldVersion()) {
            PacketContainer ab_packet = new PacketContainer(PacketType.Play.Server.CHAT);
            ab_packet.getBytes().write(0, (byte) 2);
            ab_packet.getChatComponents().write(0, WrappedChatComponent.fromText(message));
            try {
                protocolManager.sendServerPacket(p, ab_packet);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                BetterDeathScreen.logger(Messages.AB_ERROR.replace("%player%", p.getDisplayName()));
            }
        }
    }
}
