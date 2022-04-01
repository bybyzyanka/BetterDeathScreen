package me.tedesk.plugin.systems.general;

import me.tedesk.plugin.configs.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class FakeMechanics {

    public static void sendDeath(Player p){
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
        PlayerDeathEvent playerdeath = new PlayerDeathEvent(p, null, 0, null);
        Bukkit.getPluginManager().callEvent(playerdeath);
        EntityDeathEvent entdeath = new EntityDeathEvent(p, null);
        Bukkit.getPluginManager().callEvent(entdeath);
    }

    public static void dropInventory(Player p) {

        World w = Bukkit.getServer().getWorld(p.getWorld().getName());

        if (w.getGameRuleValue("keepInventory").equals("false")) {
            try {
                for (ItemStack itemStackArmor : p.getInventory().getArmorContents()) {
                    p.getWorld().dropItemNaturally(p.getLocation(), itemStackArmor);
                    p.getInventory().removeItem(itemStackArmor);
                }
            } catch (IllegalArgumentException ignored) {
            }
            try {
                for (ItemStack itemStack : p.getInventory().getContents()) {
                    p.getWorld().dropItemNaturally(p.getLocation(), itemStack);
                    p.getWorld().dropItemNaturally(p.getLocation(), p.getItemOnCursor());
                    p.setItemOnCursor(new ItemStack(Material.AIR));
                    p.getInventory().removeItem(itemStack);
                }
            } catch (IllegalArgumentException ignored) {
            }
            p.getInventory().setHelmet(null);
            p.getInventory().setChestplate(null);
            p.getInventory().setLeggings(null);
            p.getInventory().setBoots(null);
            p.getInventory().clear();
        }
    }

    public static void changeStatisticsVictim(Player p) {
        p.setStatistic(Statistic.DEATHS, p.getStatistic(Statistic.DEATHS) + 1);
        p.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
    }

    public static void changeStatisticsKiller(Player p, EntityDamageByEntityEvent event) {
        p.setStatistic(Statistic.PLAYER_KILLS, p.getStatistic(Statistic.PLAYER_KILLS) + 1);
        p.setStatistic(Statistic.KILL_ENTITY, p.getStatistic(Statistic.KILL_ENTITY) + 1);
        p.setStatistic(Statistic.DAMAGE_DEALT, p.getStatistic(Statistic.DAMAGE_DEALT) + (int) event.getDamage());
    }
}
