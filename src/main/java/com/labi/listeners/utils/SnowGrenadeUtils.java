package com.labi.listeners.utils;

import com.labi.items.SnowGrenade;
import com.labi.listeners.utils.enums.SnowGrenadeState;
import com.labi.main.Modemate;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class SnowGrenadeUtils {

    private static final Modemate MODEMATE = Modemate.getInstance();

    /* event -> Snowball Hit */
    private static final float BIG_EXPLOSION = 0.8f;
    private static final float MIN_EXPLOSION = 0.6f;
    private static final float MAX_EXPLOSION = 1.2f;

    /* event -> Snowball Throw */
    private static final double VECTOR_MULTIPLY_X = 0.4;
    private static final double VECTOR_MULTIPLY_Y = 0.4;
    private static final double VECTOR_MULTIPLY_Z = 0.4;

    // private SnowGrenadeState state = SnowGrenadeState.NOT_ACTIVATED;

    /* **************** */
    /* END OF VARIABLES */
    /* **************** */

    /* event -> Snowball Hit */

    public void explodeSnowGrenade(Projectile projectile, Player reference) {
        final float explosion = randomExplosion();
        final boolean isBigExplosion = explosion > BIG_EXPLOSION;

        if (!projectile.isValid()) return;

        projectile.getWorld().createExplosion(projectile.getLocation(), explosion, isBigExplosion, true, reference);
        setDamageNearbyEntities(projectile, explosion + 0.5f);
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

    private void setDamageNearbyEntities(Projectile projectile, float explosion) {
        projectile.getWorld().getNearbyEntities(projectile.getLocation(), explosion, explosion, explosion).forEach(entity -> {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.damage(8.5);
                livingEntity.setFireTicks(60);
            }
        });
    }

    public void applyMetaData(Projectile projectile) {
        projectile.setMetadata(String.valueOf(SnowGrenade.getItemUUID()), new FixedMetadataValue(MODEMATE, true));
    }

/*    public boolean getState() {
        return state == SnowGrenadeState.ACTIVATED;
    }

    public void setState(boolean flag) {
        state = flag ? SnowGrenadeState.ACTIVATED : SnowGrenadeState.NOT_ACTIVATED;
    }*/
}
