package com.labi.main;

import com.labi.commands.HungerCommand;
import com.labi.commands.ModemateCommand;
import com.labi.commands.TimeCommand;
import com.labi.commands.WeatherCommand;
import com.labi.crafts.LandMineCraft;
import com.labi.listeners.FlyListener;
import com.labi.listeners.HungerListener;
import com.labi.listeners.LandMineListener;
import com.labi.listeners.SnowGrenadeListener;
import org.bukkit.plugin.java.JavaPlugin;

import static com.labi.crafts.SnowGrenadeCraft.registerSnowGrenade;

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
        getServer().getPluginManager().registerEvents(new SnowGrenadeListener(modemateCommand), instance);
        getServer().getPluginManager().registerEvents(new HungerListener(hungerCommand), instance);
        getServer().getPluginManager().registerEvents(new FlyListener(modemateCommand), instance);
        getServer().getPluginManager().registerEvents(new LandMineListener(), instance);

        /* Crafts */
        registerSnowGrenade(instance);

        LandMineCraft landMineCraft = new LandMineCraft();
        landMineCraft.register(instance);
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
