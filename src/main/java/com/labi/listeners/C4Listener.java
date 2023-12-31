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
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static com.labi.items.C4.isC4Block;
import static com.labi.items.C4.isC4Item;
import static com.labi.items.DetonatorC4.isDetonatorC4Item;
import static com.labi.listeners.utils.C4Utils.*;
import static com.labi.utils.ExplosionUtil.explodeAfter;
import static com.labi.utils.ExplosionUtil.explodeInstantly;

public class C4Listener implements Listener {

    private final C4Utils utils = new C4Utils();
    private final CooldownMap<Player> cooldownMap = new CooldownMap<>(15000L);

    private final ModemateCommand modemateCommand;

    public C4Listener(ModemateCommand modemateCommand) {
        this.modemateCommand = modemateCommand;
    }

    @EventHandler
    public void onPlaceC4(BlockPlaceEvent event) {
        if (!modemateCommand.isEnable()) return;

        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();

        if (!isC4Item(mainHandItem) && !isC4Item(offHandItem)) return;

        if (utils.isC4Placed() || utils.getC4State()) {
            player.sendMessage(ChatColor.YELLOW + "You can't place another C4!");
            event.setCancelled(true);
            return;
        }

        if (cooldownMap.isOnCooldown(player)) {
            player.sendMessage(cooldownMap.getMsgCooldown(player, "s to place again!"));
            event.setCancelled(true);
            return;
        }

        Block c4Block = event.getBlockPlaced();
        utils.addC4(c4Block);
        utils.applyMetaData(c4Block);

        cooldownMap.setCooldown(player);
    }

    @EventHandler
    public void onC4Activate(PlayerInteractEvent event) {
        if (!modemateCommand.isEnable()) return;

        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR) return;

        ItemStack mainHandItem = player.getInventory().getItemInMainHand();
        ItemStack offHandItem = player.getInventory().getItemInOffHand();

        if (!isDetonatorC4Item(mainHandItem) && !isDetonatorC4Item(offHandItem)) {
            return;
        }

        if (!utils.isC4Placed()) {
            player.sendMessage(ChatColor.YELLOW + "You must place a C4 first!");
            return;
        }

        if (utils.getTimer()) {
            player.sendMessage(ChatColor.YELLOW + "C4 is already activated!");
            return;
        }

        utils.startCount(player);
        explodeAfter(utils.getC4().getLocation(), EXPLOSION_RANGE, true, true, DAMAGE, DELAY_SECONDS);
    }

    @EventHandler
    public void onBreakC4(BlockBreakEvent event) {
        if (!modemateCommand.isEnable()) return;

        Block block = event.getBlock();

        if (!isC4Block(block)) return;

        if (utils.getC4State()) {
            event.setCancelled(true);
            return;
        }

        explodeInstantly(block.getLocation(), EXPLOSION_RANGE, true, true, DAMAGE);
        utils.removeC4();
    }

    @EventHandler
    public void onPlaceDetonator(BlockPlaceEvent event) {
        if (!modemateCommand.isEnable()) return;

        ItemStack mainHandItem = event.getPlayer().getInventory().getItemInMainHand();

        if (!isDetonatorC4Item(mainHandItem)) return;

        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        if (utils.getC4State()) {
            player.sendMessage(ChatColor.YELLOW + "C4 is already activated!");
            block.setType(Material.AIR);
            event.setCancelled(true);
            return;
        }

        if (!utils.isC4Placed()) {
            player.sendMessage(ChatColor.YELLOW + "You must place a C4 first!");
            block.setType(Material.AIR);
            event.setCancelled(true);
            return;
        }

        if (utils.getTimer()) {
            player.sendMessage(ChatColor.YELLOW + "C4 is already activated!");
            block.setType(Material.AIR);
            event.setCancelled(true);
            return;
        }

        utils.startCount(player);
        explodeAfter(utils.getC4().getLocation(), EXPLOSION_RANGE, true, true, DAMAGE, DELAY_SECONDS);

        block.setType(Material.AIR);
        event.setCancelled(true);
    }

    @EventHandler
    public void onBreakC4ByExplosion(BlockExplodeEvent event) {
        // TODO: create a event to remove c4 if it's destroyed by explosion
    }
}
