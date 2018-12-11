package client.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.alibaba.fastjson.JSON;
import com.sun.prism.Image;

import client.model.RelationModel;
import client.model.UserModel;

public class AddFriendInterface extends JFrame{
	
	private UserModel user;
	private PrintWriter os;
	private JPanel 	jPanel = new JPanel();
	private JTextField jTextField=new JTextField();
	private JButton jButton= new JButton("发送好友申请");
	
	private AddFriendInterface addFriendInterface = this;
	public AddFriendInterface(UserModel userr, PrintWriter oss) {
		
		this.user = userr;
		this.os = oss;
		this.jTextField.setPreferredSize(new Dimension (260,32)); 
		this.jButton.setBounds(100, 100, 100, 40);
		this.jPanel.add(jTextField);
		this.jPanel.add(jButton);
		this.jPanel.setBounds(0, 0, 300, 140);
		add(jPanel);
		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		this.setIconImage(new ImageIcon("src/images/timg.jpg").getImage());
		this.setTitle("查找好友");
		this.setSize(300, 140);
		this.setVisible(true);

		this.setLocationRelativeTo(null);
		jButton.addActionListener(new ActionListener() {

	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	
	        	if(!user.isFriend(jTextField.getText())) {
	        		RelationModel relationModel= new RelationModel("addFriend", user.getUsername(),jTextField.getText());
		        	jTextField.setText("");
		        	String json=JSON.toJSONString(relationModel);
		        	System.out.println("客户端发送添加好友信息："+json); 
		        	os.println(json);
		    		os.flush();
		    		
		    		new TipInterface("已发送添加好友申请!");
	        	}else {
	        		jTextField.setText("");
	        		new TipInterface("对方已经是您的好友了!");
	        	}
	        	
	        	
	    		
	        }
	    });
	}
	
	public static void main(String args[]) {
		new AddFriendInterface(null,null);
	}
}
