package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.io.IOException;
import java.util.logging.Logger;
public class ServerModel extends ClientModel{
	// Data
	private WordDictionary dictionary;

	// Game Data
	private int remainPoint;

	public ServerModel() {
		super();
		currentDrawerId = 0;
	}

	public void readGraph(String filename) throws IOException{
		if(gameStatus == GameStatus.INIT){
			dictionary = new WordDictionary(filename);
		}
	}

	@Override
	public void addUser(String name, int id){
		UserServer user = new UserServer(name, id);
		if(gameStatus == GameStatus.PROCESSING){
			user.changeStatus(PlayerStatus.Waiter);
		}
        userList.add(user);
    }
	
	public void startGame(){
		if(gameStatus == GameStatus.INIT || gameStatus == GameStatus.END){
			intializeGame();
		}
	}

	public void intializeGame(){
		dictionary.resetWordList();
		for(User user: userList){
			((UserServer)user).initialize();
		}
		currentDrawerId = 0;
		gameStatus = GameStatus.WAITING;
	}

	public void startRound(){
		if(gameStatus == GameStatus.WAITING || gameStatus == GameStatus.PROCESSING_WAIT){
			super.startRound();
			secretWord = dictionary.getRandomWord();
			secretWordHint = dictionary.getCategory(secretWord);
			gameStatus = GameStatus.PROCESSING;
			for(User user: userList){
				((UserServer)user).newRound();
			}
			remainPoint = userList.size() - 1;
		}
	}

	public boolean isRoundEnd(){
		int guesser = userList.size() - 1;
		boolean hasDrawer = false;
		for(User user: userList){
			if(((UserServer)user).getGuessSuccess()){
				--guesser;
			}else if(user.id == currentDrawerId){
				hasDrawer = true;
			}
		}
		return guesser == 0 || remainTime == 0 || !hasDrawer;
	}

	public void endRound(){
		if(gameStatus == GameStatus.PROCESSING){
			++currentDrawerId;
			if(currentDrawerId > userList.size()){
				currentDrawerId = 0;
			}
			gameStatus = GameStatus.PROCESSING_WAIT;
		}
	}

	public int guessWord(int id, String word){
		if(gameStatus == GameStatus.PROCESSING){
			if(currentDrawerId != id){
				UserServer user = getUser(id);
				if(!user.getGuessSuccess()){
					if(equalSecret(word)){
						user.setGuessSuccess();
						return 2;
					}else{
						return 0;
					}
				}else{
					return 1;
				}
			}else{
				return 0;
			}
		}
		return -1;
	}

	public int getDrawerScore(){
		if(remainPoint == 0){
			return 0;
		}else{
			return userList.size() - 1 - remainPoint;
		}
	}

	public int decrementPoint(){
		return remainPoint--;
	}

	private UserServer getUser(int id){
		for(User user: userList){
			if(user.id == id){
				return (UserServer)user;
			}
		}
		return null;
	}

	private boolean equalSecret(String word){
		return secretWord.toLowerCase().equals(word.toLowerCase());
	}
}
