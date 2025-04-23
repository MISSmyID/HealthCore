package net.missid.healthCore;
import net.missid.healthCore.IO.Config;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import java.util.*;

public class Main {
    public static Map<UUID,Double> JoinedPlayers = new HashMap<>();
    public static Location revivePoint;
    public static List<UUID> frozenPlayers = new ArrayList<>();
    private static final int defaultHeartsLevel = Config.getInstance().getDefaultHeartsLevel();
    private static final double scale = Config.getInstance().getScale(); ;



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
    public static void XPHealthScale(Player player, Integer playerXpLevel){
        double heartsAmount = Math.floor(playerXpLevel/scale);
        if(playerXpLevel<10){
            player.setHealthScale(defaultHeartsLevel);
            PlayerLevelChanged(player);
        }else{
            if(player.getHealthScale()<60){
                player.setHealthScale(defaultHeartsLevel + heartsAmount);
                PlayerLevelChanged(player);
            } else if (player.getHealthScale()>60) {
                player.setHealthScale(60);
            }
        }
    }
}
