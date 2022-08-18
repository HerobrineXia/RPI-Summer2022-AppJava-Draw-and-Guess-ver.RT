package edu.rpi.cs.csci4963.u22.cheny63.project.drawAndGuess.control;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

/**
* Game Network Client 
* @author Kevin Xia
* @version 1.0
*/
public class Server implements Runnable{
    
    // Connection variable
    private HashMap<Integer, Socket> socketList;
    private ArrayList<Thread> threadList;
    private ServerSocket serverSocket;
	private int port;
    private int currentId;
    private boolean flag;

	// Logger
	private Logger log;	
    
    // Reference
    private Controller controller;
    /**
	 * Constructor for the game net work
	 * @param port the port
	 * @param log the logger
	 */
    public Server(int port, Logger log, Controller controller){
        this.controller = controller;
		this.port = port;
		this.log = log;
        socketList = new HashMap<>();
        threadList = new ArrayList<>();
        currentId = 1;
    }

    public int getId(String address){
        synchronized(socketList){
            for(int id: socketList.keySet()){
                if(socketList.get(id).getRemoteSocketAddress().toString().equals(address)){
                    return id;
                }
            }
        }
        return -1;
    }

    public void sendMessage(String message, int id){
        PrintWriter out = null;
        synchronized(socketList){
            Socket socket = socketList.get(id);
            try{
                out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
                out.println(message);	
            }catch(IOException e){
                log.warning("Failed to send the message to the client at %s".formatted(socket.getRemoteSocketAddress().toString()));
            }
        }
        log.info(String.format("Message \"%s\" sent.\n", message));
    }

    public void sendMessageToAll(String message){
        PrintWriter out = null;
        synchronized (socketList){
            for (Socket socket : socketList.values()){
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
            for(int id: socketList.keySet()){
                if(socketList.get(id).equals(socket)){
                    socketList.remove(id);
                    controller.onPlayerLeaveServer(id);
                    break;
                }
            }
        }
    }

    public void closeServer(){
        flag = false;
    }

    private void onServerClose(){
        log.info("Server closing...");
        synchronized(socketList){
            for(Socket socket: socketList.values()){
                try{
                    log.info("Closing socket at %s".formatted(socket.getRemoteSocketAddress().toString()));
                    socket.close();
                }catch(IOException e){
                    log.warning("Failed to close socket at %s on server close...".formatted(socket.getRemoteSocketAddress().toString()));
                }
            }
            socketList.clear();
        }
        try{
            serverSocket.close();
        }catch(IOException e){
            log.warning("Failed to close server socket on server close...");
        }
        for(Thread thread: threadList){
            try{
                thread.join();
            }catch(InterruptedException e){
                log.warning("Failed to join the client thread...");
            }
        }
    }

    @Override
    public void run(){
        flag = true;
        try{
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000);
            log.info("Server started with ip %s:%d".formatted(InetAddress.getLocalHost().toString().split("/")[1],port));
        }catch(IOException e){
            flag = false;
            log.warning("Failed to create server socket.");
            controller.onConnectionFailed("Failed to create server", "Connection Failed");
        }
        if(!serverSocket.isClosed() && flag){
            controller.onConnectionSuccess();
        }
		// Manage connection while the connection is not close
		while(!Thread.currentThread().isInterrupted() && flag){
            try {
                // Try to establish the connection 
                if(controller.isGameStart()){
                    continue;
                }
                Socket accept = serverSocket.accept();
                log.info(String.format("Incoming connection from a client at %s accepted.\n", accept.getRemoteSocketAddress().toString()));
                synchronized(socketList){
                    socketList.put(currentId, accept);
                    ++currentId;
                }
                Thread thread = new Thread(new ServerThread(accept, log, this, controller));
                threadList.add(thread);
                thread.start();
            }catch(SocketTimeoutException e){
                // DO NOTHING
            }catch(IOException e){
                log.warning(String.format("Unable to create socket: %s", e.getMessage()));
                flag = false;
            }
		}
        onServerClose();
		log.info("Server Closed...");
    }
}