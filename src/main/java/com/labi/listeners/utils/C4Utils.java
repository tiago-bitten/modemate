package com.labi.listeners.utils;

import com.labi.main.Modemate;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import static com.labi.main.Modemate.getInstance;

public class C4Utils {

    private static final Modemate MODEMATE = getInstance();
    private static final int C4_COOLDOWN_SECONDS = 6;
    private static final Float C4_EXPLOSION_POWER = 5.0F;
    private boolean timerStarted = false;
    private boolean isC4Placed = false;

    private Block c4;

    public void explodeC4(Player player, boolean cooldown) {
        if (cooldown) {
            startCountdown(player);

            Bukkit.getScheduler().runTaskLater(MODEMATE, () -> {
                c4.getWorld().createExplosion(c4.getLocation(), C4_EXPLOSION_POWER, true, true, player);
                applyParticleEffect(c4.getLocation(), Particle.FLASH, 50, 5);
                applyParticleEffect(c4.getLocation(), Particle.FLAME, 50, 0);
                c4 = null;
            }, C4_COOLDOWN_SECONDS * 20L);
        }
        else {
            c4.getWorld().createExplosion(c4.getLocation(), C4_EXPLOSION_POWER, true, true);
            c4 = null;
        }
    }

    private void startCountdown(Player player) {
        final int[] countdownTime = {C4_COOLDOWN_SECONDS};

        if (timerStarted) return;
        timerStarted = true;

        Bukkit.getScheduler().runTaskTimer(MODEMATE, () -> {
            if (countdownTime[0] > 0) {
                player.sendMessage(ChatColor.RED + "C4 will explode in " + ChatColor.YELLOW + countdownTime[0] + ChatColor.RED + " seconds!");
                playC4Sound(c4.getLocation());
                applyParticleEffect(c4.getLocation(), Particle.FLAME, 1, 0);
                countdownTime[0]--;
            } else {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "C4 exploded!");
                timerStarted = false;
                Bukkit.getScheduler().cancelTasks(MODEMATE);
            }
        }, 0L, 20L);
    }

    private void playC4Sound(Location location) {
        location.getWorld().playSound(location, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.0F, 1.0F);
    }

    private void applyParticleEffect(Location location, Particle particle, int amount, int speed) {
        location.getWorld().spawnParticle(particle, location.getX() + 0.5, location.getY() + 1.15, location.getZ() + 0.5, amount, 0, 0, 0, speed);
    }

    public boolean checkC4Placed(Player player, String msg) {
        if (isC4Placed) {
            player.sendMessage(ChatColor.YELLOW + msg);
            return true;
        }
        if (msg != null) {
            player.sendMessage(ChatColor.YELLOW + msg);
        }
        return false;
    }

    public Block getC4() {
        return c4;
    }

    public void setC4(Block c4) {
        this.c4 = c4;
    }

    public boolean isC4Placed() {
        return isC4Placed;
    }

    public void setC4Placed(boolean c4Placed) {
        this.isC4Placed = c4Placed;
    }
}