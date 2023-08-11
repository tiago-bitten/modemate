package com.labi.listeners;
// ||

import com.labi.commands.ModemateCommand;
import com.labi.utils.CooldownMap;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import static com.labi.items.SnowGrenade.*;
import static com.labi.listeners.utils.SnowGrenadeUtils.*;
public class SnowGrenadeListener implements Listener {

    private final CooldownMap<Player> grenadeCooldowns = new CooldownMap<>();
    private final Long SNOW_GRENADE_COOLDOWN = 2000L;
    private static boolean isSnowGrenade = false;

    private final ModemateCommand modemateCommand;

    public SnowGrenadeListener(ModemateCommand modemateCommand) {
        this.modemateCommand = modemateCommand;
    }

    @EventHandler
    public void onSnowGrenadeThrow(ProjectileLaunchEvent event) {
        if (!modemateCommand.isEnable()) return;

        Projectile projectile = event.getEntity();
        Player player = (Player) projectile.getShooter();

        if (grenadeCooldowns.isOnCooldown(player)) {
            player.sendMessage(grenadeCooldowns.getMsgCooldown(player, "s to launch again!"));
            event.setCancelled(true);
            return;
        }

        ItemStack mainHandItemStack = player.getInventory().getItemInMainHand();
        ItemStack offHandItemStack = player.getInventory().getItemInOffHand();
        if (!isSnowGrenadeThrow(mainHandItemStack) && !isSnowGrenadeThrow(offHandItemStack)) return;

        updateVelocity(projectile);
        createParticleTrail(projectile, Particle.SNOWBALL, 1, 2);
        createParticleTrail(projectile, Particle.SMOKE_NORMAL, 1, 7);

        isSnowGrenade = true;
        grenadeCooldowns.setCooldown(player, SNOW_GRENADE_COOLDOWN);
    }

    @EventHandler
    public void onSnowGrenadeHit(ProjectileHitEvent event) {
        if (!modemateCommand.isEnable()) return;
        if (!isSnowGrenade) return;

        Projectile projectile = event.getEntity();

        Entity entity = event.getHitEntity();
        Block hitBlock = event.getHitBlock();
        if (hitBlock != null && entity != null) return;

        Player player = (Player) projectile.getShooter();

        explodeSnowball(projectile, player);

        isSnowGrenade = false;
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
