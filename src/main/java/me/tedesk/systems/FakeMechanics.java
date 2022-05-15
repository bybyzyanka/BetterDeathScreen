package me.tedesk.systems;

import me.tedesk.BetterDeathScreen;
import me.tedesk.api.PlayerAPI;
import me.tedesk.configs.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FakeMechanics {

    public static void sendDeath(Player p) {
        World w = Bukkit.getServer().getWorld(p.getWorld().getName());

        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        if (w.getGameRuleValue("keepInventory").equals("false")) {
            p.closeInventory();
            List<ItemStack> items = Arrays.stream(p.getInventory().getContents())
                    .filter(stack -> !PlayerAPI.isStackEmpty(stack))
                    .collect(Collectors.toList());
            if (!p.hasPermission(Config.KEEP_XP)) {
                PlayerDeathEvent player_death = new PlayerDeathEvent(p, items, PlayerAPI.dropExpOnDeath(p), "");
                p.getWorld().spawn(p.getLocation(), ExperienceOrb.class).setExperience(PlayerAPI.dropExpOnDeath(p));
                Bukkit.getPluginManager().callEvent(player_death);
                p.setLevel(0);
                p.setExp(0);
            }
            if (p.hasPermission(Config.KEEP_XP)) {
                PlayerDeathEvent player_death = new PlayerDeathEvent(p, items, 0, "");
                Bukkit.getPluginManager().callEvent(player_death);
            }
            for (ItemStack drops : items) {
                p.getWorld().dropItemNaturally(p.getLocation(), drops);
            }
            p.getInventory().clear();
            if (BetterDeathScreen.oldVersion()) {
                for (ItemStack armor : p.getInventory().getArmorContents()) {
                    if (armor.getType() == Material.AIR) {
                        continue;
                    }
                    if (armor.getType() != Material.AIR) {
                        p.getWorld().dropItemNaturally(p.getLocation(), armor);
                    }
                }
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);
            }
        }
        if (w.getGameRuleValue("keepInventory").equals("true")) {
            PlayerDeathEvent player_death = new PlayerDeathEvent(p, new ArrayList<>(), 0, "");
            Bukkit.getPluginManager().callEvent(player_death);
        }
    }

    public static void changeStatisticsVictim(Player p) {
        p.incrementStatistic(Statistic.DEATHS, 1);
        p.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
    }

    public static void changeStatisticsKiller(Player p) {
        p.incrementStatistic(Statistic.PLAYER_KILLS, 1);
    }
}