package net.missid.healthCore;

import net.missid.healthCore.Commands.ReviveCommand;
import net.missid.healthCore.Commands.SetRevivePosCommand;
import net.missid.healthCore.Generics.Config;
import net.missid.healthCore.Listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HealthCore extends JavaPlugin {
    public static HealthCore getInstance(){ return getPlugin(HealthCore.class);}
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(),this);
        getCommand("revive").setExecutor(new ReviveCommand());
        getCommand("setRevivePosition").setExecutor(new SetRevivePosCommand());
        Config.getInstance().load();
    }

    @Override
    public void onDisable() {
        Config.getInstance().SetPlayersData("playersdata",Main.JoinedPlayers);
    }

}
