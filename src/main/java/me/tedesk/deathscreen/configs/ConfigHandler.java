package me.tedesk.deathscreen.configs;

import me.tedesk.deathscreen.BetterDeathScreen;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigHandler {

    public static void createConfig(String file) {
        try {
            if (!new File(BetterDeathScreen.plugin.getDataFolder(), file + ".yml").exists()) {
                BetterDeathScreen.plugin.saveResource(file + ".yml", false);
            }
        } catch (IllegalArgumentException e) {
            BetterDeathScreen.logger("§cCould not load the plugin messages! Please check the language section in your config.yml");
        }
    }

    public static FileConfiguration getConfig(String file) {
        File newfile = new File(BetterDeathScreen.plugin.getDataFolder() + File.separator + file + ".yml");
        return YamlConfiguration.loadConfiguration(newfile);
    }

}

