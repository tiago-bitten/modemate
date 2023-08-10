package com.labi.crafts;

import com.labi.commands.ModemateCommand;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import static com.labi.items.SnowGrenade.createSnowGrenade;

public class SnowGrenadeCraft {

    private static ItemStack snowGrenade = createSnowGrenade();

    public static void registerSnowGrenade(JavaPlugin modemate) {
        ShapedRecipe customRecipe = new ShapedRecipe(NamespacedKey.minecraft("snow_grenade"), snowGrenade)
                .shape("PPP", "PSP", "PPP")
                .setIngredient('P', Material.GUNPOWDER)
                .setIngredient('S', Material.SNOWBALL);

        modemate.getServer().addRecipe(customRecipe);
    }
}
