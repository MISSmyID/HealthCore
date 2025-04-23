package net.missid.healthCore.IO;

import net.missid.healthCore.HealthCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {
    private File file;
    private YamlConfiguration config;
    private Config(){}
    private static final Config instance = new Config();
    public static Config getInstance(){
        return instance;
    }
    public void load(){
        file = new File(HealthCore.getInstance().getDataFolder(), "config.yml");
        if(!file.exists())HealthCore.getInstance().saveResource("config.yml", false);
        config = new YamlConfiguration();
        config.options().parseComments(true);
        try{
            config.load(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public Integer getDefaultHeartsLevel(){
        return config.getInt("default_hearts_level");
    }
    public Double getScale(){
        return config.getDouble("scale");
    }
    public void save(){
        try{
            config.save(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
