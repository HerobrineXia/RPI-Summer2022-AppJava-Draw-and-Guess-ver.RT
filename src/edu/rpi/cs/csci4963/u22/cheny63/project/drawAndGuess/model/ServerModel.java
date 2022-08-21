package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.io.IOException;
/**
* Server Room Model for storing the game info 
* @author Kevin Xia
* @version 1.0
*/
public class ServerModel extends ClientModel{
	// Data stored on server side
	private WordDictionary dictionary;

	// Remaining poiint for the round
	private int remainPoint;

	/**
	 * Constructor of server model
	 */
	public ServerModel() {
		super();
		currentDrawerId = 0;
	}

	/**
	 * Read the graph from the file
	 * @param filename the file to read
	 * @throws IOException if failed to read the graph
	 */
	public void readGraph(String filename) throws IOException{
		if(gameStatus == GameStatus.INIT){
			dictionary = new WordDictionary(filename);
		}
	}

	/**
	 * Add the server user object into the model with name and id
	 * @param name the name of the user
	 * @param id the id of the user
	 */
	@Override
	public void addUser(String name, int id){
		UserServer user = new UserServer(name, id);
		if(gameStatus == GameStatus.PROCESSING){
			user.changeStatus(PlayerStatus.Waiter);
		}
        userList.add(user);
    }
	
	/**
	 * Preparing starting the game
	 */
	public void startGame(){
		if(gameStatus == GameStatus.INIT || gameStatus == GameStatus.END){
			intializeGame();
		}
	}

	/**
	 * Initialize the game data
	 */
	public void intializeGame(){
		dictionary.resetWordList();
		for(User user: userList){
			((UserServer)user).initialize();
		}
		currentDrawerId = 0;
		gameStatus = GameStatus.WAITING;
	}

	/**
	 * Start the round and update all the server side information
	 */
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

	/**
	 * Check if the round needed to be end
	 * If there is no point left, which all online users have guessed out the word.
	 * Or the drawer left, which the round needed to be terminated.
	 * @return
	 */
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

	/**
	 * End the round and update all server side information
	 */
	public void endRound(){
		if(gameStatus == GameStatus.PROCESSING){
			++currentDrawerId;
			if(currentDrawerId >= userList.size()){
				currentDrawerId = 0;
			}
			gameStatus = GameStatus.PROCESSING_WAIT;
		}
	}

	/**
	 * Trigger function when the user try to guess the word
	 * @param id the guesser id
	 * @param word the word the guesser guess
	 * @return 0 if word is not match, no need for alternate the message,
	 * 			1 if the user already guess the word, do not allow send the message,
	 * 			2 if the user guess the word, replace the message with a score update event
	 */
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

	/**
	 * Get the drawer score when the round end
	 * @return the score the drawer get
	 */
	public int getDrawerScore(){
		if(remainPoint == 0){
			return 0;
		}else{
			return userList.size() - 1 - remainPoint;
		}
	}

	/**
	 * Return the remain point and decrement the remain point
	 * @return
	 */
	public int decrementPoint(){
		return remainPoint--;
	}

	/**
	 * Get the server user object with given id
	 * @param id the id of the user
	 * @return the server user object,
	 * 			null if no user matched
	 */
	private UserServer getUser(int id){
		for(User user: userList){
			if(user.id == id){
				return (UserServer)user;
			}
		}
		return null;
	}

	/**
	 * Compare the word with the secret word ignoring the letter cases
	 * @param word the word to compare
	 * @return true if words match,
	 * 			false otherwise
	 */
	private boolean equalSecret(String word){
		return secretWord.toLowerCase().equals(word.toLowerCase());
	}
}
