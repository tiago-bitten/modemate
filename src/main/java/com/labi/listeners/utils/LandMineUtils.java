package com.labi.listeners.utils;

import com.labi.items.LandMine;
import com.labi.main.Modemate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LandMineUtils {

    private static final Modemate MODEMATE = Modemate.getInstance();
    private final Set<Location> placedBlocksLocations = new HashSet<>();
    public static final float EXPLOSION_RANGE = 4.0f;
    public static final float DAMAGE = 6.0f;
    private static final int RADIUS = 20;

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


    public void applyMetaData(Block block) {
        block.setMetadata(String.valueOf(LandMine.getItemUUID()), new FixedMetadataValue(MODEMATE, true));
    }

    public void addBlockLocation(Block block) {
        placedBlocksLocations.add(block.getLocation());
    }

    public void removeBlockLocation(Block block) {
        placedBlocksLocations.remove(block.getLocation());
    }
}