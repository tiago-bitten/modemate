package com.labi.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class LandMineListener implements Listener {

    @EventHandler
    public void onStepOnLandMine(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        boolean action = event.getAction().equals(Action.PHYSICAL);
        if (!action) return;

        Block block = event.getClickedBlock();
        if (block == null) return;

        boolean material = event.getClickedBlock().getType().equals(Material.STONE_PRESSURE_PLATE);
        if (!material) return;

        block.getWorld().createExplosion(block.getLocation(), 4f, true, true, player);
        event.setCancelled(true);
        block.setType(Material.AIR);
    }
}
