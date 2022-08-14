package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
* Game config loader
* @author Kevin Xia
* @version 1.0
*/
public class Config {
    // Config value
    private Properties configFile;
    private String username;
    private Logger log;

    /**
     * Constructor of game config
     */
    public Config(Logger log){
        this.log = log;
        configFile = new Properties();
        load();
    }

    /**
     * Default config of the game
     */
    public void defaultConfig(){
        username = "Player";
    }

    /**
     * Validate the data and change if needed
     * @return true if data changes and need to update in other component,
     *          false otherwise
     */
    public boolean validateData(){
        Boolean update = false;
        if(username == null){
            username = "Player";
            update = true;
        }
        return update;
    }

    /**
     * Load the config file
     * It will create a new config file from default config if failed to load config file
     */
    public void load(){
        log.info("Loading config file...");
        try {
            // Loading
            FileInputStream file = new FileInputStream("Config.cfg");
            configFile.load(file);
            file.close();
            // Game
            username = configFile.getProperty("user.name");
            // Validate
            log.info("Validating config...");
            if(validateData()){
                log.info("Found invalid config, resetting them to default value");
                save();
            }
            log.info("Finish loading config file");
        }catch(Exception e){
            log.warning("Unable to load config file, using default config");
            defaultConfig();
            save();
        }
    }

    /**
     * Save the config file
     */
    public void save(){
        log.info("Saving config file...");
        try{
            // Game
            configFile.setProperty("user.name", username);
            // Saving
            FileOutputStream file = new FileOutputStream("Config.cfg");
            configFile.store(file, "Config for Draw and Guess");            
            file.close();
            log.info("Finish saving config file");
        }catch(Exception e){
            log.severe("Unable to save config file");
        }
    }

    /**
     * Set the usename
     * @param name the username
     */
    public void setName(String name){
        username = name;
    }

    /**
     * Get the username
     * @return the username
     */
    public String getName(){
        return username;
    }
}
