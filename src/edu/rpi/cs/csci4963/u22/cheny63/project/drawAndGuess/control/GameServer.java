package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
* Game Network Client 
* @author Kevin Xia
* @version 1.0
*/
public class GameServer implements Runnable{
    
    // Connection variable
    private ArrayList<Socket> socketList;
    private ArrayList<Thread> threadList;
    private ServerSocket serverSocket;
    private Socket socket;
	private int port;

	// Logger
	private Logger log;	
    
    /**
	 * Constructor for the game net work
	 * @param port the port
	 * @param log the logger
	 */
    public GameServer(int port, Logger log){
		this.port = port;
		this.log = log;
        socketList = new ArrayList<>();
    }

    public void sendMessageToAll(String message){
        PrintWriter out = null;
        synchronized (socketList){
            for (Socket socket : socketList){
                try{
                    out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                    out.println(message);	
                }catch(IOException e){
                    log.warning("Failed to send the message to the client at %s".formatted(socket.getRemoteSocketAddress().toString()));
                }
            }
        }
        log.info(String.format("Message \"%s\" sent.\n", message));
    }

    public void removeSocket(Socket socket){
        synchronized(socketList){
            socketList.remove(socket);
        }
    }

    private void onServerClose(){
        for(Socket socket: socketList){
            try{
                socket.close();
            }catch(IOException e){
                log.warning("Failed to close socket at %s on server close...".formatted(socket.getRemoteSocketAddress().toString()));
            }
        }
        socketList.clear();
        try{
            serverSocket.close();
        }catch(IOException e){
            log.warning("Failed to close server socket on server close...");
        }
        for(Thread thread: threadList){
            thread.interrupt();
            try{
                thread.join();
            }catch(InterruptedException e){
                log.warning("Failed to join the client thread...");
            }
        }
    }

    @Override
    public void run(){
        boolean flag = true;
        try{
            serverSocket = new ServerSocket(port);
        }catch(IOException e){
            flag = false;
            log.warning("Failed to create server socket.");
        }
		// Manage connection while the connection is not close
		while(!Thread.currentThread().isInterrupted() && flag){
            try {
                // Try to establish the connection 
                Socket accept = serverSocket.accept();
                log.info(String.format("Incoming connection from a client at %s accepted.\n", socket.getRemoteSocketAddress().toString()));
                synchronized(socketList){
                    socketList.add(accept);
                }
                Thread thread = new Thread(new GameServerThread(accept, log, this));
                threadList.add(thread);
                thread.start();
            }catch(IOException e){
                log.warning(String.format("Unable to create socket: %s", e.getMessage()));
                flag = false;
            }
		}
        onServerClose();
		log.info("Server Closed...");
    }
}