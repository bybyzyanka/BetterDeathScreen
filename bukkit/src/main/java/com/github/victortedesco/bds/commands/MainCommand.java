package com.github.victortedesco.bds.commands;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.ConfigHandler;
import com.github.victortedesco.bds.configs.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        boolean performed = false;
        if (!(sender instanceof Player)) {
            if (args.length != 0 && args[0].equalsIgnoreCase("reload")) {
                BetterDeathScreen.createAndLoadConfigs();
                sender.sendMessage(Messages.RELOAD);
                performed = true;
            }
            if (!performed) {
                for (String help : Messages.HELP) {
                    if (help.contains("setspawn")) {
                        continue;
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                }
            }
        }
        if (sender instanceof Player) {
            if (!sender.hasPermission(Config.ADMIN)) {
                sender.sendMessage(Messages.NO_PERM);
                return true;
            }
            if (args.length != 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    try {
                        BetterDeathScreen.createAndLoadConfigs();
                        sender.sendMessage(Messages.RELOAD);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                    performed = true;
                }
                if (args[0].equalsIgnoreCase("setspawn") && args.length > 1) {
                    if (args[1].equalsIgnoreCase("normal")) {
                        File spawn_loc = ConfigHandler.getFile("locations");
                        FileConfiguration spawn_cfg = ConfigHandler.getSavedConfiguration(spawn_loc);
                        spawn_cfg.set("normal.world", ((Player) sender).getWorld().getName());
                        spawn_cfg.set("normal.X", ((Player) sender).getLocation().getX());
                        spawn_cfg.set("normal.Y", ((Player) sender).getLocation().getY());
                        spawn_cfg.set("normal.Z", ((Player) sender).getLocation().getZ());
                        spawn_cfg.set("normal.yaw", ((Player) sender).getLocation().getYaw());
                        spawn_cfg.set("normal.pitch", ((Player) sender).getLocation().getPitch());
                        try {
                            spawn_cfg.save(spawn_loc);
                            BetterDeathScreen.createAndLoadConfigs();
                            sender.sendMessage(Messages.SPAWN_SET.replace("%type%", "Normal"));
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        performed = true;
                    }
                    if (args[1].equalsIgnoreCase("vip")) {
                        File spawn_loc = ConfigHandler.getFile("locations");
                        FileConfiguration spawn_cfg = ConfigHandler.getSavedConfiguration(spawn_loc);
                        spawn_cfg.set("vip.world", ((Player) sender).getWorld().getName());
                        spawn_cfg.set("vip.X", ((Player) sender).getLocation().getX());
                        spawn_cfg.set("vip.Y", ((Player) sender).getLocation().getY());
                        spawn_cfg.set("vip.Z", ((Player) sender).getLocation().getZ());
                        spawn_cfg.set("vip.yaw", ((Player) sender).getLocation().getYaw());
                        spawn_cfg.set("vip.pitch", ((Player) sender).getLocation().getPitch());
                        try {
                            spawn_cfg.save(spawn_loc);
                            BetterDeathScreen.createAndLoadConfigs();
                            sender.sendMessage(Messages.SPAWN_SET.replace("%type%", "VIP"));
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        performed = true;
                    }
                }
            }
            if (!performed) {
                for (String help : Messages.HELP) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', help));
                }
            }
        }
        return true;
    }
}