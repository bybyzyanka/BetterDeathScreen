package me.tedesk.deathscreen.events.deathlogic;

import me.tedesk.deathscreen.configs.Config;
import me.tedesk.deathscreen.events.Listeners;
import me.tedesk.deathscreen.systems.general.Timer;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join extends Listeners {

    // Criado para jogadores que são considerados mortos (Modo espectador) ao reconectarem.
    @EventHandler(priority = EventPriority.HIGHEST)
    public void playerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        // Caso o servidor não esteja no hardcore, reinicia a contagem.
        if (p.getGameMode() == GameMode.SPECTATOR && !Bukkit.getServer().isHardcore() && !p.hasPermission(Config.ADMIN)) {
            Timer.normal(p);
        }
        // Caso o servidor esteja no hardcore, envia a mensagem permamente.
        if (p.getGameMode() == GameMode.SPECTATOR && Bukkit.getServer().isHardcore() && !p.hasPermission(Config.ADMIN)) {
            Timer.hardcore(p);
        }
    }
}
