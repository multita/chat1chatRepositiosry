package server.service;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
//Ϊ�˲�����������ࣿ

public class ChatManager { 
	static private  Map<String,SreverInteration> usersAndThreads =new HashMap();
	//��������д��̬
	public static Boolean isOnLine(String username) {
		for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
			if( username.equals(str.getKey())) {
				return true;
			}
		}	
		return false;
	}
	public static void delectDownLine(Socket mysocket) {
		for(Map.Entry<String,SreverInteration> str1 : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
			if( mysocket.equals(str1.getValue())) {
				usersAndThreads.remove(str1);
			}
		}
	}
	public static void setUsersAndThreads(Map<String, SreverInteration> usersAndThreads) {
		ChatManager.usersAndThreads = usersAndThreads;
	}

	public static Map<String, SreverInteration> getUsersAndThreads() {
		return usersAndThreads;
	}

	
}
