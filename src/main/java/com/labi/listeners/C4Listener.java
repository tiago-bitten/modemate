package com.labi.listeners;

import com.labi.commands.ModemateCommand;
import com.labi.utils.CooldownMap;
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

import static com.labi.items.C4.*;
import static com.labi.listeners.utils.C4Utils.explodeC4;

public class C4Listener implements Listener {

    private CooldownMap<Player> cooldownMap = new CooldownMap<>();
    private static final Long C4_COOLDOWN = 5000L;
    private static JavaPlugin modemate;
    private static ModemateCommand modemateCommand;

    public C4Listener(JavaPlugin modemate, ModemateCommand modemateCommand) {
        this.modemate = modemate;
        this.modemateCommand = modemateCommand;
    }

    @EventHandler
    public void onPlaceC4(BlockPlaceEvent event) {
        if (!modemateCommand.isEnable()) return;

        Player player = event.getPlayer();

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
        if (!isC4Item(itemInMainHand) && !isC4Item(itemInOffHand)) return;

        Block c4Block = event.getBlockPlaced();

        c4Block.setMetadata(String.valueOf(getItemUUID()), new FixedMetadataValue(modemate, true));
    }

    @EventHandler
    public void onC4Activate(PlayerInteractEvent event) {
        if (!modemateCommand.isEnable()) return;

        boolean action = event.getAction().equals(Action.RIGHT_CLICK_BLOCK);
        if (!action) return;

        Block block = event.getClickedBlock();
        if (!isC4Block(block)) return;

        Player player = event.getPlayer();

        explodeC4(block, player);
    }
}
