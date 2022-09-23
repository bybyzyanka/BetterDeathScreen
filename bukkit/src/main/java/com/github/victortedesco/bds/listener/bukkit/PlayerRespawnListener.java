package com.github.victortedesco.bds.listener.bukkit;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.utils.PlayerUtils;
import com.github.victortedesco.bds.utils.Version;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashSet;
import java.util.Set;

public class PlayerRespawnListener implements Listener {

    public static Set<String> BED_MESSAGE = new HashSet<>();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        // Resets all events related to last damage.
        PlayerUtils.resetDamageBeforeDeath(player);

        if (!BED_MESSAGE.contains(player.getName()) && player.getBedSpawnLocation() == null) {
            TranslatableComponent noBed = new TranslatableComponent("tile.bed.notValid");
            if (BetterDeathScreen.getVersion().value >= Version.v1_13.value)
                noBed = new TranslatableComponent("block.minecraft.bed.not_valid");
            if (BetterDeathScreen.getVersion().value >= Version.v1_16.value)
                noBed = new TranslatableComponent("block.minecraft.spawn.not_valid");
            player.spigot().sendMessage(noBed);
            BED_MESSAGE.add(player.getName());
        }
        if (player.getBedSpawnLocation() != null) {
            BED_MESSAGE.remove(player.getName());
        }
    }
}
