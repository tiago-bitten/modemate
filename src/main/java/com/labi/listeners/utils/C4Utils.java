package com.labi.listeners.utils;

import com.labi.listeners.utils.enums.C4State;
import com.labi.main.Modemate;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

import static com.labi.main.Modemate.getInstance;

public class C4Utils {

    private static final Modemate MODEMATE = getInstance();
    private static final Float C4_EXPLOSION_POWER = 5.0F;
    private boolean timerStarted = false;
    private C4State c4State = C4State.NOT_ACTIVATED;
    private List<Block> c4 = new ArrayList<>();
    private int delay = 5; // seconds to explode

    public void explodeWithDelay(Player player) {
        startCount(player);

        Bukkit.getScheduler().runTaskLater(MODEMATE, () -> {
            createExplosion(getC4());
            removeC4(getC4());
        }, delay * 20L);
    }

    public void explodeWithoutDelay() {
        createExplosion(getC4());
        removeC4(getC4());
    }

    private void createExplosion(Block block) {
        block.getWorld().createExplosion(block.getLocation(), C4_EXPLOSION_POWER, true, true);
        applyParticleEffect(block.getLocation(), Particle.FLASH, 50, 5);
        applyParticleEffect(block.getLocation(), Particle.FLAME, 50, 0);
        block.setType(Material.AIR);
    }

    private void startCount(Player player) {
        if (timerStarted) return;
        timerStarted = true;

        new BukkitRunnable() {
            int count = delay;
            @Override
            public void run() {
                if (count > 0) {
                    player.sendMessage(ChatColor.RED + "C4 will explode in " + ChatColor.YELLOW + count + ChatColor.RED + " seconds!");
                    playSound(c4.get(0).getLocation());
                    applyParticleEffect(c4.get(0).getLocation(), Particle.FLAME, 1, 0);
                    count--;
                }
                else {
                    player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "C4 exploded!");
                    timerStarted = false;
                    cancel();
                }
            }
        }.runTaskTimer(MODEMATE, 0L, 20L);
    }

    private void playSound(Location location) {
        location.getWorld().playSound(location, Sound.ITEM_LODESTONE_COMPASS_LOCK, 1.0F, 0.5F);
    }

    private void applyParticleEffect(Location location, Particle particle, int amount, int speed) {
        location.getWorld().spawnParticle(particle, location.getX() + 0.5, location.getY() + 1.15, location.getZ() + 0.5, amount, 0, 0, 0, speed);
    }

    public Block getC4() {
        return c4.get(0);
    }

    public void addC4(Block block) {
        c4.add(block);
    }

    public void removeC4(Block block) {
        c4.remove(block);
    }

    public boolean isC4Placed() {
        return c4.size() == 1;
    }

    public boolean getC4State() {
        return c4State == C4State.ACTIVATED;
    }

    public void setC4State(boolean state) {
        if (state) {
            c4State = C4State.ACTIVATED;
            return;
        }
        c4State = C4State.NOT_ACTIVATED;
    }
}