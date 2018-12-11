package client.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.alibaba.fastjson.JSON;

import client.model.LoginModel;
import client.model.RegisterModel;

public class RegisterInterface extends JFrame{
	
	private PrintWriter os;
	private JPanel p1;
	private JLabel name;
	private JTextField field;
	private JLabel passwd1;
	private JPasswordField pass1; 
	private JLabel passwd2;
	private JPasswordField pass2; 
	private JButton button;
	
	private   RegisterInterface registerInterface = this;
	public static void main (String args[]) {
		new RegisterInterface(null);
	}
	public RegisterInterface(PrintWriter oss) {
		
		this.os = oss;
		 // 面板
        p1 = new JPanel();
         add(p1);

        // 标签
        name = new JLabel("          用户名:");
        p1.add(name);

        // 文本域
        field = new JTextField(16);
        p1.add(field);

        // 标签
        passwd1 = new JLabel("\n"+"     输出密码:");
        p1.add(passwd1);
        // 密码域
        pass1 = new JPasswordField(16);
        p1.add(pass1);
        // 标签
        passwd2 = new JLabel("\n"+"再次输入密码:");
        p1.add(passwd2);
        // 密码域
        pass2 = new JPasswordField(15);
        p1.add(pass2);

        // 普通按钮
        button = new JButton("立即注册");
        p1.add(button);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	System.out.println("触发注册事件");
	        	String name = field.getText();
	        	String password1 = pass1.getText();
	        	String password2 = pass1.getText();
	        	if(name.equals("")) {
	        		new TipInterface("注册名不能为空");
	        		return;
	        	}
	        	
	        	if(password1.equals("")||password2.equals("")) {
	        		new TipInterface("密码不能为空");
	        		return;
	        	}
	        	if(pass1.getText().equals(pass2.getText())) {
	        		RegisterModel registerModel = new RegisterModel("register",name,password1);
		        	registerInterface.setVisible(false);
		        	String json=JSON.toJSONString(registerModel);
		    		os.println(json);
		    		os.flush();
		    		return;
	        	}
	        	new TipInterface("两次输入的密码不相同");
        		return;
	        	
            }
        });
        this.setTitle("Chat1chat");
		this.setSize(300,200);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon("src/images/timg.jpg").getImage());
		
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		this.setVisible(true);
	}
	
}
