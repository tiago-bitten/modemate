package com.labi.listeners;

import com.labi.commands.ModemateCommand;
import com.labi.listeners.utils.LandMineUtils;
import com.labi.utils.CooldownMap;
import org.bukkit.ChatColor;
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

    private LandMineUtils utils = new LandMineUtils();
    private final CooldownMap<Player> cooldownMap = new CooldownMap<>();
    private static final long LANDMINE_COOLWDOWN = 2000L;


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

        block.setMetadata(getItemName(), new FixedMetadataValue(modemate, true));

        cooldownMap.setCooldown(player, LANDMINE_COOLWDOWN);
        utils.addBlockLocation(block);
    }

    @EventHandler
    public void onStepOnLandMine(PlayerInteractEvent event) {
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
}
