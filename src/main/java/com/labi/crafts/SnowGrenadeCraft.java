package com.labi.crafts;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import static com.labi.items.SnowGrenade.createSnowGrenade;

public class SnowGrenadeCraft implements CraftInterface {

    private static ItemStack snowGrenade = createSnowGrenade();


    @Override
    public void register(JavaPlugin modemate) {
        ShapedRecipe customRecipe = new ShapedRecipe(NamespacedKey.minecraft("snow_grenade"), snowGrenade)
                .shape("PPP", "PSP", "PPP")
                .setIngredient('P', Material.GUNPOWDER)
                .setIngredient('S', Material.SNOWBALL);

        modemate.getServer().addRecipe(customRecipe);
    }
}
