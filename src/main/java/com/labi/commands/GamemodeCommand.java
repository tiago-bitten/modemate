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

public class GamemodeCommand extends DefaultImpCommand implements CommandExecutor, TabCompleter {
    public GamemodeCommand(String commandName, PermissionsEnum permissionsEnum) {
        super(commandName, permissionsEnum);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!defaultCheck(commandSender, command)) return true;

        Player player = (Player) commandSender;

        if (player.getGameMode() == GameMode.SURVIVAL) {
            setState(false);

            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(ChatColor.GREEN + "You are now in creative mode!");

            return true;
        }

        if (player.getGameMode() == GameMode.CREATIVE) {
            setState(false);

            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(ChatColor.GREEN + "You are now in survival mode!");

            return true;
        }

        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage(ChatColor.GREEN + "You are now in creative mode!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
