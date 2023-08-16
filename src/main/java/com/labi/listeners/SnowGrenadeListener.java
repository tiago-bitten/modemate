package com.labi.listeners;
// ||

import com.labi.commands.ModemateCommand;
import com.labi.listeners.utils.SnowGrenadeUtils;
import com.labi.utils.CooldownMap;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

import static com.labi.items.SnowGrenade.*;

public class SnowGrenadeListener implements Listener {

    private final SnowGrenadeUtils utils = new SnowGrenadeUtils();
    private final CooldownMap<Player> cooldownMap = new CooldownMap<>(2000L);

    private final JavaPlugin modemate;
    private final ModemateCommand modemateCommand;

    public SnowGrenadeListener(JavaPlugin modemate, ModemateCommand modemateCommand) {
        this.modemate = modemate;
        this.modemateCommand = modemateCommand;
    }

    @EventHandler
    public void onSnowGrenadeThrow(ProjectileLaunchEvent event) {
        if (!modemateCommand.isEnable()) return;

        Projectile projectile = event.getEntity();
        if (!(projectile.getShooter() instanceof Player)) return;

        Player player = (Player) projectile.getShooter();

        ItemStack mainHandItemStack = player.getInventory().getItemInMainHand();
        ItemStack offHandItemStack = player.getInventory().getItemInOffHand();
        if (!isSnowGrenadeThrow(mainHandItemStack) && !isSnowGrenadeThrow(offHandItemStack)) return;

        if (cooldownMap.isOnCooldown(player)) {
            player.sendMessage(cooldownMap.getMsgCooldown(player, "s to launch again!"));
            event.setCancelled(true);
            return;
        }

        utils.updateVelocity(projectile);
        utils.createParticleTrail(projectile, Particle.SNOWBALL, 1, 2);
        utils.createParticleTrail(projectile, Particle.SMOKE_NORMAL, 1, 7);

        projectile.setMetadata(String.valueOf(getItemUUID()), new FixedMetadataValue(modemate, true));
        cooldownMap.setCooldown(player);
    }

    @EventHandler
    public void onSnowGrenadeHit(ProjectileHitEvent event) {
        if (!modemateCommand.isEnable()) return;

        Projectile projectile = event.getEntity();

        Entity entity = event.getHitEntity();
        Block hitBlock = event.getHitBlock();
        if (hitBlock != null && entity != null) return;

        if (!isSnowGrenadeProjectile(projectile)) return;

        if (!(projectile.getShooter() instanceof Player)) return;

        Player player = (Player) projectile.getShooter();

        utils.explodeSnowGrenade(projectile, player);
    }

    @EventHandler
    public void onSnowGrenadeExplode(EntityExplodeEvent event) {
        List<Entity> affectedEntities = event.getEntity().getNearbyEntities(3, 3, 3);

        // TODO: FINISH THIS IMPLEMENTATION

        for (Entity entity : affectedEntities) {
            if (!(entity instanceof LivingEntity)) {
                System.out.println("Not a living entity");
                continue;
            }

            System.out.println("Living entity");

            LivingEntity livingEntity = (LivingEntity) entity;
            livingEntity.damage(3.0);
            livingEntity.setFireTicks(60);
        }
    }


/*    @EventHandler
    public void onSnowGrenadeExplode(EntityDamageEvent event) {
        if (!modemateCommand.isEnable()) return;

        System.out.println(event.getCause());

        boolean explosionCause = event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
        boolean projectileCause = event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE;

        if (!explosionCause && !projectileCause) return;

        if (!utils.getState()) return;

        Entity entity = event.getEntity();
        if (!(entity instanceof LivingEntity)) return;

        LivingEntity livingEntity = (LivingEntity) entity;
        livingEntity.damage(3.0);
        livingEntity.setFireTicks(60);
        utils.setState(false);
    }*/
}
