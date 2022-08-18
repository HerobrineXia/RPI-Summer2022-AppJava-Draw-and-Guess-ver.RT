package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI.DrawAndGuessGUI;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.UI.HoldConnection;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.ClientModel;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.GameStatus;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.ServerModel;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.User;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.tools.StringUtil;

public class Controller{
    // Game Object
    private Config config;
    private Protocol protocol;
    private ClientModel model;
    private DrawAndGuessGUI window;
    private HoldConnection holdWindow;
    
    // Game Network
    private Thread network;
    private Server server;
    private Client client;
    private boolean isServer;
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
        window = new DrawAndGuessGUI(this);
    }

    public String getDrawerName(){
        return model.getPlayerName(model.getDrawerId());
    }

    public void onStartServer(String name, int port, String filePath) throws IOException{
        config.setName(name);
        config.setFilePath(filePath);
        config.setPort(port);
        startServer(port);
        ((ServerModel)model).readGraph(filePath);
        myName = name;
    }

    public void onClientStart(String name, String address, int port){
        config.setName(name);
        config.setAddress(address);
        config.setPort(port);
        startClient(address, port);
        myName = name;
    }

    protected void onConnectionWait(){
        holdWindow = new HoldConnection();
    }

    protected void onConnectionWaitEnd(){
        if(holdWindow != null){
            holdWindow.close();
        }
    }

    protected void onConnectionFailed(String message, String title){
        onConnectionWaitEnd();
        window.interrupt(message, title);
        window.returnMainMenu();
    }

    protected void onConnectionSuccess(){
        // Send the player name to server
        if(isServer){
            protocol.process(protocol.userJoinServerEvent("localhost", myName));
        }else{
            client.send(protocol.userJoinServerEvent(client.getAddress(), myName));
        }
        window.startGame();
    }

    public void processCommand(String command){
        protocol.process(command);
    }

    public String getNameConfig(){
        return config.getName();
    }

    public String getFileConfig(){
        return config.getFilePath();
    }

    public String getPort(){
        return Integer.toString(config.getPort());
    }

    public String getAddress(){
        return config.getAddress();
    }

    public LinkedList<String> getChat(){
        return model.getChat();
    }

    public boolean isServer(){
        return isServer;
    }

    public void onClose(){
        log.info("Closing the application...");
        config.save();
        if(network != null){
            if(isServer){
                server.closeServer();
            }else{
                client.closeClient();
            }
            try{
                network.join();
            }catch(InterruptedException e){
                log.warning("Failed to interrupt network thread");
            }
        }
        System.exit(0);
    }

    protected void onIdReturn(int id){
        if(!isServer){
            myId = id;
        }
    }

    protected void onPlayerJoinServer(String name, String address){
        if(isServer){
            int id = server.getId(address);
            if(!address.equals("localhost")){
                server.sendMessage(protocol.serverReturnIdEvent(id), id);
                server.sendMessage(protocol.userDataPack(model.getUserList(), model.getDrawerId(), model.getStatus()), id);
            }else{
                myId = 0;
                id = 0;
                addChat("Room IP", StringUtil.getInetAddress());
            }
            sendMessageToAll(protocol.userJoinClientEvent(id, name));
        }
    }

    protected void onPlayerReceiveDatapack(LinkedList<User> users, int currentDrawerId, GameStatus status){
        if(!isServer){
            for(User user: users){
                System.out.println("%s,%d,%d".formatted(user.getName(),user.getId(),user.getScore()));
                model.addUser(user.getName(), user.getId(), user.getScore());
            }
            System.out.println("%d,%s".formatted(currentDrawerId,status.toString()));
            model.setDrawerId(currentDrawerId);
            model.setStatus(status);
        }
    }

    protected void onPlayerLeaveServer(int id){
        if(isServer){
            sendMessageToAll(protocol.userLeftEvent(id));
        }
    }

    protected void onPlayerJoinClient(String name, int id){
        model.addUser(name, id);
        addChat("System", "Welcome %s to the game!".formatted(name));
    }

    protected void onPlayerLeaveClient(int id){
        addChat("System", "Player %s left the game!".formatted(model.getPlayerName(id)));
        model.removeUser(id);
    }

    public void onPlayerSentMessage(String message){
        if(isServer){
            onPlayerReceiveMessageServer(myId, message);
        }else{
            client.send(protocol.userSentMessageEvent(myId, message));
        }
    }

    public void onStartGameServer(){
        if(isServer){
            sendMessageToAll(protocol.eventStartGame());
            sendMessageToAll(protocol.newRound(model.getDrawerId()));
        }
    }

    public void onStartGameClient(){
        if(isServer){
            ((ServerModel)model).startGame();
        }else{
            model.startGame();
        }
    }

    public void onNewRound(int drawerId){
        if(isServer){
            ((ServerModel) model).startRound();
        }else{
            model.setDrawerId(drawerId);
            model.setStatus(GameStatus.PROCESSING);
        }
        if(myId == drawerId){
            window.activate();
        }else{
            window.deactivate();
        }
    }

    protected void onPlayerReceiveMessageClient(int id, String message){
        addChat(model.getPlayerName(id), message);
    }

    protected void onPlayerReceiveMessageServer(int id, String message){
        if(isServer){
            if(model.getStatus() == GameStatus.PROCESSING){
                int result = ((ServerModel) model).guessWord(id, message);
                if(result == 1){
                    server.sendMessage(protocol., id);
                }
            }else{
                sendMessageToAll(protocol.serverSentMessageEvent(id, message));
            }
        }
    }

    private void sendMessageToAll(String message){
        if(isServer){
            server.sendMessageToAll(message);
            protocol.process(message);
        }
    }

    private void addChat(String name, String message){
        model.addChat(name, message);
        window.updateChat();
    }

    private void startServer(int port){
        model = new ServerModel(log);
        server = new Server(port, log, this);
        network = new Thread(server);
        isServer = true;
        network.start();
    }

    private void startClient(String address, int port){
        model = new ClientModel(log);
        client = new Client(address, port, log, this);
        network = new Thread(client);
        isServer = false;
        network.start();
    }
}
