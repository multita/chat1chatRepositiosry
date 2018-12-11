package client.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.alibaba.fastjson.JSON;

import client.model.MessageModel;
import client.model.RelationModel;

public class RelationInterface extends JFrame{
	
	private String username;
	private String chatObject;

	private BufferedReader is;
	private PrintWriter os;
	
	private JScrollPane jsp2=null;
	private JTextArea jta=null;
	private JScrollPane jsp=null;
	private JPanel jp1=null;
	private JButton jb=null;
	private JButton jb2=null;
	
	private RelationInterface relationitf=this;
	public JTextArea getJta() {
		return jta;
	}


	public void setJta(JTextArea jta) {
		this.jta = jta;
	}


	public RelationInterface(String cchatObject,String uusername,BufferedReader iis,PrintWriter oos)
	{
		this.chatObject=cchatObject;
    	this.username=uusername;
		this.is = iis;
		this.os = oos;
		
		
		jta=new JTextArea(uusername+"请求添加您（"+cchatObject+"）为好友！");
		jsp=new JScrollPane(jta);
		jp1=new JPanel();
		jb=new JButton("同意");
		jb.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	RelationModel relationModel= new RelationModel("acceptFriend",username,chatObject);
	            	String json=JSON.toJSONString(relationModel);
		        	
	            	os.println(json);
		    		os.flush();
	            	relationitf.setVisible(false);
	            }
	        });
		jb2=new JButton("拒绝");
		jb2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	RelationModel relationModel= new RelationModel("refuseFriend",username,chatObject);
            	String json=JSON.toJSONString(relationModel);
	        	
            	os.println(json);
	    		os.flush();
            	relationitf.setVisible(false);
            }
        });
		jp1.add(jb);
		jp1.add(jb2);
		this.add(jsp);

		
		
		this.add(jp1,BorderLayout.SOUTH);
		
		
		this.setTitle("好友申请");
		this.setSize(300, 150);
		
		this.setIconImage(new ImageIcon("src/images/timg.jpg").getImage());
		
		this.setResizable(true);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
	}
	
}
