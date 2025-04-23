package net.missid.healthCore;

import net.missid.healthCore.Commands.ReviveCommand;
import net.missid.healthCore.Commands.SetRevivePosCommand;
import net.missid.healthCore.IO.Storage;
import net.missid.healthCore.Listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HealthCore extends JavaPlugin {
    public static HealthCore getInstance(){ return getPlugin(HealthCore.class);}
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        getCommand("revive").setExecutor(new ReviveCommand());
        getCommand("setRevivePosition").setExecutor(new SetRevivePosCommand());
        Storage.getInstance().load();
    }

    @Override
    public void onDisable() {
        Storage.getInstance().setFrozenList("frozenlist",Main.frozenPlayers);
        Storage.getInstance().SetPlayersData("playersdata",Main.JoinedPlayers);
    }
}
