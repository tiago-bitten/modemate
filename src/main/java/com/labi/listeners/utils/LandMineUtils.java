package com.labi.listeners.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class LandMineUtils {

    private final Set<Location> placedBlocksLocations = new HashSet<>();
    private static final float EXPLOSION_RANGE = 3f;
    private static final int RADIUS = 20;

    public void explodeLandMine(Block block, Player reference) {
        block.getWorld().createExplosion(block.getLocation(), EXPLOSION_RANGE, true, true, reference);
        block.setType(Material.AIR);
        reference.damage(8.5);
    }

    public boolean checkRadius(Block block) {
        Location blockLocation = block.getLocation();
        World world = blockLocation.getWorld();

        for (Location location : placedBlocksLocations) {
            if (world.equals(location.getWorld()) && location.distance(blockLocation) <= RADIUS) {
                return false;
            }
        }
        return true;
    }

    public void addBlockLocation(Block block) {
        placedBlocksLocations.add(block.getLocation());
    }

    public void removeBlockLocation(Block block) {
        placedBlocksLocations.remove(block.getLocation());
    }
}