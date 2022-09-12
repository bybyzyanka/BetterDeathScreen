package com.github.victortedesco.bds.utils;

import com.github.victortedesco.bds.BetterDeathScreen;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathMessage {

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    public static BaseComponent getMessage(Player player, Entity entity) {
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
                    Entity ent = ((EntityDamageByEntityEvent) player.getLastDamageCause()).getDamager();
                    message = new TranslatableComponent("death.attack.mob");
                    message.addWith(player.getName());
                    message.addWith(ent.getName());
                    if (ent instanceof LivingEntity) {
                        LivingEntity d = (LivingEntity) ent;
                        if (!PlayerAPI.isStackEmpty(d.getEquipment().getItemInHand())) {
                            if (d.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.mob.item");
                                    message.addWith(player.getName());
                                    message.addWith(ent.getName());
                                    message.addWith(d.getEquipment().getItemInHand().getItemMeta().getDisplayName());
                                }
                            }
                        }
                        if (BetterDeathScreen.getVersion().value >= Version.v1_15.value) {
                            if (d instanceof Bee) {
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
                    if (ent instanceof Player) {
                        Player d = (Player) ent;
                        message = new TranslatableComponent("death.attack.player");
                        message.addWith(player.getName());
                        message.addWith(ent.getName());
                        if (!PlayerAPI.isStackEmpty(d.getEquipment().getItemInHand())) {
                            if (d.getEquipment().getItemInMainHand().getItemMeta().hasDisplayName()) {
                                message = new TranslatableComponent("death.attack.player.item");
                                message.addWith(player.getName());
                                message.addWith(ent.getName());
                                message.addWith(d.getEquipment().getItemInMainHand().getItemMeta().getDisplayName());
                            }
                        }
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                        message = new TranslatableComponent("death.attack.indirectMagic");
                        message.addWith(player.getName());
                        message.addWith(ent.getName());
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.THORNS) {
                        message = new TranslatableComponent("death.attack.thorns");
                        message.addWith(player.getName());
                        message.addWith(ent.getName());
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                        message = new TranslatableComponent("death.attack.explosion.player");
                        message.addWith(player.getName());
                        message.addWith(ent.getName());
                        if (ent instanceof TNTPrimed) {
                            TNTPrimed tnt = (TNTPrimed) ent;
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
                        if (ent instanceof Firework) {
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
                        if (ent instanceof WitherSkull) {
                            WitherSkull ws = (WitherSkull) ent;
                            Entity shooter = (Entity) ws.getShooter();
                            message = new TranslatableComponent("death.attack.witherSkull");
                            message.addWith(player.getName());
                            message.addWith(shooter.getName());
                        }
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                        if (ent instanceof Projectile) {
                            Projectile pj = (Projectile) ent;
                            if (pj.getShooter() instanceof LivingEntity) {
                                LivingEntity d = (LivingEntity) pj.getShooter();
                                message = new TranslatableComponent("death.attack.arrow");
                                message.addWith(player.getName());
                                message.addWith(d.getName());
                                if (!PlayerAPI.isStackEmpty(d.getEquipment().getItemInHand())) {
                                    if (d.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                                        message = new TranslatableComponent("death.attack.arrow.item");
                                        message.addWith(player.getName());
                                        message.addWith(d.getName());
                                        message.addWith(d.getEquipment().getItemInHand().getItemMeta().getDisplayName());
                                    }
                                }
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    if (pj instanceof Trident) {
                                        Trident t = (Trident) pj;
                                        message = new TranslatableComponent("death.attack.trident");
                                        message.addWith(player.getName());
                                        message.addWith(d.getName());
                                        if (!PlayerAPI.isStackEmpty(d.getEquipment().getItemInHand())) {
                                            if (t.getItem().getItemMeta().hasDisplayName()) {
                                                message = new TranslatableComponent("death.attack.trident.item");
                                                message.addWith(player.getName());
                                                message.addWith(d.getName());
                                                message.addWith(t.getItem().getItemMeta().getDisplayName());
                                            }
                                        }
                                    }
                                }
                                if (pj instanceof Fireball) {
                                    message = new TranslatableComponent("death.attack.fireball");
                                    message.addWith(player.getName());
                                    message.addWith(d.getName());
                                }
                                if (BetterDeathScreen.getVersion().value >= Version.v1_9.value) {
                                    if (pj instanceof ShulkerBullet) {
                                        message = new TranslatableComponent("death.attack.generic.player");
                                        message.addWith(player.getName());
                                        message.addWith(d.getName());
                                    }
                                }
                                if (BetterDeathScreen.getVersion().value >= Version.v1_11.value) {
                                    if (pj instanceof LlamaSpit) {
                                        message = new TranslatableComponent("death.attack.generic.player");
                                        message.addWith(player.getName());
                                        message.addWith(d.getName());
                                    }
                                }
                            }
                        }
                    }
                    if (player.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                        if (ent instanceof EnderPearl) {
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
                        if (ent instanceof FallingBlock) {
                            FallingBlock f = (FallingBlock) ent;
                            message = new TranslatableComponent("death.attack.fallingBlock");
                            message.addWith(player.getName());
                            if (entity != null) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.fallingBlock.player");
                                    message.addWith(player.getName());
                                    message.addWith(entity.getName());
                                }
                            }
                            if (f.getBlockData().getMaterial() == Material.ANVIL) {
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
                                if (f.getBlockData().getMaterial() == Material.POINTED_DRIPSTONE) {
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
