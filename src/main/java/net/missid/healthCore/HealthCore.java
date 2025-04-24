package net.missid.healthCore;

import net.missid.healthCore.Commands.PurchaseCMD;
import net.missid.healthCore.Commands.ReviveCMD;
import net.missid.healthCore.Commands.SetRevivePosCMD;
import net.missid.healthCore.IO.Config;
import net.missid.healthCore.IO.Storage;
import net.missid.healthCore.Listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HealthCore extends JavaPlugin {
    public static HealthCore getInstance(){ return getPlugin(HealthCore.class);}
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        getCommand("revive").setExecutor(new ReviveCMD());
        getCommand("setRevivePosition").setExecutor(new SetRevivePosCMD());
        getCommand("purchase").setExecutor(new PurchaseCMD());
        Storage.getInstance().load();
        Config.getInstance().load();
    }

    @Override
    public void onDisable() {
        Storage.getInstance().SetFrozenList("frozenlist",Main.frozenPlayers);
        Storage.getInstance().SetPlayersData("playersdata",Main.enteredPlayers);
        Storage.getInstance().SetPurchasedHearts("purchasedhearts",Main.purchasedHeartsMap);
    }
}
