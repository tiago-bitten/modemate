package com.labi.listeners;

import com.labi.commands.ModemateCommand;
import com.labi.listeners.utils.C4Utils;
import com.labi.utils.CooldownMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import static com.labi.items.C4.getItemUUID;
import static com.labi.items.C4.isC4Item;
import static com.labi.items.DetonatorC4.isDetonatorC4Item;

public class C4Listener implements Listener {

    private C4Utils utils = new C4Utils();
    private CooldownMap<Player> cooldownMap = new CooldownMap<>();
    private static final Long C4_COOLDOWN = 2000L;

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

        if (utils.isC4Placed() || utils.isC4Activated()) {
            player.sendMessage(ChatColor.YELLOW + "You can't place another C4!");
            event.setCancelled(true);
            return;
        }

        if (cooldownMap.isOnCooldown(player)) {
            player.sendMessage(cooldownMap.getMsgCooldown(player, "s to place again!"));
            return;
        }

        utils.setC4(event.getBlockPlaced());
        utils.getC4().setMetadata(String.valueOf(getItemUUID()), new FixedMetadataValue(modemate, true));

        utils.setC4Placed(true);
        cooldownMap.setCooldown(player, C4_COOLDOWN);
    }

    @EventHandler
    public void onC4Activate(PlayerInteractEvent event) {
        if (!modemateCommand.isEnable()) return;

        Player player = event.getPlayer();

        boolean action = event.getAction().equals(Action.RIGHT_CLICK_AIR);
        if (!action) return;

        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        ItemStack itemInOffHand = event.getPlayer().getInventory().getItemInOffHand();

        if (!isDetonatorC4Item(itemInMainHand) && !isDetonatorC4Item(itemInOffHand)) return;

        if (!utils.isC4Placed()) {
            player.sendMessage(ChatColor.YELLOW + "You must place a C4 first!");
            return;
        }

        utils.explodeC4(player, true);
    }

    @EventHandler
    public void onBreakC4(BlockBreakEvent event) {
        if (!modemateCommand.isEnable()) return;

        Block block = event.getBlock();

        if (!block.hasMetadata(String.valueOf(getItemUUID()))) return;

        if (utils.isC4Activated()) {
            event.setCancelled(true);
            return;
        }

        utils.explodeC4(null, false);
    }

    @EventHandler
    public void onPlaceDetonator(BlockPlaceEvent event) {
        if (!modemateCommand.isEnable()) return;

        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();

        if (!isDetonatorC4Item(itemInMainHand)) return;

        Player player = event.getPlayer();

        Block block = event.getBlockPlaced();
        block.setType(Material.AIR);
        event.setCancelled(true);

        if (utils.isC4Activated()) {
            player.sendMessage(ChatColor.YELLOW + "C4 is already activated!");
            return;
        }

        if (!utils.isC4Placed()) {
            player.sendMessage(ChatColor.YELLOW + "You must place a C4 first!");
            return;
        }

        utils.explodeC4(player, true);
    }
}
