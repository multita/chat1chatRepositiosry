package server.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	//监听指定端口，并将接受到的socket传给DealClient线程
	ServerSocket serverSocket;
	//UserManager userManager = new  UserManager();
	
	public ChatServer() {
	
		try {
			System.out.println("正在开启服务器...");
			serverSocket = new ServerSocket(1201);
			//userManager = new UserManager();
			//chatManager = new ChatManager ();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("服务器已经开启...");
		
	}
	
	public void start() {
		
		while(true){
			
			 System.out.println("正在等待客户端连接...");
			 
			 try {
			 
				Socket ss=serverSocket.accept();
				
				SreverInteration dealClient=new SreverInteration(ss);
				dealClient.start();
				
			  } catch (IOException e) {
			
				e.printStackTrace();
			  }
			
			System.out.println("新的客户端已连接...");
		}
		
	}
	
}