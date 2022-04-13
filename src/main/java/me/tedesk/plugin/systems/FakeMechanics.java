package me.tedesk.plugin.systems;

import me.tedesk.plugin.configs.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class FakeMechanics {

    public static void sendDeath(Player p) {
        World w = Bukkit.getServer().getWorld(p.getWorld().getName());

        if (!p.hasPermission(Config.KEEP_XP)) {
            if (w.getGameRuleValue("keepInventory").equals("false")) {
                p.setLevel(0);
                p.setExp(0);
            }
        }
        for (PotionEffect effect : p.getActivePotionEffects()) {
            p.removePotionEffect(effect.getType());
        }
        EntityDeathEvent entdeath = new EntityDeathEvent(p, null);
        Bukkit.getPluginManager().callEvent(entdeath);
        PlayerDeathEvent playerdeath = new PlayerDeathEvent(p, null, 0, null);
        Bukkit.getPluginManager().callEvent(playerdeath);
    }

    public static void dropInventory(Player p) {

        World w = Bukkit.getServer().getWorld(p.getWorld().getName());

        if (w.getGameRuleValue("keepInventory").equals("false")) {
            p.updateInventory();
            try {
                int remove = 0;
                for (ItemStack stack : p.getInventory().getArmorContents().clone()) {
                    if (stack == null)
                        continue;
                    if (stack.getType() != Material.AIR) {
                        remove++;
                        p.getWorld().dropItemNaturally(p.getLocation(), stack);
                        p.getInventory().removeItem(stack);
                    }
                }
                p.getInventory().setHelmet(null);
                p.getInventory().setChestplate(null);
                p.getInventory().setLeggings(null);
                p.getInventory().setBoots(null);
            } catch (RuntimeException ignored) {
            }
            try {
                int remove = 0;
                for (ItemStack stack : p.getInventory().getContents().clone()) {
                    if (stack == null)
                        continue;
                    if (stack.getType() != Material.AIR) {
                        remove++;
                        p.getWorld().dropItemNaturally(p.getLocation(), stack);
                        p.getInventory().removeItem(stack);
                    }
                }
            } catch (RuntimeException ignored) {
            }
            try {
                if (p.getItemOnCursor() == null)
                    return;
                if (p.getItemOnCursor() != null) {
                    p.getWorld().dropItemNaturally(p.getLocation(), p.getItemOnCursor());
                    p.setItemOnCursor(null);
                }
            } catch (RuntimeException ignored) {
            }
        }
    }

    public static void changeStatisticsVictim(Player p) {
        p.setStatistic(Statistic.DEATHS, p.getStatistic(Statistic.DEATHS) + 1);
        p.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
    }

    public static void changeStatisticsKiller(Player p) {
        p.setStatistic(Statistic.PLAYER_KILLS, p.getStatistic(Statistic.PLAYER_KILLS) + 1);
    }
}
