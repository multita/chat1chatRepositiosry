package client.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.alibaba.fastjson.JSON;

import client.model.FileModel;
import client.model.RelationModel;

public class FileInterface extends JFrame {
	
	private FileModel fileModel;
	private BufferedReader is;
	private PrintWriter os;
	private ChatInterface chatInterface;
	
	private JScrollPane jsp2=null;
	private JTextArea jta=null;
	private JScrollPane jsp=null;
	private JPanel jp1=null;
	private JButton jb=null;
	private JButton jb2=null;
	private FileInterface fileInterface=this;
	
	public FileInterface(FileModel fileModell,ChatInterface chatInterfacee,BufferedReader iis,PrintWriter oos){
		this.fileModel = fileModell;
		this.chatInterface = chatInterfacee;
		this.is = iis;
		this.os = oos;
		jta=new JTextArea(fileModell.getSender()+"������"+fileModell.getReceiver()+"�������ļ���"+fileModell.getFileName());
		jsp=new JScrollPane(jta);
		jp1=new JPanel();
		jb=new JButton("����");
		jb.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	chatInterface.setVisible(true);
	            	
	            	try {
						FileOutputStream fos=new FileOutputStream("C:\\Users\\Public\\Desktop\\"+fileModel.getFileName());
						PrintStream ps=new PrintStream(fos);
						ps.println(fileModel.getContent());
						ps.flush();
						new TipInterface("�Ѿ������ļ�");
	            	} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
	            		new TipInterface("�ļ�����ʧ��");
					}
	        		
					chatInterface.getJta().append("\n-----���յ��Է����ļ����Ѵ���C:\\Users\\Public\\Desktop------");;
					fileInterface.setVisible(false);
	            }
	        });
		jb2=new JButton("�ܾ�");
		jb2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	new TipInterface("�Ѿ��ܾ������ļ�");
	    		fileInterface.setVisible(false);
            }
        });
		jp1.add(jb);
		jp1.add(jb2);
		this.add(jsp);

		
		
		this.add(jp1,BorderLayout.SOUTH);
		
		
		this.setTitle("�ļ�����");
		this.setSize(300, 150);
		
		this.setIconImage(new ImageIcon("src/images/timg.jpg").getImage());
		
		this.setResizable(true);
		
		this.setLocationRelativeTo(null);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setVisible(true);
	}
}