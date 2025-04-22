package net.missid.healthCore.Generics;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class Helpers {
    public static void PlayerHealthScale(int hearts, Player player){
        int halfHearts = hearts * 2;
        player.setHealthScale(halfHearts);
    }
    public static void SayAsConsole(String s){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(),s);
    }
    public static void SayAsServer(Component s){ Bukkit.broadcast(s);}
}
