package client.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.alibaba.fastjson.JSON;

import client.model.RelationModel;

public class TipInterface extends JFrame{
		
		private JScrollPane jsp2=null;
		private JTextArea jta=null;
		private JScrollPane jsp=null;
		private JPanel jp1=null;
		private JButton jb=null;
		private JButton jb2=null;
		
		private TipInterface relationitf=this;
		public JTextArea getJta() {
			return jta;
		}


		public void setJta(JTextArea jta) {
			this.jta = jta;
		}

		public static  void main(String args[]) {
			TipInterface tip = new TipInterface("ooo ");
		}
		public TipInterface(String tipContent,int e) {
			
			if(tipContent.equals("mistakeAccount")) {
				tipContent = "您的账号不正确";
			}
			if(tipContent.equals("mistakePassword")){
				tipContent = "您的密码不正确";
			}
			jta=new JTextArea("-----"+tipContent+"-----");
			jsp=new JScrollPane(jta);
			
			jp1=new JPanel();
			jb=new JButton("确定");
			jb.addActionListener(new ActionListener() {

		            @Override
		            public void actionPerformed(ActionEvent e) {
		            
		            	relationitf.setVisible(false);
		            	System.exit(1);
		            }
		        });
			jp1.add(jb);
			
			this.add(jsp);
			this.add(jp1,BorderLayout.SOUTH);
			
			
			this.setTitle("贴心小提示");
			this.setSize(300, 150);
			
			this.setIconImage(new ImageIcon("src/images/timg.jpg").getImage());
			
			this.setResizable(true);
			
			this.setLocationRelativeTo(null);
			
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			this.setVisible(true);
		}
		public TipInterface(String tipContent)
		{
	
			if(tipContent.equals("mistakeAccount")) {
				tipContent = "您的账号不正确";
			}
			if(tipContent.equals("mistakePassword")){
				tipContent = "您的密码不正确";
			}
			jta=new JTextArea("-----"+tipContent+"-----");
			jsp=new JScrollPane(jta);
			
			jp1=new JPanel();
			jb=new JButton("确定");
			jb.addActionListener(new ActionListener() {

		            @Override
		            public void actionPerformed(ActionEvent e) {
		            
		            	relationitf.setVisible(false);
		            }
		        });
			jp1.add(jb);
			
			this.add(jsp);
			this.add(jp1,BorderLayout.SOUTH);
			
			
			this.setTitle("贴心小提示");
			this.setSize(300, 150);
			
			this.setIconImage(new ImageIcon("src/images/timg.jpg").getImage());
			
			this.setResizable(true);
			
			this.setLocationRelativeTo(null);
			
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			this.setVisible(true);
		}
		
	}

