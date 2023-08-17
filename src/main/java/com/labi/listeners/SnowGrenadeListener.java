package com.labi.listeners;
// ||

import com.labi.commands.ModemateCommand;
import com.labi.listeners.utils.SnowGrenadeUtils;
import com.labi.utils.CooldownMap;
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
        utils.createParticleTrail(projectile, Particle.SNOWBALL, 1, 2);
        utils.createParticleTrail(projectile, Particle.SMOKE_NORMAL, 1, 7);

        utils.applyMetaData(projectile);

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
}
