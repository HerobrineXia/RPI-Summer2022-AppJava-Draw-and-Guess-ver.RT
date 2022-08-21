package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.util.LinkedList;
/**
* Client Room Model for storing the game info 
* @author Kevin Xia
* @version 1.0
*/
public class ClientModel {
    // Room info
    protected LinkedList<String> chatHistory;
    protected LinkedList<User> userList;
    protected int currentDrawerId;
    protected GameStatus gameStatus;
    protected String secretWord;
    protected String secretWordHint;
    protected int remainTime;
    
    /**
     * Constructor of client model, initializing all the variable for the room info
     */
    public ClientModel(){
        chatHistory = new LinkedList<>();
        userList = new LinkedList<>();
        currentDrawerId = -1;
        gameStatus = GameStatus.INIT;
    }

    /**
     * Add user into the model with name and id
     * @param name the name of the user
     * @param id the id of the user
     */
    public void addUser(String name, int id){
        userList.add(new User(name, id));
    }

    /**
     * Add user into the model with name, id and score
     * @param name the name of the user
     * @param id the id of the user
     * @param score the score of the user
     */
    public void addUser(String name, int id, int score){
        userList.add(new User(name, id, score));
    }

    /**
     * Remove the user from the model with given id
     * @param id the id of the user
     */
    public void removeUser(int id){
        for(User user: userList){
            if(user.getId() == id){
                userList.remove(user);
                break;
            }
        }
    }

    /**
     * Get the remaining time for the game round
     * @return the remain time in second
     */
    public int getRemainTime(){
        return remainTime;
    }

    /**
     * Reduce the remain time by 1 second until 0 second and return the new remain time
     * @return the new remain time
     */
	public int reduceTime(){
		if(remainTime > 0){
			--remainTime;
		}
		return remainTime;
	}

    /**
     * Set the waiting timer between round switch
     */
    public void startWait(){
        if(gameStatus == GameStatus.PROCESSING_WAIT){
            remainTime = 10;
        }
    }

    /**
     * Set the round timer for the new round
     */
    public void startRound(){
        if(gameStatus == GameStatus.INIT || gameStatus == GameStatus.WAITING || gameStatus == GameStatus.PROCESSING_WAIT){
            remainTime = 90;
        }
    }

    /**
     * Prepare the start game user variable 
     */
    public void startGame(){
        for(User user: userList){
            user.initialize();
        }
    }

    /**
     * Add the chat message into the chat history
     * @param name the name of the message sender
     * @param message the message
     */
    public void addChat(String name, String message){
        chatHistory.add(name);
        chatHistory.add(message);
    }

    /**
     * Add the score to a certain player
     * @param id the id of the player
     * @param score the score to be added
     */
    public void addScore(int id, int score){
        for(User user: userList){
            if(user.id == id){
                user.addScore(score);
                return;
            }
        }
    }

    /**
     * Set the drawer id of the round
     * @param id the new drawer id
     */
    public void setDrawerId(int id){
        currentDrawerId = id;
    }

    /**
     * Get the drawer id of the round
     * @return current drawer id
     */
    public int getDrawerId(){
        return currentDrawerId;
    }

    /**
     * Get the player name with given player id
     * @param id the player id
     * @return the player name, will be null if player is not recorded in the model
     */
    public String getPlayerName(int id){
        for(User user: userList){
            if(user.id == id){
                return user.name;
            }
        }
        return null;
    }

    /**
     * Set the secret word of the round 
     * @param secret the new secret word
     */
    public void setSecret(String secret){
        this.secretWord = secret;
    }

    /**
     * Get the secret word of the round
     * @return the secret word for the round
     */
	public String getSecret(){
		return secretWord;
	}

    /**
     * Get the secret word hint of the game
     * @return the category of the secret word
     */
    public String getSecretHint(){
        return secretWordHint;
    }

    /**
     * Set the secret word hint of the game
     * @param hint the category of the secret word
     */
    public void setSecretHint(String hint){
        this.secretWordHint = hint;
    }

    /**
     * Get the chat history in the model
     * @return the linked list of the chat history,
     *         starting from 0, even index represents the sender name,
     *                          odd index represents the message
     */
    public LinkedList<String> getChat(){
        return new LinkedList<>(chatHistory);
    }

    /**
     * Set the game status of the model
     * @param status the new game status
     */
    public void setStatus(GameStatus status){
        gameStatus = status;
    }

    /**
     * Get current game status of the model
     * @return the game status
     */
    public GameStatus getStatus(){
        return gameStatus;
    }

    /**
     * Get the user list in term of the linked list
     * @return the user object in the model
     */
    public LinkedList<User> getUserList(){
        return new LinkedList<>(userList);
    }
}
