package client.view;

import client.interation.ClientInteration;
import client.model.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.alibaba.fastjson.JSON;
/*菜单项，卡片*/
public class MainInterface extends JFrame {
	UserModel user;
	List<ChatInterface> chatInterfaces;
	BufferedReader is;
	PrintWriter os;
	
	JPanel personInfoPanel = new JPanel();
	JLabel personInfoLabel;
	JPanel friendPanel = new JPanel();
	JTextField friendTextField = new JTextField();
	JPanel functionPanel = new JPanel();
	JList  friendJList = new JList();
	JTabbedPane middlePanel = new JTabbedPane();
	
	JButton jb ;
	
	String chatObjectt;
	
	MainInterface mainInterface=this;
	
	public static void main(String args[]) {
		FriendModel[] friendModel =new FriendModel[2];
		friendModel[0]=new FriendModel("hah",true);
		friendModel[1]=new FriendModel("fsah",true);
		MainInterface  frame = new MainInterface(new UserModel("","","",friendModel),null,null,null);
		
		
	}
	public ChatInterface findChatInterface(String object) {
		for(ChatInterface chatInterface : this.chatInterfaces) {
			if(chatInterface.getChatObject().equals(object))
				return chatInterface;
		}
		return null;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	private DefaultListModel dlm;
	
	public DefaultListModel getDefaultListModel() {
		return getDlm();
	}
    public MainInterface(UserModel uuser, List<ChatInterface> chatInterfacess,BufferedReader iis,PrintWriter oos) {
    	
    	this.user = uuser ;
    	//数组不能直接赋值;
    	//this.chatInterfaces = new ChatInterface[chatInterfacess.length];
    	this.chatInterfaces = chatInterfacess;
    	this.is=iis;
    	this.os=oos;
    	this.setLayout(null);
    	
    	
        this.personInfoLabel = new JLabel(""+user.getUsername());
        this.personInfoLabel.setIcon(new ImageIcon("src/images/goodboy.png"));
        this.personInfoLabel.setPreferredSize(new java.awt.Dimension(300, 98));
        this.friendTextField.setPreferredSize(new Dimension (300,32)); 
        this.friendTextField.setBackground(new Color(192,182,154));
       
        this.personInfoPanel.add(personInfoLabel);
        this.personInfoPanel.setBounds(0, 0, 300, 140);
        this.personInfoPanel.add(friendTextField);
        this.personInfoPanel.setBackground(new Color(211,202,174));
        add(personInfoPanel);

        //数据
        this.setDlm(new DefaultListModel());
        CardLayout cl = new CardLayout();
        String[] data=new String[user.getFriends().length];
        for(int i=0;i<user.getFriends().length;i++) {
        	String s = "离线";
        	if(user.getFriends()[i].getIsOnLine()) s="在线"; 
        	data[i]=user.getFriends()[i].getName()+"["+s+"]";
        	getDlm().addElement(data[i]);
        }
        this.friendJList.setModel(getDlm());
        this.friendJList.setBackground(new Color(244,241,233));
        this.friendJList.setPreferredSize(new java.awt.Dimension(300, 450));
        this.friendPanel.add(friendJList);
        this.friendPanel.setBounds(0, 140, 300, 430);
        add(this.friendPanel);
        
//        final JTabbedPane jTabbedPane = new JTabbedPane();
//        getContentPane().add(jTabbedPane,BorderLayout.CENTER);
//        jTabbedPane.addTab("121",this.friendPanel);

        
        
        JButton jb = new JButton("添加好友");
        jb.setBackground(new Color(251,250,246));
        this.functionPanel.add(jb);
        this.functionPanel.setBackground(new Color(251,250,246));
        this.functionPanel.setBounds(0, 570, 300, 100);
        add(this.functionPanel);

        this.setIconImage(new ImageIcon("src/images/timg.jpg").getImage());
    	  this.setTitle("Chat1chat");
		this.setSize(300, 650);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UIManager.put(this,new Font("微软雅黑", Font.PLAIN, 14));
		this.setVisible(true);
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
		friendJList.addMouseListener(new MouseAdapter() {
//			 	@Override	
//			  	public void mousePressed(MouseEvent e) {
//			 		JList myList = (JList) e.getSource();
//                	int index1 = myList.locationToIndex(e.getPoint());  
//                	myList.setSelectedIndex(index1); 
//            	    int index = myList.getSelectedIndex();    //已选项的下标
//                    Object obj = myList.getModel().getElementAt(index);  //取出数据
//                    String chatObject = obj.toString().substring(0,obj.toString().length() - 4);
//                    chatObjectt = chatObject;
//				}
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            	  
	                if(e.getClickCount() == 2){
	                    JList myList = (JList) e.getSource();
	                    int index = myList.getSelectedIndex();    //已选项的下标
	                    Object obj = myList.getModel().getElementAt(index);  //取出数据
	                    String chatObject = obj.toString().substring(0,obj.toString().length() - 4);
	                    chatObjectt = chatObject;
	                    System.err.println("这是主界面，一共有"+chatInterfaces.size()+"个界面");
	                    mainInterface.findChatInterface(chatObject).setVisible(true);
	                }
	                if(e.isMetaDown()){//检测鼠标右键单击
	                	JList myList = (JList) e.getSource();
	                	int index1 = myList.locationToIndex(e.getPoint());  
	                	myList.setSelectedIndex(index1); 
	                    
                	    int index = myList.getSelectedIndex();    //已选项的下标
	                    Object obj = myList.getModel().getElementAt(index);  //取出数据
	                    String chatObject = obj.toString().substring(0,obj.toString().length() - 4);
	                    chatObjectt = chatObject;
	                	JPopupMenu popMenu = new JPopupMenu();
	                	JMenuItem jMenuItemnew1 = new JMenuItem("删除好友");
	                	popMenu.add(jMenuItemnew1);
						popMenu.add(new JMenuItem("抛媚眼"));
						popMenu.add(new JMenuItem("丢西瓜"));
						popMenu.show(e.getComponent(), e.getX(), e.getY());
					    jMenuItemnew1.addActionListener(new ActionListener() {
						
							@Override
							public void actionPerformed(ActionEvent a) {
								// TODO Auto-generated method stub
								System.out.println("6++++++++");
								String object = chatObjectt;
			                  
			                    RelationModel relationModel= new RelationModel("delectFriend",user.getUsername(),object);
			                    getDlm().removeElement(object+"[在线]");
			                	getDlm().removeElement(object+"[离线]");
			                	String json=JSON.toJSONString(relationModel);
			    	        	System.out.println(json+"---------");
			                	
			    	    		os.println(json);
			    	    		os.flush();
							}
						});
	                }
	            }
	      });
		jb.addActionListener(new ActionListener() {

	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	
	            	System.out.println("添加好友");
	            	new AddFriendInterface(user,os);
	
	            }
	        });
      

    }
	public DefaultListModel getDlm() {
		return dlm;
	}
	public void setDlm(DefaultListModel dlm) {
		this.dlm = dlm;
	}

}
