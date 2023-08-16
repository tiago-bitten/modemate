package com.labi.commands;

import com.labi.permissions.PermissionsEnum;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;


public abstract class DefaultImpCommand {

    private enum State {
        ENABLE,
        DISABLE
    }

    public final String COMMAND_NAME;
    public final PermissionsEnum PERMISSIONS;
    public State state;


    public DefaultImpCommand(String commandName, PermissionsEnum permission) {
        this.COMMAND_NAME = commandName;
        this.PERMISSIONS = permission;
        this.state = state.DISABLE;
    }

    public boolean getState() {
        return state == state.ENABLE;
    }

    public void setState(boolean flag) {
        if (flag) {
            state = state.ENABLE;
            return;
        }
        state = state.DISABLE;
    }


    protected Boolean defaultCheck(CommandSender commandSender, Command command) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("\u001B[31m" + "Only players can execute this command!" + "\u001B[0m");
            return false;
        }

        Player player = (Player) commandSender;
        if (!player.hasPermission(PERMISSIONS.getPermission())) {
            player.sendMessage(ChatColor.DARK_RED + "You don't have permission!");
            return false;
        }

        boolean commandName = command.getName().equalsIgnoreCase(COMMAND_NAME);
        if (!commandName) return false;

        return true;
    }
}
