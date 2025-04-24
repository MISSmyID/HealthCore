package net.missid.healthCore;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;

public class PurchaseHandler {
    private static final int DEFAULT_LEVEL_PRICE = 100;
    public int levelPrice;
    public int addedLevelValueDefault = 10;
    private static int purchaseCounter;
    private static final int PURCHASE_COUNTER_MAX = 54;
    public static int getPurchaseCounter(){
        return purchaseCounter;
    }
    public static void setPurchaseCounter(int value){
        purchaseCounter = value;
    }

    private void checkPrices(){
        if(levelPrice == 0){
            levelPrice = DEFAULT_LEVEL_PRICE;
        }
    }
    public void purchase(Player player,int addedValue){
        int xpLevel = player.getLevel();
        checkPrices();
        boolean canBuy = levelPrice < xpLevel && purchaseCounter < PURCHASE_COUNTER_MAX;
        Component message;
        if(canBuy){
            levelPrice = addedValue == 0 ?
                    levelPrice + (addedLevelValueDefault * (purchaseCounter/2)):
                    levelPrice + (addedValue * (purchaseCounter/2));
            purchaseCounter += 2;
            player.setLevel(xpLevel - levelPrice);
            message = MiniMessage.miniMessage().deserialize("<i><b><green>Покупка совершена успешно!</green></b></i>");
        }else message = MiniMessage.miniMessage().deserialize("<i><b><red>Недостаточно уровней для покупки\nИли лимит на покупку сердец исчерпан!</red></b></i>");
        player.sendMessage(message);
    }
}
