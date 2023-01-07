package com.github.victortedesco.betterdeathscreen.bukkit.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.api.utils.Version;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Statistic;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class DeathTasks {

    public void sendPlayerDeathEvent(Player player) {
        List<ItemStack> inventory = BetterDeathScreenAPI.getPlayerManager().getFilteredInventory(player);
        PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(player, inventory, Math.min(100, player.getLevel() * 7), "BetterDeathScreen");

        if (player.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            playerDeathEvent.setKeepInventory(false);
            playerDeathEvent.setKeepLevel(false);
            playerDeathEvent.setNewExp(0);
            playerDeathEvent.setNewLevel(0);
        }
        if (player.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            playerDeathEvent.setKeepInventory(true);
            playerDeathEvent.setDroppedExp(0);
            playerDeathEvent.setKeepLevel(true);
            playerDeathEvent.setNewExp((int) player.getExp());
            playerDeathEvent.setNewLevel(player.getLevel());
        }
        Bukkit.getPluginManager().callEvent(playerDeathEvent);
    }

    public void performDeath(Player player) {
        player.closeInventory();
        player.incrementStatistic(Statistic.DAMAGE_TAKEN, (int) Math.max(1, player.getHealth()));
        player.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
        for (PotionEffect potion : player.getActivePotionEffects()) player.removePotionEffect(potion.getType());
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 30000, 0, false, false));
        BetterDeathScreenAPI.getPlayerManager().getDeadPlayers().add(player);
        player.setHealth(0.1);
        player.setFireTicks(0);
        player.setFoodLevel(20);
        changeDisplayHealth(player);
        player.eject();
        for (Entity entity : player.getWorld().getEntities()) {
            if (Version.getServerVersion().getValue() < Version.v1_12.getValue()) {
                if (entity.getPassenger() == player) entity.eject();
            } else if (entity.getPassengers().contains(player)) entity.removePassenger(player);
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                if (creature.getTarget() == player) creature.setTarget(null);
            }
        }
        sendPlayerDeathEvent(player);
        BetterDeathScreen.getRespawnTasks().startCountdown(player);
        BetterDeathScreenAPI.getPlayerManager().playSound(player, BetterDeathScreen.getConfiguration().getDeathSounds(), true, false);
        BetterDeathScreenAPI.getAnimations().sendAnimation(player, BetterDeathScreen.getConfiguration().getAnimationType());
        if (BetterDeathScreenAPI.getPlayerManager().isHardcore(player)) player.setGameMode(GameMode.SPECTATOR);
    }

    public void sendCommandsOnDeath(Player player, Player killer) {
        for (String regex : BetterDeathScreen.getConfiguration().getCommandsOnDeath()) {
            String sender = StringUtils.substringBefore(regex, ": ");
            String command = StringUtils.substringAfter(regex, ": ");
            if ((sender.equalsIgnoreCase("killer") || command.contains("@killer")) && killer == null) continue;
            assert killer != null;

            String formattedCommand = command.replace("@player", player.getName()).replace("@killer", killer.getName());

            if (sender.equalsIgnoreCase("console"))
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), StringUtils.substringAfter(formattedCommand, "/"));

            if (sender.equalsIgnoreCase("player")) player.chat(formattedCommand);
            if (sender.equalsIgnoreCase("killer")) killer.chat(formattedCommand);
        }
    }

    public void changeDisplayHealth(Player player) {
        PacketContainer healthPacket = new PacketContainer(PacketType.Play.Server.UPDATE_HEALTH);
        healthPacket.getFloat().write(0, (float) BetterDeathScreenAPI.getPlayerManager().getMaxHealth(player));
        healthPacket.getIntegers().write(0, 20);
        healthPacket.getFloat().write(1, 2F);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (BetterDeathScreenAPI.getPlayerManager().isDead(player)) {
                    try {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, healthPacket);
                    } catch (InvocationTargetException ignored) {
                    }
                } else cancel();
            }
        }.runTaskTimerAsynchronously(BetterDeathScreen.getInstance(), 1L, 1L);
    }
}
