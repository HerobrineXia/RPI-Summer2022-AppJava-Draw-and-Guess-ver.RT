package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.logging.Logger;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.StringUtil;

/**
* Game config loader
* @author Kevin Xia
* @version 1.0
*/
public class Config {
    // Config value
    private Properties configFile;
    private String username;
    private String filePath;
    private String address;
    private int port;
    private Logger log;

    /**
     * Constructor of game config
     * @param log the logger
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
        filePath = System.getProperty("user.dir");
        address = StringUtil.getInetAddress();
        port = 8180;
    }

    /**
     * Validate the data and change if needed
     * @return true if data changes and need to update in other component,
     *          false otherwise
     */
    public boolean validateData(){
        boolean update = false;
        if(username == null){
            username = "Player";
            update = true;
        }
        File path = new File(filePath);
        if(!path.exists() || !path.isFile() || !path.getName().endsWith(".csv")){
            setFilePath(System.getProperty("user.dir"));
            update = false;
        }
        if(port < 0 || port > 65565){
            port = 8180;
            update = true;
        }
        if(!StringUtil.validAddress(address)){
            StringUtil.getInetAddress();
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
            // Config
            filePath = configFile.getProperty("file.path");
            username = configFile.getProperty("network.username");
            address = configFile.getProperty("network.address");
            port = Integer.parseInt(configFile.getProperty("network.port"));
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
            configFile.setProperty("file.path", filePath);
            configFile.setProperty("network.username", username);
            configFile.setProperty("network.address", address);
            configFile.setProperty("network.port", Integer.toString(port));
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
     * Set the usename of the player
     * @param name the username
     */
    public void setName(String name){
        username = name;
    }

    /**
     * Get the username set in the config
     * @return the username
     */
    public String getName(){
        return username;
    }

    /**
     * Record the dictionary file path selected 
     * @param filePath the word dictionary file path 
     */
    public void setFilePath(String filePath){
        this.filePath = filePath;
    }

    /**
     * Get the dictionary file path
     * @return the file path to the dictionary file
     */
    public String getFilePath(){
        return filePath;
    }

    /**
     * Set the address for the server connected last time
     * @param address the address of the previous server
     */
    public void setAddress(String address){
        this.address = address;
    }

    /**
     * Get the address for the server connected last time
     * @return the address of the previous connected server
     */
    public String getAddress(){
        return address;
    }

    /**
     * Set the port for the server connected last time
     * @param port the port of the previous server
     */
    public void setPort(int port){
        this.port = port;
    }

    /**
     * Get the port for the server connected last time
     * @return the port of the previous server
     */
    public int getPort(){
        return port;
    }
}
