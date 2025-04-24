package net.missid.healthCore.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.missid.healthCore.Main;
import net.missid.healthCore.PurchaseHandler;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class ReviveCMD implements TabExecutor {
    // Сообщения
    private static final Component INVALID_ARGUMENTS = MiniMessage.miniMessage().deserialize("<b><red>Usage: /revive <player></red></b>");
    private static final Component PLAYER_NOT_FOUND = MiniMessage.miniMessage().deserialize("<b><red>Player not found or offline</red></b>");
    private static final Component NOT_SPECTATOR = MiniMessage.miniMessage().deserialize("<b><red>Player is already alive</red></b>");
    private static final Component SUCCESS = MiniMessage.miniMessage().deserialize("<b><green>Player successfully revived</green></b>");
    Main main = new Main();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1) {
            sender.sendMessage(INVALID_ARGUMENTS);
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(PLAYER_NOT_FOUND);
            return true;
        }
        if (target.getGameMode() != GameMode.SPECTATOR) {
            sender.sendMessage(NOT_SPECTATOR);
            return true;
        }
        executeRevive(target);
        sender.sendMessage(SUCCESS);
        return true;
    }
    private void executeRevive(Player player) {
        player.setGameMode(GameMode.SURVIVAL);
        main.setPlayerHealth(player, 6.0);
        player.teleport(Main.reviveLocation);
        Main.frozenPlayers.remove(player.getUniqueId());
        Main.SetPurchaseData(player,0);
        PurchaseHandler.setPurchaseCounter(0);
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1) {
            return Bukkit.getOnlinePlayers().stream()
                    .filter(p -> p.getGameMode() == GameMode.SPECTATOR)
                    .map(Player::getName)
                    .filter(name -> name.startsWith(args[0]))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
