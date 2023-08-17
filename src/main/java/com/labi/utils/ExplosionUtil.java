package com.labi.utils;

import com.labi.main.Modemate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;

import static com.labi.utils.ParticlesUtil.applyParticleEffect;

public class ExplosionUtil {

    public static void explodeInstantly(@NonNull Location location, float range, float damage) {
        createExplosion(location, range);
        setDamageNearbyEntities(location, range, damage);
    }

    public static void explodeAfter(@NonNull Location location, float range, float damage, long ticks) {
        Bukkit.getScheduler().runTaskLater(Modemate.getInstance(), () -> {
            createExplosion(location, range);
            setDamageNearbyEntities(location, range, damage);
        }, ticks);
    }

    private static void createExplosion(Location location, float range) {
        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, range, range, range);

        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setInvulnerable(true);
            }
        });

        location.getWorld().createExplosion(location, range, true, true);
        applyParticleEffect(location, Particle.FLASH, 50, 0, 0, 0, 0.5f);

        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setInvulnerable(false);
            }
        });
    }

    private static void setDamageNearbyEntities(Location location, float range, float damage) {
        Collection<Entity> entities = location.getWorld().getNearbyEntities(location, range, range, range);

        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.damage(damage);
                livingEntity.setFireTicks(60);
            }
        });
    }
}
