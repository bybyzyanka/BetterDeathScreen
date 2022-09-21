package com.github.victortedesco.bds.commands;

import com.github.victortedesco.bds.configs.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainTabComplete implements TabCompleter {

    private final String[] FIRST_COMPLETIONS_CONSOLE = {"reload"};
    private final String[] FIRST_COMPLETIONS_PLAYER = {"reload", "setspawn"};
    private final String[] RELOAD_COMPLETIONS = {"normal", "vip"};

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 1) {
                final List<String> completions = new ArrayList<>();

                StringUtil.copyPartialMatches(args[0], Arrays.asList(FIRST_COMPLETIONS_CONSOLE), completions);
                Collections.sort(completions);
                return completions;
            }
        }
        if (sender instanceof Player && sender.hasPermission(Config.ADMIN)) {
            if (args.length == 1) {
                final List<String> completions = new ArrayList<>();

                StringUtil.copyPartialMatches(args[0], Arrays.asList(FIRST_COMPLETIONS_PLAYER), completions);
                Collections.sort(completions);
                return completions;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("setspawn")) {
                final List<String> completions = new ArrayList<>();

                StringUtil.copyPartialMatches(args[1], Arrays.asList(RELOAD_COMPLETIONS), completions);
                Collections.sort(completions);
                return completions;
            }
        }
        return Collections.emptyList();
    }
}
