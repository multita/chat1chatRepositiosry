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

//ÿ�ζ���һ��main��ڣ����ö��߳�
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
 * ���캯��
 * �½�socket
 * �½���
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
		System.out.println("������������JSONObject����Ϊ��"+jsonObject);
		String command=(String)jsonObject.get("command");
		if("sendFile".equals(command)) {
			FileModel fileModel = JSON.parseObject(json, FileModel.class);
			new FileInterface(fileModel,findChatInterface(fileModel.getSender()),is,os);
		}

		if("acceptFriend".equals(command)) {
			RelationModel relationModel = JSON.parseObject(json, RelationModel.class);
			System.out.println("��֪������ͬ����"+relationModel.getObject());
			if(relationModel.getObject().equals(username)) {
				this.chatInterfaces.add(new ChatInterface(relationModel.getUsername(),username,is,os));
				this.mainInterface.getDlm().addElement(relationModel.getUsername()+"[����]");
			}else {
				this.chatInterfaces.add(new ChatInterface(relationModel.getObject(),username,is,os));
				this.mainInterface.getDlm().addElement(relationModel.getObject()+"[����]");
				
			}
		}
		if("delectFriend".equals(command)) {
			RelationModel relationModel = JSON.parseObject(json, RelationModel.class);
			this.mainInterface.getDlm().removeElement(relationModel.getUsername()+"[����]");
			this.mainInterface.getDlm().removeElement(relationModel.getUsername()+"[����]");
		}
		if("addFriend".equals(command)) {
			RelationModel relationModel = JSON.parseObject(json, RelationModel.class);
			new RelationInterface(relationModel.getObject(),relationModel.getUsername(),is,os);
		}
		if("repeatLogin".equals(command)) {
			new TipInterface("���Ѿ���¼���˺š�");
		}
		if("remoteLogin".equals(command)) {
			new TipInterface("���Ѿ���¼���˺š�");
			this.mainInterface.setVisible(false);
			this.loginInterface.setVisible(true);
		}
		if("downLine".equals(command)) {
			FriendDownLineModel friendDownLineModel = JSON.parseObject(json, FriendDownLineModel.class);
			this.mainInterface.getDefaultListModel().removeElement(friendDownLineModel.getUsername()+"[����]");
			this.mainInterface.getDefaultListModel().addElement(friendDownLineModel.getUsername()+"[����]");
		}
		if("UpLine".equals(command)) {
			FriendUpLineModel friendUpLineModel = JSON.parseObject(json, FriendUpLineModel.class);
			this.mainInterface.getDefaultListModel().removeElement(friendUpLineModel.getUsername()+"[����]");
			this.mainInterface.getDefaultListModel().addElement(friendUpLineModel.getUsername()+"[����]");
		}
		if("nameUsed".equals(command)) {
			new TipInterface("���û����Ѿ���ʹ����!");
		}
		if("succeedRegister".equals(command)) {
			new TipInterface("ע��ɹ�!");
			loginInterface.setVisible(true);
		}
		if("succeedLogin".equals(command)) {
			UserModel user = JSON.parseObject(json, UserModel.class);
			System.out.println("���������������ĵ�¼ָ��");
			this.loginInterface.setVisible(false);
			this.username=user.getUsername();
			//this.chatInterfaces = new ChatInterface[user.getFriends().length];
			//System.out.println("һ����"+this.chatInterfaces.length+"������");
			for(int i=0 ;i < user.getFriends().length ; i++) {
				//this.chatInterfaces[i] = new ChatInterface(user.getFriends()[i].getName(),user.getUsername(),is,os);
				this.chatInterfaces.add(new ChatInterface(user.getFriends()[i].getName(),user.getUsername(),is,os));
			}
			this.mainInterface = new MainInterface(user,this.chatInterfaces,is,os);
			
		}
		if("mistakePassword".equals(command)) {
			UserModel user = JSON.parseObject(json, UserModel.class);
			System.out.println("���������������Ĵ�������ָ��");
			new TipInterface(user.getCommand());
		}
		if("mistakeAccount".equals(command)) {
			UserModel user = JSON.parseObject(json, UserModel.class);
			System.out.println("���������������Ĵ����˺�ָ��");
			new TipInterface(user.getCommand());
		}
		if("message".equals(command)) {
			System.out.println("������������������Ϣָ��");
			MessageModel messageModel = JSON.parseObject(json,MessageModel.class);
			//�ͻ����յ���Ϣ���󣬾��ҵ��ý��棬����message��䵽Label��
			String sender =  messageModel.getSender();
			String message = messageModel.getMessage();
			String time =  messageModel.getNowTime();
			//System.out.println(receiver+"��"+sender+"����Ϣ����");
			this.findChatInterface(sender).setVisible(true);
			this.findChatInterface(sender).getJta().append("\n"+time+"\n"+sender+"��"+message);
			
		}
		//��HisMessageModel ��һ��sender��receiver
		if("queryMessage".equals(command)) {
			HisMessageModel hisMessageModel = JSON.parseObject(json,HisMessageModel.class);
			System.out.println("������������������ʷ��¼ָ��");
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
				this.findChatInterface(receiver3).getJta().append("\n------------�����¼����Ϊֹ----------------");
				
			}
			
		}
	}
	
	BufferedReader is;
	public void run(){
		 try {
			 is = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));//��������
			System.out.println("�Ѿ�����io������");
	
			while(true) {
				System.out.println("���ڵȴ���ȡ����");
				String json = is.readLine();//�ӷ���˶�ȡһ��������
				System.out.println("���յ���Ϣ:"+json);
				System.out.println("�ͻ������ڽ������ݴ���");
				dealData(json);
				if(json.equals("bye")) break;
				
			}
			is.close();
			mySocket.close();
		 }catch(IOException e){
			new TipInterface("�������Ͽ�������������",1);
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
		
		
		System.out.println("�ͻ���������...");

		try {
			//�����������
			this.mySocket = new Socket(InetAddress.getLocalHost(),1201);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			TipInterface tipInterface = new TipInterface("�Ҳ����÷�����"); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			TipInterface tipInterface = new TipInterface("������δ����"); 
		}
		System.out.println("�ͻ����Ѿ������Ϸ����...");
		
		try {
			//���� ����¼����
			
			BufferedReader is = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));//��������
			PrintWriter os = new PrintWriter(mySocket.getOutputStream());//�������
			System.out.println("-------�����ͻ���¼����");
	    	LoginInterface loginInterface = new LoginInterface(is,os);
	    	this.loginInterface = loginInterface;
		}catch(IOException e){
			e.printStackTrace();
			TipInterface tipInterface = new TipInterface("io���쳣��"); 
		}
	
	}
	

	
 }
