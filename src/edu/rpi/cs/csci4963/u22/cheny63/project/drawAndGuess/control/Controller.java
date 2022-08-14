package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.Room;

public class Controller{
    // Game Object
    private Config config;
    private Protocol protocol;
    private Room model;
    // Logger
    private Logger log;

    public Controller(){
        // Create logger
        log = Logger.getLogger("DrawAndGuess");
        StreamHandler handler;
		try {
			handler = new FileHandler();
		}
		catch (Exception e) {
			log.warning(String.format("Unable to create logger file handler: %s", e.getMessage()));
			throw e;
		} 
		handler.setFormatter(new SimpleFormatter());
		log.addHandler(handler);

        // Create game object
        config = new Config(log);
        model = new Room();
        protocol = new Protocol();
        window = new GameWindow(this, log);
    }
}
