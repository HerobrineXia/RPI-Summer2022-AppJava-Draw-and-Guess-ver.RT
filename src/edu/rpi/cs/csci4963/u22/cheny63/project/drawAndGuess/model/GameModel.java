package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.util.LinkedList;
import java.util.Timer;
import java.util.logging.Logger;

public class GameModel{
	private LinkedList<UserServer> userList;
	private String secretWord;
	private WordDictionary dictionary;
	public GameStatus gameStatus;
	public Timer timer;
	public Logger log;
	// grid
	public GameModel(Logger log) {
		this.log = log;
		userList = new LinkedList<UserServer>();
		// grid
		gameStatus = GameStatus.INIT;
		timer = new Timer();		
	}

	public void readGraph(String filename){
		dictionary = new WordDictionary(filename);
	}

	public void addUser(String name, int id){
        userList.add(new UserServer(name, id));
    }

    public void removeUser(int id){
        for(UserServer user: userList){
            if(user.getId() == id){
                userList.remove(user);
                break;
            }
        }
    }
}
