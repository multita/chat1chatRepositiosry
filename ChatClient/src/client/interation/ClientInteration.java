package client.interation;
import client.main.*;
import client.view.*;
import client.model.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

//每次都用一个main入口，不用多线程
public class ClientInteration {
	Socket mySocket;
	
	private String username=null;
	private String password=null;
	private String[] friends=null;

	private LoginInterface loginInterface=null;
	private MainInterface mainInterface=null;
	//private ChatInterface[] chatInterfaces =null;
	private List<ChatInterface> chatInterfaces=new ArrayList<ChatInterface>();
/**
 * 构造函数
 * 新建socket
 * 新建流
 *
 */


	public ChatInterface findChatInterface(String object) {
		for(ChatInterface chatInterface : this.chatInterfaces) {
			if(chatInterface.getChatObject().equals(object))
				return chatInterface;
		}
		return null;
	}
	public void dealData(String json) throws FileNotFoundException {
		
		BufferedReader is=null;
		PrintWriter os=null;
		try {
			 is = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			 os = new PrintWriter(this.mySocket.getOutputStream());
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JSONObject jsonObject = JSON.parseObject(json);
		System.out.println("服务器传来的JSONObject对象为："+jsonObject);
		String command=(String)jsonObject.get("command");
		if("sendFile".equals(command)) {
			FileModel fileModel = JSON.parseObject(json, FileModel.class);
			new FileInterface(fileModel,findChatInterface(fileModel.getSender()),is,os);
		}

		if("acceptFriend".equals(command)) {
			RelationModel relationModel = JSON.parseObject(json, RelationModel.class);
			System.out.println("我知道请求同意了"+relationModel.getObject());
			if(relationModel.getObject().equals(username)) {
				this.chatInterfaces.add(new ChatInterface(relationModel.getUsername(),username,is,os));
				this.mainInterface.getDlm().addElement(relationModel.getUsername()+"[在线]");
			}else {
				this.chatInterfaces.add(new ChatInterface(relationModel.getObject(),username,is,os));
				this.mainInterface.getDlm().addElement(relationModel.getObject()+"[在线]");
				
			}
		}
		if("delectFriend".equals(command)) {
			RelationModel relationModel = JSON.parseObject(json, RelationModel.class);
			this.mainInterface.getDlm().removeElement(relationModel.getUsername()+"[在线]");
			this.mainInterface.getDlm().removeElement(relationModel.getUsername()+"[离线]");
		}
		if("addFriend".equals(command)) {
			RelationModel relationModel = JSON.parseObject(json, RelationModel.class);
			new RelationInterface(relationModel.getObject(),relationModel.getUsername(),is,os);
		}
		if("repeatLogin".equals(command)) {
			new TipInterface("您已经登录该账号。");
		}
		if("remoteLogin".equals(command)) {
			new TipInterface("您已经登录该账号。");
			this.mainInterface.setVisible(false);
			this.loginInterface.setVisible(true);
		}
		if("downLine".equals(command)) {
			FriendDownLineModel friendDownLineModel = JSON.parseObject(json, FriendDownLineModel.class);
			this.mainInterface.getDefaultListModel().removeElement(friendDownLineModel.getUsername()+"[在线]");
			this.mainInterface.getDefaultListModel().addElement(friendDownLineModel.getUsername()+"[离线]");
		}
		if("UpLine".equals(command)) {
			FriendUpLineModel friendUpLineModel = JSON.parseObject(json, FriendUpLineModel.class);
			this.mainInterface.getDefaultListModel().removeElement(friendUpLineModel.getUsername()+"[离线]");
			this.mainInterface.getDefaultListModel().addElement(friendUpLineModel.getUsername()+"[在线]");
		}
		if("nameUsed".equals(command)) {
			new TipInterface("改用户名已经被使用了!");
		}
		if("succeedRegister".equals(command)) {
			new TipInterface("注册成功!");
			loginInterface.setVisible(true);
		}
		if("succeedLogin".equals(command)) {
			UserModel user = JSON.parseObject(json, UserModel.class);
			System.out.println("解析服务器传来的登录指令");
			this.loginInterface.setVisible(false);
			this.username=user.getUsername();
			//this.chatInterfaces = new ChatInterface[user.getFriends().length];
			//System.out.println("一共有"+this.chatInterfaces.length+"个界面");
			for(int i=0 ;i < user.getFriends().length ; i++) {
				//this.chatInterfaces[i] = new ChatInterface(user.getFriends()[i].getName(),user.getUsername(),is,os);
				this.chatInterfaces.add(new ChatInterface(user.getFriends()[i].getName(),user.getUsername(),is,os));
			}
			this.mainInterface = new MainInterface(user,this.chatInterfaces,is,os);
			
		}
		if("mistakePassword".equals(command)) {
			UserModel user = JSON.parseObject(json, UserModel.class);
			System.out.println("解析服务器传来的错误密码指令");
			new TipInterface(user.getCommand());
		}
		if("mistakeAccount".equals(command)) {
			UserModel user = JSON.parseObject(json, UserModel.class);
			System.out.println("解析服务器传来的错误账号指令");
			new TipInterface(user.getCommand());
		}
		if("message".equals(command)) {
			System.out.println("解析服务器传来的消息指令");
			MessageModel messageModel = JSON.parseObject(json,MessageModel.class);
			//客户端收到消息对象，就找到该界面，并将message填充到Label上
			String sender =  messageModel.getSender();
			String message = messageModel.getMessage();
			String time =  messageModel.getNowTime();
			//System.out.println(receiver+"是"+sender+"发信息给我");
			this.findChatInterface(sender).setVisible(true);
			this.findChatInterface(sender).getJta().append("\n"+time+"\n"+sender+"："+message);
			
		}
		//给HisMessageModel 加一个sender和receiver
		if("queryMessage".equals(command)) {
			HisMessageModel hisMessageModel = JSON.parseObject(json,HisMessageModel.class);
			System.out.println("解析服务器传来的历史记录指令");
			MessageModel[] messageModel = hisMessageModel.getMessageModel();
			String receiver3 = null ;
			System.out.println(messageModel.toString()+"++++++++++++"+messageModel.length);
			if(messageModel.length!=0) {
				if(messageModel[0].getSender().equals(this.username)){
					receiver3 = messageModel[0].getReceiver();
				}else {
					receiver3 = messageModel[0].getSender();
				}
				
				for(int i=0;i<messageModel.length;i++) {
					this.findChatInterface(receiver3).getJta().append("\n-------------"+messageModel[i].getNowTime()+"\n--------------"+messageModel[i].getSender()+":"+messageModel[i].getMessage());
				}
				this.findChatInterface(receiver3).getJta().append("\n------------聊天记录到此为止----------------");
				
			}
			
		}
	}
	
	BufferedReader is;
	public void run(){
		 try {
			 is = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));//打开输入流
			System.out.println("已经建立io流连接");
	
			while(true) {
				System.out.println("正在等待读取数据");
				String json = is.readLine();//从服务端读取一整行数据
				System.out.println("接收到信息:"+json);
				System.out.println("客户端正在进行数据处理");
				dealData(json);
				if(json.equals("bye")) break;
				
			}
			is.close();
			mySocket.close();
		 }catch(IOException e){
			new TipInterface("服务器断开，您被迫下线",1);
			mainInterface.setVisible(false);
		 }finally {
			try {
				is.close();
				mySocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		 }
	}
	public ClientInteration() {
		
		
		System.out.println("客户端启动中...");

		try {
			//与服务器连接
			this.mySocket = new Socket(InetAddress.getLocalHost(),1201);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			TipInterface tipInterface = new TipInterface("找不到该服务器"); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			TipInterface tipInterface = new TipInterface("服务器未启动"); 
		}
		System.out.println("客户端已经连接上服务端...");
		
		try {
			//建流 开登录界面
			
			BufferedReader is = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));//打开输入流
			PrintWriter os = new PrintWriter(mySocket.getOutputStream());//打开输出流
			System.out.println("-------开启客户登录界面");
	    	LoginInterface loginInterface = new LoginInterface(is,os);
	    	this.loginInterface = loginInterface;
		}catch(IOException e){
			e.printStackTrace();
			TipInterface tipInterface = new TipInterface("io流异常。"); 
		}
	
	}
	

	
 }
