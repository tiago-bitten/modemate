package com.labi.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class HungerCommand extends DefaultImpCommand implements CommandExecutor, TabCompleter {

    public HungerCommand(String commandName, String operatorPermission) {
        super(commandName, operatorPermission);
    }

    private static boolean isHungerEnable = true;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (!defaultCheck(commandSender, command)) return true;

        Player player = (Player) commandSender;

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

        if (strings.length == 3) {
            if (!isHungerEnable) {
                player.sendMessage(ChatColor.RED + "hunger is disabled!");
                return true;
            }

            String actionArg = strings[0];
            String targetArg = strings[1];
            String amountArg = strings[2];

            try {
                Player target = Bukkit.getPlayer(targetArg);

                if (actionArg.equalsIgnoreCase("set")) {
                    if (amountArg.equalsIgnoreCase("max")) {
                        target.setFoodLevel(20);
                        player.sendMessage(ChatColor.YELLOW + "You have set " + target.getName() + "'s hunger to max!");
                        return true;
                    }

                    if (amountArg.equalsIgnoreCase("half")) {
                        target.setFoodLevel(10);
                        player.sendMessage(ChatColor.YELLOW + "You have set " + target.getName() + "'s hunger to half!");
                        return true;
                    }

                    if (amountArg.equalsIgnoreCase("min")) {
                        target.setFoodLevel(0);
                        player.sendMessage(ChatColor.YELLOW + "You have set " + target.getName() + "'s hunger to zero!");
                        return true;
                    }

                    player.sendMessage(ChatColor.YELLOW + "<amount> must be max, half or min!");
                    return true;
                }
            }
            catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Player " + targetArg + " is not online!");
                return true;
            }
        }

        player.sendMessage(ChatColor.YELLOW + "/" + COMMAND_NAME + " <action>");
        player.sendMessage(ChatColor.YELLOW + "/" + COMMAND_NAME + " <action> <player> <amount>");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 1) {
            return List.of("enable", "disable", "set");
        }

        // TODO: tab complete for players

        if (strings.length == 3) {
            return List.of("max", "half", "min");
        }

        return null;
    }

    public boolean isHungerEnable() {
        return isHungerEnable;
    }
}
