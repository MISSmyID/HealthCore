package net.missid.healthCore.IO;

import net.missid.healthCore.HealthCore;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.units.qual.C;

import java.io.File;

public class Config {
    private File file;
    private YamlConfiguration config;
    private int defaultHeartsLevel;
    private double scale;
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
        defaultHeartsLevel = config.getInt("default_hearts_level");
        scale = config.getDouble("scale");
    }
    public Integer getDefaultHeartsLevel(){
        return defaultHeartsLevel;
    }
    public Double getScale(){
        return scale;
    }
    public void save(){
        try{
            config.save(file);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
