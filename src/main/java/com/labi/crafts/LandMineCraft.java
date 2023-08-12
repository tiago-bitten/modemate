package com.labi.crafts;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import static com.labi.items.LandMine.createLandMine;

public class LandMineCraft implements RegisterCraft {

    private static ItemStack landMine = createLandMine();

    @Override
    public void register(JavaPlugin modemate) {
        ShapedRecipe landMineRecipe = new ShapedRecipe(NamespacedKey.minecraft("land_mine"), landMine)
                .shape("TTT", "TST", "TTT")
                .setIngredient('T', Material.TNT)
                .setIngredient('S', Material.STONE_PRESSURE_PLATE);

        modemate.getServer().addRecipe(landMineRecipe);
    }
}
