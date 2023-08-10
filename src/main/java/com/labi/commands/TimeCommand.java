package com.labi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommand implements CommandExecutor {

    private static final String COMMAND_NAME = "mtime";
    private static final String OPERATOR_PERMISSION = "server.op";

    private static final int SUNRISE = 0;
    private static final int MORNING = 1000;
    private static final int NOON = 6000;
    private static final int AFTERNOON = 12000;
    private static final int SUNSET = 13000;
    private static final int NIGHT = 14000;
    private static final int MIDNIGHT = 18000;

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

        boolean commandName = command.getName().equalsIgnoreCase(COMMAND_NAME);
        if (!commandName) return true;

        if (strings.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "/" + COMMAND_NAME + " <args>");
            return true;
        }

        String arg = strings[0];

        if (arg.equalsIgnoreCase("sunrise")) {
            player.getWorld().setTime(SUNRISE);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to day!");
            return true;
        }

        if (arg.equalsIgnoreCase("morning")) {
            player.getWorld().setTime(MORNING);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to night!");
            return true;
        }

        if (arg.equalsIgnoreCase("noon")) {
            player.getWorld().setTime(NOON);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to midnight!");
            return true;
        }

        if (arg.equalsIgnoreCase("afternoon")) {
            player.getWorld().setTime(AFTERNOON);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to noon!");
            return true;
        }

        if (arg.equalsIgnoreCase("sunset")) {
            player.getWorld().setTime(SUNSET);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to afternoon!");
            return true;
        }

        if (arg.equalsIgnoreCase("night")) {
            player.getWorld().setTime(NIGHT);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to morning!");
            return true;
        }

        if (arg.equalsIgnoreCase("midnight")) {
            player.getWorld().setTime(MIDNIGHT);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to midnight!");
            return true;
        }

        if (arg.equalsIgnoreCase("sun")) {
            player.getWorld().setStorm(false);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Weather has been set to sun!");
            return true;
        }
        return false;
    }

    public static String getCommandName() {
        return COMMAND_NAME;
    }
}
