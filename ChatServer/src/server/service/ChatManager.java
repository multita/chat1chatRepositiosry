package server.service;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
//为了操作而分离出类？

public class ChatManager { 
	static private  Map<String,SreverInteration> usersAndThreads =new HashMap();
	//用来管理写静态
	public static Boolean isOnLine(String username) {
		for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
			if( username.equals(str.getKey())) {
				return true;
			}
		}	
		return false;
	}
	public static void delectDownLine(Socket mysocket) {
		for(Map.Entry<String,SreverInteration> str1 : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
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
