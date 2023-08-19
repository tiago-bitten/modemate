package com.labi.main;

import com.labi.commands.*;
import com.labi.crafts.C4Craft;
import com.labi.crafts.DetonatorC4Craft;
import com.labi.crafts.LandMineCraft;
import com.labi.crafts.SnowGrenadeCraft;
import com.labi.listeners.*;
import com.labi.permissions.PermissionsEnum;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Modemate extends JavaPlugin {

    private static Modemate instance;

    private ModemateCommand modemateCommand;
    private HungerCommand hungerCommand;
    private TimeCommand timeCommand;
    private WeatherCommand weatherCommand;
    private FlyCommand flyCommand;
    private GamemodeCommand gamemodeCommand;
    private BanCommand banCommand;
    private LifeCommand lifeCommand;

    @Override
    public void onEnable() {
        instance = this;

        /* Plugin startup */
        getLogger().info("\u001B[32m" + "modemate has been enabled!" + "\u001B[0m");

        /* Main command */
        modemateCommand = new ModemateCommand("mplugin", PermissionsEnum.OPERATOR);
        getCommand(modemateCommand.COMMAND_NAME).setExecutor(modemateCommand);

        /* Commands */
        timeCommand = new TimeCommand("mtime", PermissionsEnum.OPERATOR);
        getCommand(timeCommand.COMMAND_NAME).setExecutor(timeCommand);

        weatherCommand = new WeatherCommand("mweather", PermissionsEnum.OPERATOR);
        getCommand(weatherCommand.COMMAND_NAME).setExecutor(weatherCommand);

        hungerCommand = new HungerCommand("mhunger", PermissionsEnum.OPERATOR);
        getCommand(hungerCommand.COMMAND_NAME).setExecutor(hungerCommand);

        flyCommand = new FlyCommand("mfly", PermissionsEnum.OPERATOR);
        getCommand(flyCommand.COMMAND_NAME).setExecutor(flyCommand);

        gamemodeCommand = new GamemodeCommand("mgmd", PermissionsEnum.OPERATOR);
        getCommand(gamemodeCommand.COMMAND_NAME).setExecutor(gamemodeCommand);

        banCommand = new BanCommand("mban", PermissionsEnum.OPERATOR);
        getCommand(banCommand.COMMAND_NAME).setExecutor(banCommand);

        lifeCommand = new LifeCommand("mlife", PermissionsEnum.OPERATOR);
        getCommand(lifeCommand.COMMAND_NAME).setExecutor(lifeCommand);

        /* Listeners */
        getServer().getPluginManager().registerEvents(new SnowGrenadeListener(modemateCommand), instance);
        getServer().getPluginManager().registerEvents(new HungerListener(modemateCommand, hungerCommand), instance);
        getServer().getPluginManager().registerEvents(new FlyListener(modemateCommand), instance);
        getServer().getPluginManager().registerEvents(new LandMineListener(modemateCommand), instance);
        getServer().getPluginManager().registerEvents(new C4Listener(modemateCommand), instance);
        getServer().getPluginManager().registerEvents(new PlayerPermissionJoinListener(modemateCommand), instance);

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
