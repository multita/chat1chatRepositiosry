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
		 // ���
        p1 = new JPanel();
         add(p1);

        // ��ǩ
        name = new JLabel("          �û���:");
        p1.add(name);

        // �ı���
        field = new JTextField(16);
        p1.add(field);

        // ��ǩ
        passwd1 = new JLabel("\n"+"     �������:");
        p1.add(passwd1);
        // ������
        pass1 = new JPasswordField(16);
        p1.add(pass1);
        // ��ǩ
        passwd2 = new JLabel("\n"+"�ٴ���������:");
        p1.add(passwd2);
        // ������
        pass2 = new JPasswordField(15);
        p1.add(pass2);

        // ��ͨ��ť
        button = new JButton("����ע��");
        p1.add(button);
        
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	System.out.println("����ע���¼�");
	        	String name = field.getText();
	        	String password1 = pass1.getText();
	        	String password2 = pass1.getText();
	        	if(name.equals("")) {
	        		new TipInterface("ע��������Ϊ��");
	        		return;
	        	}
	        	
	        	if(password1.equals("")||password2.equals("")) {
	        		new TipInterface("���벻��Ϊ��");
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
	        	new TipInterface("������������벻��ͬ");
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
