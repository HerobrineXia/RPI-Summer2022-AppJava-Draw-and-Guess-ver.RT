package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.net.Socket;
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
	
	public String unicodeEscaped(char ch) {
	      if (ch < 0x10) {
	          return "\\u000" + Integer.toHexString(ch);
	      } else if (ch < 0x100) {
	          return "\\u00" + Integer.toHexString(ch);
	      } else if (ch < 0x1000) {
	          return "\\u0" + Integer.toHexString(ch);
	      }
	      return "\\u" + Integer.toHexString(ch);
	  }
	
	public String stringToUnicode(String message) {
		StringBuilder response = new StringBuilder();
		for(int i=0;i<message.length();i++) {
			if(Character.isAlphabetic(message.charAt(i))) {
				response.append(new StringBuilder(unicodeEscaped(message.charAt(i))));
			}
			else {
				if(message.charAt(i) == ' ') {
					response.append(' ');
				}
				else {
					response.append(message.charAt(i));
				}		
			}
		}
		return response.toString();
	}
	
	public String unicodeToString(String message) {
		String str = message;
		str = str.replace("\\","");
		String[] arr = str.split("u");
		String text = "";
		System.out.println(arr.length);
		for(int i = 0; i < arr.length; i++){
			if(arr[i].length()>4) {
				int hexVal = Integer.parseInt(arr[i].substring(0, 4), 16);
				text += (char)hexVal;
				text+= arr[i].substring(4);
			}
			else {
				if(arr[i].length()<4) {
					text += arr[i];
				}
				else {
					int hexVal = Integer.parseInt(arr[i], 16);
					text += (char)hexVal;
				}
			}
		}
		return text;
	}

	public String messagePack(int id, String message){
		message = stringToUnicode(message);
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%s".formatted("DATA",SEPARATOR,"MESSAGE",SEPARATOR,id,SEPARATOR,message));
		return response.toString();
	}

	public String userDataPack(LinkedList<User> users, int num){
		StringBuilder response = new StringBuilder("%s%s%s%s%s".formatted("DATA",SEPARATOR,"MODEL",SEPARATOR,num));
		for(int i = 0; i<num;i++) {
			response.append(new StringBuilder("%s%d%s%s%s%d".formatted(SEPARATOR,users.get(i).getScore(),SEPARATOR,stringToUnicode(users.get(i).getName()),SEPARATOR,users.get(i).getId())));
		}
		return response.toString();
	}
	public String userLeftEvent(int id) {
		StringBuilder response = new StringBuilder("%s%s%s%s%d".formatted("EVENT",SEPARATOR,"LEFT",SEPARATOR,id));
		return response.toString();
	}
	public String userJoinServerEvent(String address, String name) {
		name = stringToUnicode(name);
		address = stringToUnicode(address);
		StringBuilder response = new StringBuilder("%s%s%s%s%s%s%s".formatted("EVENT",SEPARATOR,"JOIN_SERVER",SEPARATOR,name,SEPARATOR,address));
		return response.toString();
	}
	public String serverReturnIdEvent(int id) {
		StringBuilder response = new StringBuilder("%s%s%s%s%d".formatted("EVENT",SEPARATOR,"RETURN_ID",SEPARATOR,id));
		return response.toString();
	}
	public String userJoinClientEvent(int id, String name) {
		name = stringToUnicode(name);
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%s".formatted("EVENT",SEPARATOR,"JOIN",SEPARATOR,id,SEPARATOR,name));
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
	public String userSentMessageEvent(int id,String message) {
		message = stringToUnicode(message);
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%s".formatted("EVENT",SEPARATOR,"MESSAGE_CLIENT",SEPARATOR,id,SEPARATOR,message));
		return response.toString();
	}
	public String serverSentMessageEvent(int id,String message) {
		message = stringToUnicode(message);
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%s".formatted("EVENT",SEPARATOR,"MESSAGE_SERVER",SEPARATOR,id,SEPARATOR,message));
		return response.toString();
	}

	public String process(String command){
        StringBuilder response = new StringBuilder();
        String[] commands = parseCommand(command);
        if(commands.length<2) {
        	response = new StringBuilder("Invalid Command: command length less than 2");
        	return response.toString();
        }
        String keyword = commands[0];
        String secondary = commands[1];

		if(keyword.equals("EVENT")) {
			if(secondary.equals("JOIN_SERVER")) {
				if(commands.length<4) {
		        	response = new StringBuilder("Invalid Command: EVENT JOIN_SERVER command length less than 4");
		        	return response.toString();
		        }
				String name = commands[2];
				String address = commands[3];
				name = unicodeToString(name);
				address = unicodeToString(address);
				controller.onPlayerJoinServer(name, address);
			}else if(secondary.equals("RETURN_ID")){
				int id = Integer.parseInt(commands[2]);
				if(commands.length<3) {
		        	response = new StringBuilder("Invalid Command: EVENT JOIN_RETURN_ID command length less than 3");
		        	return response.toString();
		        }
				controller.onIdReturn(id);
			}
			else if(secondary.equals("JOIN")) {
				if(commands.length<4) {
		        	response = new StringBuilder("Invalid Command: EVENT JOIN command length less than 4");
		        	return response.toString();
		        }
				int id = Integer.parseInt(commands[2]);
				String name = commands[3];
				name = unicodeToString(name);
				controller.onPlayerJoinClient(name, id);
			}
			else if(secondary.equals("MESSAGE_SERVER")) {
				if(commands.length<4) {
		        	response = new StringBuilder("Invalid Command: EVENT MESSAGE_SERVER command length less than 4");
		        	return response.toString();
		        }
				int id = Integer.parseInt(commands[2]);
				String message = commands[3];
				message = unicodeToString(message);
				controller.onPlayerReceiveMessageClient(id, message);
			}
			else if(secondary.equals("MESSAGE_CLIENT")) {
				if(commands.length<4) {
		        	response = new StringBuilder("Invalid Command: EVENT MESSAGE_CLIENT command length less than 4");
		        	return response.toString();
		        }
				int id = Integer.parseInt(commands[2]);
				String message = commands[3];
				message = unicodeToString(message);
				controller.onPlayerReceiveMessageServer(id, message);
			}
			else if(secondary.equals( "LEFT")) {
				int id = Integer.parseInt(commands[2]);
				if(commands.length<3) {
		        	response = new StringBuilder("Invalid Command: EVENT LEFT command length less than 4");
		        	return response.toString();
		        }
				controller.onPlayerLeaveClient(id);
			}
			else if(secondary.equals("NEW_ROUND")) {
				int painterId = Integer.parseInt(commands[2]);
				if(commands.length<3) {
		        	response = new StringBuilder("Invalid Command: EVENT NEW_ROUND command length less than 4");
		        	return response.toString();
		        }
				//controller
			}
			else {
				//invalid
	        	response = new StringBuilder("Invalid Command: EVENT command not match");
			}
		}
		else if( keyword.equals("DATA")) {
			if(secondary.equals("SCORE")) {
				if(commands.length<4) {
		        	response = new StringBuilder("Invalid Command: DATA SCORE command length less than 4");
		        	return response.toString();
		        }
				int id = Integer.parseInt(commands[2]);
				int score = Integer.parseInt(commands[3]);
			}
			else if(secondary.equals("MESSAGE")) {
				if(commands.length<4) {
		        	response = new StringBuilder("Invalid Command: DATA MESSAGE command length less than 4");
		        	return response.toString();
		        }
				int id = Integer.parseInt(commands[2]);
				String message = commands[3];
				message = unicodeToString(message);
				// controller
			}
			else if(secondary.equals("MODEL")) {
				LinkedList<User> users = new LinkedList<User>();
				if(commands.length<4) {
		        	response = new StringBuilder("Invalid Command: DATA MODEL command length less than 4");
		        	return response.toString();
		        }
				
				for(int i = 3;i<Integer.parseInt(commands[2]);i+=3) {
					String name = commands[i+1];
					name = unicodeToString(name);
					int id = Integer.parseInt(commands[i+2]);
					int score =  Integer.parseInt(commands[i]);
					users.addLast(new User(name, id, score));
				}
				//controller
			}
			else if(secondary.equals("DREW")) {
				
			}
			
			else {
				response = new StringBuilder("Invalid Command:DATA command not match");
			}
		}
		else {
			response = new StringBuilder("Invalid Command: Command not find");
		}
        return response.toString();
	}
}
