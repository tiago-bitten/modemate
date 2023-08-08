package com.labi.main;

import org.bukkit.plugin.java.JavaPlugin;

public final class Modemate extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Initialize!!!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
