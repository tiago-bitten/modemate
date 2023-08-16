package com.labi.commands;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class FlyCommand extends DefaultImpCommand implements CommandExecutor, TabCompleter {
    public FlyCommand(String commandName, String operatorPermission) {
        super(commandName, operatorPermission);
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!defaultCheck(commandSender, command)) return true;

        Player player = (Player) commandSender;

        if (player.getGameMode() == GameMode.CREATIVE) {
            player.sendMessage(ChatColor.RED + "You can't use this command in creative mode!");
            return true;
        }

        if (getState()) {
            setState(false);

            player.setAllowFlight(false);
            player.setFlying(false);
            player.sendMessage(ChatColor.RED + "You are no longer flying!");
            return true;
        }

        setState(true);

        player.setAllowFlight(true);
        player.setFlySpeed(0.1f);
        player.sendMessage(ChatColor.GREEN + "You are now flying!");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
