package me.tedesk.plugin.commands;

import me.tedesk.plugin.BetterDeathScreen;
import me.tedesk.plugin.configs.Config;
import me.tedesk.plugin.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadConfig implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cnd, String lbl, String[] args) {

        String reloaded = ChatColor.translateAlternateColorCodes('&', Messages.RELOAD);
        String noperm = ChatColor.translateAlternateColorCodes('&', Messages.NO_PERM);

        if (!(s instanceof Player)) {
            BetterDeathScreen.createAndLoadConfigs();
            s.sendMessage(reloaded);
        } else if (!s.hasPermission(Config.ADMIN)) {
            s.sendMessage(noperm);
        } else {
            try {
                BetterDeathScreen.createAndLoadConfigs();
                s.sendMessage(reloaded);
            } catch (Throwable e) {
                e.printStackTrace();
                Bukkit.getConsoleSender().sendMessage("§cO arquivo não pôde ser salvo.");
            }
            return true;
        }
        return true;
    }
}
