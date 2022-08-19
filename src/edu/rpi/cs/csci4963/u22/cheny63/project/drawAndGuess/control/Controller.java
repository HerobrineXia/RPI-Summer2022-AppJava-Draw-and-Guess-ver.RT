package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;
import java.awt.Color;

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
    private Timer timer;
    
    // Game Network
    private Thread network;
    private Server server;
    private Client client;
    private boolean isServer;
    private int myId;
    private String myName;
    private boolean needTimer;

    // Logger
    private Logger log;

    public Controller() throws Exception{
        // Create logger
        log = Logger.getLogger("DrawAndGuess");
        log.setLevel(Level.SEVERE);
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

    public boolean isGameStart(){
        return model.getStatus() != GameStatus.END && model.getStatus() != GameStatus.INIT && model.getStatus() != GameStatus.WAITING;
    }

    public String getDrawerName(){
        return model.getPlayerName(model.getDrawerId());
    }

    public void onStartServer(String name, int port, String filePath) throws IOException{
        config.setName(name);
        config.setFilePath(filePath);
        config.setPort(port);
        timer = new Timer();
        model = new ServerModel();
        ((ServerModel)model).readGraph(filePath);
        startServer(port);
        myName = name;
    }

    public void onClientStart(String name, String address, int port){
        config.setName(name);
        config.setAddress(address);
        config.setPort(port);
        timer = new Timer();
        model = new ClientModel();
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

    public String getSecret(){
        return model.getSecret();
    }

    public String getSecretHint(){
        return model.getSecretHint();
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

    public void onBoardClear(){
        if(isServer){
            sendMessageToAll(protocol.eventCleanBoard(0));
        }else{
            client.send(protocol.eventCleanBoard(1));
        }
    }

    public void onBoardClearReceive(int flag){
        if(flag == 1){
            if(isServer){
                onBoardClear();
            }
        }else{
            window.clear();
        }
    }

    public void onBoardDraw(int x, int y, Color color){
        if(isServer){
            protocol.process(protocol.dataDrawServer(x, y, color, myId));
        }else{
            client.send(protocol.dataDrawServer(x, y, color, myId));
        }
    }

    public void onBoardReceiveServer(int x, int y, Color color, int drawerId){
        if(isServer){
            sendMessageToAll(protocol.dataDrawClient(x, y, color, drawerId));
        }
    }

    protected void onBoardReceiveClient(int x, int y, Color color, int drawerId){
        if(myId != model.getDrawerId()){
            window.setEntryColor(x, y, color);
        }
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
                model.addUser(user.getName(), user.getId(), user.getScore());
            }
            model.setDrawerId(currentDrawerId);
            model.setStatus(status);
        }
    }

    protected void onPlayerLeaveServer(int id){
        if(isServer){
            sendMessageToAll(protocol.userLeftEvent(id));
            if(((ServerModel)model).isRoundEnd()){
                sendMessageToAll(protocol.eventRoundEnd());
            }
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

    public boolean onStartGameServer(){
        if(isServer){
            if(model.getUserList().size() > 1){
                sendMessageToAll(protocol.eventStartGame());
                sendMessageToAll(protocol.newRound(model.getDrawerId()));
                return true;
            }else{
                addChat("Server", "You need to have at least two players to start the game!");
            }
        }
        return false;
    }

    public void onStartGameClient(){
        if(isServer){
            ((ServerModel)model).startGame();
        }else{
            model.startGame();
        }
        needTimer = false;
        startTimer();
    }

    public void onNewRound(int drawerId){
        window.clear();
        addChat("Server", "Round Start!");
        if(isServer){
            ((ServerModel) model).startRound();
            sendMessageToAll(protocol.sendSecretWord(getSecret(), getSecretHint()));
        }else{
            model.startRound();
            model.setDrawerId(drawerId);
            model.setStatus(GameStatus.PROCESSING);
        }
        if(myId == drawerId){
            window.activate();
            addChat("Server", "You have 90 seconds to draw!");
        }else{
            window.deactivate();
            addChat("Server", "You have 90 seconds to guess!");
        }
        needTimer = true;
    }

    private void startTimer(){
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                runTimer();
            }
        }, 1000, 1000);
    }

    private void runTimer(){
        if(needTimer){
            int remainTime = model.reduceTime();
            window.timerUpdate(remainTime);
            if(remainTime <= 0){
                if(isServer){
                    if(model.getStatus() == GameStatus.PROCESSING){
                        sendMessageToAll(protocol.eventRoundEnd());
                    }else if(model.getStatus() == GameStatus.PROCESSING_WAIT){
                        sendMessageToAll(protocol.newRound(model.getDrawerId()));
                    }
                }
                needTimer = false;
            }
        }
	}

    protected void onSecretWordReceive(String word, String category){
        if(myId == model.getDrawerId()){
            addChat("Server", "You are the drawer, the secret word is %s!".formatted(word));
        }else{
            model.setSecret(word);
            model.setSecretHint(category);
            addChat("Server", "You are the guesser, the secret word is a kind of %s!".formatted(category));
        }
        window.setPrompt(word, category);
    }

    protected void onPlayerReceiveMessageClient(int id, String message){
        addChat(model.getPlayerName(id), message);
    }

    protected void onPlayerReceiveMessageServer(int id, String message){
        if(isServer){
            if(model.getStatus() == GameStatus.PROCESSING){
                int result = ((ServerModel) model).guessWord(id, message);
                if(result == 0){
                    sendMessageToAll(protocol.serverSentMessageEvent(id, message));
                }else if(result == 1){
                    server.sendMessage(protocol.messagePack("You have already guessed out the word!"), id);
                }else if(result == 2){
                    sendMessageToAll(protocol.userScorePack(((ServerModel)model).decrementPoint(), id));
                    if(((ServerModel)model).isRoundEnd()){
                        sendMessageToAll(protocol.userScorePack(((ServerModel)model).getDrawerScore(), model.getDrawerId()));
                        sendMessageToAll(protocol.eventRoundEnd());
                    }
                }
            }else{
                sendMessageToAll(protocol.serverSentMessageEvent(id, message));
            }
        }
    }

    protected void onRoundEnd(){
        addChat("Server", "Round End!");
        addChat("Server", "The secret word is %s!".formatted(model.getSecret()));
        StringBuilder scoreboard = new StringBuilder();
        for(User user: model.getUserList()){
            scoreboard.append(user.getName()).append(": ").append(Integer.toString(user.getScore())).append("\n");
        }
        addChat("Current Score Board", scoreboard.toString().strip());
        addChat("Server", "Next round will start in 10 seconds!");
        if(isServer){
            ((ServerModel)model).endRound();
        }else{
            model.setStatus(GameStatus.PROCESSING_WAIT);
        }
        model.startWait();
        needTimer = true;
    }

    protected void onUserScoreReceive(int id, int score){
        model.addScore(id, score);
        if(id != model.getDrawerId()){
            addChat("System", "%s has guessed out the right word and earn %d point!".formatted(model.getPlayerName(id),score));
        }else{
            addChat("System", "Drawer %s has earned %d point!".formatted(model.getPlayerName(id),score));
        }
    }

    protected void onMessageReceive(String message){
        addChat("System", message);
    }

    private void sendMessageToAll(String message){
        if(isServer){
            server.sendMessageToAll(message);
            protocol.process(message);
        }
    }

    private void addChat(String name, String message){
        model.addChat(name, message);
        window.updateStats();
    }

    private void startServer(int port){
        server = new Server(port, log, this);
        network = new Thread(server);
        isServer = true;
        network.start();
    }

    private void startClient(String address, int port){
        client = new Client(address, port, log, this);
        network = new Thread(client);
        isServer = false;
        network.start();
    }
}
