package com.labi.listeners.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class LandMineUtils {

    private static final float EXPLOSION_RANGE = 3f;
    private static final int RADIUS = 7;

    public static void explodeLandMine(Block block, Player reference) {
        block.getWorld().createExplosion(block.getLocation(), EXPLOSION_RANGE, true, true, reference);
        reference.damage(10);
    }

    public static boolean checkRadius(Location blockLocation, Set<Location> placedBlocksLocations) {
        World world = blockLocation.getWorld();

        for (Location location : placedBlocksLocations) {
            if (world.equals(location.getWorld()) && location.distance(blockLocation) <= RADIUS) {
                return false;
            }
        }
        return true;
    }
}