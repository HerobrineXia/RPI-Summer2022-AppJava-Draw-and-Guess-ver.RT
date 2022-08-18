package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ServerThread implements Runnable {
    private Socket socket;
    private InputStream inStream;
	private Scanner in;
	private Logger log;
	private Controller controller;

	// Reference
	private Server server;

    public ServerThread(Socket socket, Logger log, Server server, Controller controller) throws IOException{
        this.socket = socket;
		this.server = server;
		this.log = log;
		this.controller = controller;
        // I/O port
		inStream =  socket.getInputStream();
		in = new Scanner(new InputStreamReader(inStream));
    }

    /**
	 * Check if the connection is closed
	 * @return true if it is,
	 * 		   false otherwise
	 */
	public boolean isConnectionClosed(){
		if(socket == null){
			return true;
		}
		return !socket.isConnected();
	}

	/**
	 * Receive the message
	 * @return the message
	 */
	private String receive(){
		String message;
		message = this.in.nextLine();
		if(message != null){
			log.info(String.format("Message \"%s\" received.\n", message));
		}else{
			log.info("No message received.");
		}
		return message;
	}

	@Override
	public void run(){
		// Check if the connection is established
		if(!isConnectionClosed()){
			// Run the intialize method
			// controller.afterConnect();
		}
		String message;
		// Manage the I/O flow while the connection is not close
		while(!Thread.currentThread().isInterrupted() && !isConnectionClosed()){
			// Receive the message
            message = receive();
			if(message != null){
				// Receive the respond and remove the command from list
				controller.processCommand(message);
			}else{
				log.info("Connection from %s lost, closing socket...".formatted(socket.getInetAddress().getAddress()));
				server.removeSocket(socket);
                try{
                    socket.close();
                }catch(IOException e){
                    log.info("Failed to close socket...");
                }
				break;
            }
		}
		log.info("Connection End...");
    }
}
