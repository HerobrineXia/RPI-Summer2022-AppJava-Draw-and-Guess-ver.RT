package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
* Game Network Server Thread 
* @author Kevin Xia
* @version 1.0
*/
public class ServerThread implements Runnable {
	// Connection variable
    private Socket socket;
    private InputStream inStream;
	private Scanner in;
	private Logger log;

	// Reference
	private Server server;
	private Controller controller;

	/**
	 * Constructor of the server thread
	 * @param socket the socket of this connection
	 * @param log the logger
	 * @param server the server reference
	 * @param controller the controller reference
	 * @throws IOException if cannot establish the input stream
	 */
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
	 * Receive the message from the client
	 * @return the message
	 */
	private String receive(){
		String message;
		try{
			message = this.in.nextLine();
		}catch(NoSuchElementException e){
			message = null;
		}
		if(message != null){
			log.info(String.format("Message \"%s\" received.\n", message));
		}else{
			log.info("No message received.");
		}
		return message;
	}

	/**
	* Start to listening for the message from the client 
	*/
	@Override
	public void run(){
		// Check if the connection is established
		String message;
		// Manage the I/O flow while the connection is not close
		while(!Thread.currentThread().isInterrupted() && !isConnectionClosed()){
			// Receive the message
            message = receive();
			if(message != null){
				// Receive the respond
				controller.processCommand(message);
			}else{
				// Receiving null message means the connection is lost. Remove the socket and clean up.
				log.info("Connection from %s lost, closing socket...".formatted(socket.getRemoteSocketAddress().toString()));
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
