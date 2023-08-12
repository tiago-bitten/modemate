package com.labi.listeners;

import com.labi.commands.ModemateCommand;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class FlyListener implements Listener {

    private ModemateCommand modemateCommand;

    public FlyListener(ModemateCommand modemateCommand) {
        this.modemateCommand = modemateCommand;
    }

    private static final String OPERATOR_PERMISSION = "server.op";
    private boolean isNotFlying = false;
    @EventHandler
    public void onPlayerFly(PlayerMoveEvent event) {
        if (!modemateCommand.isEnable()) return;

        Player player = event.getPlayer();

        if (!player.isFlying()) {
            player.setFlySpeed(0.1f);
            isNotFlying = true;
            return;
        }
        if (player.getGameMode() != GameMode.CREATIVE) return;
        if (!player.hasPermission(OPERATOR_PERMISSION)) return;
        if (!player.isSprinting()) return;

        if (isNotFlying) {
            player.setFlySpeed(0.2f);
            isNotFlying = false;
        }
    }
}
