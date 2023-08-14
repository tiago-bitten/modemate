package com.labi.listeners.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LandMineUtils {

    private static final float EXPLOSION_RANGE = 3f;

    public static void explodeLandMine(Block block, Player reference) {
        block.getWorld().createExplosion(block.getLocation(), EXPLOSION_RANGE, true, true, reference);
        reference.damage(10);
    }
}
