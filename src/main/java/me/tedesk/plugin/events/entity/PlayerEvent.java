package me.tedesk.plugin.events.entity;

import me.tedesk.plugin.configs.Config;
import me.tedesk.plugin.events.Listeners;
import me.tedesk.plugin.systems.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerEvent extends Listeners {

    // Criado para jogadores que são considerados mortos (no modo espectador) ao reconectarem.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        // Reinicia a contagem ou mostra a mensagem do modo de jogo hardcore ao reconectar.
        if ((p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission(Config.ADMIN)) || Config.DEAD_PLAYERS.contains(p.getName())) {
            if (Bukkit.getServer().isHardcore()) {
                Timer.hardcore(p);
            }
            if (!Bukkit.getServer().isHardcore()) {
                Timer.normal(p);
            }
        }
        // Desbugando o respawn em outras dimensões.
        if (p.getBedSpawnLocation() != null) {
            if (p.getBedSpawnLocation().getWorld().getEnvironment() != World.Environment.NORMAL) {
                p.setBedSpawnLocation(Bukkit.getWorlds().get(0).getSpawnLocation(), false);
            }
        }
    }
}
