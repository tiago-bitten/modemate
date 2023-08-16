package com.labi.commands;

import com.labi.permissions.PermissionsEnum;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class TimeCommand extends DefaultImpCommand implements CommandExecutor, TabCompleter {


    public TimeCommand(String commandName, PermissionsEnum permissionsEnum) {
        super(commandName, permissionsEnum);
    }
    private static final int SUNRISE = 0;
    private static final int MORNING = 1000;
    private static final int NOON = 6000;
    private static final int AFTERNOON = 12000;
    private static final int SUNSET = 13000;
    private static final int NIGHT = 14000;

    private static final int MIDNIGHT = 18000;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!defaultCheck(commandSender, command)) return true;

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.sendMessage(ChatColor.YELLOW + "/" + COMMAND_NAME + " <args>");
            return true;
        }

        String arg = strings[0];

        if (arg.equalsIgnoreCase("sunrise")) {
            player.getWorld().setTime(SUNRISE);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to sunrise!");
            return true;
        }

        if (arg.equalsIgnoreCase("morning")) {
            player.getWorld().setTime(MORNING);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to morning!");
            return true;
        }

        if (arg.equalsIgnoreCase("noon")) {
            player.getWorld().setTime(NOON);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to noon!");
            return true;
        }

        if (arg.equalsIgnoreCase("afternoon")) {
            player.getWorld().setTime(AFTERNOON);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to afternoon!");
            return true;
        }

        if (arg.equalsIgnoreCase("sunset")) {
            player.getWorld().setTime(SUNSET);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to sunset!");
            return true;
        }

        if (arg.equalsIgnoreCase("night")) {
            player.getWorld().setTime(NIGHT);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to night!");
            return true;
        }

        if (arg.equalsIgnoreCase("midnight")) {
            player.getWorld().setTime(MIDNIGHT);
            player.sendMessage(ChatColor.GREEN + "modemate: " + ChatColor.WHITE + "Time has been set to midnight!");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return List.of("sunrise", "morning", "noon", "afternoon", "sunset", "night", "midnight");
        }
        return null;
    }
}
