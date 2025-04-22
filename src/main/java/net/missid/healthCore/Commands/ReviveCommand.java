package net.missid.healthCore.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.missid.healthCore.Listeners.PlayerListener;
import net.missid.healthCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ReviveCommand implements TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Component message = MiniMessage.miniMessage().deserialize("<b><red>Player is NULL</red></b>");
        if(args.length != 0){
            Player player = Bukkit.getPlayer(args[0]);
            if (player != null) {
                if (player.getGameMode() == GameMode.SPECTATOR) {
                    player.setGameMode(GameMode.SURVIVAL);
                    Main.PlayerLevelChanged(player);
                    player.setHealthScale(6);
                    player.teleport(Main.revivePoint);
                    message = MiniMessage.miniMessage().deserialize("<i><green>Player revived</green></i>");
                    sender.sendMessage(message);
                    PlayerListener.freeze = false;
                }else{
                    message = MiniMessage.miniMessage().deserialize("<b><red>Player is alive</red></b>");
                    sender.sendMessage(message);
                }
                return true;
            }else{
                sender.sendMessage(message);
            }
        }else{
            sender.sendMessage(message);
        }
        return true;
    }
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<Player> playerList = new ArrayList<>(Bukkit.getOnlinePlayers());
        List<String> PlayerNames = new ArrayList<>();
        for (Player player:playerList){
            PlayerNames.add(player.getName());
        }
        if(args.length == 1){
            return PlayerNames;
        }
        return List.of();
    }
}
