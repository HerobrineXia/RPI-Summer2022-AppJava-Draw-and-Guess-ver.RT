package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

/**
* User model stores on the server side
* @author Kevin Xia
* @version 1.0
*/
public class UserServer extends User{
	private PlayerStatus status;
	private boolean guessCorrect;

	/**
	 * Constructor of user model with name and id
	 * @param name the name of the user
	 * @param id the id of the user
	 */
	public UserServer(String name, int id) {
		this(name, id, 0);
	}

	/**
	 * Constructor of user model with name, id and score
	 * @param name the name of the user
	 * @param id the id of the user
	 * @param score the score of the user
	 */
	public UserServer(String name, int id, int score) {
		super(name, id, score);
		status = PlayerStatus.Guesser;	
	}
	
	/**
	 * Get the player status
	 * @return the player status
	 */
	public PlayerStatus getStatus(){
		return status;
	}
	
	/**
	 * Change the player status 
	 * @param status the status to be set
	 */
	public void changeStatus(PlayerStatus status) {
		this.status = status;
	}

	/**
	 * Call when user guess the right word and store the information
	 */
	public void setGuessSuccess(){
		guessCorrect = true;
	}

	/**
	 * Check if user guess out the word this round
	 * @return true if the user did,
	 * 			false otherwise
	 */
	public boolean getGuessSuccess(){
		return guessCorrect;
	}

	/** 
	 * Initialize method to be called when the game start
	 */
	@Override
	public void initialize(){
        super.initialize();
		newRound();
    }

	/**
	 * Method to be called when the new round start
	 */
	public void newRound(){
		status = PlayerStatus.Guesser;
		guessCorrect = false;
	}


}
