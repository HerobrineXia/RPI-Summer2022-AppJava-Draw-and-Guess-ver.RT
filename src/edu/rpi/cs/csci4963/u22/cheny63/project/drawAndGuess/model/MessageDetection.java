package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

public class MessageDetection {
	String secret;
	public MessageDetection(String s) {
		secret = s.toLowerCase();
	}
	public Boolean guessWord(String message) {
		if(message.toLowerCase() == secret) {
			return true;
		}
		return false;
		
	}
	
	
}
