package com.labi.crafts;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import static com.labi.items.DetonatorC4.createDetonatorC4;

public class DetonatorC4Craft implements CraftInterface {

    private static final ItemStack detonatorC4 = createDetonatorC4();
    @Override
    public void register(JavaPlugin modemate) {
        ShapedRecipe detonatorC4Recipe = new ShapedRecipe(NamespacedKey.minecraft("detonator_c4"), detonatorC4)
                .shape("AAA", "ABA", "ARA")
                .setIngredient('A', Material.AIR)
                .setIngredient('B', Material.STONE_BUTTON)
                .setIngredient('R', Material.REDSTONE);

        modemate.getServer().addRecipe(detonatorC4Recipe);
    }
}
