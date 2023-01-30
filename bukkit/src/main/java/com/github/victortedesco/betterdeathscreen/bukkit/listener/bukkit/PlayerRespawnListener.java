package com.github.victortedesco.betterdeathscreen.bukkit.listener.bukkit;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.api.utils.Version;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffectType;

import java.util.HashSet;
import java.util.Set;

public class PlayerRespawnListener implements Listener {

    private static final Set<Player> BED_NOT_FOUND_MESSAGE_SENT = new HashSet<>();

    public static Set<Player> getBedNotFoundMessageSent() {
        return BED_NOT_FOUND_MESSAGE_SENT;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawnMonitorPriority(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        BetterDeathScreenAPI.getEventManager().resetPlayerDamage(player);
        Titles.clearTitle(player);
        ActionBar.clearActionBar(player);
        if (player.getBedSpawnLocation() == null) {
            if (!getBedNotFoundMessageSent().contains(player)) {
                TranslatableComponent noBed = new TranslatableComponent("tile.bed.notValid");
                if (Version.getServerVersion().getValue() >= Version.v1_13.getValue())
                    noBed = new TranslatableComponent("block.minecraft.bed.not_valid");
                if (Version.getServerVersion().getValue() >= Version.v1_16.getValue())
                    noBed = new TranslatableComponent("block.minecraft.spawn.not_valid");
                player.spigot().sendMessage(noBed);
                getBedNotFoundMessageSent().add(player);
            }
        } else getBedNotFoundMessageSent().remove(player);
        player.removePotionEffect(PotionEffectType.INVISIBILITY);
        player.setAllowFlight(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR);
        if (PlayerDeathListener.getEquippedArmor().containsKey(player)) {
            player.getInventory().setArmorContents(PlayerDeathListener.getEquippedArmor().get(player));
            PlayerDeathListener.getEquippedArmor().remove(player);
            if (Version.getServerVersion() == Version.v1_8) {
                player.setItemInHand(PlayerDeathListener.getItemInMainHand().get(player));
                PlayerDeathListener.getItemInMainHand().remove(player);
            } else {
                player.getInventory().setItemInMainHand(PlayerDeathListener.getItemInMainHand().get(player));
                PlayerDeathListener.getItemInMainHand().remove(player);
                player.getInventory().setItemInOffHand(PlayerDeathListener.getItemInOffHand().get(player));
                PlayerDeathListener.getItemInOffHand().get(player);
            }
        }
        player.teleport(event.getRespawnLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawnHighestPriority(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (PlayerTeleportListener.getQueueTeleportLocation().containsKey(player))
            event.setRespawnLocation(PlayerTeleportListener.getQueueTeleportLocation().get(player));
        PlayerTeleportListener.getQueueTeleportLocation().remove(player);
    }
}
