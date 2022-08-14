package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.util.LinkedList;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.PlayerStatus;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.User;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.UserServer;



// format %s#%s#... category,item,...


public class Protocol {
	public static final String SEPARATOR = "#";

	
	
	public Protocol() {

	}
	private String[] parseCommand(String command) {
        return command.split(SEPARATOR);
    }
	public String transferString(String message) {
		return message;
		
	}
	public StringBuilder messagePack(int u,String message) {
		StringBuilder response = new StringBuilder("%s#%s#%s#%s".formatted("DATA","MESSAGE",u,message));
		return response;
	}
	public StringBuilder userDataPack(LinkedList<User> users,int num ) {
		StringBuilder response = new StringBuilder("%s#%s#%s".formatted("DATA","MODEL",num));
		for(int i = 0; i<num;i++) {
			response.append(new StringBuilder("#%s#%s#%s".formatted(users.get(i).getScore(),users.get(i).getName(),users.get(i).getId())));
		}
		return response;
	}
	public String process(UserServer self, String command) {
		
        StringBuilder response = new StringBuilder();
        StringBuilder m;
        command = transferString(command);
        String[] commands = parseCommand(command);
        String keyword = commands[0];
        String secondary = commands[1];

        	if(keyword.equals("EVENT")) {
        		if(secondary.equals("JOIN")) {
        			
        		}
        		else if(secondary.equals("SENT")) {
        			int index = Integer.parseInt(commands[2]);
        			String message = commands[3];
        			response = messagePack(index,message);
        		}
        		else if(secondary.equals( "LEFT")) {
        			int index = Integer.parseInt(commands[2]);
        			
        		}
        		else if(secondary.equals("NEW_ROUND")) {
        			int index = Integer.parseInt(commands[2]);
        			//controller
        		}
        		else {
        			//invalid
        		}
        	}
        	else if( keyword.equals("DATA")) {
        		if(secondary.equals("SCORE")) {
        			int index = Integer.parseInt(commands[2]);
        			int score = Integer.parseInt(commands[3]);
        		}
        		else if(secondary.equals("MESSAGE")) {
        			int index = Integer.parseInt(commands[2]);
        			String message = commands[3];
        			// controller
        		}
        		else if(secondary.equals("MODEL")) {
        			LinkedList <User> users = new LinkedList <User>(); 
        			for(int i = 3;i<Integer.parseInt(commands[2]);i+=3) {
        				users.addLast(new User(commands[i+1],Integer.parseInt(commands[i+2]),Integer.parseInt(commands[i])));
        			}
        			//controller
        		}
        		else if(secondary.equals("DREW")) {
        			
        		}
        		
        		else {
        			//invalid
        		}
        	}
        	else {
        		//invalid
        	}

        return response.toString();
	}
}
