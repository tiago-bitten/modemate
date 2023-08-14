package com.labi.listeners.utils;

import com.labi.main.Modemate;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import static com.labi.main.Modemate.getInstance;

public class C4Utils {

    private static final Modemate MODEMATE = getInstance();
    private static final int C4_COOLDOWN_SECONDS = 6;
    private static boolean timerStarted = false;

    private static Block c4;

    public static void explodeC4(Player player, boolean cooldown) {
        if (cooldown) {
            startCountdown(player);

            Bukkit.getScheduler().runTaskLater(MODEMATE, () -> {
                c4.getWorld().createExplosion(c4.getLocation(), 4.0F, true, true, player);
                applyParticleEffect(c4.getLocation(), Particle.FLASH, 50, 5);
                applyParticleEffect(c4.getLocation(), Particle.FLAME, 50, 0);
                c4 = null;
            }, C4_COOLDOWN_SECONDS * 20L);
        }
        else {
            c4.getWorld().createExplosion(c4.getLocation(), 4.0F, true, true);
            c4 = null;
        }
    }

    private static void startCountdown(Player player) {
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

    private static void playC4Sound(Location location) {
        location.getWorld().playSound(location, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.0F, 1.0F);
    }

    private static void applyParticleEffect(Location location, Particle particle, int amount, int speed) {
        location.getWorld().spawnParticle(particle, location.getX() + 0.5, location.getY() + 0.5, location.getZ() + 0.5, amount, 0, 0, 0, speed);
    }

    public Block getC4() {
        return c4;
    }

    public void setC4(Block c4) {
        C4Utils.c4 = c4;
    }
}