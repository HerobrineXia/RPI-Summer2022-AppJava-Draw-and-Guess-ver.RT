package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.util.LinkedList;

public class ClientModel {
    protected LinkedList<String> chatHistory;
    protected LinkedList<User> userList;
    protected int currentDrawerId;
    
    public ClientModel(){
        chatHistory = new LinkedList<>();
        userList = new LinkedList<>();
        currentDrawerId = -1;
    }

    public void addUser(User user){
        userList.add(user);
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

    public int getDraerId(){
        return currentDrawerId;
    }
}
