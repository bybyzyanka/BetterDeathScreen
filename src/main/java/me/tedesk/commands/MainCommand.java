package me.tedesk.commands;

import me.tedesk.BetterDeathScreen;
import me.tedesk.configs.Config;
import me.tedesk.configs.Messages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cnd, String lbl, String[] args) {

        // Odeio usar else, mas tive que usar para facilitar.
        if (!(s instanceof Player)) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    BetterDeathScreen.createAndLoadConfigs();
                    if (Config.LANGUAGE.equals("en_US") || Config.LANGUAGE.equals("pt_BR")) {
                        s.sendMessage(Messages.RELOAD.replace("&", "§"));
                    }
                }
                if (!args[0].equalsIgnoreCase("reload")) {
                    for (String help : Messages.HELP) {
                        s.sendMessage(help.replace("&", "§"));
                    }
                }
            } else {
                for (String help : Messages.HELP) {
                    s.sendMessage(help.replace("&", "§"));
                }
            }
        }
        if (s instanceof Player) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!s.hasPermission(Config.ADMIN)) {
                        s.sendMessage(Messages.NO_PERM.replace("&", "§"));
                    }
                    if (s.hasPermission(Config.ADMIN)) {
                        try {
                            BetterDeathScreen.createAndLoadConfigs();
                            if (Config.LANGUAGE.equals("en_US") || Config.LANGUAGE.equals("pt_BR")) {
                                s.sendMessage(Messages.RELOAD.replace("&", "§"));
                            }
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (!args[0].equalsIgnoreCase("reload")) {
                    for (String help : Messages.HELP) {
                        s.sendMessage(help.replace("&", "§"));
                    }
                }
            } else {
                for (String help : Messages.HELP) {
                    s.sendMessage(help.replace("&", "§"));
                }
            }
        }
        return true;
    }
}