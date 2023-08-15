package com.labi.listeners.utils;

import com.labi.listeners.utils.enums.C4State;
import com.labi.main.Modemate;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static com.labi.main.Modemate.getInstance;

public class C4Utils {

    private static final Modemate MODEMATE = getInstance();
    private static final float C4_EXPLOSION_POWER = 5.0F;
    private static final int DELAY_SECONDS = 5;

    private Block c4Block = null;
    private C4State c4State = C4State.NOT_ACTIVATED;
    private boolean timerStarted = false;

    public void explodeWithDelay(Player player) {
        if (timerStarted) return;
        timerStarted = true;

        startCount(player);

        Bukkit.getScheduler().runTaskLater(MODEMATE, () -> {
            createExplosion(c4Block);
        }, DELAY_SECONDS * 20L);
    }

    public void explodeWithoutDelay() {
        createExplosion(c4Block);
    }

    private void createExplosion(Block block) {
        if (block == null) return;

        Location blockLocation = block.getLocation();
        World world = blockLocation.getWorld();

        if (world == null) return;

        world.createExplosion(blockLocation, C4_EXPLOSION_POWER, true, true);
        applyParticleEffect(blockLocation, Particle.FLASH, 50, 5);
        applyParticleEffect(blockLocation, Particle.FLAME, 50, 0);

        block.setType(Material.AIR);
        removeC4();
    }

    private void startCount(Player player) {
        new BukkitRunnable() {
            int count = DELAY_SECONDS;

            @Override
            public void run() {
                if (count > 0) {
                    player.sendMessage(ChatColor.RED + "C4 will explode in " + ChatColor.YELLOW + count + ChatColor.RED + " seconds!");
                    playSound(c4Block.getLocation());
                    applyParticleEffect(c4Block.getLocation(), Particle.FLAME, 1, 0);
                    count--;
                } else {
                    player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "C4 exploded!");
                    timerStarted = false;
                    cancel();
                }
            }
        }.runTaskTimer(MODEMATE, 0L, 20L);
    }

    private void playSound(Location location) {
        if (location == null) return;
        location.getWorld().playSound(location, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.0F, 0.5F);
    }

    private void applyParticleEffect(Location location, Particle particle, int amount, int speed) {
        if (location == null) return;
        location.getWorld().spawnParticle(particle, location.getX() + 0.5, location.getY() + 1.15, location.getZ() + 0.5, amount, 0, 0, 0, speed);
    }

    public void addC4(Block block) {
        c4Block = block;
    }

    public Block getC4() {
        return c4Block;
    }

    public void removeC4() {
        c4Block = null;
    }

    public boolean isC4Placed() {
        return c4Block != null;
    }

    public boolean getC4State() {
        return c4State == C4State.ACTIVATED;
    }

    public void setC4State(boolean state) {
        c4State = state ? C4State.ACTIVATED : C4State.NOT_ACTIVATED;
    }
}