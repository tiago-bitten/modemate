package com.labi.listeners.utils;

import com.labi.items.SnowGrenade;
import com.labi.main.Modemate;
import org.bukkit.entity.Projectile;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;

public class SnowGrenadeUtils {

    private static final Modemate MODEMATE = Modemate.getInstance();

    /* event -> Snowball Hit */
    private static final float BIG_EXPLOSION = 0.8f;
    private static final float MIN_EXPLOSION = 0.6f;
    private static final float MAX_EXPLOSION = 1.2f;
    public static final float DAMAGE = 4.0f;

    private float explosion;

    /* event -> Snowball Throw */
    private static final double VECTOR_MULTIPLY_X = 0.4;
    private static final double VECTOR_MULTIPLY_Y = 0.4;
    private static final double VECTOR_MULTIPLY_Z = 0.4;

    // private SnowGrenadeState state = SnowGrenadeState.NOT_ACTIVATED;

    /* **************** */
    /* END OF VARIABLES */
    /* **************** */

    /* event -> Snowball Hit */

    public boolean setFire() {
        return explosion > BIG_EXPLOSION;
    }

    public float randomExplosion() {
        explosion = (float) (Math.random() * (MAX_EXPLOSION - MIN_EXPLOSION) + MIN_EXPLOSION);
        return explosion;
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
