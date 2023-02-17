package com.github.victortedesco.betterdeathscreen.api.utils;

import com.cryptomorin.xseries.ReflectionUtils;
import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class DeathMessageCreator {

    public TranslatableComponent getMessage(Player player, Entity assist) {
        // https://minecraft.fandom.com/wiki/Death_messages#Java_Edition

        TranslatableComponent message = new TranslatableComponent("death.attack.generic");
        Entity damager = null;
        ItemStack item = null;

        if (player.getLastDamageCause() != null && !player.getLastDamageCause().isCancelled()) {
            switch (player.getLastDamageCause().getCause().name()) {
                case "STARVATION":
                    message = new TranslatableComponent("death.attack.starve");
                    if (assist != null && ReflectionUtils.VER > 12)
                        message = new TranslatableComponent("death.attack.starve.player");
                    break;
                case "DROWNING":
                    message = new TranslatableComponent("death.attack.drown");
                    if (assist != null) message = new TranslatableComponent("death.attack.drown.player");
                    break;
                case "FLY_INTO_WALL":
                    message = new TranslatableComponent("death.attack.flyIntoWall");
                    if (assist != null && ReflectionUtils.VER > 12)
                        message = new TranslatableComponent("death.attack.flyIntoWall.player");
                    break;
                case "FALL":
                    if (player.getLastDamageCause().getDamage() <= 4) {
                        message = new TranslatableComponent("death.attack.fall");
                        if (assist != null && ReflectionUtils.VER > 12)
                            message = new TranslatableComponent("death.attack.fall.player");
                    }
                    if (player.getLastDamageCause().getDamage() > 4) {
                        message = new TranslatableComponent("death.fell.accident.generic");
                    }
                    break;
                case "HOT_FLOOR":
                    message = new TranslatableComponent("death.attack.hotFloor");
                    if (assist != null) message = new TranslatableComponent("death.attack.hotFloor.player");
                    break;
                case "FIRE":
                    message = new TranslatableComponent("death.attack.inFire");
                    if (assist != null) message = new TranslatableComponent("death.attack.inFire.player");
                    break;
                case "FIRE_TICK":
                    message = new TranslatableComponent("death.attack.onFire");
                    if (assist != null) message = new TranslatableComponent("death.attack.onFire.player");
                    break;
                case "LAVA":
                    message = new TranslatableComponent("death.attack.lava");
                    if (assist != null) message = new TranslatableComponent("death.attack.lava.player");
                    break;
                case "MAGIC":
                    message = new TranslatableComponent("death.attack.magic");
                    if (assist != null && ReflectionUtils.VER > 12)
                        message = new TranslatableComponent("death.attack.magic.player");
                    if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent)
                        message = new TranslatableComponent("death.attack.indirectMagic");
                    break;
                case "LIGHTNING":
                    message = new TranslatableComponent("death.attack.lightningBolt");
                    if (assist != null && ReflectionUtils.VER > 12)
                        message = new TranslatableComponent("death.attack.lightningBolt.player");
                    break;
                case "SUFFOCATION":
                    message = new TranslatableComponent("death.attack.inWall");
                    if (assist != null && ReflectionUtils.VER > 12)
                        message = new TranslatableComponent("death.attack.inWall.player");
                    break;
                case "CRAMMING":
                    message = new TranslatableComponent("death.attack.cramming");
                    if (assist != null && ReflectionUtils.VER > 12)
                        message = new TranslatableComponent("death.attack.cramming.player");
                    break;
                case "WITHER":
                    message = new TranslatableComponent("death.attack.wither");
                    if (assist != null && ReflectionUtils.VER > 12)
                        message = new TranslatableComponent("death.attack.wither.player");
                    break;
                case "VOID":
                case "SUICIDE":
                    message = new TranslatableComponent("death.attack.outOfWorld");
                    if (assist != null && ReflectionUtils.VER > 12)
                        message = new TranslatableComponent("death.attack.outOfWorld.player");
                    break;
                case "FREEZE":
                    message = new TranslatableComponent("death.attack.freeze");
                    if (assist != null) message = new TranslatableComponent("death.attack.freeze.player");
                    break;
                case "SONIC_BOOM":
                    message = new TranslatableComponent("death.attack.sonic_boom");
                    if (assist != null) message = new TranslatableComponent("death.attack.sonic_boom.player");
                    break;
                default:
                    message = new TranslatableComponent("death.attack.generic");
                    if (assist != null) message = new TranslatableComponent("death.attack.player");
                    break;
            }
            if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                damager = ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager();
                message = new TranslatableComponent("death.attack.mob");
                if (damager instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) damager;
                    if (!BetterDeathScreenAPI.getPlayerManager().isStackEmpty(livingEntity.getEquipment().getItemInHand())) {
                        if (livingEntity.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                            if (ReflectionUtils.VER > 12) {
                                item = livingEntity.getEquipment().getItemInHand();
                                message = new TranslatableComponent("death.attack.mob.item");
                            }
                        }
                    }
                    if (ReflectionUtils.VER >= 15) {
                        if (livingEntity instanceof Bee) {
                            message = new TranslatableComponent("death.attack.sting");
                            if (assist != null) message = new TranslatableComponent("death.attack.sting.player");
                        }
                    }
                }
                if (damager instanceof Player) {
                    Player playerDamager = (Player) damager;
                    message = new TranslatableComponent("death.attack.player");
                    if (!BetterDeathScreenAPI.getPlayerManager().isStackEmpty(playerDamager.getInventory().getItemInMainHand())) {
                        if (playerDamager.getInventory().getItemInHand().getItemMeta().hasDisplayName()) {
                            item = playerDamager.getInventory().getItemInHand();
                            message = new TranslatableComponent("death.attack.player.item");
                        }
                    }
                }
                switch (player.getLastDamageCause().getCause().name()) {
                    case "THORNS":
                        message = new TranslatableComponent("death.attack.thorns");
                        break;
                    case "ENTITY_EXPLOSION":
                        message = new TranslatableComponent("death.attack.explosion.player");
                        if (damager instanceof TNTPrimed) {
                            if (((TNTPrimed) damager).getSource() == null)
                                message = new TranslatableComponent("death.attack.explosion");
                            if (((TNTPrimed) damager).getSource() instanceof LivingEntity)
                                message = new TranslatableComponent("death.attack.explosion.player");
                        }
                        if (damager instanceof Firework) {
                            message = new TranslatableComponent("death.attack.fireworks");
                            if (assist != null && ReflectionUtils.VER > 12)
                                message = new TranslatableComponent("death.attack.fireworks.player");
                        }
                        if (damager instanceof WitherSkull) {
                            message = new TranslatableComponent("death.attack.witherSkull");
                        }
                        break;
                    case "PROJECTILE":
                        if (damager instanceof Projectile) {
                            Projectile projectile = (Projectile) damager;
                            message = new TranslatableComponent("death.attack.generic.player");
                            if (projectile.getShooter() instanceof LivingEntity) {
                                LivingEntity livingEntity = (LivingEntity) damager;
                                message = new TranslatableComponent("death.attack.arrow");
                                if (!BetterDeathScreenAPI.getPlayerManager().isStackEmpty(livingEntity.getEquipment().getItemInHand())) {
                                    if (livingEntity.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                                        item = livingEntity.getEquipment().getItemInHand();
                                        message = new TranslatableComponent("death.attack.arrow.item");
                                    }
                                }
                                if (ReflectionUtils.VER > 12) {
                                    if (projectile instanceof Trident) {
                                        Trident trident = (Trident) projectile;
                                        message = new TranslatableComponent("death.attack.trident");
                                        if (trident.getItem().getItemMeta().hasDisplayName()) {
                                            item = trident.getItem();
                                            message = new TranslatableComponent("death.attack.trident.item");
                                        }
                                    }
                                }
                                if (projectile instanceof Fireball) {
                                    message = new TranslatableComponent("death.attack.fireball");
                                }
                            }
                        }
                        break;
                    case "FALL":
                        if (damager instanceof EnderPearl) {
                            message = new TranslatableComponent("death.attack.fall");
                            if (assist != null && ReflectionUtils.VER > 12)
                                message = new TranslatableComponent("death.attack.fall.player");
                        }
                        break;
                    case "FALLING_BLOCK":
                        if (damager instanceof FallingBlock) {
                            FallingBlock fallingBlock = (FallingBlock) damager;
                            message = new TranslatableComponent("death.attack.fallingBlock");
                            if (assist != null && ReflectionUtils.VER > 12)
                                message = new TranslatableComponent("death.attack.fallingBlock.player");
                            if (fallingBlock.getBlockData().getMaterial() == Material.ANVIL) {
                                message = new TranslatableComponent("death.attack.anvil");
                                if (assist != null && ReflectionUtils.VER > 12)
                                    message = new TranslatableComponent("death.attack.anvil.player");
                            }
                            if (ReflectionUtils.VER >= 17) {
                                if (fallingBlock.getBlockData().getMaterial() == Material.POINTED_DRIPSTONE) {
                                    message = new TranslatableComponent("death.attack.fallingStalactite");
                                    if (assist != null)
                                        message = new TranslatableComponent("death.attack.fallingStalactite.player");
                                }
                            }
                        }
                        break;
                    default:
                        message = new TranslatableComponent("death.attack.generic");
                        if (assist != null) message = new TranslatableComponent("death.attack.player");
                        break;
                }
            }
            if (player.getLastDamageCause() instanceof EntityDamageByBlockEvent) {
                switch (player.getLastDamageCause().getCause().name()) {
                    case "BLOCK_EXPLOSION":
                        if (((EntityDamageByBlockEvent) player.getLastDamageCause()).getDamager() == null && ReflectionUtils.VER > 12) {
                            message = new TranslatableComponent("death.attack.badRespawnPoint.message");
                        }
                        break;
                    case "VOID":
                    case "SUICIDE":
                        if (((EntityDamageByBlockEvent) player.getLastDamageCause()).getDamager() == null) {
                            message = new TranslatableComponent("death.attack.outOfWorld");
                            if (assist != null && ReflectionUtils.VER > 12)
                                message = new TranslatableComponent("death.attack.outOfWorld.player");
                        }
                        break;
                    case "CONTACT":
                        message = new TranslatableComponent("death.attack.cactus");
                        if (assist != null) message = new TranslatableComponent("death.attack.cactus.player");
                        if (((EntityDamageByBlockEvent) player.getLastDamageCause()).getDamager().getType() != Material.CACTUS) {
                            message = new TranslatableComponent("death.attack.sweetBerryBush");
                            if (assist != null)
                                message = new TranslatableComponent("death.attack.sweetBerryBush.player");
                        }
                        break;
                    default:
                        message = new TranslatableComponent("death.attack.generic");
                        if (assist != null) message = new TranslatableComponent("death.attack.player");
                        break;
                }
            }
        }
        if (player.getLastDamageCause() == null) {
            message = new TranslatableComponent("death.attack.outOfWorld");
            if (assist != null && ReflectionUtils.VER > 12)
                message = new TranslatableComponent("death.attack.outOfWorld.player");
        }
        message.addWith(player.getName());
        if (assist != null && !(player.getLastDamageCause() instanceof EntityDamageByEntityEvent))
            message.addWith(assist.getName());
        if (damager != null) {
            if (damager instanceof Projectile) {
                Projectile projectile = (Projectile) damager;
                if (projectile.getShooter() instanceof LivingEntity) damager = (Entity) projectile.getShooter();
            }
            message.addWith(damager.getName());
        }
        if (item != null) message.addWith(item.getItemMeta().getDisplayName());

        return message;
    }

    public void sendDeathMessage(Player player, Entity assist) {
        TranslatableComponent deathMessage = BetterDeathScreenAPI.getDeathMessageCreator().getMessage(player, assist);
        if (ReflectionUtils.VER < 12) Bukkit.getConsoleSender().sendMessage(player.getName() + " died");
        else Bukkit.getConsoleSender().spigot().sendMessage(deathMessage);
        Bukkit.getOnlinePlayers().forEach(players -> players.spigot().sendMessage(deathMessage));
    }
}
