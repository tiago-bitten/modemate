package com.labi.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;

public class ParticlesUtil {

    public static void applyParticleEffect(Location location, Particle particle, int amount, int radius) {
        World world = location.getWorld();
        if (world == null) return;

        world.spawnParticle(particle, location, amount, radius, radius, radius);
    }
}
