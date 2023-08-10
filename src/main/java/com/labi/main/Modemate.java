package com.labi.main;

import com.labi.commands.ModemateCommand;
import com.labi.commands.TimeCommand;
import com.labi.listeners.SnowGrenadeListener;
import org.bukkit.plugin.java.JavaPlugin;

import static com.labi.crafts.SnowGrenadeCraft.registerSnowGrenade;

public final class Modemate extends JavaPlugin {

    private static Modemate instance;
    private ModemateCommand modemateCommand;

    @Override
    public void onEnable() {
        instance = this;

        /* Plugin startup */
        getLogger().info("\u001B[32m" + "modemate has been enabled!" + "\u001B[0m");

        /* Main command */
        modemateCommand = new ModemateCommand();
        getCommand(ModemateCommand.getCommandName()).setExecutor(modemateCommand);

        /* Commands */
        getCommand(TimeCommand.getCommandName()).setExecutor(new TimeCommand());

        /* Listeners */
        getServer().getPluginManager().registerEvents(new SnowGrenadeListener(modemateCommand), instance);

        /* Crafts */
        registerSnowGrenade(instance);
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
