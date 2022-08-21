package com.github.victortedesco.bds.configs;

import com.github.victortedesco.bds.BetterDeathScreen;
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
        File new_file = new File(BetterDeathScreen.plugin.getDataFolder() + File.separator + file + ".yml");
        return YamlConfiguration.loadConfiguration(new_file);
    }

    public static FileConfiguration getSavedConfiguration(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }

    public static File getFile(String file) {
        return new File(BetterDeathScreen.plugin.getDataFolder() + File.separator + file + ".yml");
    }
}

