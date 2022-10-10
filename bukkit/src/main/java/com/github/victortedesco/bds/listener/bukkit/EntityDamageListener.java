package com.github.victortedesco.bds.listener.bukkit;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.animations.Animation;
import com.github.victortedesco.bds.configs.Config;
import com.github.victortedesco.bds.configs.Messages;
import com.github.victortedesco.bds.utils.PlayerUtils;
import com.github.victortedesco.bds.utils.Randomizer;
import com.github.victortedesco.bds.utils.Tasks;
import com.github.victortedesco.bds.utils.Version;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityDamageListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        int time = Config.TIME;
        if (time <= 0) time = 1;

        if (entity instanceof Player) {
            Player playerVictim = (Player) entity;
            Player playerKiller = null;

            if (PlayerUtils.isHardcore(playerVictim)) time = 5;
            if (Config.DEAD_PLAYERS.contains(playerVictim.getName())) {
                event.setCancelled(true);
                return;
            }
            if (playerVictim.getHealth() > event.getFinalDamage() || !noTotem(playerVictim, event)) {
                sendDamageInfo(playerVictim, event);
            }
            if (playerVictim.getHealth() <= event.getFinalDamage() && noTotem(playerVictim, event)) {
                Config.DEAD_PLAYERS.add(playerVictim.getName());
                PlayerTeleportListener.TELEPORT_MESSAGE_IMUNE.add(playerVictim.getName());
                Bukkit.getScheduler().runTaskLaterAsynchronously(BetterDeathScreen.getInstance(),
                        () -> PlayerTeleportListener.TELEPORT_MESSAGE_IMUNE.remove(playerVictim.getName()), 5);
                playerVictim.setGameMode(GameMode.SPECTATOR);
                Titles.sendTitle(playerVictim, 2, 20 * time, 2, Randomizer.getRandomMessage(playerVictim, null, Messages.KILLED_TITLES), Randomizer.getRandomMessage(playerVictim, null, Messages.KILLED_SUBTITLES));
                PlayerUtils.incrementStatistic(playerVictim, Statistic.DAMAGE_TAKEN, (int) event.getFinalDamage());
                if (event instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) event).getDamager();

                    if (Config.USE_KILL_CAM) playerVictim.setSpectatorTarget(damager);
                    if (damager instanceof Player) {
                        playerKiller = (Player) damager;
                        killedByPlayer(playerVictim, playerKiller, 20 * time, event);
                    }
                    if (damager instanceof Projectile) {
                        Projectile projectile = (Projectile) damager;
                        if (projectile.getShooter() instanceof Entity) {
                            if (Config.USE_KILL_CAM) playerVictim.setSpectatorTarget((Entity) projectile.getShooter());
                        }
                        if (projectile.getShooter() instanceof Player) {
                            playerKiller = (Player) projectile.getShooter();
                            killedByPlayer(playerVictim, playerKiller, 20 * time, event);
                        }
                    }
                }
                sendDeath(playerVictim);
                sendCommands(playerVictim, playerKiller);
                event.setDamage(0);
            }
        }
    }

    private boolean noTotem(Player player, EntityDamageEvent event) {
        if (BetterDeathScreen.getVersion().value >= Version.v1_11.value) {
            Material mainHand = player.getInventory().getItemInMainHand().getType();
            Material offHand = player.getInventory().getItemInOffHand().getType();

            if (mainHand != XMaterial.TOTEM_OF_UNDYING.parseMaterial() && offHand != XMaterial.TOTEM_OF_UNDYING.parseMaterial())
                return true;
            return event.getCause() == EntityDamageEvent.DamageCause.SUICIDE || event.getCause() == EntityDamageEvent.DamageCause.VOID;
        }
        return true;
    }

    private void sendDamageInfo(Player player, EntityDamageEvent event) {
        if (!(event instanceof EntityDamageByEntityEvent) && !(event instanceof EntityDamageByBlockEvent)) {
            Object[] array = new Object[3];

            array[0] = event.getCause();
            array[1] = event.getDamage();
            array[2] = event.getFinalDamage();
            PlayerDeathListener.DAMAGE_BEFORE_DEATH.put(player.getName(), array);
        }
        if (event instanceof EntityDamageByEntityEvent) {
            Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
            Object[] array = new Object[4];

            array[0] = damager;
            if (damager instanceof Projectile) {
                array[0] = ((Projectile) damager).getShooter();
            }
            array[1] = event.getCause();
            array[2] = event.getDamage();
            array[3] = event.getFinalDamage();
            PlayerDeathListener.DAMAGE_BY_ENTITY_BEFORE_DEATH.put(player.getName(), array);
        }
        if (event instanceof EntityDamageByBlockEvent) {
            Object[] array = new Object[4];

            array[0] = ((EntityDamageByBlockEvent) event).getDamager();
            array[1] = event.getCause();
            array[2] = event.getDamage();
            array[3] = event.getFinalDamage();
            PlayerDeathListener.DAMAGE_BY_BLOCK_BEFORE_DEATH.put(player.getName(), array);
        }
    }

    @SuppressWarnings("deprecation")
    private void sendDeath(Player player) {
        player.closeInventory();
        List<ItemStack> inventory = Arrays.stream(player.getInventory().getContents())
                .filter(stack -> !PlayerUtils.isStackEmpty(stack)).collect(Collectors.toList());
        if (BetterDeathScreen.getVersion() == Version.v1_8) {
            List<ItemStack> armor = Arrays.stream(player.getInventory().getArmorContents())
                    .filter(stack -> !PlayerUtils.isStackEmpty(stack))
                    .collect(Collectors.toList());
            inventory.addAll(armor);
        }
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        if (!Config.MOVE_SPECTATOR) {
            player.setWalkSpeed(0F);
            player.setFlySpeed(0F);
        }
        PlayerDeathEvent playerDeathEvent = new PlayerDeathEvent(player, inventory, 0, "BDS Handled Death");
        if (player.getWorld().getGameRuleValue("keepInventory").equals("false")) {
            playerDeathEvent.setKeepInventory(false);
            if (player.hasPermission(Config.KEEP_XP)) {
                playerDeathEvent.setKeepLevel(true);
                playerDeathEvent.setNewExp((int) player.getExp());
                playerDeathEvent.setNewLevel(player.getLevel());
            }
            if (!player.hasPermission(Config.KEEP_XP)) {
                playerDeathEvent.setDroppedExp(Math.min(100, player.getLevel() * 7));
                playerDeathEvent.setKeepLevel(false);
                playerDeathEvent.setNewExp(0);
                playerDeathEvent.setNewLevel(0);
            }
        }
        if (player.getWorld().getGameRuleValue("keepInventory").equals("true")) {
            playerDeathEvent.setKeepInventory(true);
            playerDeathEvent.setKeepLevel(true);
            playerDeathEvent.setNewExp((int) player.getExp());
            playerDeathEvent.setNewLevel(player.getLevel());
        }
        playerDeathEvent.setNewTotalExp(player.getTotalExperience());
        Bukkit.getPluginManager().callEvent(playerDeathEvent);
        PlayerUtils.playSound(player, Config.SOUND_DEATH, true, false);
        player.setHealth(0.1);
        player.setStatistic(Statistic.TIME_SINCE_DEATH, 0);
        player.incrementStatistic(Statistic.DEATHS, 1);
        Tasks.startTimer(player);
        Animation.sendAnimation(player);
    }

    private void killedByPlayer(Player player, Player killer, int time, EntityDamageEvent event) {
        Titles.sendTitle(player, 2, time, 2, Randomizer.getRandomMessage(player, killer, Messages.KILLED_BY_PLAYER_TITLES), Randomizer.getRandomMessage(player, killer, Messages.KILLED_BY_PLAYER_SUBTITLES));
        ActionBar.sendActionBar(killer, Randomizer.getRandomMessage(player, null, Messages.ACTIONBAR_KILL));
        PlayerUtils.playSound(killer, Config.SOUND_KILL, true, false);
        PlayerUtils.incrementStatistic(killer, Statistic.DAMAGE_DEALT, (int) event.getFinalDamage());
        killer.incrementStatistic(Statistic.PLAYER_KILLS, 1);
    }

    private void sendCommands(Player player, Player killer) {
        PlayerCommandListener.DISABLE_COMMANDS_IMUNE.add(player.getName());
        for (String syntax : Config.COMMANDS_ON_DEATH) {
            String sender = StringUtils.substringBefore(syntax, ": ");
            String command = StringUtils.substringAfter(syntax, ": ");
            if (killer == null && (command.contains("@killer") || sender.equalsIgnoreCase("killer"))) continue;
            String formattedCommand = "";
            if (killer == null) formattedCommand = command.replace("@player", player.getName());
            if (killer != null)
                formattedCommand = command.replace("@player", player.getName()).replace("@killer", killer.getName());

            if (sender.equalsIgnoreCase("console")) Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCommand);
            if (sender.equalsIgnoreCase("player")) player.chat(formattedCommand);
            if (sender.equalsIgnoreCase("killer")) killer.chat(formattedCommand);
        }
        PlayerCommandListener.DISABLE_COMMANDS_IMUNE.remove(player.getName());
    }
}
