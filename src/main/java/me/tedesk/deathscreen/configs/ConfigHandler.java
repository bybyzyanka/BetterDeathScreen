package me.tedesk.deathscreen.configs;

import me.tedesk.deathscreen.BetterDeathScreen;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigHandler {

    public static void createConfig(String file) {
        if (!new File(BetterDeathScreen.plugin.getDataFolder(), file + ".yml").exists()) {
            BetterDeathScreen.plugin.saveResource(file + ".yml", false);
        }
    }

    public static FileConfiguration getConfig(String file) {
        File newfile = new File(BetterDeathScreen.plugin.getDataFolder() + File.separator + file + ".yml");
        return YamlConfiguration.loadConfiguration(newfile);
    }

}

