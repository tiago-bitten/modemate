package com.labi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ModemateCommand implements CommandExecutor {

    private static final String OPERATOR_PERMISSION = "server.op";

    private static boolean isEnable = true;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("\u001B[31m" + "Only players can execute this command!" + "\u001B[0m");
            return true;
        }

        Player player = (Player) commandSender;

        if (!player.hasPermission(OPERATOR_PERMISSION)) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
            return true;
        }

        boolean commandName = command.getName().equalsIgnoreCase("mplugin");
        if (!commandName) return true;

        if (strings.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "/m <args>");
            return true;
        }

        String arg = strings[0];

        if (arg.equalsIgnoreCase("enable")) {
            if (isEnable) {
                player.sendMessage(ChatColor.YELLOW + "modemate is already enabled!");
            }
            else {
                isEnable = true;
                player.sendMessage(ChatColor.GREEN + "modemate has been enabled!");
            }
            return true;
        }

        if (arg.equalsIgnoreCase("disable")) {
            if (!isEnable) {
                player.sendMessage(ChatColor.YELLOW + "modemate is already disabled!");
            }
            else {
                isEnable = false;
                player.sendMessage(ChatColor.RED + "modemate has been disabled!");
            }
            return true;
        }
        return false;
    }

    public boolean isEnable() {
        return isEnable;
    }
}
