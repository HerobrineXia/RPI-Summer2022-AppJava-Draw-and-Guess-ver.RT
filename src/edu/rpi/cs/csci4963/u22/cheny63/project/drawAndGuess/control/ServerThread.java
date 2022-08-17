package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class ServerThread implements Runnable {
    private Socket socket;
    private InputStream inStream;
	private OutputStream outStream;
	private BufferedReader in;
	private PrintWriter out;
	private Logger log;
	private Protocol protocol;

	// Reference
	private Server server;

    public ServerThread(Socket socket, Logger log, Server server, Protocol protocol) throws IOException{
        this.socket = socket;
		this.server = server;
		this.log = log;
		this.protocol = protocol;
        // I/O port
		inStream =  socket.getInputStream();
		outStream = socket.getOutputStream();
		in = new BufferedReader(new InputStreamReader(inStream));
		out = new PrintWriter(new OutputStreamWriter(outStream, StandardCharsets.UTF_8), true);
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
	 * Send the message
	 * @param message the message
	 */
	private void send(String message){
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
				log.info(String.format("Message \"%s\" received.\n", message));
			}else{
				log.info("No message received.");
			}
		}catch(IOException e){
			message = null;
			log.warning("Failed to receive the line...");
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
			message = null;
			// Receive the message
            message = receive();
			if(message != null){
				// Receive the respond and remove the command from list
				protocol.process(message);
			}else{
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
