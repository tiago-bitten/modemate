package com.labi.listeners;

import com.labi.commands.ModemateCommand;
import com.labi.permissions.PermissionsEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerPermissionJoinListener implements Listener {

    private PermissionsEnum permissionsEnum;

    private final ModemateCommand modemateCommand;

    public PlayerPermissionJoinListener(ModemateCommand modemateCommand) {
        this.modemateCommand = modemateCommand;
    }

    @EventHandler
    public void onOperatorJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission(permissionsEnum.OPERATOR.getPermission())) return;

        player.sendMessage("Welcome to the server! OP =)");
    }
}
