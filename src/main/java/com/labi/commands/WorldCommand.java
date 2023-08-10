package com.labi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldCommand implements CommandExecutor {

    private static final String OPERATOR_PERMISSION = "server.op";

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

        boolean commandName = command.getName().equalsIgnoreCase("w");
        if (!commandName) return true;

        if (strings.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "/mworld<args>");
            return true;
        }

        String arg = strings[0];

        if (arg.equalsIgnoreCase("day")) {
            player.getWorld().setTime(1000);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to day!");
            return true;
        }

        if (arg.equalsIgnoreCase("night")) {
            player.getWorld().setTime(13000);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to night!");
            return true;
        }

        if (arg.equalsIgnoreCase("noon")) {
            player.getWorld().setTime(6000);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to noon!");
            return true;
        }

        if (arg.equalsIgnoreCase("midnight")) {
            player.getWorld().setTime(18000);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to midnight!");
            return true;
        }

        if (arg.equalsIgnoreCase("sun")) {
            player.getWorld().setStorm(false);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Weather has been set to sun!");
            return true;
        }

        if (arg.equalsIgnoreCase("rain")) {
            player.getWorld().setStorm(true);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Weather has been set to rain!");
            return true;
        }

        if (arg.equalsIgnoreCase("thunder")) {
            player.getWorld().setThundering(true);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Weather has been set to thunder!");
            return true;
        }

        if (arg.equalsIgnoreCase("clear")) {
            player.getWorld().setThundering(false);
            player.getWorld().setStorm(false);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Weather has been set to clear!");
            return true;
        }
        return false;
    }
}
