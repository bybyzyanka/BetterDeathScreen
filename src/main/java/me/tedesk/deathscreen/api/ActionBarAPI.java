package me.tedesk.deathscreen.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import me.tedesk.deathscreen.BetterDeathScreen;
import me.tedesk.deathscreen.configs.Messages;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class ActionBarAPI {

    @SuppressWarnings("deprecation")
    public static void sendActionBar(Player p, String message) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

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
        // Envio da ActionBar para versões antigas: pré-1.17
        } else if (BetterDeathScreen.newVersion()) {
                PacketContainer ab_packet = new PacketContainer(PacketType.Play.Server.TITLE);
                ab_packet.getTitleActions().write(0, EnumWrappers.TitleAction.ACTIONBAR);
                ab_packet.getChatComponents().write(0, WrappedChatComponent.fromText(message));
                try {
                    protocolManager.sendServerPacket(p, ab_packet);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    BetterDeathScreen.logger(Messages.AB_ERROR.replace("%player%", p.getDisplayName()));
                }
        // Envio da ActionBar para versões MUITO antigas: pré-1.12
        } else {
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
