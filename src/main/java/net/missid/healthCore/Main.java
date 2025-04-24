package net.missid.healthCore;
import net.missid.healthCore.IO.Config;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import java.util.*;

public class Main {
    public static Map<UUID,Double> enteredPlayers = new HashMap<>();
    public static Map<UUID,Double> purchasedHeartsMap = new HashMap<>();
    public static Location reviveLocation;
    public static List<UUID> frozenPlayers = new ArrayList<>();
    private static final int MAX_HEARTS = 54;
    private static final int HEARTS_PURCHASE_LIMIT = 54;
    private static final int XP_LEVEL_THRESHOLD = 270;
    private static final int LOW_LEVEL_THRESHOLD = 10;
    private static final double DEFAULT_ADDED_HEARTS = 0d;
    //#################################################################
    //PlayerDataSection
    public static void SetPlayerData(Player player){
        UUID PlayerUUID = player.getUniqueId();
        Double healthScale = player.getHealthScale();
        if(enteredPlayers.containsKey(PlayerUUID)){
            enteredPlayers.replace(PlayerUUID,healthScale);
        }else{
            enteredPlayers.put(PlayerUUID,healthScale);
        }
    }
    public static void updateHealthMap(Player player){
        UUID PlayerID = player.getUniqueId();
        Double healthScale = player.getHealthScale();
        for(Map.Entry<UUID,Double> entry : enteredPlayers.entrySet()) {
            if(Objects.equals(PlayerID,entry.getKey())){
                entry.setValue(healthScale);}
        }
    }
    public static void SetPurchaseData(Player player){
        UUID PlayerUUID = player.getUniqueId();
        int purchaseCounter = PurchaseHandler.getPurchaseCounter();
        if(purchasedHeartsMap.containsKey(PlayerUUID)){
            purchasedHeartsMap.replace(PlayerUUID,(double) purchaseCounter);
        }else{
            purchasedHeartsMap.put(PlayerUUID,(double) purchaseCounter);
        }
    }
    public static void SetPurchaseData(Player player,double var) {
        UUID PlayerUUID = player.getUniqueId();
        if (purchasedHeartsMap.containsKey(PlayerUUID)) {
            purchasedHeartsMap.replace(PlayerUUID,var);
        } else {
            purchasedHeartsMap.put(PlayerUUID,var);
        }
    }
    //############################################################################
    //HeartsSection

    public void XPHealthScale(Player player, Integer playerXpLevel) {
        int startHeartsLevel = Config.getDefaultHeartsLevel();
        double scale = Config.getScale();
        double purchasedHearts = purchasedHeartsMap.getOrDefault(player.getUniqueId(), DEFAULT_ADDED_HEARTS);
        if (shouldApplyPurchasedHealthChanges(purchasedHearts)) {
            applyHealthChanges(player,purchasedHearts,startHeartsLevel,calculateHeartsAmount(playerXpLevel,scale),playerXpLevel);
        }else {
            applyHealthChanges(player,startHeartsLevel,calculateHeartsAmount(playerXpLevel,scale),playerXpLevel);
        }
    }
    private double calculateHeartsAmount(int xpLevel, double scale) {
        return xpLevel < XP_LEVEL_THRESHOLD ?
                Math.floor(xpLevel / scale) :
                MAX_HEARTS;
    }
    private boolean shouldApplyPurchasedHealthChanges(double Hearts) {
        return Hearts > DEFAULT_ADDED_HEARTS && Hearts < HEARTS_PURCHASE_LIMIT;
    }
    public void setPlayerHealth(Player player,double health){
        player.setHealthScale(health);
        AttributeInstance maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(health);
        }
        updateHealthMap(player);
    }

    private void applyHealthChanges(Player player, double startHearts, double heartsAmount, int xpLevel) {
        double totalHealth = xpLevel < LOW_LEVEL_THRESHOLD ?
                startHearts :
                startHearts + heartsAmount;
        setPlayerHealth(player, totalHealth);
    }
    private void applyHealthChanges(Player player, double addedHearts, double startHearts, double heartsAmount, int xpLevel) {
        player.sendMessage("Adding hearts");
        double totalHealth = xpLevel < LOW_LEVEL_THRESHOLD ?
                startHearts + addedHearts :
                startHearts + addedHearts + heartsAmount - addedHearts;
        setPlayerHealth(player, totalHealth);
        player.sendMessage("Hearts added");
    }
}
