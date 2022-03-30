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
        // Reinicia a contagem ou mostra a mensagem do modo de jogo hardcore ao reconectar.
        if (p.getGameMode() == GameMode.SPECTATOR && !p.hasPermission(Config.ADMIN)) {
            if (!Bukkit.getServer().isHardcore()) {
                Timer.normal(p);
            }
            if (Bukkit.getServer().isHardcore()) {
                Timer.hardcore(p);
            }
        }
    }
}
