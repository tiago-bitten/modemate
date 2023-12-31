package com.labi.listeners.utils;

import com.labi.items.C4;
import com.labi.listeners.utils.enums.C4State;
import com.labi.main.Modemate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

public class C4Utils {

    public static final float EXPLOSION_RANGE = 5.0F;
    public static final float DAMAGE = 7.5F;
    public static final long DELAY_SECONDS = 100L;

    private Block c4Block = null;
    private C4State c4State = C4State.NOT_ACTIVATED;
    private boolean timerStarted = false;

    public void startCount(Player player) {
        if (timerStarted) return;
        timerStarted = true;
        new BukkitRunnable() {
            long count = DELAY_SECONDS / 20L;

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
                    removeC4();
                    cancel();
                }
            }
        }.runTaskTimer(Modemate.getInstance(), 0L, 20L);
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
        block.setMetadata(String.valueOf(C4.getItemUUID()), new FixedMetadataValue(Modemate.getInstance(), true));
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

    public boolean getTimer() {
        return timerStarted;
    }

    public void setTimer(boolean flag) {
        timerStarted = flag;
    }
}