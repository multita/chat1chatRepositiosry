package client.view;

import client.model.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
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


public class LoginInterface extends JFrame {
    
	private BufferedReader is;  //�ڼ����¼��ڲ�����
	private PrintWriter os;
	
	private JPanel p1;
	private JLabel name;
	private JTextField field;
	private JLabel passwd;
	private JPasswordField pass; 
	private JButton button;
	private JButton button2;
	private LoginInterface loginInterface = this;
    public LoginInterface(BufferedReader is,PrintWriter oos) {
    	this.is=is;
    	this.os=oos;
    	
        // ���
        p1 = new JPanel();
         add(p1);

        // ��ǩ
        name = new JLabel("�û���:");
        p1.add(name);

        // �ı���
        field = new JTextField(20);
        p1.add(field);

        // ��ǩ
        passwd = new JLabel("\n"+"����:");
        p1.add(passwd);
        // ������
        pass = new JPasswordField(21);
        p1.add(pass);

        // ��ͨ��ť
        button = new JButton("��¼");
        p1.add(button);
        
        //��Ӽ��̼���
        button.setMnemonic(KeyEvent.VK_ENTER);
       
        // ����������¼�
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	System.out.println("������¼�¼�");
	        	String name = field.getText();
	        	String password = pass.getText();
	        	LoginModel loginModel= new LoginModel(name,password);
	        	loginModel.setPassword(password);
	        	loginModel.setUsername(name);
	        	
	        	String json=JSON.toJSONString(loginModel);
	    		
	    		os.println(json);
	    		os.flush();
            }
        });
        button2 = new JButton("ע��");
        p1.add(button2);
        button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	new RegisterInterface(os);
            	loginInterface.setVisible(false);
            	//new TipInterface("ע�Ṧ����δ����");
            }
        });
        setLayout(new BorderLayout(10,5));
        add(p1);
       // add(p2);
        
        this.setTitle("Chat1chat");
		this.setSize(300,200);
		this.setLocationRelativeTo(null);
		this.setIconImage(new ImageIcon("src/images/timg.jpg").getImage());
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
