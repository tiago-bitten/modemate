package com.labi.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class SnowGrenade {

    private static final UUID ITEM_UUID = UUID.fromString("e1c5f2a0-0b0a-4b0e-9e0a-5b0a0e0a0b0e");
    private static final String ITEM_NAME = ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Snow Grenade";
    private static final String ITEM_LORE = ChatColor.GRAY + "Explodes on impact.";

    public static ItemStack createSnowGrenade() {
        ItemStack snowGrenade = new ItemStack(Material.SNOWBALL);
        ItemMeta snowGrenadeMeta = snowGrenade.getItemMeta();

        snowGrenadeMeta.setDisplayName(ITEM_NAME);
        snowGrenadeMeta.setLore(Arrays.asList(ITEM_LORE));
        snowGrenadeMeta.setCustomModelData((int) ITEM_UUID.getMostSignificantBits());
        snowGrenadeMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        snowGrenadeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        snowGrenade.setItemMeta(snowGrenadeMeta);

        return snowGrenade;
    }

    public static boolean isSnowGrenade(ItemStack itemStack) {
        if (itemStack == null || itemStack.getType() != Material.SNOWBALL) {
            return false;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return false;
        }

        return itemMeta.getDisplayName().equals(ITEM_NAME);
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