package com.labi.listeners;

import com.labi.commands.ModemateCommand;
import com.labi.items.LandMine;
import com.labi.listeners.utils.LandMineUtils;
import com.labi.utils.CooldownMap;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static com.labi.items.LandMine.isLandMineBlock;
import static com.labi.items.LandMine.isLandMineItem;

public class LandMineListener implements Listener {

    private LandMineUtils utils = new LandMineUtils();
    private CooldownMap<Player> cooldownMap = new CooldownMap<>(2000L);

    private ModemateCommand modemateCommand;

    public LandMineListener(ModemateCommand modemateCommand) {
        this.modemateCommand = modemateCommand;
    }

    @EventHandler
    public void onPlaceLandMine(BlockPlaceEvent event) {
        if (!modemateCommand.isEnable()) return;

        Player player = event.getPlayer();

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
        if (!isLandMineItem(itemInMainHand) && !isLandMineItem(itemInOffHand)) return;

        if (cooldownMap.isOnCooldown(player)) {
            player.sendMessage(cooldownMap.getMsgCooldown(player, "s to place again!"));
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlock();

        if (!utils.checkRadius(block)) {
            player.sendMessage(ChatColor.RED + "There is a landmine nearby!");
            event.setCancelled(true);
            return;
        }

        utils.applyMetaData(block);

        cooldownMap.setCooldown(player);
        utils.addBlockLocation(block);
    }

    @EventHandler
    public void onPlayerStepOnLandMine(PlayerInteractEvent event) {
        if (!modemateCommand.isEnable()) return;

        boolean action = event.getAction().equals(Action.PHYSICAL);
        if (!action) return;

        Block block = event.getClickedBlock();
        if (!isLandMineBlock(block)) return;

        Player player = event.getPlayer();

        utils.explodeLandMine(block, player);
        utils.removeBlockLocation(block);
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityStepOnLandMine(EntityInteractEvent event) {
        if (!modemateCommand.isEnable()) return;

        Block block = event.getBlock();
        if (!isLandMineBlock(block)) return;

        LivingEntity livingEntity = (LivingEntity) event.getEntity();

        utils.explodeLandMine(block, livingEntity);
        utils.removeBlockLocation(block);
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreakLandMine(BlockBreakEvent event) {
        if (!modemateCommand.isEnable()) return;

        Block block = event.getBlock();
        if (!isLandMineBlock(block)) return;

        utils.removeBlockLocation(block);
    }

    @EventHandler
    public void onBreakLandMineByExplosion(BlockExplodeEvent event) {
        if (!modemateCommand.isEnable()) return;

        List<Block> blocks = event.blockList();
        for (Block block : blocks) {
            if (!isLandMineBlock(block)) continue;

            utils.removeBlockLocation(block);
        }
    }
}
