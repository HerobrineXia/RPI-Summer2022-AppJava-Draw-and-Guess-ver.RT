package main.java.model;

import java.util.*;


public class User  {
	
	LinkedList<String> messages = new LinkedList<String>();
	
	public static enum Role {Painter,Winner,Guesser};
	private Role status;
	private String username;
	
	public User(String name, int s) {
		username = name;
		if(s == 0) {
			status = Role.Guesser;
		}
		else {
			status = Role.Painter;
		}
	}
	
	public Role getRole(){
		return status;
	}
	public String getUsername() {
		return username;
	}
	
	public void addNewMessage(String message) {
		messages.addFirst(message);
	}
	public void ChangeToGuesser() {
		status = Role.Guesser;
	}
	public void ChangeToPainter() {
		status = Role.Painter;
	}
	public void ChangeToWinner() {
		status = Role.Winner;
	}
}

