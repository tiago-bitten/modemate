package com.labi.listeners;

import com.labi.commands.ModemateCommand;
import com.labi.listeners.utils.LandMineUtils;
import com.labi.utils.CooldownMap;
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

    private final LandMineUtils landMineUtils = new LandMineUtils();
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

        if (cooldownMap.isOnCooldown(player)) {
            player.sendMessage(cooldownMap.getMsgCooldown(player, "s to place again!"));
            event.setCancelled(true);
            return;
        }

        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!isLandMineItem(itemInHand)) return;

        Block block = event.getBlockPlaced();
        block.setMetadata(getItemName(), new FixedMetadataValue(modemate, true));

        cooldownMap.setCooldown(player, LANDMINE_COOLWDOWN);
    }

    @EventHandler
    public void onStepOnLandMine(PlayerInteractEvent event) {
        if (!modemateCommand.isEnable()) return;

        boolean action = event.getAction().equals(Action.PHYSICAL);
        if (!action) return;

        Block block = event.getClickedBlock();
        if (!isLandMineBlock(block)) return;

        Player player = event.getPlayer();

        landMineUtils.explode(block, player);

        event.setCancelled(true);
        block.setType(Material.AIR);
    }
}
