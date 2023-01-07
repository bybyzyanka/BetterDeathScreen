package com.github.victortedesco.betterdeathscreen.api.utils;

import com.github.victortedesco.betterdeathscreen.api.BetterDeathScreenAPI;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DeathMessageCreator {

    public TranslatableComponent getMessage(Player player, Entity assist) {
        // https://minecraft.fandom.com/wiki/Death_messages#Java_Edition

        TranslatableComponent message = new TranslatableComponent("death.attack.generic");
        Entity damager = null;
        ItemStack item = null;

        if (player.getLastDamageCause() != null && !player.getLastDamageCause().isCancelled()) {
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.STARVATION) {
                message = new TranslatableComponent("death.attack.starve");
                if (assist != null && Version.isNewVersion() | Version.isVeryNewVersion())
                    message = new TranslatableComponent("death.attack.starve.player");
            }
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                message = new TranslatableComponent("death.attack.drown");
                if (assist != null) message = new TranslatableComponent("death.attack.drown.player");
            }
            if (Version.getServerVersion() != Version.v1_8) {
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
                    message = new TranslatableComponent("death.attack.flyIntoWall");
                    if (assist != null && Version.isNewVersion() | Version.isVeryNewVersion())
                        message = new TranslatableComponent("death.attack.flyIntoWall.player");
                }
            }
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (player.getLastDamageCause().getDamage() <= 4) {
                    message = new TranslatableComponent("death.attack.fall");
                    if (assist != null && Version.isNewVersion() | Version.isVeryNewVersion())
                        message = new TranslatableComponent("death.attack.fall.player");
                }
                if (player.getLastDamageCause().getDamage() > 4) {
                    message = new TranslatableComponent("death.fell.accident.generic");
                }
            }
            if (Version.getServerVersion().getValue() >= Version.v1_10.getValue()) {
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
                    message = new TranslatableComponent("death.attack.hotFloor");
                    if (assist != null) message = new TranslatableComponent("death.attack.hotFloor.player");
                }
            }
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) {
                message = new TranslatableComponent("death.attack.inFire");
                if (assist != null) message = new TranslatableComponent("death.attack.inFire.player");
            }
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                message = new TranslatableComponent("death.attack.onFire");
                if (assist != null) message = new TranslatableComponent("death.attack.onFire.player");
            }
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
                message = new TranslatableComponent("death.attack.lava");
                if (assist != null) message = new TranslatableComponent("death.attack.lava.player");
            }
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                message = new TranslatableComponent("death.attack.magic");
                if (assist != null && Version.isNewVersion() | Version.isVeryNewVersion())
                    message = new TranslatableComponent("death.attack.magic.player");
            }
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
                message = new TranslatableComponent("death.attack.lightningBolt");
                if (assist != null && Version.isNewVersion() | Version.isVeryNewVersion())
                    message = new TranslatableComponent("death.attack.lightningBolt.player");
            }
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                message = new TranslatableComponent("death.attack.inWall");
                if (assist != null && Version.isNewVersion() | Version.isVeryNewVersion())
                    message = new TranslatableComponent("death.attack.inWall.player");
            }
            if (Version.getServerVersion().getValue() >= Version.v1_11.getValue()) {
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CRAMMING) {
                    message = new TranslatableComponent("death.attack.cramming");
                    if (assist != null && Version.isNewVersion() | Version.isVeryNewVersion())
                        message = new TranslatableComponent("death.attack.cramming.player");
                }
            }
            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.WITHER) {
                message = new TranslatableComponent("death.attack.wither");
                if (assist != null && Version.isNewVersion() | Version.isVeryNewVersion())
                    message = new TranslatableComponent("death.attack.wither.player");
            }

            if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID || player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
                message = new TranslatableComponent("death.attack.outOfWorld");
                if (assist != null && Version.isNewVersion() | Version.isVeryNewVersion())
                    message = new TranslatableComponent("death.attack.outOfWorld.player");
            }
            if (Version.getServerVersion().getValue() >= Version.v1_17.getValue()) {
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FREEZE) {
                    message = new TranslatableComponent("death.attack.freeze");
                    if (assist != null) message = new TranslatableComponent("death.attack.freeze.player");
                }
            }
            if (Version.getServerVersion().getValue() >= Version.v1_19.getValue()) {
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SONIC_BOOM) {
                    message = new TranslatableComponent("death.attack.sonic_boom");
                    if (assist != null) message = new TranslatableComponent("death.attack.sonic_boom.player");
                }
            }
            if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                damager = ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager();
                message = new TranslatableComponent("death.attack.mob");
                if (damager instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) damager;
                    if (!BetterDeathScreenAPI.getPlayerManager().isStackEmpty(livingEntity.getEquipment().getItemInHand())) {
                        if (livingEntity.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                            if (Version.isNewVersion() | Version.isVeryNewVersion())
                                message = new TranslatableComponent("death.attack.mob.item");
                        }
                    }
                    if (Version.getServerVersion().getValue() >= Version.v1_15.getValue()) {
                        if (livingEntity instanceof Bee) {
                            message = new TranslatableComponent("death.attack.sting");
                            if (assist != null) message = new TranslatableComponent("death.attack.sting.player");
                        }
                    }
                }
                if (damager instanceof Player) {
                    message = new TranslatableComponent("death.attack.player");
                    if (!BetterDeathScreenAPI.getPlayerManager().isStackEmpty(((Player) damager).getInventory().getItemInMainHand())) {
                        if (((Player) damager).getInventory().getItemInHand().getItemMeta().hasDisplayName())
                            message = new TranslatableComponent("death.attack.player.item");
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                    message = new TranslatableComponent("death.attack.indirectMagic");
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.THORNS) {
                    message = new TranslatableComponent("death.attack.thorns");
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                    message = new TranslatableComponent("death.attack.explosion.player");
                    if (damager instanceof TNTPrimed) {
                        if (((TNTPrimed) damager).getSource() == null)
                            message = new TranslatableComponent("death.attack.explosion");
                        if (((TNTPrimed) damager).getSource() instanceof LivingEntity)
                            message = new TranslatableComponent("death.attack.explosion.player");
                    }
                    if (damager instanceof Firework) {
                        message = new TranslatableComponent("death.attack.fireworks");
                        if (assist != null) {
                            if (Version.isNewVersion() | Version.isVeryNewVersion())
                                message = new TranslatableComponent("death.attack.fireworks.player");
                        }
                    }
                    if (damager instanceof WitherSkull) {
                        message = new TranslatableComponent("death.attack.witherSkull");
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                    if (damager instanceof Projectile) {
                        Projectile projectile = (Projectile) damager;
                        if (projectile.getShooter() instanceof LivingEntity) {
                            message = new TranslatableComponent("death.attack.arrow");
                            if (!BetterDeathScreenAPI.getPlayerManager().isStackEmpty(((LivingEntity) projectile.getShooter()).getEquipment().getItemInHand())) {
                                if (((LivingEntity) projectile.getShooter()).getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                                    message = new TranslatableComponent("death.attack.arrow.item");
                                    item = ((LivingEntity) projectile.getShooter()).getEquipment().getItemInHand();
                                }
                            }
                            if ((Version.isNewVersion() | Version.isVeryNewVersion())) {
                                if (projectile instanceof Trident) {
                                    Trident trident = (Trident) projectile;
                                    message = new TranslatableComponent("death.attack.trident");
                                    if (trident.getItem().getItemMeta().hasDisplayName()) {
                                        message = new TranslatableComponent("death.attack.trident.item");
                                        item = trident.getItem();
                                    }
                                }
                            }
                            if (projectile instanceof Fireball) {
                                message = new TranslatableComponent("death.attack.fireball");
                            }
                            if (Version.getServerVersion() != Version.v1_8) {
                                if (projectile instanceof ShulkerBullet)
                                    message = new TranslatableComponent("death.attack.generic.player");
                            }
                            if (Version.getServerVersion().getValue() >= Version.v1_11.getValue()) {
                                if (projectile instanceof LlamaSpit)
                                    message = new TranslatableComponent("death.attack.generic.player");
                            }
                        }
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (damager instanceof EnderPearl) {
                        message = new TranslatableComponent("death.attack.fall");
                        if (assist != null) {
                            if (Version.isNewVersion() | Version.isVeryNewVersion())
                                message = new TranslatableComponent("death.attack.fall.player");
                        }
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                    if (damager instanceof FallingBlock) {
                        FallingBlock fallingBlock = (FallingBlock) damager;
                        message = new TranslatableComponent("death.attack.fallingBlock");
                        if (assist != null) {
                            if (Version.isNewVersion() | Version.isVeryNewVersion())
                                message = new TranslatableComponent("death.attack.fallingBlock.player");
                        }
                        if (fallingBlock.getBlockData().getMaterial() == Material.ANVIL) {
                            message = new TranslatableComponent("death.attack.anvil");
                            if (assist != null) {
                                if (Version.isNewVersion() | Version.isVeryNewVersion())
                                    message = new TranslatableComponent("death.attack.anvil.player");
                            }
                        }
                        if (Version.getServerVersion().getValue() >= Version.v1_17.getValue()) {
                            if (fallingBlock.getBlockData().getMaterial() == Material.POINTED_DRIPSTONE) {
                                message = new TranslatableComponent("death.attack.fallingStalactite");
                                if (assist != null)
                                    message = new TranslatableComponent("death.attack.fallingStalactite.player");
                            }
                        }
                    }
                }
            }
            if (player.getLastDamageCause() instanceof EntityDamageByBlockEvent) {
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                    if (((EntityDamageByBlockEvent) player.getLastDamageCause()).getDamager() == null) {
                        if (Version.isNewVersion() | Version.isVeryNewVersion())
                            message = new TranslatableComponent("death.attack.badRespawnPoint.message");
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID || player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
                    if (((EntityDamageByBlockEvent) player.getLastDamageCause()).getDamager() == null) {
                        message = new TranslatableComponent("death.attack.outOfWorld");
                        if (assist != null) {
                            if (Version.isNewVersion() | Version.isVeryNewVersion())
                                message = new TranslatableComponent("death.attack.outOfWorld.player");
                        }
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                    message = new TranslatableComponent("death.attack.cactus");
                    if (assist != null) message = new TranslatableComponent("death.attack.cactus.player");
                    if (((EntityDamageByBlockEvent) player.getLastDamageCause()).getDamager().getType() != Material.CACTUS) {
                        message = new TranslatableComponent("death.attack.sweetBerryBush");
                        if (assist != null) message = new TranslatableComponent("death.attack.sweetBerryBush.player");
                    }
                }
            }
        }
        if (player.getLastDamageCause() == null) {
            message = new TranslatableComponent("death.attack.outOfWorld");
            if (assist != null) {
                if (Version.isNewVersion() | Version.isVeryNewVersion())
                    message = new TranslatableComponent("death.attack.outOfWorld.player");
            }
        }
        message.addWith(player.getName());
        if (assist != null && !(player.getLastDamageCause() instanceof EntityDamageByEntityEvent))
            message.addWith(assist.getName());
        if (damager != null) {
            if (damager instanceof Projectile) {
                if (((Projectile) damager).getShooter() instanceof LivingEntity) {
                    damager = (Entity) ((Projectile) damager).getShooter();
                }
            }
            message.addWith(damager.getName());
        }
        if (item != null) {
            message.addWith(item.getItemMeta().getDisplayName());
        }
        return message;
    }
}
