package com.labi.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class HungerCommand implements CommandExecutor, TabCompleter {

    private static final String COMMAND_NAME = "mhunger";
    private static final String OPERATOR_PERMISSION = "server.op";
    private static boolean isHungerEnable = true;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("\u001B[31m" + "Only players can execute this command!" + "\u001B[0m");
            return true;
        }

        Player player = (Player) commandSender;
        if (!player.hasPermission(OPERATOR_PERMISSION)) {
            player.sendMessage("\u001B[31m" + "You don't have permission!" + "\u001B[0m");
            return true;
        }

        boolean commandName = command.getName().equalsIgnoreCase(COMMAND_NAME);
        if (!commandName) return true;

        if (strings.length == 0) {
            player.sendMessage("\u001B[33m" + "/" + COMMAND_NAME + " <args>" + "\u001B[0m");
            return true;
        }

        String difficult = player.getWorld().getDifficulty().toString();
        if (difficult.equalsIgnoreCase("peaceful")) {
            player.sendMessage(ChatColor.YELLOW + "The world difficulty is set to peaceful, you can't use this command!");
            return true;
        }

        if (strings.length == 1) {
            String arg = strings[0];

            if (arg.equalsIgnoreCase("enable")) {
                if (isHungerEnable) {
                    player.sendMessage(ChatColor.YELLOW + "hunger is already enabled!");
                }
                else {
                    isHungerEnable = true;
                    player.sendMessage(ChatColor.GREEN + "hunger has been enabled!");
                }
                return true;
            }
            if (arg.equalsIgnoreCase("disable")) {
                if (!isHungerEnable) {
                    player.sendMessage(ChatColor.YELLOW + "hunger is already disabled!");
                }
                else {
                    isHungerEnable = false;
                    player.setFoodLevel(20);
                    player.sendMessage(ChatColor.RED + "hunger has been disabled!");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return List.of("enable", "disable");
        }
        return null;
    }

    public static String getCommandName() {
        return COMMAND_NAME;
    }

    public boolean isHungerEnable() {
        return isHungerEnable;
    }
}
