package com.labi.crafts;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import static com.labi.items.SnowGrenade.createSnowGrenade;

public class SnowGrenadeCraft {

    private static ItemStack snowGrenade = createSnowGrenade();

    public static void registerSnowGrenade() {
        ShapedRecipe customRecipe = new ShapedRecipe(NamespacedKey.minecraft("snow_grenade"), snowGrenade)
                .shape("PPP", "PSP", "PPP")
                .setIngredient('P', Material.GUNPOWDER)
                .setIngredient('S', Material.SNOWBALL);

        Bukkit.getServer().addRecipe(customRecipe);
    }
}
