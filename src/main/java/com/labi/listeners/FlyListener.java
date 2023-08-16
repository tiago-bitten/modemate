package com.labi.listeners;

import com.labi.commands.ModemateCommand;
import com.labi.permissions.PermissionsEnum;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyListener implements Listener {

    private final JavaPlugin modemate;
    private final ModemateCommand modemateCommand;

    public FlyListener(JavaPlugin modemate, ModemateCommand modemateCommand) {
        this.modemate = modemate;
        this.modemateCommand = modemateCommand;
    }

    private PermissionsEnum permissionsEnum;
    private boolean hasAppliedSpeed = false;

    @EventHandler
    public void onPlayerFly(PlayerMoveEvent event) {
        if (!modemateCommand.isEnable()) return;

        Player player = event.getPlayer();

        if (!player.hasPermission(permissionsEnum.OPERATOR.getPermission())) return;
        if (player.getGameMode() != GameMode.CREATIVE) return;
        if (!player.isFlying()) return;
        if (hasAppliedSpeed) return;

        player.setFlySpeed(0.2f);
        hasAppliedSpeed = true;
    }
}
