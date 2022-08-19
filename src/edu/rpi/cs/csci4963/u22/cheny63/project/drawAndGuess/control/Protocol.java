package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.util.Base64;
import java.util.LinkedList;
import java.awt.Color;

import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.User;
import edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.model.GameStatus;



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
	
	public String baseToString(String a) {
		byte[] decodedBytes = Base64.getDecoder().decode(a);
		String decodedString = new String(decodedBytes);
		return decodedString;
	}
	
	


	public String messagePack(String message){
		message = Base64.getEncoder().encodeToString(message.getBytes());
		
		StringBuilder response = new StringBuilder("%s%s%s%s%s".formatted("DATA",SEPARATOR,"MESSAGE",SEPARATOR,message));
		return response.toString();
	}

	public String userDataPack(LinkedList<User> users,int currentDrawerId, GameStatus g){
		int num = users.size();
		StringBuilder response = new StringBuilder("%s%s%s%s%s".formatted("DATA",SEPARATOR,"MODEL",SEPARATOR,num));
		response.append(new StringBuilder(("%s%d%s%s").formatted(SEPARATOR,currentDrawerId,SEPARATOR,g.toString())));
		for(int i = 0; i<num;i++) {
			response.append(new StringBuilder("%s%d%s%s%s%d".formatted(SEPARATOR,users.get(i).getScore(),SEPARATOR,Base64.getEncoder().encodeToString(users.get(i).getName().getBytes()),SEPARATOR,users.get(i).getId())));
		}
		return response.toString();
	}
	public String userLeftEvent(int id) {
		StringBuilder response = new StringBuilder("%s%s%s%s%d".formatted("EVENT",SEPARATOR,"LEFT",SEPARATOR,id));
		return response.toString();
	}
	public String userJoinServerEvent(String address, String name) {
		name = Base64.getEncoder().encodeToString(name.getBytes());
		address = Base64.getEncoder().encodeToString(address.getBytes());
		StringBuilder response = new StringBuilder("%s%s%s%s%s%s%s".formatted("EVENT",SEPARATOR,"JOIN_SERVER",SEPARATOR,name,SEPARATOR,address));
		return response.toString();
	}
	public String serverReturnIdEvent(int id) {
		StringBuilder response = new StringBuilder("%s%s%s%s%d".formatted("EVENT",SEPARATOR,"RETURN_ID",SEPARATOR,id));
		return response.toString();
	}
	public String userJoinClientEvent(int id, String name) {
		name = Base64.getEncoder().encodeToString(name.getBytes());
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
		message = Base64.getEncoder().encodeToString(message.getBytes());
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%s".formatted("EVENT",SEPARATOR,"MESSAGE_CLIENT",SEPARATOR,id,SEPARATOR,message));
		return response.toString();
	}
	public String serverSentMessageEvent(int id,String message) {
		message = Base64.getEncoder().encodeToString(message.getBytes());
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%s".formatted("EVENT",SEPARATOR,"MESSAGE_SERVER",SEPARATOR,id,SEPARATOR,message));
		return response.toString();
	}
	public String eventStartGame() {
		StringBuilder response = new StringBuilder("%s%s%s".formatted("EVENT",SEPARATOR,"NEW_GAME"));
		return response.toString();
	}
	public String eventRoundEnd() {
		StringBuilder response = new StringBuilder("%s%s%s".formatted("EVENT",SEPARATOR,"ROUND_END"));
		return response.toString();
	}
	public String sendSecretWord(String secret,String hint) {
		secret = Base64.getEncoder().encodeToString(secret.getBytes());
		hint = Base64.getEncoder().encodeToString(hint.getBytes());
		StringBuilder response = new StringBuilder("%s%s%s%s%s%s%s".formatted("EVENT",SEPARATOR,"SECRET",SEPARATOR,secret,SEPARATOR,hint));
		return response.toString();
	}
	public String dataDrawServer(int x,int y,Color color,int id) {
		int a = color.getAlpha();
		int g = color.getGreen();
		int r = color.getRed();
		int b = color.getBlue();
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%d%s%d%s%d%s%d%s%d%s%d".formatted("DATA",SEPARATOR,"DRAW_SERVER",SEPARATOR,x,SEPARATOR,y,SEPARATOR,id,SEPARATOR,a,SEPARATOR,r,SEPARATOR,g,SEPARATOR,b));
		return response.toString();
	}
	public String dataDrawClient(int x,int y,Color color,int id) {
		int a = color.getAlpha();
		int g = color.getGreen();
		int r = color.getRed();
		int b = color.getBlue();
		StringBuilder response = new StringBuilder("%s%s%s%s%d%s%d%s%d%s%d%s%d%s%d%s%d".formatted("DATA",SEPARATOR,"DRAW_CLIENT",SEPARATOR,x,SEPARATOR,y,SEPARATOR,id,SEPARATOR,a,SEPARATOR,r,SEPARATOR,g,SEPARATOR,b));
		return response.toString();
	}
	public String eventCleanBoard(int bool) {
		StringBuilder response = new StringBuilder("%s%s%s%s%d".formatted("EVENT",SEPARATOR,"CLEAN_BOARD",SEPARATOR,bool));
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
				name = baseToString(name);
				address = baseToString(address);
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
				name = baseToString(name);
				controller.onPlayerJoinClient(name, id);
			}
			else if(secondary.equals("MESSAGE_SERVER")) {
				if(commands.length<4) {
		        	response = new StringBuilder("Invalid Command: EVENT MESSAGE_SERVER command length less than 4");
		        	return response.toString();
		        }
				int id = Integer.parseInt(commands[2]);
				String message = commands[3];
				message = baseToString(message);
				controller.onPlayerReceiveMessageClient(id, message);
			}
			else if(secondary.equals("MESSAGE_CLIENT")) {
				if(commands.length<4) {
		        	response = new StringBuilder("Invalid Command: EVENT MESSAGE_CLIENT command length less than 4");
		        	return response.toString();
		        }
				int id = Integer.parseInt(commands[2]);
				String message = commands[3];
				message = baseToString(message);
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
			else if(secondary.equals("NEW_GAME")) {
				controller.onStartGameClient();
			}
			else if(secondary.equals("NEW_ROUND")) {
				int drawerId = Integer.parseInt(commands[2]);
				if(commands.length<3) {
		        	response = new StringBuilder("Invalid Command: EVENT NEW_ROUND command length less than 3");
		        	return response.toString();
		        }
				controller.onNewRound(drawerId);
			}
			else if(secondary.equals("ROUND_END")) {
				controller.onRoundEnd();
			}
			else if(secondary.equals("SECRET")){
				if(commands.length<4) {
		        	response = new StringBuilder("Invalid Command: EVENT SECRET command length less than 4");
		        	return response.toString();
		        }
				String secret = commands[2];
				String hint = commands[3];
				secret = baseToString(secret);
				hint = baseToString(hint);
				controller.onSecretWordReceive(secret, hint);
			}
			else if(secondary.equals("CLEAN_BOARD")) {
				if(commands.length<3) {
		        	response = new StringBuilder("Invalid Command: EVENT CLEAN_BOARD command length less than 3");
		        	return response.toString();
		        }
				int bool = Integer.parseInt(commands[2]);
				controller.onBoardClearReceive(bool);
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
				controller.onUserScoreReceive(id, score);
			}
			else if(secondary.equals("MESSAGE")) {
				if(commands.length<3) {
		        	response = new StringBuilder("Invalid Command: DATA MESSAGE command length less than 4");
		        	return response.toString();
		        }
				String message = commands[2];
				message = baseToString(message);
				controller.onMessageReceive(message);
			}
			else if(secondary.equals("MODEL")) {
				LinkedList<User> users = new LinkedList<User>();
				if(commands.length<5) {
		        	response = new StringBuilder("Invalid Command: DATA MODEL command length less than 4");
		        	return response.toString();
		        }
				int currentDrawerId = Integer.parseInt(commands[3]);
				GameStatus g = GameStatus.valueOf(commands[4]);
				for(int i = 5;i<(Integer.parseInt(commands[2])*3)+5;i+=3) {
					String name = commands[i+1];
					name = baseToString(name);
					int id = Integer.parseInt(commands[i+2]);
					int score =  Integer.parseInt(commands[i]);
					users.addLast(new User(name, id, score));
				}
				controller.onPlayerReceiveDatapack(users, currentDrawerId, g);
			}
			else if(secondary.equals("DRAW_SERVER")) {
				if(commands.length<9) {
		        	response = new StringBuilder("Invalid Command: DRAW_SERVER command length less than 9");
		        	return response.toString();
		        }
				int x = Integer.parseInt(commands[2]);
				int y = Integer.parseInt(commands[3]);
				int id = Integer.parseInt(commands[4]);
				int a = Integer.parseInt(commands[5]);
				int r = Integer.parseInt(commands[6]);
				int g = Integer.parseInt(commands[7]);
				int b = Integer.parseInt(commands[8]);
				Color c = new Color(r,g,b,a);
				controller.onBoardReceiveServer(x, y, c, id);
			}
			else if(secondary.equals("DRAW_CLIENT")) {
				if(commands.length<9) {
		        	response = new StringBuilder("Invalid Command: DRAW_CLIENT command length less than 9");
		        	return response.toString();
		        }
				int x = Integer.parseInt(commands[2]);
				int y = Integer.parseInt(commands[3]);
				int id = Integer.parseInt(commands[4]);
				int a = Integer.parseInt(commands[5]);
				int r = Integer.parseInt(commands[6]);
				int g = Integer.parseInt(commands[7]);
				int b = Integer.parseInt(commands[8]);
				Color c = new Color( r, g, b,a);
				controller.onBoardReceiveClient(x, y, c, id);
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
