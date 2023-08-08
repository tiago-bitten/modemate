package com.labi.listeners;
// ||
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.meta.ItemMeta;

import static com.labi.listeners.utils.SnowballThrowUtils.randomExplosion;

public class ProjectilesEvents implements Listener {

    private static final String SNOW_GRENADE = ChatColor.YELLOW + "" + ChatColor.BOLD + "" + ChatColor.ITALIC + "Snow Grenade";

    @EventHandler
    public void onSnowballThrow(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        Projectile projectile = event.getEntity();

        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();

        if (itemMeta != null && itemMeta.getDisplayName().equals(SNOW_GRENADE)) {
            if (projectile instanceof Snowball) {
                Block hitBlock = event.getHitBlock();
                LivingEntity livingEntity = (LivingEntity) event.getHitEntity();

                if (hitBlock != null ) {
                    hitBlock.getWorld().createExplosion(hitBlock.getLocation(), randomExplosion(), true, true, player);
                    projectile.remove();
                }

                if (livingEntity != null) {
                    double health = livingEntity.getHealth();
                    livingEntity.setInvulnerable(true);

                    livingEntity.getWorld().createExplosion(livingEntity.getLocation(), randomExplosion(), true, true);

                    livingEntity.setHealth(health);
                    livingEntity.setInvulnerable(false);

                    projectile.remove();
                }
            }
        }
    }
}
