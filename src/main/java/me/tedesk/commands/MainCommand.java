package me.tedesk.commands;

import me.tedesk.BetterDeathScreen;
import me.tedesk.configs.Config;
import me.tedesk.configs.ConfigHandler;
import me.tedesk.configs.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cnd, String lbl, String[] args) {

        // Odeio usar else, mas tive que usar para facilitar.
        if (!(s instanceof Player)) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    BetterDeathScreen.createAndLoadConfigs();
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.RELOAD));
                }
                else {
                    for (String help : Messages.HELP) {
                        if (help.contains("setspawn")) {
                            continue;
                        }
                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                    }
                }
            } else {
                for (String help : Messages.HELP) {
                    if (help.contains("setspawn")) {
                        continue;
                    }
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                }
            }
        }
        if (s instanceof Player) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!s.hasPermission(Config.ADMIN)) {
                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_PERM));
                    }
                    if (s.hasPermission(Config.ADMIN)) {
                        try {
                            BetterDeathScreen.createAndLoadConfigs();
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.RELOAD));
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("setspawn")) {
                    if (!s.hasPermission(Config.ADMIN)) {
                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.NO_PERM));
                    }
                    if (s.hasPermission(Config.ADMIN)) {
                        File spawn_loc = ConfigHandler.getFile("locations");
                        FileConfiguration spawn_cfg = ConfigHandler.getSavedConfiguration(spawn_loc);
                        spawn_cfg.set("spawn.world", ((Player) s).getWorld().getName());
                        spawn_cfg.set("spawn.X", ((Player) s).getLocation().getX());
                        spawn_cfg.set("spawn.Y", ((Player) s).getLocation().getY());
                        spawn_cfg.set("spawn.Z", ((Player) s).getLocation().getZ());
                        spawn_cfg.set("spawn.yaw", ((Player) s).getLocation().getYaw());
                        spawn_cfg.set("spawn.pitch", ((Player) s).getLocation().getPitch());
                        try {
                            spawn_cfg.save(spawn_loc);
                            BetterDeathScreen.createAndLoadConfigs();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', Messages.SPAWN_SET));
                    }
                } else {
                    for (String help : Messages.HELP) {
                        s.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                    }
                }
            } else {
                for (String help : Messages.HELP) {
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                }
            }
        }
        return true;
    }
}