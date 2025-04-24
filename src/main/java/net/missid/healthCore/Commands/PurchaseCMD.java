package net.missid.healthCore.Commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.missid.healthCore.Main;
import net.missid.healthCore.PurchaseHandler;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PurchaseCMD  implements CommandExecutor{

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        PurchaseHandler purchaseHandler = new PurchaseHandler();
        Component Message;
        if(!(sender instanceof Player)){
            Message = MiniMessage.miniMessage().deserialize("<b><color:#ff2a00>Usage of this command is player only</color></b>");
            sender.sendMessage(Message);
        }else{
            Player player = Bukkit.getPlayer(sender.getName());
            if(player != null){
                purchaseHandler.purchase(player,0);
                Main.SetPurchaseData(player);
            }else{
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<red><i><b>Игрок не найден!</b></i></red>"));
            }
        }
        return true;
    }
}
