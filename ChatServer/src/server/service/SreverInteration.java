
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
	
	//对指定socket的io流进行操作
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
			for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
				if( object.equals(str.getKey())) {
					
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					System.out.println("接受者接受");
					//this.bw=str.getValue().bw;  //曾经的bug
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
			System.out.println("我是"+"服务端"+"收到"+fileName);
			
			System.out.println("我收到文件啦");
			System.out.println(fileContent);
			for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
				if( receiver.equals(str.getKey())) {
					
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					//this.bw=str.getValue().bw;  //曾经的bug
					System.out.println("我发送好了"+json);
					
					bw.println(json);
					bw.flush();
				}
			}
		}	
		if(command.equals("acceptFriend")) {
			
			String username=(String)jsonObject.get("username");
			String object=(String)jsonObject.get("object");
			for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
				if( object.equals(str.getKey())) {
					
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					System.out.println("接受者接受");
					//this.bw=str.getValue().bw;  //曾经的bug
					bw.println(json);
					bw.flush();
				}
				if( username.equals(str.getKey())) {
					
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}

					System.out.println("发送者接受");
					//this.bw=str.getValue().bw;  //曾经的bug
					bw.println(json);
					bw.flush();
				}
			}
			ControlDB.addFriend(username,object);
			
		
		}
		if(command.equals("addFriend")) {
			
			String username=(String)jsonObject.get("username");
			String object=(String)jsonObject.get("object");
			for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
				if( object.equals(str.getKey())) {
					
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					//this.bw=str.getValue().bw;  //曾经的bug
					bw.println(json);
					bw.flush();
				}
			}
			//ControlDB.addFriend(username,object);
			
		
		}
		//传hisMessage对象
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
		//都是传message对象
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
			System.out.println("正在解析message—JSONObject对象："+jsonObject);
			String sender=(String)jsonObject.get("sender");
			String receiver=(String)jsonObject.get("receiver");
			String message=(String)jsonObject.get("message");
			String nowTime=(String)jsonObject.get("nowTime");
			
			for(Map.Entry<String,SreverInteration> str : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
				if( receiver.equals(str.getKey())) {
					MessageModel messageModel = new MessageModel("message",message,sender,receiver,nowTime);
					ControlDB.putMessage(messageModel);
					try {
						this.bw=new PrintWriter(str.getValue().mySocket.getOutputStream());
						this.send(messageModel);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					//this.bw=str.getValue().bw;  //曾经的bug
					
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
		//传login对象
		if(command.equals("login")) {
			
			System.out.println("正在解析login-JSONObject对象："+jsonObject);
			String username=(String)jsonObject.get("username");
			String password=(String)jsonObject.get("password");
			
			
				if(ControlDB.isexist(username)) {
					if(ControlDB.isexist(username,password)) {
						
						for(Map.Entry<String,SreverInteration> onliner : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
							if( username.equals(onliner.getKey())) {
								
								if(mySocket.getLocalAddress().equals(onliner.getValue().mySocket.getLocalAddress())) {
									System.out.println("地址"+mySocket.getLocalAddress());
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
							for(Map.Entry<String,SreverInteration> str1 : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
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
						System.out.println("密码错误");
						
						UserModel user= new UserModel("mistakePassword",username,password,null);
						this.send(user);
						return;
					}
				}
			
			System.out.println("账号不存在");
			UserModel user= new UserModel("mistakeAccount",username,password,null);
			this.send(user);
		}
		
		
	}
	
	public void run(){
		 try {
		 	 is = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));//打开输入流
		 	 bw = new PrintWriter(mySocket.getOutputStream());
			System.out.println("已经建立io流连接");
			while(mySocket.isConnected()) {
				String json = is.readLine();//从客户端读取一整行数据
				System.out.println("接收到信息:"+json);
				System.out.println("正在进行数据处理");
				dealData(json);
				if(json.equals("bye")) break;
				
			}
			
		 }catch(IOException e){
			 String temp = null;
			 for(Map.Entry<String,SreverInteration> str3 : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
				
				 if( this.mySocket.toString().equals(str3.getValue().mySocket.toString())) {
					 temp = str3.getKey();
					
				 }
			 }
			 String[] friends = ControlDB.getFriends(temp); //java数组可以赋值
				
			 for(String friend: friends) {
				 
					for(Map.Entry<String,SreverInteration> str2 : ChatManager.getUsersAndThreads().entrySet()) {//遍历所有的值
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
