package com.labi.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class DefaultImpCommand {

    protected final String COMMAND_NAME;
    protected final String OPERATOR_PERMISSION;

    DefaultImpCommand(String commandName, String operatorPermission) {
        this.COMMAND_NAME = commandName;
        this.OPERATOR_PERMISSION = operatorPermission;
    }

    public String getCommandName() {
        return COMMAND_NAME;
    }

    protected Boolean defaultCheck(CommandSender commandSender, Command command) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("\u001B[31m" + "Only players can execute this command!" + "\u001B[0m");
            return false;
        }

        Player player = (Player) commandSender;
        if (!player.hasPermission(OPERATOR_PERMISSION)) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
            return false;
        }

        boolean commandName = command.getName().equalsIgnoreCase(COMMAND_NAME);
        if (!commandName) return false;

        return true;
    }
}
