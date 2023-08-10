package com.labi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModemateCommand implements CommandExecutor {

    // TODO: Create a command to enable e disable the plugin

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("\u001B[31m" + "Only players can execute this command!" + "\u001B[0m");
            return true;
        }

        Player player = (Player) commandSender;

        if (!player.hasPermission("modemate.command.switch")) {
            player.sendMessage(ChatColor.DARK_RED + "" + ChatColor.ITALIC + "You don't have permission!");
            return true;
        }

        if (command.getName().equalsIgnoreCase("modemate")) {
            player.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.ITALIC + "Modemate v1.0.0");
            return true;
        }
        return true;
    }
}
