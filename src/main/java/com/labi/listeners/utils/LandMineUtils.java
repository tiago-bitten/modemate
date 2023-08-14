package com.labi.listeners.utils;

import com.labi.utils.ExplodeItem;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.checkerframework.framework.qual.DefaultQualifier;

public class LandMineUtils implements ExplodeItem<Block, Player> {

    private static final float EXPLOSION_RANGE = 3f;

    @Override
    public void explode(Block obj, Player reference) {
        obj.getWorld().createExplosion(obj.getLocation(), EXPLOSION_RANGE, true, true, reference);
        reference.damage(10);
    }
}
