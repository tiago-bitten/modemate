package com.labi.listeners.utils;

import com.labi.items.C4;
import com.labi.listeners.utils.enums.C4State;
import com.labi.main.Modemate;
import com.labi.utils.ExplosionUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import static com.labi.main.Modemate.getInstance;
import static com.labi.utils.ExplosionUtil.explode;

public class C4Utils {

    private static final Modemate MODEMATE = getInstance();
    private static final float EXPLOSION_RANGE = 5.0F;
    private static final float DAMAGE = 7.5F;
    private static final int DELAY_SECONDS = 5;

    private Block c4Block = null;
    private C4State c4State = C4State.NOT_ACTIVATED;
    private boolean timerStarted = false;

    public void explodeWithDelay(Player player) {
        if (timerStarted) return;
        timerStarted = true;

        startCount(player);

        Bukkit.getScheduler().runTaskLater(MODEMATE, () -> {
            explode(c4Block.getLocation(), EXPLOSION_RANGE, DAMAGE);
            c4Block.setType(Material.AIR);
            removeC4();
        }, DELAY_SECONDS * 20L);
    }

    public void explodeWithoutDelay() {
        explode(c4Block.getLocation(), EXPLOSION_RANGE, DAMAGE);
        c4Block.setType(Material.AIR);
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

    public void applyMetaData(Block block) {
        block.setMetadata(String.valueOf(C4.getItemUUID()), new FixedMetadataValue(MODEMATE, true));
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