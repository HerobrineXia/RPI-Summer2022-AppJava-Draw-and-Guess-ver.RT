package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.util.LinkedList;
import java.util.logging.Logger;

public class ClientModel {
    protected LinkedList<String> chatHistory;
    protected LinkedList<User> userList;
    protected int currentDrawerId;
    protected Logger log;
    protected GameStatus gameStatus;
    
    public ClientModel(Logger log){
        this.log = log;
        chatHistory = new LinkedList<>();
        userList = new LinkedList<>();
        currentDrawerId = -1;
        gameStatus = GameStatus.INIT;
    }

    public void addUser(String name, int id){
        userList.add(new User(name, id));
    }

    public void removeUser(int id){
        for(User user: userList){
            if(user.getId() == id){
                userList.remove(user);
                break;
            }
        }
    }

    public void addChat(String message){
        chatHistory.add(message);
    }

    public void setDrawerId(int id){
        currentDrawerId = id;
    }

    public int getDrawerId(){
        return currentDrawerId;
    }

    public String getPlayerName(int id){
        for(User user: userList){
            if(user.id == id){
                return user.name;
            }
        }
        return null;
    }

    public GameStatus getStatus(){
        return gameStatus;
    }
}
