package com.github.victortedesco.betterdeathscreen.bukkit.utils;

import com.cryptomorin.xseries.ReflectionUtils;
import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.bukkit.BetterDeathScreen;
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

import java.util.List;

public final class DeathTasks {

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
        List<String> deathSounds = BetterDeathScreen.getConfiguration().getDeathSounds();

        player.closeInventory();
        player.incrementStatistic(Statistic.DEATHS, 1);
        player.incrementStatistic(Statistic.DAMAGE_TAKEN, (int) Math.max(1, player.getHealth()));
        player.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
        player.getActivePotionEffects().forEach(potion -> player.removePotionEffect(potion.getType()));
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20 * 30000, 0, false, false));
        BetterDeathScreenAPI.getPlayerManager().getDeadPlayers().add(player);
        changeAttributes(player);
        sendPlayerDeathEvent(player);
        BetterDeathScreen.getRespawnTasks().startCountdown(player);
        BetterDeathScreenAPI.getPlayerManager()
                .playSound(player, BetterDeathScreenAPI.getRandomizer().getRandomItemFromList(deathSounds), false);
        BetterDeathScreenAPI.getAnimations().sendAnimation(player, BetterDeathScreen.getConfiguration().getAnimationType());
        if (Bukkit.isHardcore()) player.setGameMode(GameMode.SPECTATOR);
    }

    public void sendCommandsOnDeath(Player player, Player killer) {
        for (String regex : BetterDeathScreen.getConfiguration().getCommandsOnDeath()) {
            try {
                String[] array = regex.split(": ");
                String sender = array[0];
                String command = array[1];
                if ((sender.equalsIgnoreCase("killer") || command.contains("@killer")) && killer == null) continue;
                String formattedCommand;
                if (killer != null)
                    formattedCommand = command.replace("@player", player.getName()).replace("@killer", killer.getName());
                else formattedCommand = command.replace("@player", player.getName());

                if (sender.equalsIgnoreCase("console"))
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand.split("/")[1]);
                if (sender.equalsIgnoreCase("player")) player.chat(formattedCommand);
                if (sender.equalsIgnoreCase("killer")) killer.chat(formattedCommand);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public void changeAttributes(Player player) {
        player.setHealth(BetterDeathScreenAPI.getPlayerManager().getMaxHealth(player));
        player.setRemainingAir(player.getMaximumAir());
        player.setFireTicks(0);
        player.setFoodLevel(20);
        player.eject();
        for (Entity entity : player.getWorld().getEntities()) {
            if (ReflectionUtils.VER < 12) {
                if (entity.getPassenger() == player) entity.eject();
            } else if (entity.getPassengers().contains(player)) entity.removePassenger(player);
            if (entity instanceof Creature) {
                Creature creature = (Creature) entity;
                if (creature.getTarget() == player) creature.setTarget(null);
            }
        }
    }
}
