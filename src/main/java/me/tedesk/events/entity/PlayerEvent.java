package me.tedesk.events.entity;

import me.tedesk.events.Listeners;
import me.tedesk.configs.Config;
import me.tedesk.systems.Tasks;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerEvent extends Listeners {

    // Criado para jogadores que são considerados mortos (no modo espectador) ao reconectarem.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        // Reinicia a contagem ou mostra a mensagem do modo de jogo hardcore ao reconectar.
        if ((p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission(Config.ADMIN)) || Config.DEAD_PLAYERS.contains(p.getName())) {
            if (!Bukkit.getServer().isHardcore()) {
                Tasks.normalTimer(p);
                return;
            }
            if (Bukkit.getServer().isHardcore()) {
                Tasks.hardcoreTimer(p);
            }
        }
        // Desbugando o respawn em outras dimensões.
        if (p.getBedSpawnLocation() != null) {
            if (p.getBedSpawnLocation().getWorld().getEnvironment() != World.Environment.NORMAL) {
                p.setBedSpawnLocation(Bukkit.getWorlds().get(0).getSpawnLocation(), false);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerTeleport (PlayerTeleportEvent e) {
        Player p = e.getPlayer();

        if (!Config.HOTBAR_TELEPORT_SPECTATOR) {
            if (Config.DEAD_PLAYERS.contains(p.getName()) && e.getCause() == PlayerTeleportEvent.TeleportCause.SPECTATE) {
                e.setCancelled(true);
            }
        }
    }
}
