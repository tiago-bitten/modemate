package com.labi.listeners;
// ||

import com.labi.commands.ModemateCommand;
import com.labi.listeners.utils.SnowGrenadeUtils;
import com.labi.utils.CooldownMap;
import com.labi.utils.ExplosionUtil;
import com.labi.utils.ParticlesUtil;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import static com.labi.items.SnowGrenade.isSnowGrenadeProjectile;
import static com.labi.items.SnowGrenade.isSnowGrenadeThrow;
import static com.labi.listeners.utils.SnowGrenadeUtils.DAMAGE;
import static com.labi.utils.ExplosionUtil.explodeInstantly;
import static com.labi.utils.ParticlesUtil.createParticleTrailEffect;

public class SnowGrenadeListener implements Listener {

    private final SnowGrenadeUtils utils = new SnowGrenadeUtils();
    private final CooldownMap<Player> cooldownMap = new CooldownMap<>(2000L);

    private final ModemateCommand modemateCommand;

    public SnowGrenadeListener(ModemateCommand modemateCommand) {
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
        createParticleTrailEffect(projectile, Particle.SNOWBALL, 1, 0, 0, 0, 0, 1L);
        createParticleTrailEffect(projectile, Particle.SMOKE_NORMAL, 1, 0, 0, 0, 0, 7L);

        utils.applyMetaData(projectile);
        cooldownMap.setCooldown(player);
    }

    @EventHandler
    public void onSnowGrenadeHit(ProjectileHitEvent event) {
        if (!modemateCommand.isEnable()) return;

        Projectile projectile = event.getEntity();
        if (!(projectile.getShooter() instanceof Player)) return;
        if (!isSnowGrenadeProjectile(projectile)) return;

        Entity entity = event.getHitEntity();
        Block hitBlock = event.getHitBlock();
        if (hitBlock != null && entity != null) return;

        explodeInstantly(projectile.getLocation(), utils.randomExplosion(), utils.setFire(), true, DAMAGE);
    }
}
