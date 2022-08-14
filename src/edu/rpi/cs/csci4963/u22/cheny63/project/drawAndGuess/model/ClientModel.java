package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.util.LinkedList;

public class ClientModel {
    private LinkedList<String> chatHistory;
    private LinkedList<User> userList;
    
    public ClientModel(){
        chatHistory = new LinkedList<>();
        userList = new LinkedList<>();
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
}
