package com.labi.utils;

import com.labi.main.Modemate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class ParticlesUtil {

    public static void applyParticleEffect(Location location, Particle particle, int amount, int radius, int speed) {
        World world = location.getWorld();
        if (world == null) return;

        world.spawnParticle(particle, location, amount, radius, radius, radius, speed);
    }

    public static void createParticleTrailEffect(Location location, Particle particle, int amount, int radius, int speed, int ticks) {
        World world = location.getWorld();
        if (world == null) return;

        Bukkit.getScheduler().runTaskTimer(Modemate.getInstance(), () -> {
            applyParticleEffect(location, particle, amount, radius, speed);
        }, 0L, ticks);
    }
}
