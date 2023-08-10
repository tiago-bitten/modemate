package com.labi.listeners;
// ||

import com.labi.items.SnowGrenade;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import static com.labi.items.SnowGrenade.isSnowGrenade;
import static com.labi.listeners.utils.SnowballUtils.*;

public class ProjectilesEvents implements Listener {

    @EventHandler
    public void onSnowGrenadeThrow(ProjectileLaunchEvent event) {
        Player player = (Player) event.getEntity().getShooter();

        Projectile projectile = event.getEntity();
        if (!(projectile instanceof Snowball)) return;

        ItemStack mainHandItemStack = player.getInventory().getItemInMainHand();
        ItemStack offHandItemStack = player.getInventory().getItemInOffHand();
        if (!isSnowGrenade(mainHandItemStack) && !isSnowGrenade(offHandItemStack)) return;

        updateVelocity(projectile);
        createParticleTrail(projectile, Particle.SNOWBALL, 1, 2);
        createParticleTrail(projectile, Particle.SMOKE_NORMAL, 1, 7);
    }

    @EventHandler
    public void onSnowGrenadeHit(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();

        Projectile projectile = event.getEntity();
        if (!(projectile instanceof Snowball)) return;

        ItemStack mainHandItemStack = player.getInventory().getItemInMainHand();
        ItemStack offHandItemStack = player.getInventory().getItemInOffHand();
        if (!isSnowGrenade(mainHandItemStack) && !isSnowGrenade(offHandItemStack)) return;

        Block hitBlock = event.getHitBlock();
        LivingEntity livingEntity = (LivingEntity) event.getHitEntity();

        if (hitBlock != null || livingEntity != null) {
            explodeSnowball(projectile, player);
        }
    }
}
