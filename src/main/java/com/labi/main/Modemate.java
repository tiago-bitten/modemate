package com.labi.main;

import com.labi.listeners.ProjectilesEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class Modemate extends JavaPlugin {

    @Override
    public void onEnable() {

        getLogger().info("\u001B[32m" + "Modemate has been enabled!" + "\u001B[0m");

        getServer().getPluginManager().registerEvents(new ProjectilesEvents(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
