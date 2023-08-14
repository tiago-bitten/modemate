package com.labi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class WeatherCommand extends DefaultImpCommand implements CommandExecutor, TabCompleter {

    public WeatherCommand(String commandName, String operatorPermission) {
        super(commandName, operatorPermission);
    }

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

        if (arg.equalsIgnoreCase("sun")) {
            player.getWorld().setStorm(false);
            player.getWorld().setThundering(false);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Weather has been set to sun!");
            return true;
        }

        if (arg.equalsIgnoreCase("rain")) {
            player.getWorld().setStorm(true);
            player.getWorld().setThundering(false);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Weather has been set to rain!");
            return true;
        }

        if (arg.equalsIgnoreCase("thunder")) {
            player.getWorld().setStorm(true);
            player.getWorld().setThundering(true);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Weather has been set to thunder!");
            return true;
        }

        if (arg.equalsIgnoreCase("clear")) {
            player.getWorld().setStorm(false);
            player.getWorld().setThundering(false);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Weather has been cleared!");
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return List.of("sun", "rain", "thunder", "clear");
        }
        return null;
    }
}
