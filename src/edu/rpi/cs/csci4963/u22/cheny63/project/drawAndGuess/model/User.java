package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.util.LinkedList;
public class User implements Status {
	
	public Role status;
	private String username;
	private int score;
	/**
	 * 
	 * @param name username
	 * @param s represent role
	 */
	public User(String name) {
		username = name;

		status = Role.Guesser;

		score = 0;
		
	}
	
	public Role getRole(){
		return status;
	}
	public String getUsername() {
		return username;
	}
	
	public void changeToGuesser() {
		status = Role.Guesser;
	}
	public void changeToPainter() {
		status = Role.Painter;
	}
	public void changeToWinner() {
		status = Role.Winner;
	}
	
	public void addscore(int n) {
		score+=n;
	}
	public int getScore() {
		return score;
	}
}
