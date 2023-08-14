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
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import static com.labi.items.C4.getItemUUID;
import static com.labi.items.C4.isC4Item;
import static com.labi.items.DetonatorC4.isDetonatorC4Item;
import static com.labi.listeners.utils.C4Utils.explodeC4;

public class C4Listener implements Listener {

    private CooldownMap<Player> cooldownMap = new CooldownMap<>();
    private static final Long C4_COOLDOWN = 5000L;
    private static boolean isC4Placed = false;

    private C4Utils c4Utils = new C4Utils();

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

        if (cooldownMap.isOnCooldown(player)) {
            player.sendMessage(cooldownMap.getMsgCooldown(player, "s to place again!"));
            event.setCancelled(true);
            return;
        }

        if (isC4Placed) {
            player.sendMessage(ChatColor.YELLOW + "You can only place one C4 at a time!");
            event.setCancelled(true);
            return;
        }

        c4Utils.setC4(event.getBlockPlaced());
        c4Utils.getC4().setType(Material.FLOWER_POT);
        c4Utils.getC4().setMetadata(String.valueOf(getItemUUID()), new FixedMetadataValue(modemate, true));

        cooldownMap.setCooldown(player, C4_COOLDOWN);
        isC4Placed = true;
    }

    @EventHandler
    public void onC4Activate(PlayerInteractEvent event) {
        if (!modemateCommand.isEnable()) return;

        Player player = event.getPlayer();

        if (!isC4Placed) {
            player.sendMessage(ChatColor.YELLOW + "You must place a C4 first!");
            return;
        }

        boolean action = event.getAction().equals(Action.RIGHT_CLICK_AIR);
        if (!action) return;

        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        ItemStack itemInOffHand = event.getPlayer().getInventory().getItemInOffHand();

        if (!isDetonatorC4Item(itemInMainHand) && !isDetonatorC4Item(itemInOffHand)) return;

        explodeC4(player);
        isC4Placed = false;
    }

    @EventHandler
    public void onPlaceDetonator(BlockPlaceEvent event) {
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();

        if (!isDetonatorC4Item(itemInMainHand)) return;

        Player player = event.getPlayer();

        Block block = event.getBlockPlaced();
        block.setType(Material.AIR);
        event.setCancelled(true);

        if (!isC4Placed) {
            player.sendMessage(ChatColor.YELLOW + "You must place a C4 first!");
            return;
        }

        explodeC4(player);
        isC4Placed = false;
    }
}
