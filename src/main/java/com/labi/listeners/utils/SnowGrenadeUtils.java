package com.labi.listeners.utils;

import com.labi.listeners.utils.enums.SnowGrenadeState;
import com.labi.main.Modemate;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public class SnowGrenadeUtils {

    /* event -> Snowball Hit */
    private static final float BIG_EXPLOSION = 0.8f;
    private static final float MIN_EXPLOSION = 0.6f;
    private static final float MAX_EXPLOSION = 1.2f;

    /* event -> Snowball Throw */
    private static final double VECTOR_MULTIPLY_X = 0.4;
    private static final double VECTOR_MULTIPLY_Y = 0.4;
    private static final double VECTOR_MULTIPLY_Z = 0.4;

    private SnowGrenadeState snowGrenadeState = SnowGrenadeState.IS_NOT_SNOW_GRENADE;

    /* **************** */
    /* END OF VARIABLES */
    /* **************** */

    /* event -> Snowball Hit */

    public void explodeSnowGrenade(Projectile projectile, Player reference) {
        final float explosion = randomExplosion();
        final boolean isBigExplosion = explosion > BIG_EXPLOSION;

        if (!projectile.isValid()) return;

        projectile.getWorld().createExplosion(projectile.getLocation(), explosion, isBigExplosion, true, reference);
        projectile.remove();
    }

    private Float randomExplosion() {
        return (float) (Math.random() * (MAX_EXPLOSION - MIN_EXPLOSION) + MIN_EXPLOSION);
    }
    /* event -> Snowball Throw */

    public void updateVelocity(Projectile projectile) {
        Vector currentVelocity = projectile.getVelocity();
        Vector adjustedVelocity = new Vector(
                currentVelocity.getX() * VECTOR_MULTIPLY_X,
                currentVelocity.getY() * VECTOR_MULTIPLY_Y,
                currentVelocity.getZ() * VECTOR_MULTIPLY_Z
        );
        projectile.setVelocity(adjustedVelocity);
    }

    public void createParticleTrail(Projectile projectile, Particle particle, int amount, int ticks) {
        Bukkit.getScheduler().runTaskTimer(Modemate.getInstance(), () -> {
            if (!projectile.isValid()) return;
            applyParticles(projectile, particle, amount);
        }, 1, ticks);
    }

    private void applyParticles(Projectile projectile, Particle particle, int amount) {
        projectile.getWorld().spawnParticle(particle, projectile.getLocation(), amount, 0, 0, 0, 0);
    }

    public boolean isSnowGrenade() {
        return snowGrenadeState == SnowGrenadeState.IS_SNOW_GRENADE;
    }

    public void setSnowGrenadeState(boolean state) {
        if (state) {
            snowGrenadeState = SnowGrenadeState.IS_SNOW_GRENADE;
            return;
        }
        snowGrenadeState = SnowGrenadeState.IS_NOT_SNOW_GRENADE;
    }
}
