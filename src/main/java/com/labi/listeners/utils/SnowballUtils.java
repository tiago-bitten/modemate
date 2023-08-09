package com.labi.listeners.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

public class SnowballUtils {

    private static final String SNOW_GRENADE = ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Snow Grenade";

    /* event -> Snowball Hit */
    private static final float BIG_EXPLOSION = 0.8f;
    private static final float MIN_EXPLOSION = 0.6f;
    private static final float MAX_EXPLOSION = 1.2f;

    /* event -> Snowball Throw */
    private static final double VECTOR_MULTIPLY_X = 0.4;
    private static final double VECTOR_MULTIPLY_Y = 0.7;
    private static final double VECTOR_MULTIPLY_Z = 0.4;

    /* **************** */
    /* END OF VARIABLES */
    /* **************** */

    /* event -> Snowball Hit */
    public static void explodeSnowball(Projectile projectile, Player player) {
        final float explosion = randomExplosion();
        final boolean isBigExplosion = explosion > BIG_EXPLOSION;

        projectile.getWorld().createExplosion(projectile.getLocation(), explosion, isBigExplosion, true, player);
        projectile.remove();
    }

    private static Float randomExplosion() {
        return (float) (Math.random() * (MAX_EXPLOSION - MIN_EXPLOSION) + MIN_EXPLOSION);
    }

    /* event -> Snowball Throw */
    public static void setVelocity(Projectile projectile) {
        Vector currentVelocity = projectile.getVelocity();
        Vector adjustedVelocity = new Vector(
                currentVelocity.getX() * VECTOR_MULTIPLY_X,
                currentVelocity.getY() * VECTOR_MULTIPLY_Y,
                currentVelocity.getZ() * VECTOR_MULTIPLY_Z
        );
        projectile.setVelocity(adjustedVelocity);
    }

    public static String getSnowGrenadeName() {
        return SNOW_GRENADE;
    }
}
