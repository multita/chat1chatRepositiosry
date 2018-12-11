package server.main;

import server.service.ChatServer;

public class StartServer {
	
	public static void main(String args[]) {
		
		
		ChatServer chatServer = new ChatServer();
		chatServer.start();
		
	}
}
