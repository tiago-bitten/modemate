package com.labi.listeners.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class SnowballThrowUtils {

    private static final float MIN_EXPLOSION = 0.6f;
    private static final float MAX_EXPLOSION = 1.2f;
    private static final String SNOW_GRENADE = ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Snow Grenade";

    public static void explodeSnowball(Projectile projectile, Player player) {
        final float explosion = randomExplosion();
        final boolean isBigExplosion = explosion > 0.9f;

        projectile.getWorld().createExplosion(projectile.getLocation(), explosion, isBigExplosion, true, player);
        projectile.remove();
    }

    private static Float randomExplosion() {
        return (float) (Math.random() * (MAX_EXPLOSION - MIN_EXPLOSION) + MIN_EXPLOSION);
    }

    public static String getSnowGrenadeName() {
        return SNOW_GRENADE;
    }
}
