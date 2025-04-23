package net.missid.healthCore.Listeners;

import net.missid.healthCore.IO.Storage;
import net.missid.healthCore.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

import java.util.Objects;

public class PlayerListener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    private void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(!Main.JoinedPlayers.containsKey(player.getUniqueId())){
            player.setHealthScale(6);
            Main.SetPlayerData(player);
            Storage.getInstance().SetPlayersData("playersdata",Main.JoinedPlayers);
        }else{
            Main.JoinedPlayers.forEach((Key,value) -> {
                if (Objects.equals(Key,player.getUniqueId())) {
                    player.setHealthScale(value);
                }
            });
        }
    }
    @EventHandler(priority = EventPriority.NORMAL)
    private void onPlayerLevelChanged(PlayerLevelChangeEvent event){
        Player player = event.getPlayer();
        int playerXpLevel = player.getLevel();
        Main.XPHealthScale(player,playerXpLevel);

    }
    @EventHandler
    private void onMoves(PlayerMoveEvent event) {
        if(Main.frozenPlayers.contains(event.getPlayer().getUniqueId())){
            if (event.getFrom().getX() == event.getTo().getX() &&
                event.getFrom().getY() == event.getTo().getY() &&
                event.getFrom().getZ() == event.getTo().getZ())
                return;
            event.setTo(event.getFrom());
        }
    }

    @EventHandler
    private void onDeath(PlayerDeathEvent event){
        Main.frozenPlayers.add(event.getPlayer().getUniqueId());

    }
    @EventHandler
    private void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Main.SetPlayerData(player);
        Storage.getInstance().UpdatePlayersData("playersdata",player,Main.JoinedPlayers);
    }

   /* @EventHandler(priority = EventPriority.NORMAL)
    private void onPlayerXpChanged(PlayerExpChangeEvent event){
        Player player = event.getPlayer();
        int playerXpLevel = player.getLevel();
        Main.XPHealthScale(player,playerXpLevel);
    }*/
}
