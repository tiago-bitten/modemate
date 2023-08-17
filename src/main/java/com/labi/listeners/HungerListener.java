package com.labi.listeners;

import com.labi.commands.HungerCommand;
import com.labi.commands.ModemateCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class HungerListener implements Listener {

    private final ModemateCommand modemateCommand;
    private final HungerCommand hungerCommand;

    public HungerListener(ModemateCommand modemateCommand, HungerCommand hungerCommand) {
        this.modemateCommand = modemateCommand;
        this.hungerCommand = hungerCommand;
    }

    @EventHandler
    public void onHungerChange(FoodLevelChangeEvent event) {
        if (!modemateCommand.isEnable()) return;
        if (hungerCommand.getState()) return;

        Entity entity = event.getEntity();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.setFoodLevel(20);
            event.setCancelled(true);
        }
    }
}
