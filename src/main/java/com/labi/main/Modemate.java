package com.labi.main;

import com.labi.crafts.CustomCrafts;
import com.labi.listeners.ProjectilesEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class Modemate extends JavaPlugin {

    private static Modemate instance;

    @Override
    public void onEnable() {
        instance = this;

        /* Plugin startup */
        getLogger().info("\u001B[32m" + "Modemate has been enabled!" + "\u001B[0m");

        /* Listeners */
        getServer().getPluginManager().registerEvents(new ProjectilesEvents(), this);

        /* Crafts */
        CustomCrafts.registerSuperSnowball(this);
    }

    public static Modemate getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
