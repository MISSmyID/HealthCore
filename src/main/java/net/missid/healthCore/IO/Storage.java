package net.missid.healthCore.IO;

import net.missid.healthCore.HealthCore;
import net.missid.healthCore.Main;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;
import java.util.*;

public class Storage {
private File file;
private YamlConfiguration config;
    private Storage(){
    }
    private final static Storage instance = new Storage();
    public static Storage getInstance(){
        return instance;
    }
    public void load(){
        file = new File(HealthCore.getInstance().getDataFolder(), "storage.yml");
        if(!file.exists()) HealthCore.getInstance().saveResource("storage.yml", false);
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try{
            config.load(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Main.reviveLocation = GetReviveLocation("revivelocation");
        Main.enteredPlayers = GetPlayersData("playersdata.");
        Main.frozenPlayers = GetFrozenList("frozenlist");
        Main.purchasedHeartsMap = GetPurchasedHearts("purchasedhearts");
    }

    //#########################################################################################
    //FrozenPlayersSection
    public void SetFrozenList(String path, List<UUID> frozenList){
        List<String> temp = new ArrayList<>();
        for(UUID id :frozenList){
            temp.add(id.toString());
        }
        config.set(path,temp);
        save();
    }
    public List<UUID> GetFrozenList(String path){
        List<String> list = config.getStringList(path);
        List<UUID>result = new ArrayList<>();
        for(String id :list){
            result.add(UUID.fromString(id));
        }
        return result;
    }


    //#########################################################################################
    //PlayerDataSection
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
    public Map<UUID,Double> GetPlayersData(String path){
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
    //#########################################################################################
    //PlayerPurchaseSection
    public void SetPurchasedHearts(String path, Map<UUID,Double> playerData){
        for(Map.Entry<UUID,Double> entry: playerData.entrySet()){
            config.set(path+ "." + entry.getKey(),entry.getValue());
        }
        save();
    }
    public Map<UUID,Double> GetPurchasedHearts(String path){
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
    //#########################################################################################
    //ReviveLocationSection
    public void SetReviveLocation(String path,Location location){
        config.set(path,location);
        save();
    }
    public Location GetReviveLocation(String path){
        return config.getLocation(path);
    }
    //#########################################################################################
    //DefaultSection
    public void save(){
        try{
            config.save(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
