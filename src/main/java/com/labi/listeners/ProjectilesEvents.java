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
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import static com.labi.listeners.utils.SnowballThrowUtils.*;

public class ProjectilesEvents implements Listener {

    @EventHandler
    public void onSnowballThrow(ProjectileLaunchEvent event) {
        Projectile projectile = event.getEntity();
        if (!(projectile instanceof Snowball)) return;

        ItemMeta itemMeta = ((Player) projectile.getShooter()).getInventory().getItemInMainHand().getItemMeta();
        if (itemMeta == null || !itemMeta.getDisplayName().equals(getSnowGrenadeName())) return;

        Vector currentVelocity = projectile.getVelocity();
        Vector adjustedVelocity = new Vector(currentVelocity.getX() * 0.4, currentVelocity.getY() * 0.7, currentVelocity.getZ() * 0.4);
        projectile.setVelocity(adjustedVelocity);
    }

    @EventHandler
    public void onSnowballHit(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();

        Projectile projectile = event.getEntity();
        if (!(projectile instanceof Snowball)) return;

        ItemMeta itemMeta = player.getInventory().getItemInMainHand().getItemMeta();
        if (itemMeta == null || !itemMeta.getDisplayName().equals(getSnowGrenadeName())) return;

        Block hitBlock = event.getHitBlock();
        LivingEntity livingEntity = (LivingEntity) event.getHitEntity();

        if (hitBlock != null || livingEntity != null) {
            explodeSnowball(projectile, player);
        }
    }
}
