package me.tedesk.commands;

import me.tedesk.BetterDeathScreen;
import me.tedesk.configs.Config;
import me.tedesk.configs.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cnd, String lbl, String[] args) {

        if (!(s instanceof Player)) {
            if (args[0].equals("reload")) {
                BetterDeathScreen.createAndLoadConfigs();
                s.sendMessage(Messages.RELOAD.replace("&", "§"));
            }
            if (!args[0].equals("reload")) {
                for (String help : Messages.HELP) {
                    s.sendMessage(help.replace("&", "§"));
                }
            }
        }
        if (s instanceof Player) {
            if (args.length != 1) {
                for (String help : Messages.HELP) {
                    s.sendMessage(help.replace("&", "§"));
                }
            }
            if (args.length == 1) {
                if (args[0].equals("reload")) {
                    if (!s.hasPermission(Config.ADMIN)) {
                        s.sendMessage(Messages.NO_PERM.replace("&", "§"));
                        return true;
                    }
                    if (s.hasPermission(Config.ADMIN)) {
                        try {
                            BetterDeathScreen.createAndLoadConfigs();
                            s.sendMessage(Messages.RELOAD.replace("&", "§"));
                        } catch (Throwable e) {
                            e.printStackTrace();
                            Bukkit.getConsoleSender().sendMessage("§cO arquivo não pôde ser salvo.");
                        }
                        return true;
                    }
                }
                if (!args[0].equals("reload")){
                    for (String help : Messages.HELP) {
                        s.sendMessage(help.replace("&", "§"));
                    }
                    return true;
                }
            }
        }
        return false;
    }
}