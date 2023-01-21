package com.github.victortedesco.betterdeathscreen.api.manager;

import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import com.github.victortedesco.betterdeathscreen.api.utils.Version;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerManager {

    private final Set<Player> DEAD_PLAYERS = new HashSet<>();

    public Set<Player> getDeadPlayers() {
        return this.DEAD_PLAYERS;
    }

    public boolean isDead(Player player) {
        return getDeadPlayers().contains(player);
    }

    public void playSound(Player player, List<String> list, boolean throwable, boolean silent) {
        String string = BetterDeathScreenAPI.getRandomizer().getRandomItemFromList(list);
        try {
            String[] array = string.split(";");
            String sound = array[0];
            float volume = Float.parseFloat(array[1]);
            float pitch = Float.parseFloat(array[2]);
            if (silent) volume = 0;
            if (sound.contains(".")) {
                player.playSound(player.getLocation(), sound, volume, pitch);
                return;
            }
            Sound bukkitSound = XSound.matchXSound(sound).orElse(null).parseSound();
            player.playSound(player.getLocation(), bukkitSound, volume, pitch);
        } catch (Exception exception) {
            if (throwable) exception.printStackTrace();
        }
    }

    public void playSound(Player player, String string, boolean throwable, boolean silent) {
        try {
            String[] array = string.split(";");
            String sound = array[0];
            float volume = Float.parseFloat(array[1]);
            float pitch = Float.parseFloat(array[2]);
            if (silent) volume = 0;
            if (sound.contains(".")) {
                player.playSound(player.getLocation(), sound, volume, pitch);
                return;
            }
            Sound bukkitSound = XSound.matchXSound(sound).orElse(null).parseSound();
            player.playSound(player.getLocation(), bukkitSound, volume, pitch);
        } catch (Exception exception) {
            if (throwable) exception.printStackTrace();
        }
    }

    public void sendCustomMessage(Player player, Player placeholderTarget, String type, String message, int timeSeconds) {
        message = ChatColor.translateAlternateColorCodes('&', message);
        MessageType messageType;
        try {
            messageType = MessageType.valueOf(type);
        } catch (IllegalArgumentException exception) {
            messageType = MessageType.CHAT;
            exception.printStackTrace();
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null && placeholderTarget != null) {
            message = PlaceholderAPI.setPlaceholders(placeholderTarget, message);
        }
        if (messageType == MessageType.ACTIONBAR) ActionBar.sendActionBar(player, message);
        if (messageType == MessageType.TITLE) {
            String[] array = message.split("\n");
            String title;
            String subtitle;
            if (array.length == 1) {
                title = "";
                subtitle = array[0];
            } else {
                title = array[0];
                subtitle = array[1];
            }
            if (Bukkit.isHardcore()) timeSeconds = 86400;
            Titles.sendTitle(player, 5, 20 * timeSeconds, 5, title, subtitle);
        }
        if (messageType == MessageType.CHAT) player.sendMessage(message);

    }

    public void teleportSafeLocation(Player player, Location location, PlayerTeleportEvent.TeleportCause teleportCause) {
        if (location.getWorld() == null) location = Bukkit.getWorlds().get(0).getSpawnLocation();
        int y = location.getWorld().getMaxHeight() - 1;

        for (int i = y; i > 0; i--) {
            Location newLocation = new Location(location.getWorld(), location.getX(), i, location.getZ());
            if (newLocation.getBlock().getType().isSolid() && newLocation.add(0, 1, 0).getBlock().getType() == Material.AIR) {
                player.teleport(newLocation.add(0, 1.2, 0), teleportCause);
                break;
            }
        }
    }

    /**
     * Get the max health of the player, returns the GENERIC_MAX_HEALTH attribute on versions newer than 1.8
     */
    public double getMaxHealth(Player player) {
        if (Version.getServerVersion().getValue() > Version.v1_8.getValue())
            return player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        return player.getMaxHealth();
    }

    /**
     * Get the inventory of the player without null stacks
     */
    public List<ItemStack> getFilteredInventory(Player player) {
        List<ItemStack> inventory = Arrays.stream(player.getInventory().getContents())
                .filter(stack -> !isStackEmpty(stack)).collect(Collectors.toList());
        if (Version.getServerVersion() == Version.v1_8) {
            List<ItemStack> armor = Arrays.stream(player.getInventory().getArmorContents())
                    .filter(stack -> !isStackEmpty(stack))
                    .collect(Collectors.toList());
            inventory.addAll(armor);
        }
        return inventory;
    }

    public boolean isStackEmpty(ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR || itemStack.getAmount() == 0;
    }

    /**
     * Get if the player is using a Totem of Undying in the main hand or in the offhand
     */
    public boolean usingTotem(Player player) {
        if (Version.getServerVersion().getValue() >= Version.v1_11.getValue()) {
            Material mainHand = player.getInventory().getItemInMainHand().getType();
            Material offHand = player.getInventory().getItemInOffHand().getType();

            return mainHand == XMaterial.TOTEM_OF_UNDYING.parseMaterial() || offHand == XMaterial.TOTEM_OF_UNDYING.parseMaterial();
        }
        return false;
    }

    public enum MessageType {
        ACTIONBAR,
        TITLE,
        CHAT,
    }
}
