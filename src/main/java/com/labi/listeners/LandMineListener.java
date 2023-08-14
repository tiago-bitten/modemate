package com.labi.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import static com.labi.items.LandMine.*;

public class LandMineListener implements Listener {

    private JavaPlugin modemate;

    public LandMineListener(JavaPlugin modemate) {
        this.modemate = modemate;
    }

    @EventHandler
    public void onPlaceLandMine(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        Block block = event.getBlockPlaced();

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!isLandMineItem(itemInHand)) return;

        block.setMetadata(getItemName(), new FixedMetadataValue(modemate, true));
    }

    @EventHandler
    public void onStepOnLandMine(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        boolean action = event.getAction().equals(Action.PHYSICAL);
        if (!action) return;

        Block block = event.getClickedBlock();
        if (!isLandMineBlock(block)) return;

        block.getWorld().createExplosion(block.getLocation(), 5f, true, true, player);
        event.setCancelled(true);
        block.setType(Material.AIR);
    }
}
