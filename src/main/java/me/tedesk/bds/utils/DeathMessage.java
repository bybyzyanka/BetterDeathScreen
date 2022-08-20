package me.tedesk.bds.utils;

import me.tedesk.bds.BetterDeathScreen;
import me.tedesk.bds.api.PlayerAPI;
import me.tedesk.bds.api.Version;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathMessage {

    @SuppressWarnings("all")
    public static String sendMessage(Player p, EntityDamageEvent e) {
        // https://minecraft.fandom.com/wiki/Death_messages#Java_Edition
        String message = "§f%p died".replace("%p", p.getName());

        if (e.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            message = "§f%p starved to death".replace("%p", p.getName());
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            message = "§f%p drowned".replace("%p", p.getName());
        }
        if (BetterDeathScreen.version.value >= Version.v1_9.value) {
            if (e.getCause() == EntityDamageEvent.DamageCause.FLY_INTO_WALL) {
                message = "§f%p experienced kinetic energy".replace("%p", p.getName());
            }
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (e.getDamage() <= 4) {
                message = "§f%p hit the ground to hard".replace("%p", p.getName());
            }
            if (e.getDamage() > 4) {
                message = "§f%p fell from a high place".replace("%p", p.getName());
            }
        }
        if (BetterDeathScreen.version.value >= Version.v1_10.value) {
            if (e.getCause() == EntityDamageEvent.DamageCause.HOT_FLOOR) {
                message = "§f%p discovered the floor was lava".replace("%p", p.getName());
            }
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.FIRE) {
            message = "§f%p went up in flames".replace("%p", p.getName());
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            message = "§f%p burned to death".replace("%p", p.getName());
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            message = "§f%p tried to swim in lava".replace("%p", p.getName());
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
            message = "§f%p was killed by magic".replace("%p", p.getName());
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
            message = "§f%p was struck by lightning".replace("%p", p.getName());
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.SUFFOCATION) {
            message = "§f%p suffocated in a wall".replace("%p", p.getName());
        }
        if (BetterDeathScreen.version.value >= Version.v1_11.value) {
            if (e.getCause() == EntityDamageEvent.DamageCause.CRAMMING) {
                message = "§f%p was squished too much".replace("%p", p.getName());
            }
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.WITHER) {
            message = "§f%p withered away".replace("%p", p.getName());
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            message = "§f%p fell out of the world".replace("%p", p.getName());
        }
        if (BetterDeathScreen.version.value >= Version.v1_19.value) {
            if (e.getCause() == EntityDamageEvent.DamageCause.SONIC_BOOM) {
                message = "§f%p was obliterated by a sonically-charged shriek".replace("%p", p.getName());
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.FREEZE) {
                message = "§f%p froze to death".replace("%p", p.getName());
            }
        }

        if (e instanceof EntityDamageByEntityEvent) {
            Entity ent = ((EntityDamageByEntityEvent) e).getDamager();
            message = "§f%p was slain by %d".replace("%p", p.getName()).replace("%d", ent.getName());
            if (ent instanceof LivingEntity) {
                LivingEntity d = (LivingEntity) ent;
                if (!PlayerAPI.isStackEmpty(d.getEquipment().getItemInHand())) {
                    if (d.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                        message = "%p was slain by %d using %i".replace("%p", p.getName()).replace("%d", d.getName()).replace("%i", d.getEquipment().getItemInHand().getItemMeta().getDisplayName());
                    }
                }
                if (BetterDeathScreen.version.value >= Version.v1_15.value) {
                    if (d instanceof Bee) {
                        message = "§f%p was stung to death".replace("%p", p.getName());
                    }
                }
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.MAGIC) {
                message = "§f%p was killed by %d using magic".replace("%p", p.getName()).replace("%d", ent.getName());
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.THORNS) {
                message = "§f%p was killed trying to hurt %d".replace("%p", p.getName()).replace("%d", ent.getName());
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
                message = "§f%p was blown up by %d".replace("%p", p.getName()).replace("%d", ent.getName());
                if (ent instanceof TNTPrimed) {
                    TNTPrimed tnt = (TNTPrimed) ent;
                    if (tnt.getSource() == null) {
                        message = "§f%p blew up".replace("%p", p.getName());
                    }
                    if (tnt.getSource() instanceof LivingEntity) {
                        LivingEntity d = (LivingEntity) tnt.getSource();
                        message = "§f%p was blown up by %d".replace("%p", p.getName()).replace("%d", tnt.getSource().getName());
                    }
                }
                if (ent instanceof Firework) {
                    message = "§f%p went off with a bang".replace("%p", p.getName());
                }
                if (ent instanceof WitherSkull) {
                    WitherSkull ws = (WitherSkull) ent;
                    Entity wither = (Entity) ws.getShooter();
                    message = "§f%p was shot by a skull from %d".replace("%p", p.getName()).replace("%d", wither.getName());
                }
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
                if (ent instanceof Projectile) {
                    Projectile pj = (Projectile) ent;
                    message = "%p was shot by %d".replace("%p", p.getName()).replace("%d", pj.getName());
                    if (pj.getShooter() instanceof LivingEntity) {
                        LivingEntity d = (LivingEntity) pj.getShooter();
                        message = "%p was shot by %d".replace("%p", p.getName()).replace("%d", d.getName());
                        if (!PlayerAPI.isStackEmpty(d.getEquipment().getItemInHand())) {
                            if (d.getEquipment().getItemInHand().getItemMeta().hasDisplayName()) {
                                message = "%p was shot by %d using %i".replace("%p", p.getName()).replace("%d", d.getName()).replace("%i", d.getEquipment().getItemInHand().getItemMeta().getDisplayName());
                            }
                        }
                        if (BetterDeathScreen.version.value >= Version.v1_13.value) {
                            if (pj instanceof Trident) {
                                Trident t = (Trident) pj;
                                message = "%p was impaled by %d".replace("%p", p.getName()).replace("%d", d.getName());
                                if (t.getItem().getItemMeta().hasDisplayName()) {
                                    message = "%p was impaled by %d with %i".replace("%p", p.getName()).replace("%d", d.getName()).replace("%i", t.getItem().getItemMeta().getDisplayName());
                                }
                            }
                        }
                        if (pj instanceof Fireball) {
                            message = "%p was fireballed by %d".replace("%p", p.getName()).replace("%d", d.getName());
                        }
                        if (BetterDeathScreen.version.value >= Version.v1_9.value) {
                            if (pj instanceof ShulkerBullet) {
                                message = "%p was slain by %d".replace("%p", p.getName()).replace("%d", d.getName());
                            }
                        }
                        if (BetterDeathScreen.version.value >= Version.v1_11.value) {
                            if (pj instanceof LlamaSpit) {
                                message = "%p was slain by %d".replace("%p", p.getName()).replace("%d", d.getName());
                            }
                        }
                    }
                }
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                if (ent instanceof EnderPearl) {
                    message = "§f%p hit the ground to hard".replace("%p", p.getName());
                }
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.FALLING_BLOCK) {
                if (ent instanceof FallingBlock) {
                    FallingBlock f = (FallingBlock) ent;
                    message = "§f%p was squashed by a falling block".replace("%p", p.getName());
                    if (f.getBlockData().getMaterial() == Material.ANVIL) {
                        message = "§f%p was squashed by a falling anvil".replace("%p", p.getName());
                    }
                    if (BetterDeathScreen.version.value >= Version.v1_17.value) {
                        if (f.getBlockData().getMaterial() == Material.POINTED_DRIPSTONE) {
                            message = "§f%p was skewered by a falling stalactite".replace("%p", p.getName());
                        }
                    }
                }
            }
        }

        if (e instanceof EntityDamageByBlockEvent) {
            if (e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) {
                if (((EntityDamageByBlockEvent) e).getDamager() == null) {
                    message = "§f%p was killed by [Intentional Game Design]".replace("%p", p.getName());
                }
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                if (((EntityDamageByBlockEvent) e).getDamager() == null) {
                    message = "§f%p fell out of the world".replace("%p", p.getName());
                }
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                message = "§f%p was pricked to death".replace("%p", p.getName());
                if (((EntityDamageByBlockEvent) e).getDamager().getType() != Material.CACTUS) {
                    message = "§f%p was poked to death by a sweet berry bush".replace("%p", p.getName());
                }
            }
        }

        return message;
    }
}
