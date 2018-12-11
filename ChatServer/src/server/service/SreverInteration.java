
package server.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import server.dao.*;
import server.model.*;
public class SreverInteration extends Thread {
	
	//��ָ��socket��io�����в���
	Socket mySocket;
	BufferedReader is;
	PrintWriter bw;
	
	public SreverInteration(Socket mySocket){
		
		  this.mySocket = mySocket;
		 
	}
	public  void send (Object object) {
		String json=JSON.toJSONString(object);
		System.out.println("+++++4s5ad48+++"+json);
		
		bw.println(json);
		bw.flush();
	}
	public void dealData(String json) {
		JSONObject jsonObject = JSON.parseObject(json);
		String command=(String)jsonObject.get("command");
	
		if(command.equals("delectFriend")) {
			
			String username=(String)jsonObject.get("username");
			String object=(String)jsonObject.get("object");
			ControlDB.delectFriend(username,object);
			//this.send(hisMessageModel);
			for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
				if( object.equals(str.getKey())) {
					
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					System.out.println("�����߽���");
					//this.bw=str.getValue().bw;  //������bug
					bw.println(json);
					bw.flush();
				}
			}
		}
		
		if(command.equals("sendFile")) {
			
			String fileContent=(String)jsonObject.get("content");
			String sender = (String)jsonObject.get("sender");
			String receiver = (String)jsonObject.get("receiver");

			String fileName = (String)jsonObject.get("fileName");
			System.out.println("����"+"�����"+"�յ�"+fileName);
			
			System.out.println("���յ��ļ���");
			System.out.println(fileContent);
			for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
				if( receiver.equals(str.getKey())) {
					
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					//this.bw=str.getValue().bw;  //������bug
					System.out.println("�ҷ��ͺ���"+json);
					
					bw.println(json);
					bw.flush();
				}
			}
		}	
		if(command.equals("acceptFriend")) {
			
			String username=(String)jsonObject.get("username");
			String object=(String)jsonObject.get("object");
			for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
				if( object.equals(str.getKey())) {
					
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					System.out.println("�����߽���");
					//this.bw=str.getValue().bw;  //������bug
					bw.println(json);
					bw.flush();
				}
				if( username.equals(str.getKey())) {
					
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}

					System.out.println("�����߽���");
					//this.bw=str.getValue().bw;  //������bug
					bw.println(json);
					bw.flush();
				}
			}
			ControlDB.addFriend(username,object);
			
		
		}
		if(command.equals("addFriend")) {
			
			String username=(String)jsonObject.get("username");
			String object=(String)jsonObject.get("object");
			for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
				if( object.equals(str.getKey())) {
					
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					//this.bw=str.getValue().bw;  //������bug
					bw.println(json);
					bw.flush();
				}
			}
			//ControlDB.addFriend(username,object);
			
		
		}
		//��hisMessage����
		if(command.equals("queryMessage")) {
			
			String username=(String)jsonObject.get("sender");
			String object=(String)jsonObject.get("receiver");
			HisMessageModel hisMessageModel = ControlDB.getHisMessage(username,object);
			try {
				this.bw=new PrintWriter(this.mySocket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.send(hisMessageModel);
		//���Ǵ�message����
		}
		if(command.equals("delectMessage")) {
			
			String sender=(String)jsonObject.get("sender");
			String receiver=(String)jsonObject.get("receiver");
			String message=(String)jsonObject.get("message");
			String nowTime=(String)jsonObject.get("nowTime");
			MessageModel messageModel = new MessageModel("message",message,sender,receiver,nowTime);
			
			ControlDB.delectHisMessage(messageModel);
		}
		if(command.equals("delectHisMessage")) {
			
			String sender=(String)jsonObject.get("sender");
			String receiver=(String)jsonObject.get("receiver");
			MessageModel messageModel = new MessageModel("delectHisMessage",null,sender,receiver,null);
			
			ControlDB.delectHisMessage(messageModel);
		}
		if(command.equals("message")) {
			System.out.println("���ڽ���message��JSONObject����"+jsonObject);
			String sender=(String)jsonObject.get("sender");
			String receiver=(String)jsonObject.get("receiver");
			String message=(String)jsonObject.get("message");
			String nowTime=(String)jsonObject.get("nowTime");
			
			for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
				if( receiver.equals(str.getKey())) {
					MessageModel messageModel = new MessageModel("message",message,sender,receiver,nowTime);
					ControlDB.putMessage(messageModel);
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
						this.send(messageModel);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					//this.bw=str.getValue().bw;  //������bug
					
				}
			}
			
		}
		if(command.equals("register")) {
			
			String username=(String)jsonObject.get("username");
			String password=(String)jsonObject.get("password");
			
			if(ControlDB.isexist(username)) {
				RegisterModel registerModel = new RegisterModel("nameUsed",username,password);
				this.send(registerModel );
			}else {
				ControlDB.register( username, password);
				RegisterModel registerModel = new RegisterModel("succeedRegister",username,password);
				this.send(registerModel );
			}
			
			
		}
		//��login����
		if(command.equals("login")) {
			
			System.out.println("���ڽ���login-JSONObject����"+jsonObject);
			String username=(String)jsonObject.get("username");
			String password=(String)jsonObject.get("password");
			
			
				if(ControlDB.isexist(username)) {
					if(ControlDB.isexist(username,password)) {
						
						for(Map.Entry<String,SreverInteration> onliner : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
							if( username.equals(onliner.getKey())) {
								
								if(mySocket.getLocalAddress().equals(onliner.getValue().mySocket.getLocalAddress())) {
									System.out.println("��ַ"+mySocket.getLocalAddress());
									UserModel user= new UserModel("repeatLogin",username,password,null);
									this.send(user);
									return;
								}else {
									String[] friends = ControlDB.getFriends(username);
									FriendModel[] friendModel =new FriendModel[friends.length];
									for(int i=0;i<friends.length;i++) {
										friendModel[i] = new FriendModel(friends[i],ChatManager.isOnLine(friends[i]));
									}
									UserModel user= new UserModel("succeedLogin",username,password,friendModel);
									this.send(user);
									
									UserModel newUser= new UserModel("remoteLogin",username,password,null);
									try {
										this.bw=new PrintWriter(onliner.getValue().mySocket.getOutputStream());
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									this.send(newUser);
									return;
								}
								
							}
						}
								
						
						ChatManager.getUsersAndThreads().put(username, this);
						String[] friends = ControlDB.getFriends(username);
						FriendModel[] friendModel =new FriendModel[friends.length];
						
						for(int i=0;i<friends.length;i++) {
							friendModel[i] = new FriendModel(friends[i],ChatManager.isOnLine(friends[i]));
						}
						UserModel user= new UserModel("succeedLogin",username,password,friendModel);
						this.send(user);
						
						for(String friend: friends) {
							for(Map.Entry<String,SreverInteration> str1 : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
								if( friend.equals(str1.getKey())) {
									try {
										this.bw=new PrintWriter(str1.getValue().mySocket.getOutputStream());
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									FriendUpLineModel friendUpLineModel = new FriendUpLineModel("UpLine",user.getUsername());
									this.send(friendUpLineModel);
								}
							}
						} 
						return;
					}
					else {
						System.out.println("�������");
						
						UserModel user= new UserModel("mistakePassword",username,password,null);
						this.send(user);
						return;
					}
				}
			
			System.out.println("�˺Ų�����");
			UserModel user= new UserModel("mistakeAccount",username,password,null);
			this.send(user);
		}
		
		
	}
	
	public void run(){
		 try {
		 	 is = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));//��������
		 	 bw = new PrintWriter(mySocket.getOutputStream());
			System.out.println("�Ѿ�����io������");
			while(mySocket.isConnected()) {
				String json = is.readLine();//�ӿͻ��˶�ȡһ��������
				System.out.println("���յ���Ϣ:"+json);
				System.out.println("���ڽ������ݴ���");
				dealData(json);
				if(json.equals("bye")) break;
				
			}
			
		 }catch(IOException e){
			 String temp = null;
			 for(Map.Entry<String,SreverInteration> str3 : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
				
				 if( this.mySocket.toString().equals(str3.getValue().mySocket.toString())) {
					 temp = str3.getKey();
					
				 }
			 }
			 String[] friends = ControlDB.getFriends(temp); //java������Ը�ֵ
				
			 for(String friend: friends) {
				 
					for(Map.Entry<String,SreverInteration> str2 : ChatManager.getUsersAndThreads().entrySet()) {//�������е�ֵ
						if( friend.equals(str2.getKey())) {
							try {
								this.bw=new PrintWriter(str2.getValue().mySocket.getOutputStream());
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
							FriendDownLineModel friendDownLineModel = new FriendDownLineModel("downLine",temp);
							this.send(friendDownLineModel);
						}
					}
				} 
			 ChatManager.getUsersAndThreads().remove(temp);
		 } finally {
			 
			 try {
				is.close();
				mySocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	}
}
