package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

public class UserServer extends User{
	public PlayerStatus status;
	public UserServer(String name) {
		super(name);
		status = PlayerStatus.Guesser;		
	}
	
	public PlayerStatus getStatus(){
		return status;
	}
	
	public void changeStatus(PlayerStatus status) {
		this.status = status;
	}
}
