package com.labi.commands;

import com.labi.permissions.PermissionsEnum;
import org.bukkit.BanList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class BanCommand extends DefaultImpCommand implements CommandExecutor, TabCompleter {

    public BanCommand(String commandName, PermissionsEnum permission) {
        super(commandName, permission);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!defaultCheck(commandSender, command)) return true;

        if (strings.length < 1) {
            commandSender.sendMessage(ChatColor.YELLOW + "/" + COMMAND_NAME + " <player>");
            return true;
        }

        StringBuilder banReason = new StringBuilder();

        for (int i = 1; i < strings.length; i++) {
            banReason.append(ChatColor.RED + strings[i] + " ");
        }

        System.out.println(banReason);

        Player target = getServer().getPlayer(strings[0]);

        if (target == null) {
            commandSender.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }

        getServer().getBanList(BanList.Type.PROFILE).addBan(target.getName(), banReason.toString(), null, commandSender.getName());

        target.kickPlayer(ChatColor.RED + "You have been banned from this server!");


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
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
