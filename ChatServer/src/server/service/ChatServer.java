package server.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	//����ָ���˿ڣ��������ܵ���socket����DealClient�߳�
	ServerSocket serverSocket;
	//UserManager userManager = new  UserManager();
	
	public ChatServer() {
	
		try {
			System.out.println("���ڿ���������...");
			serverSocket = new ServerSocket(1201);
			//userManager = new UserManager();
			//chatManager = new ChatManager ();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("�������Ѿ�����...");
		
	}
	
	public void start() {
		
		while(true){
			
			 System.out.println("���ڵȴ��ͻ�������...");
			 
			 try {
			 
				Socket ss=serverSocket.accept();
				
				SreverInteration dealClient=new SreverInteration(ss);
				dealClient.start();
				
			  } catch (IOException e) {
			
				e.printStackTrace();
			  }
			
			System.out.println("�µĿͻ���������...");
		}
		
	}
	
}