package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.ClientModel;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.ServerModel;

public class Controller{
    // Game Object
    private Config config;
    private Protocol protocol;
    private ClientModel model;
    
    // Game Network
    private Thread network;
    private Server server;
    private Client client;
    private boolean isServer;
    private int currentId;
    private int myId;
    private String myName;

    // Logger
    private Logger log;

    public Controller() throws Exception{
        // Create logger
        log = Logger.getLogger("DrawAndGuess");
        StreamHandler handler;
		try {
			handler = new FileHandler();
		}catch (Exception e) {
			log.warning(String.format("Unable to create logger file handler: %s", e.getMessage()));
            throw e;
		} 
		handler.setFormatter(new SimpleFormatter());
		log.addHandler(handler);

        // Create game object
        config = new Config(log);
        protocol = new Protocol(this);
    	// DrawAndGuessGUI.main(new String[0]);

        // model = new ClientModel(log);
        // window = new GameWindow(this, log);
    }

    public void onStartServer(String name, int port){
        config.setName(name);
        startServer(port);
        onPlayerJoin(name);
    }

    public void onClientStart(String name, String address, int port){
        config.setName(name);
        startClient(address, port);
        onPlayerJoin(name);
    }

    public void onPlayerJoin(String name){
        addPlayer(name, currentId);
        ++currentId;
    }

    public void onPlayerLeave(int id){
        removePlayer(id);
    }

    public void removePlayer(int id){
        model.removeUser(id);
    }

    public void addPlayer(String name, int id){
        model.addUser(name, id);
    }

    public void startGame(){
        ((ServerModel)model).startGame();
    }

    public String getNameConfig(){
        return config.getName();
    }

    public void onClose(){
        network.interrupt();
        try{
            network.join();
        }catch(InterruptedException e){
            log.warning("Failed to interrupt network thread");
        }
        config.save();
    }

    private void startServer(int port){
        server = new Server(port, log);
        network = new Thread(server);
        isServer = true;
        network.start();
        model = new ServerModel(log);
        currentId = 0;
    }

    private void startClient(String address, int port){
        model = new ClientModel(log);
        client = new Client(address, port, log);
        network = new Thread(client);
        isServer = false;
        network.start();
    }
}
