package com.labi.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;

public class ExplosionUtil {

    public void explode(@NonNull Location location, float range, float damage) {
        createExplosion(location, range);
        setDamageNearbyEntities(location, range, damage);
    }

    private void createExplosion(Location location, float range) {
        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, range, range, range);

        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setInvulnerable(true);
            }
        });

        location.getWorld().createExplosion(location, range, true, true);
        applyParticleEffect(location, Particle.FLASH, 50, 5);

        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setInvulnerable(false);
            }
        });
    }

    private void setDamageNearbyEntities(Location location, float range, float damage) {
        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, range, range, range);

        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.damage(damage);
                livingEntity.setFireTicks(60);
            }
        });
    }

    private void applyParticleEffect(Location location, Particle particle, int amount, int radius) {
        World world = location.getWorld();
        if (world == null) return;

        world.spawnParticle(particle, location, amount, radius, radius, radius);
    }
}
