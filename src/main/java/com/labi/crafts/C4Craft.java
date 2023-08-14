package com.labi.crafts;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import static com.labi.items.C4.createC4;

public class C4Craft implements CraftInterface {

    private static ItemStack c4 = createC4();

    @Override
    public void register(JavaPlugin modemate) {
        ShapedRecipe c4Recipe = new ShapedRecipe(NamespacedKey.minecraft("c4"), c4)
                .shape("ABA", "ATA", "ARA")
                .setIngredient('A', Material.AIR)
                .setIngredient('B', Material.SPRUCE_BUTTON)
                .setIngredient('T', Material.TNT)
                .setIngredient('R', Material.REDSTONE);

        modemate.getServer().addRecipe(c4Recipe);
    }
}
