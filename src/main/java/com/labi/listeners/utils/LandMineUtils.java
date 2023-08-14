package com.labi.listeners.utils;

import com.labi.utils.ExplodeItem;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class LandMineUtils implements ExplodeItem<Block, Player> {

    private static final int EXPLOSION_RANGE = 5;

    @Override
    public void explode(Block obj, Player reference) {
        obj.getWorld().createExplosion(obj.getLocation(), EXPLOSION_RANGE, true, true, reference);
        reference.damage(10);
    }
}
