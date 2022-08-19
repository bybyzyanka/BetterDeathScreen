package me.tedesk.bds.commands;

import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.configs.Config;
import me.tedesk.bds.configs.ConfigHandler;
import me.tedesk.bds.configs.Messages;
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
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    BetterDeathScreen.createAndLoadConfigs();
                    s.sendMessage(Messages.RELOAD);
                } else {
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
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!s.hasPermission(Config.ADMIN)) {
                        s.sendMessage(Messages.NO_PERM);
                    }
                    if (s.hasPermission(Config.ADMIN)) {
                        try {
                            BetterDeathScreen.createAndLoadConfigs();
                            s.sendMessage(Messages.RELOAD);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                    return true;
                }
                if (args[0].equalsIgnoreCase("setspawn")) {
                    if (!s.hasPermission(Config.ADMIN)) {
                        s.sendMessage(Messages.NO_PERM);
                    }
                    if (s.hasPermission(Config.ADMIN)) {
                        if (args[1].equalsIgnoreCase("normal")) {
                            File spawn_loc = ConfigHandler.getFile("locations");
                            FileConfiguration spawn_cfg = ConfigHandler.getSavedConfiguration(spawn_loc);
                            spawn_cfg.set("normal.world", ((Player) s).getWorld().getName());
                            spawn_cfg.set("normal.X", ((Player) s).getLocation().getX());
                            spawn_cfg.set("normal.Y", ((Player) s).getLocation().getY());
                            spawn_cfg.set("normal.Z", ((Player) s).getLocation().getZ());
                            spawn_cfg.set("normal.yaw", ((Player) s).getLocation().getYaw());
                            spawn_cfg.set("normal.pitch", ((Player) s).getLocation().getPitch());
                            try {
                                spawn_cfg.save(spawn_loc);
                                BetterDeathScreen.createAndLoadConfigs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            s.sendMessage(Messages.SPAWN_SET.replace("%type%", "Normal"));
                            return true;
                        }
                        if (args[1].equalsIgnoreCase("vip")) {
                            File spawn_loc = ConfigHandler.getFile("locations");
                            FileConfiguration spawn_cfg = ConfigHandler.getSavedConfiguration(spawn_loc);
                            spawn_cfg.set("vip.world", ((Player) s).getWorld().getName());
                            spawn_cfg.set("vip.X", ((Player) s).getLocation().getX());
                            spawn_cfg.set("vip.Y", ((Player) s).getLocation().getY());
                            spawn_cfg.set("vip.Z", ((Player) s).getLocation().getZ());
                            spawn_cfg.set("vip.yaw", ((Player) s).getLocation().getYaw());
                            spawn_cfg.set("vip.pitch", ((Player) s).getLocation().getPitch());
                            try {
                                spawn_cfg.save(spawn_loc);
                                BetterDeathScreen.createAndLoadConfigs();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            s.sendMessage(Messages.SPAWN_SET.replace("%type%", "VIP"));
                        } else {
                            for (String help : Messages.HELP) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                            }
                        }
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