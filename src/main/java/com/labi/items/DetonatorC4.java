package com.labi.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class DetonatorC4 {

    private static final UUID ITEM_UUID = UUID.fromString("e1c5f2a0-0b0a-4b0e-9e0a-5b0a0e0a0b0e");
    private static final String ITEM_NAME = ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "C4 Detonator";
    private static final String ITEM_LORE = ChatColor.GRAY + "Click to detonate all C4s in a 5 block radius.";

    public static ItemStack createDetonatorC4() {
        ItemStack detonatorC4 = new ItemStack(Material.STONE_BUTTON);
        ItemMeta detonatorC4Meta = detonatorC4.getItemMeta();

        detonatorC4Meta.setDisplayName(ITEM_NAME);
        detonatorC4Meta.setLore(Arrays.asList(ITEM_LORE));
        detonatorC4Meta.setCustomModelData(ITEM_UUID.hashCode());
        detonatorC4Meta.addEnchant(Enchantment.DURABILITY, 1, false);
        detonatorC4Meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        detonatorC4.setItemMeta(detonatorC4Meta);

        return detonatorC4;
    }

    public static boolean isDetonatorC4Item(ItemStack item) {
        if (item == null || item.getType() != Material.STONE_BUTTON) return false;
        if (!item.hasItemMeta()) return false;

        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta.getCustomModelData() == ITEM_UUID.hashCode();
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
