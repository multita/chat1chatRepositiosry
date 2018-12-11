package client.view;

import client.model.*;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.alibaba.fastjson.JSON;

public class ChatInterface extends JFrame {
	
	private String username;
	private String chatObject;

	private BufferedReader is;
	private PrintWriter os;
	
	private JTextArea jta=null;
	private JScrollPane jsp=null;
	private JTextArea historyJta=null;
	private JScrollPane historyJsp=null;
	private JPanel jp1=null;
	private JTextField jtf=null;
	private JButton jb=null;
	private JButton jb2=null;
	private JButton jb3=null;
	private JButton jb4=null;
	private JButton jb5=null;
	private JButton jb6=null;
	private ChatInterface chatInterface=this;

	


	public ChatInterface(String cchatObject,String uusername,BufferedReader iis,PrintWriter oos)
	{
		this.chatObject=cchatObject;
    	this.username=uusername;
		this.is = iis;
		this.os = oos;
		
		
		jta=new JTextArea("");
		jsp=new JScrollPane(jta);
		historyJta=new JTextArea("---");
		historyJsp=new JScrollPane(historyJta);
		jp1=new JPanel();
		jtf=new JTextField(20);
		jb=new JButton("发送");
		jb.setMnemonic(KeyEvent.VK_ENTER);
		jb.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            	System.out.println("发送信息");
	            	String message=jtf.getText();
	            	String nowTime =new SimpleDateFormat (" yyyy.MM.dd  HH:mm:ss ").format(new Date());
	            	MessageModel messageModel= new MessageModel("message",message,username,chatObject,nowTime);
	            	jta.append("\n"+nowTime+"\n我："+message);
		        	jtf.setText("");
		        	
		        	String json=JSON.toJSONString(messageModel);
		        	System.out.println(json);
	            	
		    		os.println(json);
		    		os.flush();
	            }
	        });
		jb2=new JButton("关闭");
		jb2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	chatInterface.setVisible(false);
            }
        });
		
		jb3=new JButton("历史聊天记录");
		jb3.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	System.out.println("历史信息");
            	MessageModel messageModel= new MessageModel("queryMessage",null,username,chatObject,null);
            	jta.append("\n--------------展示聊天记录 -------------");
	        	
	        	
	        	String json=JSON.toJSONString(messageModel);
	        	System.out.println(json);
            	
	    		os.println(json);
	    		os.flush();
            }
        });

		jb4=new JButton("清除聊天记录");
		jb4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	System.out.println("清除历史信息");
            	MessageModel messageModel= new MessageModel("delectHisMessage",null,username,chatObject,null);
          
            	jta.append("\n------------您的聊天记录已被清除-----------------");
            	String json=JSON.toJSONString(messageModel);
	        	System.out.println(json);
            	
	    		os.println(json);
	    		os.flush();
            }
        });
		jb6=new JButton("发图片");
		jb6.addActionListener(new ActionListener() {
			 @Override
	         public void actionPerformed(ActionEvent e) {
				 BufferedInputStream in;
				 BufferedOutputStream out;
				try {
					in = new BufferedInputStream(new FileInputStream("F:\\Kugou"));
					out = new BufferedOutputStream(new FileOutputStream("F:\\eclipse_workplace"));  //你要保存在哪个目录下面
						
					int i;  
					 try {
						while((i=in.read())!=-1){  
						   out.write(i);  
						 }
						 out.flush(); 
						 out.close();  
						 in.close();  
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}   
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}  //原先图片所在路径
				 
			
		    }
	    });
		jb5=new JButton("发文件");
		jb5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	JFileChooser jf = new JFileChooser();
            	jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            	jf.showOpenDialog(null);
            	
            	String path=null;
            	File f=null;
            	String name = null;
            	String fileContent = null;
            	f=jf.getSelectedFile();  
            	name=f.getName();
                path=f.getPath();
                System.out.println(path);
                try {
					FileInputStream fis=new FileInputStream(path);
					BufferedReader is = new BufferedReader(new InputStreamReader(fis));
					String a;
					
					try {
						while((a = is.readLine())!=null) {
							fileContent += a;
						}
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                //前面多了null
                fileContent =  fileContent.substring(4,fileContent.length());
                System.out.println("我是发送者"+"文件名字"+name+"---"+fileContent+"++++++++");
                FileModel fileModel= new  FileModel("sendFile",name,fileContent,username,chatObject);
                
            	jta.append("\n------------已传送文件-----------------------");
            	String json=JSON.toJSONString(fileModel);
            	System.out.println(json+"++++++++");
                  
	        	System.out.println(json);
            	
	    		os.println(json);
	    		os.flush();
            }
        });
		jp1.add(jtf);
		jp1.add(jb);
		jp1.add(jb2);
		jp1.add(jb3);
		jp1.add(jb4);
		jp1.add(jb5);
		
		
		this.add(jsp);
		this.add(jp1,BorderLayout.SOUTH);
		
		
		this.setTitle("我（"+username+"）正在与"+chatObject+"聊天中");
		this.setSize(700, 300);
		
		this.setIconImage(new ImageIcon("src/images/timg.jpg").getImage());
		
		this.setResizable(true);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.setVisible(false);

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	public String getChatObject() {
		return chatObject;
	}


	public void setChatObject(String chatObject) {
		this.chatObject = chatObject;
	}


	public JTextArea getJta() {
		return jta;
	}


	public void setJta(JTextArea jta) {
		this.jta = jta;
	}

}
