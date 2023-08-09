package com.labi.listeners;
// ||

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.meta.ItemMeta;

import static com.labi.listeners.utils.SnowballThrowUtils.randomExplosion;

public class ProjectilesEvents implements Listener {

    private static final String SNOW_GRENADE = ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Snow Grenade";

    @EventHandler
    public void onSnowballThrow(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        Projectile projectile = event.getEntity();

        if (!(projectile instanceof Snowball)) return;

        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();

        if (itemMeta != null && itemMeta.getDisplayName().equals(SNOW_GRENADE)) {
            Block hitBlock = event.getHitBlock();
            LivingEntity livingEntity = (LivingEntity) event.getHitEntity();

            if (hitBlock != null || livingEntity != null) {
                projectile.getWorld().createExplosion(projectile.getLocation(), randomExplosion(), true, true, player);
                projectile.remove();
            }
        }
    }
}
