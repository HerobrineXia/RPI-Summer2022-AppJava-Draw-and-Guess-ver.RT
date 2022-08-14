package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

public class UserServer extends User{
	private PlayerStatus status;
	private boolean guessCorrect;

	public UserServer(String name, int id) {
		this(name, id, 0);
	}

	public UserServer(String name, int id, int score) {
		super(name, id, score);
		status = PlayerStatus.Guesser;	
	}
	
	public PlayerStatus getStatus(){
		return status;
	}
	
	public void changeStatus(PlayerStatus status) {
		this.status = status;
	}

	public void setGuessSuccess(){
		guessCorrect = true;
	}

	public boolean getGuessSuccess(){
		return guessCorrect;
	}

	@Override
	public void initialize(){
        super.initialize();
		newRound();
    }

	public void newRound(){
		status = PlayerStatus.Guesser;
		guessCorrect = false;
	}


}
