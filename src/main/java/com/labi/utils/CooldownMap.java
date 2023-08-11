package com.labi.utils;

import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Map;

public class CooldownMap<T> {

    private final Map<T, Long> cooldownMap = new HashMap<>();

    public boolean isOnCooldown(T key) {
        return cooldownMap.containsKey(key) && cooldownMap.get(key) > System.currentTimeMillis();
    }

    public void setCooldown(T key, long delay) {
        cooldownMap.put(key, System.currentTimeMillis() + delay);
    }

    public void removeCooldown(T key) {
        cooldownMap.remove(key);
    }

    private Long getRestCooldown(T key) {
        return cooldownMap.get(key) - System.currentTimeMillis();
    }

    public String getMsgCooldown(T key, String msg) {
        return ChatColor.GRAY + String.format("%.1f", getRestCooldown(key) / 1000.0) + " " + msg;
    }
}
