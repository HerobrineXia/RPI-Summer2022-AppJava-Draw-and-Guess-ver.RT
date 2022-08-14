package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model;

import java.util.*;
public class Room {
	LinkedList<User> users;
	//grid
	public Room(String[] names) {
		users = new LinkedList<User>();
		// grid
		
	}
	public void addNewUser(String n) {
		users.addFirst(new User(n));
	}
	
	
}
