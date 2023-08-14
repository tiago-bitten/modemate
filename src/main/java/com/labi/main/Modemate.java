package com.labi.main;

import com.labi.commands.HungerCommand;
import com.labi.commands.ModemateCommand;
import com.labi.commands.TimeCommand;
import com.labi.commands.WeatherCommand;
import com.labi.crafts.C4Craft;
import com.labi.crafts.DetonatorC4Craft;
import com.labi.crafts.LandMineCraft;
import com.labi.crafts.SnowGrenadeCraft;
import com.labi.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Modemate extends JavaPlugin {

    private static Modemate instance;

    private ModemateCommand modemateCommand;
    private HungerCommand hungerCommand;
    private TimeCommand timeCommand;
    private WeatherCommand weatherCommand;

    @Override
    public void onEnable() {
        instance = this;

        /* Plugin startup */
        getLogger().info("\u001B[32m" + "modemate has been enabled!" + "\u001B[0m");

        /* Main command */
        modemateCommand = new ModemateCommand("mplugin", "server.op");
        getCommand(modemateCommand.getCommandName()).setExecutor(modemateCommand);

        /* Commands */
        timeCommand = new TimeCommand("mtime", "server.op");
        getCommand(timeCommand.getCommandName()).setExecutor(timeCommand);

        weatherCommand = new WeatherCommand("mweather", "server.op");
        getCommand(weatherCommand.getCommandName()).setExecutor(weatherCommand);

        hungerCommand = new HungerCommand("mhunger", "server.op");
        getCommand(hungerCommand.getCommandName()).setExecutor(hungerCommand);

        /* Listeners */
        getServer().getPluginManager().registerEvents(new SnowGrenadeListener(instance, modemateCommand), instance);
        getServer().getPluginManager().registerEvents(new HungerListener(instance, modemateCommand, hungerCommand), instance);
        getServer().getPluginManager().registerEvents(new FlyListener(instance, modemateCommand), instance);
        getServer().getPluginManager().registerEvents(new LandMineListener(instance, modemateCommand), instance);
        getServer().getPluginManager().registerEvents(new C4Listener(instance, modemateCommand), instance);

        /* Crafts */
        SnowGrenadeCraft snowGrenadeCraft = new SnowGrenadeCraft();
        snowGrenadeCraft.register(instance);

        LandMineCraft landMineCraft = new LandMineCraft();
        landMineCraft.register(instance);

        C4Craft c4Craft = new C4Craft();
        c4Craft.register(instance);

        DetonatorC4Craft detonatorC4Craft = new DetonatorC4Craft();
        detonatorC4Craft.register(instance);
    }

    public static Modemate getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        /* Plugin shutdown */
        getLogger().info("\u001B[31m" + "modemate has been disabled!" + "\u001B[0m");
    }
}
