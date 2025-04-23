package net.missid.healthCore;
import net.missid.healthCore.IO.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.*;

public class Main {
    public static Map<UUID,Double> JoinedPlayers = new HashMap<>();
    public static Location revivePoint;
    public static List<UUID> frozenPlayers = new ArrayList<>();
    public static void SetPlayerData(Player player){
        UUID PlayerUUID = player.getUniqueId();
        Double healthScale = player.getHealthScale();
        if(JoinedPlayers.containsKey(PlayerUUID)){
            JoinedPlayers.replace(PlayerUUID,healthScale);
        }else{
            JoinedPlayers.put(PlayerUUID,healthScale);
        }
    }
    public static void PlayerLevelChanged(Player player){
        UUID PlayerID = player.getUniqueId();
        Double healthScale = player.getHealthScale();
        for(Map.Entry<UUID,Double> entry : JoinedPlayers.entrySet()) {
            if(Objects.equals(PlayerID,entry.getKey())){
                entry.setValue(healthScale);}
        }
    }
    public static void XPHealthScale(Player player, Integer playerXpLevel) {
        int defaultHeartsLevel = Config.getInstance().getDefaultHeartsLevel();
        double scale = Config.getInstance().getScale();
        double heartsAmount;
        if(playerXpLevel<270){
            heartsAmount = Math.floor(playerXpLevel / scale);
        }else{
            heartsAmount = 54;
        }
        if (playerXpLevel < 10) {
            player.setHealthScale(defaultHeartsLevel);
            PlayerLevelChanged(player);
        } else {
            player.setHealthScale(defaultHeartsLevel + heartsAmount);
            PlayerLevelChanged(player);
        }
    }
}
