package com.labi.listeners;

import com.labi.commands.ModemateCommand;
import com.labi.listeners.utils.LandMineUtils;
import com.labi.utils.CooldownMap;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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

import java.util.HashSet;
import java.util.Set;

import static com.labi.items.LandMine.*;
import static com.labi.listeners.utils.LandMineUtils.checkRadius;
import static com.labi.listeners.utils.LandMineUtils.explodeLandMine;

public class LandMineListener implements Listener {

    private final Set<Location> placedBlocksLocations = new HashSet<>();
    private final CooldownMap<Player> cooldownMap = new CooldownMap<>();
    private static final long LANDMINE_COOLWDOWN = 7000L;


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

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!isLandMineItem(itemInHand)) return;

        if (cooldownMap.isOnCooldown(player)) {
            player.sendMessage(cooldownMap.getMsgCooldown(player, "s to place again!"));
            event.setCancelled(true);
            return;
        }

        Location blockLocation = event.getBlockPlaced().getLocation();

        if (!checkRadius(blockLocation, placedBlocksLocations)) {
            player.sendMessage(ChatColor.RED + "There is a landmine nearby!");
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlockPlaced();
        block.setMetadata(getItemName(), new FixedMetadataValue(modemate, true));

        cooldownMap.setCooldown(player, LANDMINE_COOLWDOWN);
        placedBlocksLocations.add(blockLocation);
    }

    @EventHandler
    public void onStepOnLandMine(PlayerInteractEvent event) {
        if (!modemateCommand.isEnable()) return;

        boolean action = event.getAction().equals(Action.PHYSICAL);
        if (!action) return;

        Block block = event.getClickedBlock();
        if (!isLandMineBlock(block)) return;

        Player player = event.getPlayer();

        explodeLandMine(block, player);

        Location blockLocation = block.getLocation();
        placedBlocksLocations.remove(blockLocation);

        event.setCancelled(true);
    }
}
