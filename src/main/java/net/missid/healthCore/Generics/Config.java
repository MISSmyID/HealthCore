package net.missid.healthCore.Generics;

import net.missid.healthCore.HealthCore;
import net.missid.healthCore.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Config {
private File file;
private YamlConfiguration config;
    private Config(){
    }
    private final static Config instance = new Config();
    public static Config getInstance(){
        return instance;
    }
    public void load(){
        file = new File(HealthCore.getInstance().getDataFolder(), "config.yml");
        if(!file.exists()) HealthCore.getInstance().saveResource("config.yml", false);
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try{
            config.load(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Main.JoinedPlayers = GetPlayerData("playersdata.");
        Main.revivePoint = GetReviveLocation("revivelocation");
    }


    public void set(String path, Object value){
        config.set(path,value);
        save();
    }

    public String getKey(String path){
        return config.getString(path);
    }
    public void SetReviveLocation(String path,Location location){
        config.set(path,location);
        save();
    }
    public void UpdatePlayersData(String path, Player player, Map<UUID,Double> playerData){
        for(Map.Entry<UUID,Double> entry: playerData.entrySet()){
            if(Objects.equals(player.getUniqueId(),entry.getKey())){
                config.set(path+ "." + entry.getKey(),entry.getValue());
            }
        }
        save();
    }
    public void SetPlayersData(String path, Map<UUID,Double> playerData){
        for(Map.Entry<UUID,Double> entry: playerData.entrySet()){
            config.set(path+ "." + entry.getKey(),entry.getValue());
        }
        save();
    }
    public Map<UUID,Double> GetPlayerData(String path){
        Map<UUID,Double> result = new HashMap<>();
        if(config.getConfigurationSection(path)!=null){
            config.getConfigurationSection(path).getKeys(false)
                    .forEach(key ->{
                        double content = config.getDouble(path + key);
                            result.put(UUID.fromString(key), content);
                    });
        }
        return result;
    }
    public Location GetReviveLocation(String path){
        return config.getLocation(path);
    }
    public void save(){
        try{
            config.save(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
