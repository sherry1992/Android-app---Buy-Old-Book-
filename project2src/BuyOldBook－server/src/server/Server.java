/**
 * project2_Yidil_shuruil
 * 
 * The Server class use a ServerSocket to listen on the a particular port number in the
 *  constructor. When it accept the calls from the Client, use DefaultSocketClient to
 *  accept it in the startServer method. And then the DefaultSocketClient calls start() 
 *  method to do all kinds of operations in handleSession method in DefaultSocketClient
 *  class
 */
package server;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;



public class Server {
	ServerSocket serverSocket;    //The ServerSocket 
    Socket clientSocket;          //The clientSocket accepted
    
    /* Constructor that initialize the ServerSoket listening on a port number */
   public Server(int iPort){
        try {
        	serverSocket = new ServerSocket(iPort);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + iPort);
            System.exit(1);
        }
	}
   

	/*
	 * startServer:
	 * This method use DefaultSocketClient to accept the calls from the client and calls the start method in 
	 * DefaultSocketClient class to do operations in handleSession method. The server will always listen to the
	 * calls from the client and will parse it to DefaultSocketClient when accept it.
	 */
	public void startServer(){
        while(true){
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
            DefaultServer ds = new DefaultServer(clientSocket);
    		ds.start();
        }
	}
}
