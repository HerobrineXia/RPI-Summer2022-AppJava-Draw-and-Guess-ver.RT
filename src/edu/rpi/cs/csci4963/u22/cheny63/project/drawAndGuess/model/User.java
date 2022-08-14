package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.util.LinkedList;
public class User{
	
	public Status status;
	private String username;
	private int score;
	/**
	 * 
	 * @param name username
	 * @param s represent role
	 */
	public User(String name) {
		username = name;

		status = Status.Guesser;

		score = 0;
		
	}
	
	public Status getRole(){
		return status;
	}
	public String getUsername() {
		return username;
	}
	
	public void changeToGuesser() {
		status = Status.Guesser;
	}
	public void changeToPainter() {
		status = Status.Painter;
	}
	public void changeToWinner() {
		status = Status.Winner;
	}
	
	public void addscore(int n) {
		score+=n;
	}
	public int getScore() {
		return score;
	}
}
