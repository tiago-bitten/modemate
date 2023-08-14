package com.labi.listeners;

import com.labi.commands.ModemateCommand;
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
    private ModemateCommand modemateCommand;

    public LandMineListener(JavaPlugin modemate, ModemateCommand modemateCommand) {
        this.modemate = modemate;
        this.modemateCommand = modemateCommand;
    }

    @EventHandler
    public void onPlaceLandMine(BlockPlaceEvent event) {
        if (!modemateCommand.isEnable()) return;

        Player player = event.getPlayer();

        Block block = event.getBlockPlaced();

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!isLandMineItem(itemInHand)) return;

        block.setMetadata(getItemName(), new FixedMetadataValue(modemate, true));
    }

    @EventHandler
    public void onStepOnLandMine(PlayerInteractEvent event) {
        if (!modemateCommand.isEnable()) return;

        boolean action = event.getAction().equals(Action.PHYSICAL);
        if (!action) return;

        Block block = event.getClickedBlock();
        if (!isLandMineBlock(block)) return;

        Player player = event.getPlayer();

        block.getWorld().createExplosion(block.getLocation(), 5f, true, true, player);
        event.setCancelled(true);
        block.setType(Material.AIR);
    }
}
