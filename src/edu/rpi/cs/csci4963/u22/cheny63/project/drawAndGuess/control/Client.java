package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
* Game Network Client 
* @author Kevin Xia
* @version 1.0
*/
public class Client implements Runnable{
    // Connection variable
    private Socket socket;
	private String address;
	private int port;
	private InputStream inStream;
	private OutputStream outStream;
	private BufferedReader in;
	private PrintWriter out;
	private Controller controller;

	// Logger
	private Logger log;	
    
    /**
	 * Constructor for the game net work
	 * @param address the address
	 * @param port the port
	 * @param log the logger
	 */
    public Client(String address, int port, Logger log, Controller controller){
		this.address = address;
		this.port = port;
		this.log = log;
		this.controller = controller;
    }

	public String getAddress(){
		return socket.getLocalSocketAddress().toString();
	}

    /**
	 * Send the message
	 * @param message the message
	 */
	public void send(String message){
		out.println(message);
		log.info(String.format("Message \"%s\" sent.\n", message));
	}
	
	/**
	 * Receive the message
	 * @return the message
	 */
	private String receive(){
		String message;
		try{
			message = this.in.readLine();
			if(message != null){
				log.info("Message \"%s\" received.\n".formatted(message));
			}else{
				log.info("No message received.");
			}
		}catch(IOException e){
			message = null;
			e.printStackTrace();
			log.warning("Failed to receive the line...");
		}
		return message;
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
	 * Client side establish the connection
	 * @throws IOException if cannot establish the connection
	 */
	private void connect() throws IOException{
		socket = new Socket();
		// Set the timeout to 5 seconds
		socket.connect(new InetSocketAddress(address, port), 5000);
        // I/O port
		inStream =  socket.getInputStream();
		outStream = socket.getOutputStream();
		in = new BufferedReader(new InputStreamReader(inStream));
		out = new PrintWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true);
		log.info(String.format("Connection to server %s established at port %d.\n", address, port));
	}

    @Override
    public void run(){
        // Wait a little bit just for windows to be established
		controller.onConnectionWait();
		try {
			try {
				Thread.sleep(100);
			}catch(InterruptedException e) {
				log.warning("Fail to sleep the thread");
			}
			// Try to establish the connection 
			connect();
		}catch(UnknownHostException e) {
			log.warning("Unknown host: %s.".formatted(e.getMessage()));
			controller.onConnectionFailed("Unknown host: " + e.getMessage(), "Connection Failed");
		}catch(IOException e){
            log.warning(String.format("Unable to create socket: %s", e.getMessage()));
			controller.onConnectionFailed("Unable to connect: " + e.getMessage(), "Connection Failed");
		}
		// Check if the connection is established
		if(!isConnectionClosed()){
			// Run the intialize method
			controller.onConnectionWaitEnd();
			controller.onConnectionSuccess();
		}
		String message;
		// Manage the I/O flow while the connection is not close
		while(!Thread.currentThread().isInterrupted() && !isConnectionClosed()){
			// Receive the message
            message = receive();
			if(message != null){
				controller.processCommand(message);
			}else{
				controller.onConnectionFailed("Failed to receive data through socket!", "Connection Lost");
				break;
            }
		}
		try{
			socket.close();
		}catch(IOException e){
			log.info("Failed to close socket...");
		}
		log.info("Connection End...");
    }
}