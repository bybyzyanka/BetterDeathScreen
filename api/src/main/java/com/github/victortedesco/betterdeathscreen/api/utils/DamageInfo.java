package com.github.victortedesco.betterdeathscreen.api.utils;

import org.bukkit.event.entity.EntityDamageEvent;

public class DamageInfo<T> {

    final T damager;
    final EntityDamageEvent.DamageCause damageCause;
    final double damage;
    final double finalDamage;

    public DamageInfo(T damager, EntityDamageEvent.DamageCause damageCause, double damage, double finalDamage) {
        this.damager = damager;
        this.damageCause = damageCause;
        this.damage = damage;
        this.finalDamage = finalDamage;
    }

    public T getDamager() {
        return damager;
    }

    public EntityDamageEvent.DamageCause getDamageCause() {
        return damageCause;
    }

    public double getDamage() {
        return damage;
    }

    public double getFinalDamage() {
        return finalDamage;
    }
}
