package com.labi.listeners.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;

public class SnowballThrowUtils {

    private static final float MIN_EXPLOSION = 0.6f;
    private static final float MAX_EXPLOSION = 1.2f;
    private static final String SNOW_GRENADE = ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Snow Grenade";

    public static void explodeSnowball(Projectile projectile, Player player) {
        projectile.getWorld().createExplosion(projectile.getLocation(), randomExplosion(), true, true, player);
        projectile.remove();
    }

    private static Float randomExplosion() {
        return (float) (Math.random() * (MAX_EXPLOSION - MIN_EXPLOSION) + MIN_EXPLOSION);
    }

    public static String getSnowGrenadeName() {
        return SNOW_GRENADE;
    }
}
