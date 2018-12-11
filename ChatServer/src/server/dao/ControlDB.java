package server.dao;

import server.tool.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import server.model.HisMessageModel;
import server.model.MessageModel;

public class ControlDB {
	
		public static void register(String name, String password) {
			System.out.println("�������ݿ⣬׼��������û�");
			try {
				LinkDB.getStatement().executeUpdate("INSERT INTO userinfo VALUE ('"+name+"','"+password+"')");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}
		public static Boolean isexist(String username , String password) {
			try {
				if(LinkDB.getStatement().executeQuery("SELECT username , password FROM userinfo WHERE username='"+username+"'and password='"+ password+"'").next()) {
					return true;
				}else {
					return false;
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		public static Boolean isexist(String name) {
			try {
				if(LinkDB.getStatement().executeQuery("SELECT username FROM userinfo WHERE '"+name+"'=username").next()) {
					return true;
				}else {
					return false;
				}
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	    public static void addFriend(String username,String object) {
	    	System.out.println("�������ݿ⣬׼����Ӻ�������");
	    	try {
				 LinkDB.getStatement().executeUpdate("INSERT INTO relation VALUE ('"+username+"','"+object+"')");
				 LinkDB.getStatement().executeUpdate("INSERT INTO relation VALUE ('"+object+"','"+username+"')");
					 System.out.println("��Ӻ��Ѽ�¼�ɹ�");
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    public static void delectFriend(String username,String object) {
	    	System.out.println("�������ݿ⣬׼��ɾ����������");
	    	try {
	    		 LinkDB.getStatement().executeUpdate("DELETE FROM relation WHERE username='"+username+"'and friend='"+object+"'");
	    		 LinkDB.getStatement().executeUpdate("DELETE FROM relation WHERE username='"+object+"'and friend='"+username+"'");
				 System.out.println("ɾ�����Ѽ�¼�ɹ�");
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
//	    public static void delectMessage(MessageModel messageModel) {
//	    	String username = messageModel.getSender();
//	    	String object = messageModel.getReceiver();
//	    	String message = messageModel.getMessage();
//	    	String time =messageModel.getNowTime();
//	    	System.out.println("�������ݿ⣬׼��ɾ������");
//	    	try {
//	    		LinkDB.getStatement().executeUpdate("DELETE FROM message where sender='"+username+"' and receiver='"+object+"' and message='"+message+"' and time='"+time+"'");
//				 System.out.println("ɾ��message��¼�ɹ�");
//	    	} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	    }
	    public static void putMessage(MessageModel messageModel) {
	    	String username = messageModel.getSender();
	    	String object = messageModel.getReceiver();
	    	String message = messageModel.getMessage();
	    	String time =messageModel.getNowTime();
	    	System.out.println("�洢���ݵ����ݿ�");
	    	try {
	    		LinkDB.getStatement().executeUpdate("INSERT INTO message VALUE ('"+username+"','"+object+"','"+message+"','"+time+"',"+3+")");
				 System.out.println("�洢���ݵ����ݿ�ɹ�");
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    }
	    public static void delectHisMessage(MessageModel messageModel) {
	    	String sender = messageModel.getSender();
			String receiver = messageModel.getReceiver();
			
	    	try {
	    		LinkDB.getStatement().executeUpdate("UPDATE message SET flag = "+2+" WHERE sender = '"+sender+"'and receiver='"+receiver+"' and flag ="+3);
	    		LinkDB.getStatement().executeUpdate("UPDATE message SET flag = "+1+" WHERE sender = '"+receiver+"'and receiver='"+sender+"' and flag ="+3);
	    		
	    		LinkDB.getStatement().executeUpdate("UPDATE message SET flag = "+0+" WHERE sender = '"+sender+"'and receiver='"+receiver+"' and flag ="+1);
	    		LinkDB.getStatement().executeUpdate("UPDATE message SET flag = "+0+" WHERE sender = '"+receiver+"'and receiver='"+sender+"' and flag ="+2);
	    
	    		//LinkDB.getStatement().executeUpdate("DELETE FROM message where sender='"+sender+"' and receiver='"+receiver+"'");
				 System.out.println("ɾ��message��¼�ɹ�");
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    public static HisMessageModel getHisMessage(String username,String object) {
	    	
	    	
	    	System.out.println("�������ݿ���������¼");
	    	
	    	int count=0;
			try {
				ResultSet rs = LinkDB.getStatement().executeQuery("SELECT message,time FROM message WHERE '"+username+"'=sender and '"+object+"' = receiver and (flag =" + 1+" or flag ="+3+")");
				while(rs.next()){
		    		count++;
		    	}
				ResultSet rs3 = LinkDB.getStatement().executeQuery("SELECT message.time FROM message WHERE '"+object+"'=sender and '"+username+"' = receiver and (flag =" + 2+" or flag ="+3+")");
				while(rs3.next()){
		    		count++;
		    	}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				MessageModel[] messageModel= new MessageModel[count];
				count=0;
				ResultSet rs2 = LinkDB.getStatement().executeQuery("SELECT message,time,sender,receiver FROM message WHERE '"+username+"'=sender and '"+object+"' = receiver and (flag =" + 1+" or flag ="+3+") or '"+object+"'=sender and '"+username+"' = receiver and (flag =" + 2+" or flag ="+3+")");
				while(rs2.next()){
					messageModel[count++] = new MessageModel("message",rs2.getString("message"),rs2.getString("sender"),rs2.getString("receiver"),rs2.getString("time"));
					System.out.println(rs2.getString("message"));
				}
				return  new HisMessageModel("queryMessage",messageModel);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	return null;
	    }
		public static String[] getFriends(String username) {
			
			int count=0;
	    	try {
	    		System.out.println("�������ݿ����");
		    	ResultSet rs  = LinkDB.getStatement().executeQuery("SELECT friend FROM relation WHERE '"+username+"'=username");
		    	while(rs.next()){
		    		count++;
		    	}
		    	
	    	} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
			try {
				ResultSet rs2  = LinkDB.getStatement().executeQuery("SELECT friend FROM relation WHERE '"+username+"'=username");
		    	String[] result=new String[count];
		    	count=0;
		    	while(rs2.next()){
		    		result[count++] = rs2.getString("friend");
		    	
		    	}
		    	return result;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	System.out.println("���ҽ���");
	    	return null;
	    }
}    
	 
