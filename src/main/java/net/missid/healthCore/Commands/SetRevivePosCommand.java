package net.missid.healthCore.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.missid.healthCore.IO.Storage;
import net.missid.healthCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class SetRevivePosCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Component Message;
        if(!(sender instanceof Player)){
            Message = MiniMessage.miniMessage().deserialize("<b><color:#ff2a00>Usage of this command is player only</color></b>");
            sender.sendMessage(Message);
        }else{
            if(args.length == 1){
                if(args[0].equals("get")){
                    if(Main.revivePoint!=null){
                        Map<String,Object> locationToSend = Main.revivePoint.serialize();
                        for(Map.Entry<String,Object> entry: locationToSend.entrySet()){
                            sender.sendMessage(entry.getKey() + ":" +entry.getValue());
                        }
                    }else{
                        sender.sendMessage("Location Is NULL");
                    }
                }else{
                    sender.sendMessage("Incorrect usage ERROR");
                }
            }else {
                Player player = Bukkit.getPlayer(sender.getName());
                Main.revivePoint = player.getLocation();
                Storage.getInstance().SetReviveLocation("revivelocation",Main.revivePoint);
                Message = MiniMessage.miniMessage().deserialize("<b><color:#40ff00>Position successfully set!</color></b>");
                sender.sendMessage(Message);
            }
        }
        return true;
    }
}
