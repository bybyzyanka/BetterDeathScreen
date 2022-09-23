package com.github.victortedesco.bds.utils;

import com.github.victortedesco.bds.BetterDeathScreen;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathMessage {

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    public static TranslatableComponent getMessage(Player player, Entity entity) {
        // https://minecraft.fandom.com/wiki/Death_messages#Java_Edition

        TranslatableComponent message = new TranslatableComponent("death.attack.generic");
        message.addWith(player.getName());

        if (player.getLastDamageCause() != null && !player.getLastDamageCause().isCancelled()) {
            try {
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.STARVATION) {
                    message = new TranslatableComponent("death.attack.starve");
                    message.addWith(player.getName());
                    if (entity != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.starve.player");
                            message.addWith(player.getName());
                            message.addWith(entity.getName());
                        }
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                    message = new TranslatableComponent("death.attack.drown");
                    message.addWith(player.getName());
                    if (entity != null) {
                        message = new TranslatableComponent("death.attack.drown.player");
                        message.addWith(player.getName());
                        message.addWith(entity.getName());
                    }
                }
                if (BetterDeathScreen.getVersion().value >= Version.v1_9.value) {
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
                        message = new TranslatableComponent("death.attack.flyIntoWall");
                        message.addWith(player.getName());
                        if (entity != null) {
                            if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                message = new TranslatableComponent("death.attack.flyIntoWall.player");
                                message.addWith(player.getName());
                                message.addWith(entity.getName());
                            }
                        }
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (player.getLastDamageCause().getDamage() <= 4) {
                        message = new TranslatableComponent("death.attack.fall");
                        message.addWith(player.getName());
                        if (entity != null) {
                            if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                message = new TranslatableComponent("death.attack.fall.player");
                                message.addWith(player.getName());
                                message.addWith(entity.getName());
                            }
                        }
                    }
                    if (player.getLastDamageCause().getDamage() > 4) {
                        message = new TranslatableComponent("death.fell.accident.generic");
                        message.addWith(player.getName());
                    }
                }
                if (BetterDeathScreen.getVersion().value >= Version.v1_10.value) {
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
                        message = new TranslatableComponent("death.attack.hotFloor");
                        message.addWith(player.getName());
                        if (entity != null) {
                            message = new TranslatableComponent("death.attack.hotFloor.player");
                            message.addWith(player.getName());
                            message.addWith(entity.getName());
                        }
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) {
                    message = new TranslatableComponent("death.attack.inFire");
                    message.addWith(player.getName());
                    if (entity != null) {
                        message = new TranslatableComponent("death.attack.inFire.player");
                        message.addWith(player.getName());
                        message.addWith(entity.getName());
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                    message = new TranslatableComponent("death.attack.onFire");
                    message.addWith(player.getName());
                    if (entity != null) {
                        message = new TranslatableComponent("death.attack.onFire.player");
                        message.addWith(player.getName());
                        message.addWith(entity.getName());
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
                    message = new TranslatableComponent("death.attack.lava");
                    message.addWith(player.getName());
                    if (entity != null) {
                        message = new TranslatableComponent("death.attack.lava.player");
                        message.addWith(player.getName());
                        message.addWith(entity.getName());
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                    message = new TranslatableComponent("death.attack.magic");
                    message.addWith(player.getName());
                    if (entity != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.magic.player");
                            message.addWith(player.getName());
                            message.addWith(entity.getName());
                        }
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
                    message = new TranslatableComponent("death.attack.lightningBolt");
                    message.addWith(player.getName());
                    if (entity != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.lightningBolt.player");
                            message.addWith(player.getName());
                            message.addWith(entity.getName());
                        }
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                    message = new TranslatableComponent("death.attack.inWall");
                    message.addWith(player.getName());
                    if (entity != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.inWall.player");
                            message.addWith(player.getName());
                            message.addWith(entity.getName());
                        }
                    }
                }
                if (BetterDeathScreen.getVersion().value >= Version.v1_11.value) {
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CRAMMING) {
                        message = new TranslatableComponent("death.attack.cramming");
                        message.addWith(player.getName());
                        if (entity != null) {
                            if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                message = new TranslatableComponent("death.attack.cramming.player");
                                message.addWith(player.getName());
                                message.addWith(entity.getName());
                            }
                        }
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.WITHER) {
                    message = new TranslatableComponent("death.attack.wither");
                    message.addWith(player.getName());
                    if (entity != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.wither.player");
                            message.addWith(player.getName());
                            message.addWith(entity.getName());
                        }
                    }
                }
                if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID || player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
                    message = new TranslatableComponent("death.attack.outOfWorld");
                    message.addWith(player.getName());
                    if (entity != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.outOfWorld.player");
                            message.addWith(player.getName());
                            message.addWith(entity.getName());
                        }
                    }
                }
                if (BetterDeathScreen.getVersion().value >= Version.v1_17.value) {
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FREEZE) {
                        message = new TranslatableComponent("death.attack.freeze");
                        message.addWith(player.getName());
                        if (entity != null) {
                            message = new TranslatableComponent("death.attack.freeze.player");
                            message.addWith(player.getName());
                            message.addWith(entity.getName());
                        }
                    }
                }
                if (BetterDeathScreen.getVersion().value >= Version.v1_19.value) {
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SONIC_BOOM) {
                        message = new TranslatableComponent("death.attack.sonic_boom");
                        message.addWith(player.getName());
                        if (entity != null) {
                            message = new TranslatableComponent("death.attack.sonic_boom.player");
                            message.addWith(player.getName());
                            message.addWith(entity.getName());
                        }
                    }
                }

                if (player.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager();
                    message = new TranslatableComponent("death.attack.mob");
                    message.addWith(player.getName());
                    message.addWith(damager.getName());
                    if (damager instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) damager;
                        if (!PlayerUtils.isStackEmpty(livingEntity.getEquipment().getItemInHand())) {
                            if (livingEntity.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.mob.item");
                                    message.addWith(player.getName());
                                    message.addWith(damager.getName());
                                    message.addWith(livingEntity.getEquipment().getItemInHand().getItemMeta().getDisplayName());
                                }
                            }
                        }
                        if (BetterDeathScreen.getVersion().value >= Version.v1_15.value) {
                            if (livingEntity instanceof Bee) {
                                message = new TranslatableComponent("death.attack.sting");
                                message.addWith(player.getName());
                                if (entity != null) {
                                    message = new TranslatableComponent("death.attack.sting.player");
                                    message.addWith(player.getName());
                                    message.addWith(entity.getName());
                                }
                            }
                        }
                    }
                    if (damager instanceof Player) {
                        Player playerDamager = (Player) damager;
                        message = new TranslatableComponent("death.attack.player");
                        message.addWith(player.getName());
                        message.addWith(damager.getName());
                        if (!PlayerUtils.isStackEmpty(playerDamager.getEquipment().getItemInHand())) {
                            if (playerDamager.getEquipment().getItemInMainHand().getItemMeta().hasDisplayName()) {
                                message = new TranslatableComponent("death.attack.player.item");
                                message.addWith(player.getName());
                                message.addWith(damager.getName());
                                message.addWith(playerDamager.getEquipment().getItemInMainHand().getItemMeta().getDisplayName());
                            }
                        }
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                        message = new TranslatableComponent("death.attack.indirectMagic");
                        message.addWith(player.getName());
                        message.addWith(damager.getName());
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.THORNS) {
                        message = new TranslatableComponent("death.attack.thorns");
                        message.addWith(player.getName());
                        message.addWith(damager.getName());
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                        message = new TranslatableComponent("death.attack.explosion.player");
                        message.addWith(player.getName());
                        message.addWith(damager.getName());
                        if (damager instanceof TNTPrimed) {
                            TNTPrimed tnt = (TNTPrimed) damager;
                            if (tnt.getSource() == null) {
                                message = new TranslatableComponent("death.attack.explosion");
                                message.addWith(player.getName());
                            }
                            if (tnt.getSource() instanceof LivingEntity) {
                                message = new TranslatableComponent("death.attack.explosion.player");
                                message.addWith(player.getName());
                                message.addWith(tnt.getSource().getName());
                            }
                        }
                        if (damager instanceof Firework) {
                            message = new TranslatableComponent("death.attack.fireworks");
                            message.addWith(player.getName());
                            if (entity != null) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.fireworks.player");
                                    message.addWith(player.getName());
                                    message.addWith(entity.getName());
                                }
                            }
                        }
                        if (damager instanceof WitherSkull) {
                            WitherSkull ws = (WitherSkull) damager;
                            Entity shooter = (Entity) ws.getShooter();
                            message = new TranslatableComponent("death.attack.witherSkull");
                            message.addWith(player.getName());
                            message.addWith(shooter.getName());
                        }
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                        if (damager instanceof Projectile) {
                            Projectile projectile = (Projectile) damager;
                            if (projectile.getShooter() instanceof LivingEntity) {
                                LivingEntity shooter = (LivingEntity) projectile.getShooter();
                                message = new TranslatableComponent("death.attack.arrow");
                                message.addWith(player.getName());
                                message.addWith(shooter.getName());
                                if (!PlayerUtils.isStackEmpty(shooter.getEquipment().getItemInHand())) {
                                    if (shooter.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                                        message = new TranslatableComponent("death.attack.arrow.item");
                                        message.addWith(player.getName());
                                        message.addWith(shooter.getName());
                                        message.addWith(shooter.getEquipment().getItemInHand().getItemMeta().getDisplayName());
                                    }
                                }
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    if (projectile instanceof Trident) {
                                        Trident trident = (Trident) projectile;
                                        message = new TranslatableComponent("death.attack.trident");
                                        message.addWith(player.getName());
                                        message.addWith(shooter.getName());
                                        if (!PlayerUtils.isStackEmpty(shooter.getEquipment().getItemInHand())) {
                                            if (trident.getItem().getItemMeta().hasDisplayName()) {
                                                message = new TranslatableComponent("death.attack.trident.item");
                                                message.addWith(player.getName());
                                                message.addWith(shooter.getName());
                                                message.addWith(trident.getItem().getItemMeta().getDisplayName());
                                            }
                                        }
                                    }
                                }
                                if (projectile instanceof Fireball) {
                                    message = new TranslatableComponent("death.attack.fireball");
                                    message.addWith(player.getName());
                                    message.addWith(shooter.getName());
                                }
                                if (BetterDeathScreen.getVersion().value >= Version.v1_9.value) {
                                    if (projectile instanceof ShulkerBullet) {
                                        message = new TranslatableComponent("death.attack.generic.player");
                                        message.addWith(player.getName());
                                        message.addWith(shooter.getName());
                                    }
                                }
                                if (BetterDeathScreen.getVersion().value >= Version.v1_11.value) {
                                    if (projectile instanceof LlamaSpit) {
                                        message = new TranslatableComponent("death.attack.generic.player");
                                        message.addWith(player.getName());
                                        message.addWith(shooter.getName());
                                    }
                                }
                            }
                        }
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                        if (damager instanceof EnderPearl) {
                            message = new TranslatableComponent("death.attack.fall");
                            message.addWith(player.getName());
                            if (entity != null) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.fall.player");
                                    message.addWith(player.getName());
                                    message.addWith(entity.getName());
                                }
                            }
                        }
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                        if (damager instanceof FallingBlock) {
                            FallingBlock fallingBlock = (FallingBlock) damager;
                            message = new TranslatableComponent("death.attack.fallingBlock");
                            message.addWith(player.getName());
                            if (entity != null) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.fallingBlock.player");
                                    message.addWith(player.getName());
                                    message.addWith(entity.getName());
                                }
                            }
                            if (fallingBlock.getBlockData().getMaterial() == Material.ANVIL) {
                                message = new TranslatableComponent("death.attack.anvil");
                                message.addWith(player.getName());
                                if (entity != null) {
                                    if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                        message = new TranslatableComponent("death.attack.anvil.player");
                                        message.addWith(player.getName());
                                        message.addWith(entity.getName());
                                    }
                                }
                            }
                            if (BetterDeathScreen.getVersion().value >= Version.v1_17.value) {
                                if (fallingBlock.getBlockData().getMaterial() == Material.POINTED_DRIPSTONE) {
                                    message = new TranslatableComponent("death.attack.fallingStalactite");
                                    message.addWith(player.getName());
                                    if (entity != null) {
                                        message = new TranslatableComponent("death.attack.fallingStalactite.player");
                                        message.addWith(player.getName());
                                        message.addWith(entity.getName());
                                    }
                                }
                            }
                        }
                    }
                }

                if (player.getLastDamageCause() instanceof EntityDamageByBlockEvent) {
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                        if (((EntityDamageByBlockEvent) player.getLastDamageCause()).getDamager() == null) {
                            if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                message = new TranslatableComponent("death.attack.badRespawnPoint.message");
                                message.addWith(player.getName());
                                message.addWith(new TranslatableComponent("death.attack.badRespawnPoint.link"));
                            }
                        }
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID || player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
                        if (((EntityDamageByBlockEvent) player.getLastDamageCause()).getDamager() == null) {
                            message = new TranslatableComponent("death.attack.outOfWorld");
                            message.addWith(player.getName());
                            if (entity != null) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.outOfWorld.player");
                                    message.addWith(player.getName());
                                    message.addWith(entity.getName());
                                }
                            }
                        }
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                        message = new TranslatableComponent("death.attack.cactus");
                        message.addWith(player.getName());
                        if (entity != null) {
                            message = new TranslatableComponent("death.attack.cactus.player");
                            message.addWith(player.getName());
                            message.addWith(entity.getName());
                        }
                        if (((EntityDamageByBlockEvent) player.getLastDamageCause()).getDamager().getType() != Material.CACTUS) {
                            message = new TranslatableComponent("death.attack.sweetBerryBush");
                            message.addWith(player.getName());
                            if (entity != null) {
                                message = new TranslatableComponent("death.attack.sweetBerryBush.player");
                                message.addWith(player.getName());
                                message.addWith(entity.getName());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                BetterDeathScreen.sendConsoleMessage("§cPlease, contact the author §fTedesk §cabout this error!");
            }
        }
        return message;
    }
}
