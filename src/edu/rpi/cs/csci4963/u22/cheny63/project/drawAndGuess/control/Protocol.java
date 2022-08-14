package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.util.LinkedList;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.PlayerStatus;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.User;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.UserServer;



// format %s#%s#... category,item,...


public class Protocol {
	private Controller controller;
	public static final String SEPARATOR = "#";

	public Protocol(Controller controller){
		this.controller = controller;
	}

	private String[] parseCommand(String command){
        return command.split(SEPARATOR);
    }

	public String transferString(String message){
		return message;
	}

	public String messagePack(int id, String message){
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%s".formatted("DATA",SEPARATOR,"MESSAGE",SEPARATOR,id,SEPARATOR,message));
		return response.toString();
	}

	public String userDataPack(LinkedList<User> users, int num){
		StringBuilder response = new StringBuilder("%s%s%s%s%s".formatted("DATA",SEPARATOR,"MODEL",SEPARATOR,num));
		for(int i = 0; i<num;i++) {
			response.append(new StringBuilder("%s%d%s%s%s%d".formatted(SEPARATOR,users.get(i).getScore(),SEPARATOR,users.get(i).getName(),SEPARATOR,users.get(i).getId())));
		}
		return response.toString();
	}
	public String userLeftEvent(int id) {
		StringBuilder response = new StringBuilder("%s%s%s%s%d".formatted("EVENT",SEPARATOR,"LEFT",SEPARATOR,id));
		return response.toString();
	}
	public String userJoinServerEvent(int id, String name) {
		StringBuilder response = new StringBuilder("%s%s%s%s%s"
				+ "+".formatted("EVENT",SEPARATOR,"JOIN_SERVER",SEPARATOR,name));
		return response.toString();
	}
	public String userJoinClientEvent(int id, String name) {
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%s"
				+ "+".formatted("EVENT",SEPARATOR,"JOIN_CLIENT",SEPARATOR,id,SEPARATOR,name));
		return response.toString();
	}
	
	public String newRound(int id) {
		StringBuilder response = new StringBuilder("%s%s%s%s%d".formatted("EVENT",SEPARATOR,"NEW_ROUND",SEPARATOR,id));
		return response.toString();
	}
	public String userScorePack(int score,int id) {
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%d".formatted("DATA",SEPARATOR,"SCORE",SEPARATOR,id,SEPARATOR,score));
		return response.toString();
	}


	

	public String process(UserServer self, String command){
        StringBuilder response = new StringBuilder();
        command = transferString(command);
        String[] commands = parseCommand(command);
        String keyword = commands[0];
        String secondary = commands[1];

        	if(keyword.equals("EVENT")) {
        		if(secondary.equals("JOIN_SERVER")) {
        			String name = commands[2];
        		}
        		else if(secondary.equals("JOIN_CLIENT")) {
        			int id  =  Integer.parseInt(commands[2]);
        			String name = commands[3];
        		}
        		else if(secondary.equals("SENT")) {
        			int id = Integer.parseInt(commands[2]);
        			String message = commands[3];
        			response = new StringBuilder("%s%s%s%s%d%s%s".formatted("DATA",SEPARATOR,"MESSAGE",SEPARATOR,id,SEPARATOR,message));
        		}
        		else if(secondary.equals( "LEFT")) {
        			int id = Integer.parseInt(commands[2]);
        			
        			
        		}
        		else if(secondary.equals("NEW_ROUND")) {
        			int startId = Integer.parseInt(commands[2]);
        			//controller
        		}
        		else {
        			//invalid
        		}
        	}
        	else if( keyword.equals("DATA")) {
        		if(secondary.equals("SCORE")) {
        			int id = Integer.parseInt(commands[2]);
        			int score = Integer.parseInt(commands[3]);
        		}
        		else if(secondary.equals("MESSAGE")) {
        			int id = Integer.parseInt(commands[2]);
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
