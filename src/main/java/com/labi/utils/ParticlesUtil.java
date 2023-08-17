package com.labi.utils;

import com.labi.main.Modemate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Projectile;

public class ParticlesUtil {

    public static void applyParticleEffect(Location location, Particle particle, int amount, double offsetX, double offsetY, double offsetZ, float extra) {
        World world = location.getWorld();
        if (world == null) return;

        world.spawnParticle(particle, location, amount, offsetX, offsetY, offsetZ, extra);
    }

    public static void createParticleTrailEffect(Projectile projectile, Particle particle, int amount, double offsetX, double offsetY, double offsetZ, float extra, long ticks) {
        Bukkit.getScheduler().runTaskTimer(Modemate.getInstance(), () -> {
            if (!projectile.isValid()) return;

            Location location = projectile.getLocation();
            applyParticleEffect(location, particle, amount, offsetX, offsetY, offsetZ, extra);
        }, 1L, ticks);
    }
}
