package com.labi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class DefaultImpCommand {

    protected final String COMMAND_NAME;
    protected final String OPERATOR_PERMISSION;

    DefaultImpCommand(String commandName, String operatorPermission) {
        this.COMMAND_NAME = commandName;
        this.OPERATOR_PERMISSION = operatorPermission;
    }

    abstract Boolean defaultCheck(CommandSender commandSender, Command command);
}
