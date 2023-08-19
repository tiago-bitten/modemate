package com.labi.commands;

import com.labi.permissions.PermissionsEnum;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class LifeCommand extends DefaultImpCommand implements CommandExecutor, TabCompleter {
    public LifeCommand(String commandName, PermissionsEnum permission) {
        super(commandName, permission);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!defaultCheck(commandSender, command)) return true;

        if (strings.length < 1) {
            commandSender.sendMessage(ChatColor.YELLOW + "/" + COMMAND_NAME + " <player>");
            return true;
        }

        Player target = getServer().getPlayer(strings[0]);
        String arg = strings[1];

        if (target == null) {
            commandSender.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }

        if (target.getGameMode().equals(GameMode.CREATIVE) || target.getGameMode().equals(GameMode.SPECTATOR)) {
            commandSender.sendMessage(ChatColor.RED + "You can't change the life of a player in this gamemode!");
            return true;
        }

        if (arg.equalsIgnoreCase("min")) {
            target.setHealth(0.5);
            commandSender.sendMessage(ChatColor.GREEN + "You have set " + target.getName() + "'s life to the minimum!");
            return true;
        }

        if (arg.equalsIgnoreCase("half")) {
            target.setHealth(10);
            commandSender.sendMessage(ChatColor.GREEN + "You have set " + target.getName() + "'s life to half!");
            return true;
        }

        if (arg.equalsIgnoreCase("max")) {
            target.setHealth(20);
            commandSender.sendMessage(ChatColor.GREEN + "You have set " + target.getName() + "'s life to the maximum!");
            return true;
        }

        commandSender.sendMessage(ChatColor.YELLOW + "/" + COMMAND_NAME + " <player> <min/half/max>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length != 2) return null;
        return List.of("min", "half", "max");
    }

    @Override
    protected Boolean defaultCheck(CommandSender commandSender, Command command) {
        boolean commandName = command.getName().equalsIgnoreCase(COMMAND_NAME);
        if (!commandName) return false;

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.hasPermission(PERMISSIONS.getPermission())) {
                player.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
                return false;
            }
        }

        return true;
    }
}
