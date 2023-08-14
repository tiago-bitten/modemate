package com.labi.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class C4 {

    private static final UUID ITEM_UUID = UUID.fromString("e1c5f2a0-0b0a-4b0e-9e0a-5b0a0e0a0b0e");
    private static final String ITEM_NAME = ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "C4";
    private static final String ITEM_LORE = ChatColor.GRAY + "Click to arm, click again to detonate.";

    public static ItemStack createC4() {
        ItemStack c4 = new ItemStack(Material.LIGHTNING_ROD);
        ItemMeta c4Meta = c4.getItemMeta();

        c4Meta.setDisplayName(ITEM_NAME);
        c4Meta.setLore(Arrays.asList(ITEM_LORE));
        c4Meta.setCustomModelData(ITEM_UUID.hashCode());
        c4Meta.addEnchant(Enchantment.DURABILITY, 1, false);
        c4Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        c4.setItemMeta(c4Meta);

        return c4;
    }

    public static boolean isC4Item(ItemStack item) {
        if (item == null || item.getType() != Material.LIGHTNING_ROD) return false;
        if (!item.hasItemMeta()) return false;

        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta.getCustomModelData() == ITEM_UUID.hashCode();
    }

    public static boolean isC4Block(Block block) {
        if (block == null) return false;

        return block.hasMetadata(String.valueOf(getItemUUID()));
    }

    public static UUID getItemUUID() {
        return ITEM_UUID;
    }

    public static String getItemName() {
        return ITEM_NAME;
    }

    public static String getItemLore() {
        return ITEM_LORE;
    }
}
