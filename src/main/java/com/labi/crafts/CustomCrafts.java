package com.labi.crafts;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomCrafts {

    private static ItemStack snowGrenade;

    public static void registerSuperSnowball(JavaPlugin plugin) {
        ShapedRecipe customRecipe = new ShapedRecipe(NamespacedKey.minecraft("snow_grenade"), createSuperSnowball())
                .shape("PPP", "PSP", "PPP")
                .setIngredient('P', Material.GUNPOWDER)
                .setIngredient('S', Material.SNOWBALL);

        plugin.getServer().addRecipe(customRecipe);
    }

    private static ItemStack createSuperSnowball() {
        ItemStack superSnowball = new ItemStack(Material.SNOWBALL);
        ItemMeta superSnowballMeta = superSnowball.getItemMeta();

        superSnowballMeta.setDisplayName(
                ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Snow Grenade"
        );
        superSnowballMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        superSnowballMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        superSnowball.setItemMeta(superSnowballMeta);

        snowGrenade = superSnowball;

        return snowGrenade;
    }
}
