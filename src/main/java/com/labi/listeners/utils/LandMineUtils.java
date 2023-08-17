package com.labi.listeners.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LandMineUtils {

    private final Set<Location> placedBlocksLocations = new HashSet<>();
    private static final float EXPLOSION_RANGE = 3f;
    private static final int RADIUS = 20;

    public void explodeLandMine(Block block, LivingEntity livingEntity) {
        setDamageNearbyEntities(block, EXPLOSION_RANGE + 0.5f);
        createExplosion(block, EXPLOSION_RANGE);
        block.setType(Material.AIR);
    }

    private void createExplosion(Block block, float range) {
        List<Entity> entities = block.getWorld().getEntities();
        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setInvulnerable(true);
            }
        });

        block.getWorld().createExplosion(block.getLocation(), range, true, true);

        entities.forEach(entity -> {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setInvulnerable(false);
            }
        });
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

    private void setDamageNearbyEntities(Block block, float explosion) {
        block.getWorld().getNearbyEntities(block.getLocation(), explosion, explosion, explosion).forEach(entity -> {
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.damage(5.0);
                livingEntity.setFireTicks(60);
            }
        });
    }

    public void addBlockLocation(Block block) {
        placedBlocksLocations.add(block.getLocation());
    }

    public void removeBlockLocation(Block block) {
        placedBlocksLocations.remove(block.getLocation());
    }
}