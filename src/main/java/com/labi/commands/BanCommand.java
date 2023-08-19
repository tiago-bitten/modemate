package com.labi.commands;

import com.labi.main.Modemate;
import com.labi.permissions.PermissionsEnum;
import org.bukkit.BanList;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.bukkit.Bukkit.getServer;

public class BanCommand extends DefaultImpCommand implements CommandExecutor, TabCompleter {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

        Player target = getServer().getPlayer(strings[0]);
        StringBuilder reason = new StringBuilder();

        for (int i = 1; i < strings.length; i++) {
            reason.append(ChatColor.RED + strings[i] + " ");
        }
        reason.append(
                "\n" + ChatColor.WHITE + "Date: " + ChatColor.RED + LocalDate.now().format(formatter) +
                "\n" + ChatColor.WHITE + "Author: " + ChatColor.RED + commandSender.getName() +
                "\n" + ChatColor.WHITE + "Duration: " + ChatColor.RED + "Permanent"
        );

        if (target == null) {
            commandSender.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }

        if (commandSender.getName().equalsIgnoreCase(target.getName())) {
            commandSender.sendMessage(ChatColor.RED + "You can't ban yourself!");
            return true;
        }

        beforeBan(target, reason.toString(), commandSender);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 1) {
            return List.of("hacking", "spamming", "griefing", "toxicity");
        }
        return null;
    }

    private void beforeBan(Player player, String reason, CommandSender author) {
        new BukkitRunnable() {
            int delay = 3;
            @Override
            public void run() {
                if (delay > 0) {
                    player.setGameMode(GameMode.SPECTATOR);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.sendTitle(ChatColor.RED + "You'll be banned in " + ChatColor.WHITE + delay, ChatColor.RED + "Reason: " + reason, 5, 20, 5);
                    delay--;
                } else {
                    ban(player, reason, author);
                    cancel();
                }
            }
        }.runTaskTimer(Modemate.getInstance(), 0, 20);
    }

    private void ban(Player player, String reason, CommandSender author) {
        getServer().getBanList(BanList.Type.NAME).addBan(player.getName(), reason, null, author.getName());
        player.kickPlayer(ChatColor.WHITE + "" + ChatColor.BOLD + "You have been banned!");
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
