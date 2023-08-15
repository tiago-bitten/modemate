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

public class LandMine {

    private static final UUID ITEM_UUID = UUID.fromString("a3f7b2e0-4e0a-11eb-ae93-0242ac130002");
    private static final String ITEM_NAME = ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Land Mine";
    private static final String ITEM_LORE = ChatColor.GRAY + "Place this item on the ground to create a land mine!";

    public static ItemStack createLandMine() {
        ItemStack landMine = new ItemStack(Material.STONE_PRESSURE_PLATE);
        ItemMeta landMineMeta = landMine.getItemMeta();

        landMineMeta.setDisplayName(ITEM_NAME);
        landMineMeta.setLore(Arrays.asList(ITEM_LORE));
        landMineMeta.setCustomModelData(ITEM_UUID.hashCode());
        landMineMeta.addEnchant(Enchantment.DURABILITY, 1, true);
        landMineMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        landMine.setItemMeta(landMineMeta);

        return landMine;
    }

    public static boolean isLandMineItem(ItemStack item) {
        if (item == null || item.getType() != Material.STONE_PRESSURE_PLATE) return false;
        if (!item.hasItemMeta()) return false;

        ItemMeta itemMeta = item.getItemMeta();

        return itemMeta.getCustomModelData() == ITEM_UUID.hashCode();
    }

    public static boolean isLandMineBlock(Block block) {
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
