package me.tedesk.systems;

import me.tedesk.configs.Config;
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
        EntityDeathEvent ent_death = new EntityDeathEvent(p, null);
        Bukkit.getPluginManager().callEvent(ent_death);
        PlayerDeathEvent player_death = new PlayerDeathEvent(p, null, 0, null);
        Bukkit.getPluginManager().callEvent(player_death);
    }

    public static void dropInventory(Player p) {

        World w = Bukkit.getServer().getWorld(p.getWorld().getName());

        if (w.getGameRuleValue("keepInventory").equals("false")) {
            p.updateInventory();
            try {
                for (ItemStack stack : p.getInventory().getArmorContents().clone()) {
                    if (stack == null)
                        continue;
                    if (stack.getType() != Material.AIR) {
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
                for (ItemStack stack : p.getInventory().getContents().clone()) {
                    if (stack == null)
                        continue;
                    if (stack.getType() != Material.AIR) {
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
        p.incrementStatistic(Statistic.DEATHS, 1);
        p.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
    }

    public static void changeStatisticsKiller(Player p) {
        p.incrementStatistic(Statistic.PLAYER_KILLS, 1);
    }
}