package com.labi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class ModemateCommand extends DefaultImpCommand implements CommandExecutor, TabCompleter {

    public ModemateCommand(String commandName, String operatorPermission) {
        super(commandName, operatorPermission);
    }

    private static boolean isEnable = true;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!defaultCheck(commandSender, command)) return true;

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "/" + super.COMMAND_NAME + " <args>");
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

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return List.of("enable", "disable");
        }
        return null;
    }
}
