package com.labi.listeners.utils;

public class SnowballThrowUtils {

    private static final float MIN_EXPLOSION = 1.5f;
    private static final float MAX_EXPLOSION = 3.5f;

    public static Float randomExplosion() {
        return (float) (Math.random() * (MAX_EXPLOSION - MIN_EXPLOSION) + MIN_EXPLOSION);
    }
}
