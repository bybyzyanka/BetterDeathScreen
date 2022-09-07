package com.github.victortedesco.bds.utils;

import com.github.victortedesco.bds.BetterDeathScreen;
import com.github.victortedesco.bds.listener.bukkit.PlayerDeathListener;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathMessage {

    @SuppressWarnings({"deprecation", "ConstantConditions"})
    public static BaseComponent getMessage(Player p) {
        // https://minecraft.fandom.com/wiki/Death_messages#Java_Edition

        TranslatableComponent message = new TranslatableComponent("death.attack.generic");
        message.addWith(p.getName());

        if (p.getLastDamageCause() != null && !p.getLastDamageCause().isCancelled()) {
            try {
                Entity ebd = null;
                try {
                    ebd = (Entity) PlayerDeathListener.LAST_DAMAGE_BY_ENTITY_BEFORE_DEATH.get(p.getName()).get(0);
                } catch (Exception ignored) {
                }

                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.STARVATION) {
                    message = new TranslatableComponent("death.attack.starve");
                    message.addWith(p.getName());
                    if (ebd != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.starve.player");
                            message.addWith(p.getName());
                            message.addWith(ebd.getName());
                        }
                    }
                }
                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                    message = new TranslatableComponent("death.attack.drown");
                    message.addWith(p.getName());
                    if (ebd != null) {
                        message = new TranslatableComponent("death.attack.drown.player");
                        message.addWith(p.getName());
                        message.addWith(ebd.getName());
                    }
                }
                if (BetterDeathScreen.getVersion().value >= Version.v1_9.value) {
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
                        message = new TranslatableComponent("death.attack.flyIntoWall");
                        message.addWith(p.getName());
                        if (ebd != null) {
                            if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                message = new TranslatableComponent("death.attack.flyIntoWall.player");
                                message.addWith(p.getName());
                                message.addWith(ebd.getName());
                            }
                        }
                    }
                }
                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                    if (p.getLastDamageCause().getDamage() <= 4) {
                        message = new TranslatableComponent("death.attack.fall");
                        message.addWith(p.getName());
                        if (ebd != null) {
                            if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                message = new TranslatableComponent("death.attack.fall.player");
                                message.addWith(p.getName());
                                message.addWith(ebd.getName());
                            }
                        }
                    }
                    if (p.getLastDamageCause().getDamage() > 4) {
                        message = new TranslatableComponent("death.fell.accident.generic");
                        message.addWith(p.getName());
                    }
                }
                if (BetterDeathScreen.getVersion().value >= Version.v1_10.value) {
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
                        message = new TranslatableComponent("death.attack.hotFloor");
                        message.addWith(p.getName());
                        if (ebd != null) {
                            message = new TranslatableComponent("death.attack.hotFloor.player");
                            message.addWith(p.getName());
                            message.addWith(ebd.getName());
                        }
                    }
                }
                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE) {
                    message = new TranslatableComponent("death.attack.inFire");
                    message.addWith(p.getName());
                    if (ebd != null) {
                        message = new TranslatableComponent("death.attack.inFire.player");
                        message.addWith(p.getName());
                        message.addWith(ebd.getName());
                    }
                }
                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
                    message = new TranslatableComponent("death.attack.onFire");
                    message.addWith(p.getName());
                    if (ebd != null) {
                        message = new TranslatableComponent("death.attack.onFire.player");
                        message.addWith(p.getName());
                        message.addWith(ebd.getName());
                    }
                }
                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
                    message = new TranslatableComponent("death.attack.lava");
                    message.addWith(p.getName());
                    if (ebd != null) {
                        message = new TranslatableComponent("death.attack.lava.player");
                        message.addWith(p.getName());
                        message.addWith(ebd.getName());
                    }
                }
                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                    message = new TranslatableComponent("death.attack.magic");
                    message.addWith(p.getName());
                    if (ebd != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.magic.player");
                            message.addWith(p.getName());
                            message.addWith(ebd.getName());
                        }
                    }
                }
                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
                    message = new TranslatableComponent("death.attack.lightningBolt");
                    message.addWith(p.getName());
                    if (ebd != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.lightningBolt.player");
                            message.addWith(p.getName());
                            message.addWith(ebd.getName());
                        }
                    }
                }
                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
                    message = new TranslatableComponent("death.attack.inWall");
                    message.addWith(p.getName());
                    if (ebd != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.inWall.player");
                            message.addWith(p.getName());
                            message.addWith(ebd.getName());
                        }
                    }
                }
                if (BetterDeathScreen.getVersion().value >= Version.v1_11.value) {
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CRAMMING) {
                        message = new TranslatableComponent("death.attack.cramming");
                        message.addWith(p.getName());
                        if (ebd != null) {
                            if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                message = new TranslatableComponent("death.attack.cramming.player");
                                message.addWith(p.getName());
                                message.addWith(ebd.getName());
                            }
                        }
                    }
                }
                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.WITHER) {
                    message = new TranslatableComponent("death.attack.wither");
                    message.addWith(p.getName());
                    if (ebd != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.wither.player");
                            message.addWith(p.getName());
                            message.addWith(ebd.getName());
                        }
                    }
                }
                if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID || p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
                    message = new TranslatableComponent("death.attack.outOfWorld");
                    message.addWith(p.getName());
                    if (ebd != null) {
                        if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                            message = new TranslatableComponent("death.attack.outOfWorld.player");
                            message.addWith(p.getName());
                            message.addWith(ebd.getName());
                        }
                    }
                }
                if (BetterDeathScreen.getVersion().value >= Version.v1_17.value) {
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FREEZE) {
                        message = new TranslatableComponent("death.attack.freeze");
                        message.addWith(p.getName());
                        if (ebd != null) {
                            message = new TranslatableComponent("death.attack.freeze.player");
                            message.addWith(p.getName());
                            message.addWith(ebd.getName());
                        }
                    }
                }
                if (BetterDeathScreen.getVersion().value >= Version.v1_19.value) {
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SONIC_BOOM) {
                        message = new TranslatableComponent("death.attack.sonic_boom");
                        message.addWith(p.getName());
                        if (ebd != null) {
                            message = new TranslatableComponent("death.attack.sonic_boom.player");
                            message.addWith(p.getName());
                            message.addWith(ebd.getName());
                        }
                    }
                }

                if (p.getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    Entity ent = ((EntityDamageByEntityEvent) p.getLastDamageCause()).getDamager();
                    message = new TranslatableComponent("death.attack.mob");
                    message.addWith(p.getName());
                    message.addWith(ent.getName());
                    if (ent instanceof LivingEntity) {
                        LivingEntity d = (LivingEntity) ent;
                        if (!PlayerAPI.isStackEmpty(d.getEquipment().getItemInHand())) {
                            if (d.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.mob.item");
                                    message.addWith(p.getName());
                                    message.addWith(ent.getName());
                                    message.addWith(d.getEquipment().getItemInHand().getItemMeta().getDisplayName());
                                }
                            }
                        }
                        if (BetterDeathScreen.getVersion().value >= Version.v1_15.value) {
                            if (d instanceof Bee) {
                                message = new TranslatableComponent("death.attack.sting");
                                message.addWith(p.getName());
                                if (ebd != null) {
                                    message = new TranslatableComponent("death.attack.sting.player");
                                    message.addWith(p.getName());
                                    message.addWith(ebd.getName());
                                }
                            }
                        }
                    }
                    if (ent instanceof Player) {
                        Player d = (Player) ent;
                        message = new TranslatableComponent("death.attack.player");
                        message.addWith(p.getName());
                        message.addWith(ent.getName());
                        if (!PlayerAPI.isStackEmpty(d.getEquipment().getItemInHand())) {
                            if (d.getEquipment().getItemInMainHand().getItemMeta().hasDisplayName()) {
                                message = new TranslatableComponent("death.attack.player.item");
                                message.addWith(p.getName());
                                message.addWith(ent.getName());
                                message.addWith(d.getEquipment().getItemInMainHand().getItemMeta().getDisplayName());
                            }
                        }
                    }
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                        message = new TranslatableComponent("death.attack.indirectMagic");
                        message.addWith(p.getName());
                        message.addWith(ent.getName());
                    }
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.THORNS) {
                        message = new TranslatableComponent("death.attack.thorns");
                        message.addWith(p.getName());
                        message.addWith(ent.getName());
                    }
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                        message = new TranslatableComponent("death.attack.explosion.player");
                        message.addWith(p.getName());
                        message.addWith(ent.getName());
                        if (ent instanceof TNTPrimed) {
                            TNTPrimed tnt = (TNTPrimed) ent;
                            if (tnt.getSource() == null) {
                                message = new TranslatableComponent("death.attack.explosion");
                                message.addWith(p.getName());
                            }
                            if (tnt.getSource() instanceof LivingEntity) {
                                message = new TranslatableComponent("death.attack.explosion.player");
                                message.addWith(p.getName());
                                message.addWith(tnt.getSource().getName());
                            }
                        }
                        if (ent instanceof Firework) {
                            message = new TranslatableComponent("death.attack.fireworks");
                            message.addWith(p.getName());
                            if (ebd != null) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.fireworks.player");
                                    message.addWith(p.getName());
                                    message.addWith(ebd.getName());
                                }
                            }
                        }
                        if (ent instanceof WitherSkull) {
                            WitherSkull ws = (WitherSkull) ent;
                            Entity shooter = (Entity) ws.getShooter();
                            message = new TranslatableComponent("death.attack.witherSkull");
                            message.addWith(p.getName());
                            message.addWith(shooter.getName());
                        }
                    }
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                        if (ent instanceof Projectile) {
                            Projectile pj = (Projectile) ent;
                            if (pj.getShooter() instanceof LivingEntity) {
                                LivingEntity d = (LivingEntity) pj.getShooter();
                                message = new TranslatableComponent("death.attack.arrow");
                                message.addWith(p.getName());
                                message.addWith(d.getName());
                                if (!PlayerAPI.isStackEmpty(d.getEquipment().getItemInHand())) {
                                    if (d.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                                        message = new TranslatableComponent("death.attack.arrow.item");
                                        message.addWith(p.getName());
                                        message.addWith(d.getName());
                                        message.addWith(d.getEquipment().getItemInHand().getItemMeta().getDisplayName());
                                    }
                                }
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    if (pj instanceof Trident) {
                                        Trident t = (Trident) pj;
                                        message = new TranslatableComponent("death.attack.trident");
                                        message.addWith(p.getName());
                                        message.addWith(d.getName());
                                        if (!PlayerAPI.isStackEmpty(d.getEquipment().getItemInHand())) {
                                            if (t.getItem().getItemMeta().hasDisplayName()) {
                                                message = new TranslatableComponent("death.attack.trident.item");
                                                message.addWith(p.getName());
                                                message.addWith(d.getName());
                                                message.addWith(t.getItem().getItemMeta().getDisplayName());
                                            }
                                        }
                                    }
                                }
                                if (pj instanceof Fireball) {
                                    message = new TranslatableComponent("death.attack.fireball");
                                    message.addWith(p.getName());
                                    message.addWith(d.getName());
                                }
                                if (BetterDeathScreen.getVersion().value >= Version.v1_9.value) {
                                    if (pj instanceof ShulkerBullet) {
                                        message = new TranslatableComponent("death.attack.generic.player");
                                        message.addWith(p.getName());
                                        message.addWith(d.getName());
                                    }
                                }
                                if (BetterDeathScreen.getVersion().value >= Version.v1_11.value) {
                                    if (pj instanceof LlamaSpit) {
                                        message = new TranslatableComponent("death.attack.generic.player");
                                        message.addWith(p.getName());
                                        message.addWith(d.getName());
                                    }
                                }
                            }
                        }
                    }
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                        if (ent instanceof EnderPearl) {
                            message = new TranslatableComponent("death.attack.fall");
                            message.addWith(p.getName());
                            if (ebd != null) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.fall.player");
                                    message.addWith(p.getName());
                                    message.addWith(ebd.getName());
                                }
                            }
                        }
                    }
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                        if (ent instanceof FallingBlock) {
                            FallingBlock f = (FallingBlock) ent;
                            message = new TranslatableComponent("death.attack.fallingBlock");
                            message.addWith(p.getName());
                            if (ebd != null) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.fallingBlock.player");
                                    message.addWith(p.getName());
                                    message.addWith(ebd.getName());
                                }
                            }
                            if (f.getBlockData().getMaterial() == Material.ANVIL) {
                                message = new TranslatableComponent("death.attack.anvil");
                                message.addWith(p.getName());
                                if (ebd != null) {
                                    if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                        message = new TranslatableComponent("death.attack.anvil.player");
                                        message.addWith(p.getName());
                                        message.addWith(ebd.getName());
                                    }
                                }
                            }
                            if (BetterDeathScreen.getVersion().value >= Version.v1_17.value) {
                                if (f.getBlockData().getMaterial() == Material.POINTED_DRIPSTONE) {
                                    message = new TranslatableComponent("death.attack.fallingStalactite");
                                    message.addWith(p.getName());
                                    if (ebd != null) {
                                        message = new TranslatableComponent("death.attack.fallingStalactite.player");
                                        message.addWith(p.getName());
                                        message.addWith(ebd.getName());
                                    }
                                }
                            }
                        }
                    }
                }

                if (p.getLastDamageCause() instanceof EntityDamageByBlockEvent) {
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                        if (((EntityDamageByBlockEvent) p.getLastDamageCause()).getDamager() == null) {
                            if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                message = new TranslatableComponent("death.attack.badRespawnPoint.message");
                                message.addWith(p.getName());
                                message.addWith(new TranslatableComponent("death.attack.badRespawnPoint.link"));
                            }
                        }
                    }
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.VOID || p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.SUICIDE) {
                        if (((EntityDamageByBlockEvent) p.getLastDamageCause()).getDamager() == null) {
                            message = new TranslatableComponent("death.attack.outOfWorld");
                            message.addWith(p.getName());
                            if (ebd != null) {
                                if (BetterDeathScreen.getVersion().value >= Version.v1_13.value) {
                                    message = new TranslatableComponent("death.attack.outOfWorld.player");
                                    message.addWith(p.getName());
                                    message.addWith(ebd.getName());
                                }
                            }
                        }
                    }
                    if (p.getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                        message = new TranslatableComponent("death.attack.cactus");
                        message.addWith(p.getName());
                        if (ebd != null) {
                            message = new TranslatableComponent("death.attack.cactus.player");
                            message.addWith(p.getName());
                            message.addWith(ebd.getName());
                        }
                        if (((EntityDamageByBlockEvent) p.getLastDamageCause()).getDamager().getType() != Material.CACTUS) {
                            message = new TranslatableComponent("death.attack.sweetBerryBush");
                            message.addWith(p.getName());
                            if (ebd != null) {
                                message = new TranslatableComponent("death.attack.sweetBerryBush.player");
                                message.addWith(p.getName());
                                message.addWith(ebd.getName());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                BetterDeathScreen.logger("§cPlease, contact the author §fTedesk §cabout this error!");
            }
        }
        return message;
    }
}
