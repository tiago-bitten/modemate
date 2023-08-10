package com.labi.listeners;
// ||

import com.labi.commands.ModemateCommand;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import static com.labi.items.SnowGrenade.isSnowGrenade;
import static com.labi.listeners.utils.SnowGrenadeUtils.*;
public class SnowGrenadeListener implements Listener {

    private ModemateCommand modemateCommand;

    public SnowGrenadeListener(ModemateCommand modemateCommand) {
        this.modemateCommand = modemateCommand;
    }

    @EventHandler
    public void onSnowGrenadeThrow(ProjectileLaunchEvent event) {
        if (!modemateCommand.isEnable()) return;

        Projectile projectile = event.getEntity();
        if (!(projectile instanceof Snowball)) return;

        Player player = (Player) projectile.getShooter();

        ItemStack mainHandItemStack = player.getInventory().getItemInMainHand();
        ItemStack offHandItemStack = player.getInventory().getItemInOffHand();
        if (!isSnowGrenade(mainHandItemStack) && !isSnowGrenade(offHandItemStack)) return;

        updateVelocity(projectile);
        createParticleTrail(projectile, Particle.SNOWBALL, 1, 2);
        createParticleTrail(projectile, Particle.SMOKE_NORMAL, 1, 7);
    }

    @EventHandler
    public void onSnowGrenadeHit(ProjectileHitEvent event) {
        if (!modemateCommand.isEnable()) return;

        Projectile projectile = event.getEntity();
        if (!(projectile instanceof Snowball)) return;

        Player player = (Player) projectile.getShooter();

        ItemStack mainHandItemStack = player.getInventory().getItemInMainHand();
        ItemStack offHandItemStack = player.getInventory().getItemInOffHand();
        if (!isSnowGrenade(mainHandItemStack) && !isSnowGrenade(offHandItemStack)) return;

        Entity entity = event.getHitEntity();
        Block hitBlock = event.getHitBlock();
        if (hitBlock != null && entity != null) return;

        explodeSnowball(projectile, player);
    }

    @EventHandler
    public void onSnowGrenadeExplode(EntityDamageEvent event) {
        if (!modemateCommand.isEnable()) return;

        boolean explosionCause = event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
        boolean projectileCause = event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE;

        if (!explosionCause && !projectileCause) return;

        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity)) return;

        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.damage(3.0);
        livingEntity.setFireTicks(60);
    }
}
