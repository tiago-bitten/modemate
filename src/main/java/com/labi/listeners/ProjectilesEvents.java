package com.labi.listeners;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import static com.labi.listeners.utils.SnowballThrowUtils.randomExplosion;

public class ProjectilesEvents implements Listener {

    @EventHandler
    public void onSnowballThrow(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        Projectile projectile = event.getEntity();

        // ||

        if (projectile instanceof Snowball) {
            Block hitBlock = event.getHitBlock();
            LivingEntity livingEntity = (LivingEntity) event.getHitEntity();

            if (hitBlock != null ) {
                hitBlock.getWorld().createExplosion(hitBlock.getLocation(), randomExplosion());
                projectile.remove();
            }

            if (livingEntity != null) {
                livingEntity.getWorld().createExplosion(livingEntity.getLocation(), randomExplosion());
                projectile.remove();
            }
        }
    }
}
