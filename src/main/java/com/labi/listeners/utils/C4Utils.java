package com.labi.listeners.utils;

import com.labi.main.Modemate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import static com.labi.main.Modemate.getInstance;

public class C4Utils {

    private static final Modemate MODEMATE = getInstance();
    private static final int C4_COOLDOWN_SECONDS = 6;
    private static boolean timerStarted = false;

    public static void explodeC4(Block c4, Player player) {
        Bukkit.getScheduler().runTaskLater(MODEMATE, () -> {
            c4.getWorld().createExplosion(c4.getLocation(), 4.0F, true, true, player);
        }, C4_COOLDOWN_SECONDS * 20L);

        startCountdown(player);
    }

    private static void startCountdown(Player player) {
        final int[] countdownTime = {C4_COOLDOWN_SECONDS};

        if (timerStarted) return;
        timerStarted = true;

        Bukkit.getScheduler().runTaskTimer(MODEMATE, () -> {
            if (countdownTime[0] > 0) {
                player.sendMessage(ChatColor.RED + "C4 will explode in " + ChatColor.YELLOW + countdownTime[0] + ChatColor.RED + " seconds!");
                countdownTime[0]--;
            } else {
                player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "C4 exploded!");
                timerStarted = false;
                Bukkit.getScheduler().cancelTasks(MODEMATE);
            }
        }, 0L, 20L);
    }
}